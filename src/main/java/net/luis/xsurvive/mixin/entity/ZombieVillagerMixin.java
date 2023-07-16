package net.luis.xsurvive.mixin.entity;

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
 * @author Luis-st
 *
 */

@Mixin(ZombieVillager.class)
public abstract class ZombieVillagerMixin extends Zombie {
	
	private ZombieVillagerMixin(EntityType<? extends Zombie> entityType, Level level) {
		super(entityType, level);
	}
	
	@Shadow
	protected abstract void startConverting(UUID uuid, int time);
	
	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	public void mobInteract(@NotNull Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callback) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
			if (this.hasEffect(MobEffects.WEAKNESS)) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
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
