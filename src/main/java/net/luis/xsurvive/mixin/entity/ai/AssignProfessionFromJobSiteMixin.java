/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.mixin.entity.ai;

import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.ai.behavior.AssignProfessionFromJobSite;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
@Mixin(AssignProfessionFromJobSite.class)
public abstract class AssignProfessionFromJobSiteMixin {
	
	@Inject(method = "create", at = @At("HEAD"), cancellable = true)
	private static void create(@NotNull CallbackInfoReturnable<BehaviorControl<Villager>> callback) {
		callback.setReturnValue(
			BehaviorBuilder.create((builder) -> builder.group(builder.present(MemoryModuleType.POTENTIAL_JOB_SITE), builder.registered(MemoryModuleType.JOB_SITE)).apply(builder, (memory, accessor) -> (level, villager, seed) -> {
				GlobalPos pos = builder.get(memory);
				if (!pos.pos().closerToCenterThan(villager.position(), 2.0) && !villager.assignProfessionWhenSpawned()) {
					return false;
				} else {
					memory.erase();
					accessor.set(pos);
					level.broadcastEntityEvent(villager, (byte) 14);
					if (villager.getVillagerData().getProfession() == VillagerProfession.NONE) {
						if (VillagerProvider.get(villager).getResetCount() > 7) {
							villager.setVillagerData(villager.getVillagerData().setProfession(VillagerProfession.NITWIT));
							villager.refreshBrain(level);
						} else {
							MinecraftServer minecraftserver = level.getServer();
							Optional.ofNullable(minecraftserver.getLevel(pos.dimension())).flatMap((serverLevel) -> serverLevel.getPoiManager().getType(pos.pos())).flatMap((poi) -> {
								return ForgeRegistries.VILLAGER_PROFESSIONS.getValues().stream().filter((profession) -> profession.heldJobSite().test(poi)).findFirst();
							}).ifPresent((profession) -> {
								villager.setVillagerData(villager.getVillagerData().setProfession(profession));
								villager.refreshBrain(level);
							});
						}
					}
					return true;
				}
			})));
	}
}
