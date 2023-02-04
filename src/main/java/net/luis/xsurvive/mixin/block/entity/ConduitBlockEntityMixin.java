package net.luis.xsurvive.mixin.block.entity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ConduitBlockEntity.class)
public abstract class ConduitBlockEntityMixin {
	
	@Shadow
	private static LivingEntity findDestroyTarget(Level level, BlockPos pos, UUID uuid) {
		return null;
	}
	
	@Shadow
	private static AABB getDestroyRangeAABB(BlockPos pos) {
		return null;
	}
	
	@Inject(method = "updateHunting", at = @At("HEAD"), cancellable = true)
	private static void updateHunting(ConduitBlockEntity blockEntity, List<BlockPos> shapeBlocks, CallbackInfo callback) {
		blockEntity.setHunting(shapeBlocks.size() >= 30);
		callback.cancel();
	}
	
	@Inject(method = "updateDestroyTarget", at = @At("HEAD"), cancellable = true)
	private static void updateDestroyTarget(Level level, BlockPos pos, BlockState state, List<BlockPos> shapeBlocks, ConduitBlockEntity blockEntity, CallbackInfo callback) {
		LivingEntity destroyTarget = blockEntity.destroyTarget;
		if (30 > shapeBlocks.size()) {
			blockEntity.destroyTarget = null;
		} else if (blockEntity.destroyTarget == null && blockEntity.destroyTargetUUID != null) {
			blockEntity.destroyTarget = findDestroyTarget(level, pos, blockEntity.destroyTargetUUID);
			blockEntity.destroyTargetUUID = null;
		} else if (blockEntity.destroyTarget == null) {
			List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, Objects.requireNonNull(getDestroyRangeAABB(pos)), (entity) -> {
				return entity instanceof Enemy && entity.isInWaterOrRain();
			});
			if (!entities.isEmpty()) {
				blockEntity.destroyTarget = entities.get(level.random.nextInt(entities.size()));
			}
		} else if (!blockEntity.destroyTarget.isAlive() || !pos.closerThan(blockEntity.destroyTarget.blockPosition(), 8.0D)) {
			blockEntity.destroyTarget = null;
		}
		
		if (blockEntity.destroyTarget != null) {
			level.playSound(null, blockEntity.destroyTarget.getX(), blockEntity.destroyTarget.getY(), blockEntity.destroyTarget.getZ(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.BLOCKS, 1.0F, 1.0F);
			blockEntity.destroyTarget.hurt(DamageSource.MAGIC, shapeBlocks.size() >= 42 ? 8.0F : 4.0F);
		}
		if (destroyTarget != blockEntity.destroyTarget) {
			level.sendBlockUpdated(pos, state, state, 2);
		}
		callback.cancel();
	}
	
	@Inject(method = "getDestroyRangeAABB", at = @At("HEAD"), cancellable = true)
	private static void getDestroyRangeAABB(BlockPos pos, CallbackInfoReturnable<AABB> callback) {
		callback.setReturnValue(new AABB(pos).inflate(24.0));
	}
	
}
