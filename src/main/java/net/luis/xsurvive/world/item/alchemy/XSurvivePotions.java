package net.luis.xsurvive.world.item.alchemy;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSurviveMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurvivePotions {
	
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, XSurvive.MOD_ID);
	
	
	public static final RegistryObject<Potion> FORST = POTIONS.register("frost", () -> {
		return new Potion(new MobEffectInstance(XSurviveMobEffects.FROST.get(), 3600));
	});
	public static final RegistryObject<Potion> LONG_FORST = POTIONS.register("long_frost", () -> {
		return new Potion(new MobEffectInstance(XSurviveMobEffects.FROST.get(), 9600));
	});
	public static final RegistryObject<Potion> STRONG_FORST = POTIONS.register("strong_frost", () -> {
		return new Potion(new MobEffectInstance(XSurviveMobEffects.FROST.get(), 1800, 1));
	});
	public static final RegistryObject<Potion> WITHER = POTIONS.register("wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 600));
	});
	public static final RegistryObject<Potion> LONG_WITHER = POTIONS.register("long_wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 1200));
	});
	public static final RegistryObject<Potion> STRONG_WITHER = POTIONS.register("strong_wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 400, 1));
	});
	public static final RegistryObject<Potion> DIG_SPEED = POTIONS.register("haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600));
	});
	public static final RegistryObject<Potion> LONG_DIG_SPEED = POTIONS.register("long_haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 9600));
	});
	public static final RegistryObject<Potion> STRONG_DIG_SPEED = POTIONS.register("strong_haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 1));
	});
	
}
