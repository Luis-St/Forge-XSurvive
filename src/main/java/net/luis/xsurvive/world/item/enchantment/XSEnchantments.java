package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSEnchantments {
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, XSurvive.MOD_ID);
	
	private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[] {
			EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	};
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
	public static final RegistryObject<ExperienceEnchantment> EXPERIENCE = ENCHANTMENTS.register("experience", () -> {
		return new ExperienceEnchantment(Rarity.UNCOMMON, XSEnchantmentCategory.TOOLS, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<SmeltingEnchantment> SMELTING = ENCHANTMENTS.register("smelting", () -> {
		return new SmeltingEnchantment(Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<BreakingCurseEnchantment> CURSE_OF_BREAKING = ENCHANTMENTS.register("curse_of_breaking", () -> {
		return new BreakingCurseEnchantment(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.values());
	});
	public static final RegistryObject<HarmingCurseEnchantment> CURSE_OF_HARMING = ENCHANTMENTS.register("curse_of_harming", () -> {
		return new HarmingCurseEnchantment(Rarity.VERY_RARE, XSEnchantmentCategory.WEAPONS, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<VoidWalkerEnchantment> VOID_WALKER = ENCHANTMENTS.register("void_walker", () -> {
		return new VoidWalkerEnchantment(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.FEET);
	});
	public static final RegistryObject<GrowthEnchantment> GROWTH = ENCHANTMENTS.register("growth", () -> { 
		return new GrowthEnchantment(Rarity.COMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS);
	});
	public static final RegistryObject<BlastingEnchantment> BLASTING = ENCHANTMENTS.register("blasting", () -> {
		return new BlastingEnchantment(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<ThunderboltEnchantment> THUNDERBOLT = ENCHANTMENTS.register("thunderbolt", () -> { 
		return new ThunderboltEnchantment(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<VoidProtectionEnchantment> VOID_PROTECTION = ENCHANTMENTS.register("void_protection", () -> { 
		return new VoidProtectionEnchantment(Rarity.VERY_RARE, XSEnchantmentCategory.ELYTRA, EquipmentSlot.CHEST);
	});
	public static final RegistryObject<HarvestingEnchantment> HARVESTING = ENCHANTMENTS.register("harvesting", () -> { 
		return new HarvestingEnchantment(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<ReplantingEnchantment> REPLANTING = ENCHANTMENTS.register("replanting", () -> { 
		return new ReplantingEnchantment(Rarity.RARE, XSEnchantmentCategory.HOE, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<AspectOfTheEndEnchantment> ASPECT_OF_THE_END = ENCHANTMENTS.register("aspect_of_the_end", () -> { 
		return new AspectOfTheEndEnchantment(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
	});
	public static final RegistryObject<ExplosionEnchantment> EXPLOSION = ENCHANTMENTS.register("explosion", () -> { 
		return new ExplosionEnchantment(Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND);
	});
	
}
