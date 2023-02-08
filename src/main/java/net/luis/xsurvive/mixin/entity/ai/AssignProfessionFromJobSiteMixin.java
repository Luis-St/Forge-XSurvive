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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(AssignProfessionFromJobSite.class)
public abstract class AssignProfessionFromJobSiteMixin {
	
	@Inject(method = "create", at = @At(value = "HEAD"), cancellable = true)
	private static void create(CallbackInfoReturnable<BehaviorControl<Villager>> callback) {
		callback.setReturnValue(
				BehaviorBuilder.create((builder) -> builder.group(builder.present(MemoryModuleType.POTENTIAL_JOB_SITE), builder.registered(MemoryModuleType.JOB_SITE)).apply(builder, (memory, accessor) -> (level, villager, seed) -> {
					GlobalPos pos = builder.get(memory);
					if (!pos.pos().closerToCenterThan(villager.position(), 2.0D) && !villager.assignProfessionWhenSpawned()) {
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
