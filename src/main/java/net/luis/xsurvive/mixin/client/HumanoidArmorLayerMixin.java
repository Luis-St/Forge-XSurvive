package net.luis.xsurvive.mixin.client;

import net.luis.xsurvive.client.renderer.item.GlintColorHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("unused")
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
	
	@Inject(method = "getArmorModelHook", at = @At("HEAD"), remap = false)
	private void getArmorModelHook(T entity, ItemStack stack, EquipmentSlot slot, A model, CallbackInfoReturnable<A> callback) {
		GlintColorHandler.setStack(stack);
	}
}
