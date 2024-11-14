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

package net.luis.xsurvive.mixin.item;

import net.luis.xsurvive.world.entity.projectile.CursedEyeOfEnder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(EnderEyeItem.class)
@SuppressWarnings("ConstantValue")
public abstract class EnderEyeItemMixin extends Item {
	
	//region Mixin
	private EnderEyeItemMixin(@NotNull Properties properties) {
		super(properties);
	}
	//endregion
	
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(Level level, @NotNull Player player, @NotNull InteractionHand hand, @NotNull CallbackInfoReturnable<InteractionResult> callback) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
		if (hitResult instanceof BlockHitResult blockHitResult && level.getBlockState(blockHitResult.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
			callback.setReturnValue(InteractionResult.PASS);
		} else {
			player.startUsingItem(hand);
			if (level instanceof ServerLevel serverLevel) {
				BlockPos pos = serverLevel.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, player.blockPosition(), 100, false);
				if (pos != null) {
					Vec3 position;
					if (serverLevel.random.nextInt(4) >= 2) {
						position = this.summonEyeOfEnder(serverLevel, player, pos, stack);
					} else {
						position = this.summonCursedEyeOfEnder(serverLevel, player, pos, stack);
						UseCooldown cooldown = stack.get(DataComponents.USE_COOLDOWN);
						if (cooldown != null) {
							cooldown.apply(stack, player);
						} else {
							player.getCooldowns().addCooldown(stack, 1200);
						}
					}
					level.gameEvent(GameEvent.PROJECTILE_SHOOT, position, GameEvent.Context.of(player));
					if (player instanceof ServerPlayer serverPlayer) {
						CriteriaTriggers.USED_ENDER_EYE.trigger(serverPlayer, pos);
					}
					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
					level.levelEvent(null, 1003, player.blockPosition(), 0);
					if (!player.getAbilities().instabuild) {
						stack.shrink(1);
					}
					player.awardStat(Stats.ITEM_USED.get(this));
					player.swing(hand, true);
					callback.setReturnValue(InteractionResult.SUCCESS);
				}
			}
			callback.setReturnValue(InteractionResult.CONSUME);
		}
	}
	
	private @NotNull Vec3 summonEyeOfEnder(@NotNull ServerLevel serverLevel, @NotNull Player player, @NotNull BlockPos targetPos, @NotNull ItemStack stack) {
		EyeOfEnder eyeOfEnder = new EyeOfEnder(serverLevel, player.getX(), player.getY(0.5D), player.getZ());
		eyeOfEnder.setItem(stack);
		eyeOfEnder.signalTo(targetPos);
		serverLevel.addFreshEntity(eyeOfEnder);
		return eyeOfEnder.position();
	}
	
	private @NotNull Vec3 summonCursedEyeOfEnder(@NotNull ServerLevel serverLevel, @NotNull Player player, @NotNull BlockPos targetPos, @NotNull ItemStack stack) {
		CursedEyeOfEnder eyeOfEnder = new CursedEyeOfEnder(serverLevel, player.getX(), player.getY(0.5D), player.getZ());
		eyeOfEnder.setItem(stack);
		eyeOfEnder.signalTo(targetPos);
		serverLevel.addFreshEntity(eyeOfEnder);
		return eyeOfEnder.position();
	}
}
