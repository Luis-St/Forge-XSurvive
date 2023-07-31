package net.luis.xsurvive.world.effect;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSMobEffects {
	
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, XSurvive.MOD_ID);
	
	public static final RegistryObject<MobEffect> FROST = MOB_EFFECTS.register("frost", () -> {
		return new FrostMobEffect(MobEffectCategory.HARMFUL, 0xC8FFFF).addAttributeModifier(Attributes.MOVEMENT_SPEED, "98F51A36-1D86-4545-88B6-988CA1063FAD", -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
	});
}
