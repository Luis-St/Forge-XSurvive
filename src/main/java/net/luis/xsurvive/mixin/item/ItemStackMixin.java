package net.luis.xsurvive.mixin.item;

import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ItemStack.class)
@SuppressWarnings("DataFlowIssue")
public abstract class ItemStackMixin {
	
	//region Mixin
	@Shadow
	public abstract <T extends LivingEntity> void hurtAndBreak(int damage, T livingEntity, Consumer<T> consumer);
	//endregion
	
	@Inject(method = "inventoryTick", at = @At("TAIL"))
	public void inventoryTick(Level level, Entity entity, int slot, boolean selected, CallbackInfo callback) {
		if (entity instanceof LivingEntity livingEntity) {
			int breakingCurse = ((ItemStack) (Object) this).getEnchantmentLevel(XSEnchantments.CURSE_OF_BREAKING.get());
			if (breakingCurse > 0 && level.getGameTime() % 100 == 0) {
				this.hurtAndBreak(breakingCurse * 2, livingEntity, (e) -> {});
			}
		}
	}
}
