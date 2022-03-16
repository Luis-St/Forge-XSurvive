package net.luis.xsurvive.init;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.common.enchantment.EnderSlayerEnchantment;
import net.luis.xsurvive.common.enchantment.FrostAspectEnchantment;
import net.luis.xsurvive.common.enchantment.MultiDropEnchantment;
import net.luis.xsurvive.common.enchantment.PoisonAspectEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveEnchantments {
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, XSurvive.MOD_ID);
	
	
	public static final RegistryObject<MultiDropEnchantment> MULTI_DROP = ENCHANTMENTS.register("multi_drop", () -> {
		return new MultiDropEnchantment(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<EnderSlayerEnchantment> ENDER_SLAYER = ENCHANTMENTS.register("ender_slayer", () -> {
		return new EnderSlayerEnchantment(Rarity.UNCOMMON, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<FrostAspectEnchantment> FROST_ASPECT = ENCHANTMENTS.register("frost_aspect", () -> {
		return new FrostAspectEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<PoisonAspectEnchantment> POISON_ASPECT = ENCHANTMENTS.register("poison_aspect", () -> {
		return new PoisonAspectEnchantment(Rarity.RARE, EquipmentSlot.MAINHAND);
	});
	
}
