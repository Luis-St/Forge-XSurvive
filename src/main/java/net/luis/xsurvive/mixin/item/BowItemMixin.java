package net.luis.xsurvive.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.entity.projectile.IArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(BowItem.class)
public abstract class BowItemMixin extends Item {

	private BowItemMixin(Properties properties) {
		super(properties);
	}
	
	@Inject(method = "customArrow", at = @At("TAIL"), remap = false)
	public void customArrow(AbstractArrow abstractArrow, CallbackInfoReturnable<AbstractArrow> callback) {
		if (abstractArrow instanceof IArrow arrow && abstractArrow.getOwner() instanceof LivingEntity entity) {
			int explosion = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPLOSION.get(), entity);
			if (explosion > 0) {
				arrow.setExplosionLevel(explosion);
			}
		}
	}
	
}
