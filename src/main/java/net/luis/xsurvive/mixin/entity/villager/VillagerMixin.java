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

package net.luis.xsurvive.mixin.entity.villager;

import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Villager.class)
@SuppressWarnings("DataFlowIssue")
public abstract class VillagerMixin extends AbstractVillager {
	
	//region Mixin
	private VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}
	
	@Shadow
	public abstract VillagerData getVillagerData();
	
	@Shadow
	public abstract void setVillagerData(VillagerData villagerData);
	//endregion
	
	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	public void mobInteract(@NotNull Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callback) {
		ItemStack stack = player.getItemInHand(hand);
		if (this.getVillagerData().getProfession() == VillagerProfession.NITWIT && stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
			this.setVillagerData(this.getVillagerData().setProfession(VillagerProfession.NONE));
			if (this.level() instanceof ServerLevel) {
				VillagerProvider.get((Villager) (Object) this).resetResetCount();
			}
			callback.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
