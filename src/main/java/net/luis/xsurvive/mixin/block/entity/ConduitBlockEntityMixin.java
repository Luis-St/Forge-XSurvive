package net.luis.xsurvive.mixin.block.entity;

import net.luis.xsurvive.config.configs.blocks.ConduitConfig;
import net.luis.xsurvive.config.scripts.blocks.ConduitScript;
import net.luis.xsurvive.config.util.XSConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ConduitBlockEntity.class)
@SuppressWarnings({"DataFlowIssue", "ReturnOfNull"})
public abstract class ConduitBlockEntityMixin {
	
	//region Mixin
	@Shadow
	private static LivingEntity findDestroyTarget(Level level, BlockPos pos, UUID uuid) {
		return null;
	}
	
	@Shadow
	private static AABB getDestroyRangeAABB(BlockPos pos) {
		return null;
	}
	//endregion
	
	@Inject(method = "updateHunting", at = @At("HEAD"), cancellable = true)
	private static void updateHunting(@NotNull ConduitBlockEntity blockEntity, @NotNull List<BlockPos> shapeBlocks, @NotNull CallbackInfo callback) {
		blockEntity.setHunting(shapeBlocks.size() >= XSConfigManager.CONDUIT_CONFIG.get().requiredBlocks().defence());
		callback.cancel();
	}
	
	@Inject(method = "applyEffects", at = @At("HEAD"), cancellable = true)
	private static void applyEffects(@NotNull Level level, @NotNull BlockPos pos, @NotNull List<BlockPos> shapeBlocks, CallbackInfo callback) {
		int range = ConduitScript.getRange(shapeBlocks.size());
		AABB aabb = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(range).setMinY(level.getMinBuildHeight()).setMaxY(level.getMaxBuildHeight());
		for (Player player : level.getEntitiesOfClass(Player.class, aabb)) {
			if (pos.closerThan(player.blockPosition(), range) && player.isInWaterOrRain()) {
				player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260 * (shapeBlocks.size() >= XSConfigManager.CONDUIT_CONFIG.get().requiredBlocks().maxOut() ? 2 : 1), 0, true, true));
			}
		}
		callback.cancel();
	}
	
	@Inject(method = "updateDestroyTarget", at = @At("HEAD"), cancellable = true)
	private static void updateDestroyTarget(Level level, BlockPos pos, BlockState state, @NotNull List<BlockPos> shapeBlocks, @NotNull ConduitBlockEntity blockEntity, CallbackInfo callback) {
		LivingEntity destroyTarget = blockEntity.destroyTarget;
		if (30 > shapeBlocks.size()) {
			blockEntity.destroyTarget = null;
		} else if (blockEntity.destroyTarget == null && blockEntity.destroyTargetUUID != null) {
			blockEntity.destroyTarget = findDestroyTarget(level, pos, blockEntity.destroyTargetUUID);
			blockEntity.destroyTargetUUID = null;
		} else if (blockEntity.destroyTarget == null) {
			List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB(pos), (entity) -> entity instanceof Enemy && entity.isInWaterOrRain());
			if (!entities.isEmpty()) {
				blockEntity.destroyTarget = entities.get(level.random.nextInt(entities.size()));
			}
		} else if (!blockEntity.destroyTarget.isAlive()) {
			blockEntity.destroyTarget = null;
		}
		if (blockEntity.destroyTarget != null) {
			ConduitConfig config = XSConfigManager.CONDUIT_CONFIG.get();
			level.playSound(null, blockEntity.destroyTarget.getX(), blockEntity.destroyTarget.getY(), blockEntity.destroyTarget.getZ(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.BLOCKS, 1.0F, 1.0F);
			blockEntity.destroyTarget.hurt(level.damageSources().magic(), shapeBlocks.size() >= config.requiredBlocks().maxOut() ? config.damage().maxLevel() : config.damage().normal());
		}
		if (destroyTarget != blockEntity.destroyTarget) {
			level.sendBlockUpdated(pos, state, state, 2);
		}
		callback.cancel();
	}
	
	@Inject(method = "getDestroyRangeAABB", at = @At("HEAD"), cancellable = true)
	private static void getDestroyRangeAABB(BlockPos pos, @NotNull CallbackInfoReturnable<AABB> callback) {
		callback.setReturnValue(new AABB(pos).inflate(XSConfigManager.CONDUIT_CONFIG.get().attackRange()));
	}
}
