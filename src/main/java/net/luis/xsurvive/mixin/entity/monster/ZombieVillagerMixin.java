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

package net.luis.xsurvive.mixin.entity.monster;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
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

import java.util.UUID;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ZombieVillager.class)
@SuppressWarnings("UnnecessarySuperQualifier")
public abstract class ZombieVillagerMixin extends Zombie {
	
	//region Mixin
	private ZombieVillagerMixin(@NotNull EntityType<? extends Zombie> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	
	@Shadow
	protected abstract void startConverting(@NotNull UUID uuid, int time);
	//endregion
	
	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	public void mobInteract(@NotNull Player player, @NotNull InteractionHand hand, @NotNull CallbackInfoReturnable<InteractionResult> callback) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
			if (this.hasEffect(MobEffects.WEAKNESS)) {
				stack.consume(1, player);
				if (!this.level().isClientSide) {
					this.startConverting(player.getUUID(), this.random.nextInt(2401) + 3600);
				}
				callback.setReturnValue(InteractionResult.SUCCESS);
			} else {
				callback.setReturnValue(InteractionResult.CONSUME);
			}
		} else {
			callback.setReturnValue(super.mobInteract(player, hand));
		}
	}
}
