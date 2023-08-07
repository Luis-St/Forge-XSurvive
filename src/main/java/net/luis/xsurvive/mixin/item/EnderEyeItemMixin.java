package net.luis.xsurvive.mixin.item;

import net.luis.xsurvive.world.entity.projectile.CursedEyeOfEnder;
import net.luis.xsurvive.world.item.XSItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
public abstract class EnderEyeItemMixin extends Item {
	
	//region Mixin
	private EnderEyeItemMixin(Properties properties) {
		super(properties);
	}
	//endregion
	
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(Level level, @NotNull Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callback) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
		if (hitResult instanceof BlockHitResult blockHitResult && level.getBlockState(blockHitResult.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
			callback.setReturnValue(InteractionResultHolder.pass(stack));
		} else {
			player.startUsingItem(hand);
			if (level instanceof ServerLevel serverLevel) {
				BlockPos pos = serverLevel.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, player.blockPosition(), 100, false);
				if (pos != null) {
					EyeOfEnder eyeOfEnder;
					if (level.random.nextInt(5) >= 2) {
						eyeOfEnder = new EyeOfEnder(level, player.getX(), player.getY(0.5D), player.getZ());
						eyeOfEnder.setItem(stack);
					} else {
						eyeOfEnder = new CursedEyeOfEnder(level, player.getX(), player.getY(0.5D), player.getZ());
						eyeOfEnder.setItem(new ItemStack(XSItems.CURSED_ENDER_EYE.get()));
					}
					eyeOfEnder.signalTo(pos);
					level.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeOfEnder.position(), GameEvent.Context.of(player));
					level.addFreshEntity(eyeOfEnder);
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
					callback.setReturnValue(InteractionResultHolder.success(stack));
				}
			}
			callback.setReturnValue(InteractionResultHolder.consume(stack));
		}
	}
}
