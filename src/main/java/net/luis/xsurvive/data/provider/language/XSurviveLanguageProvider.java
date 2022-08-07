package net.luis.xsurvive.data.provider.language;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSurviveMobEffects;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.item.alchemy.XSurvivePotions;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.luis.xsurvive.world.level.entity.npc.XSurviveVillagerProfessions;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveLanguageProvider extends LanguageProvider {

	public XSurviveLanguageProvider(DataGenerator generator) {
		super(generator, XSurvive.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		for (Enchantment enchantment : XSurviveEnchantments.ENCHANTMENTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(enchantment, getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)));
		}
		for (Item item : XSurviveItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (Item item : XSurviveBlocks.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (MobEffect mobEffect : XSurviveMobEffects.MOB_EFFECTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(mobEffect, getName(ForgeRegistries.MOB_EFFECTS.getKey(mobEffect)));
		}
		for (Potion potion : XSurvivePotions.POTIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(potion);
		}
		for (VillagerProfession profession : XSurviveVillagerProfessions.VILLAGER_PROFESSIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			ResourceLocation location = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
			this.add("entity.minecraft.villager." + XSurvive.MOD_ID + "." + location.getPath(), getName(location));
		}
		this.add(XSurvive.TAB.getDisplayName().getString(), XSurvive.MOD_NAME);
		this.add("death.attack.curse_of_harming", "%1$s die by his own weapon");
		this.add("death.attack.curse_of_harming.player", "%1$s die by his own weapon whilst fighting %2$s");
		this.add(XSurvive.MOD_ID + ".container.smelting_furnace", "Smelting Furnace");
		this.add(XSurvive.MOD_ID + ".gui.recipebook.toggleRecipes.smeltable", "Showing Smeltable");
	}
	
	private void add(Potion potion) {
		ResourceLocation location = ForgeRegistries.POTIONS.getKey(potion);
		String potionName = location.getPath();
		this.add("item.minecraft.potion.effect." + potionName, "Potion of " + this.getPotionName(location));
		this.add("item.minecraft.splash_potion.effect." + potionName, "Splash Potion of " + this.getPotionName(location));
		this.add("item.minecraft.lingering_potion.effect." + potionName, "Lingering Potion of " + this.getPotionName(location));
		this.add("item.minecraft.tipped_arrow.effect." + potionName, "Arrow of " + this.getPotionName(location));
	}
	
	private static String getName(ResourceLocation location) { 
		String[] nameParts = location.getPath().split("_");
		String name = "";
		for (String namePart : nameParts) {
			String startChar = namePart.substring(0, 1).toUpperCase();
			name += startChar + namePart.substring(1, namePart.length()) + " ";
		}
		return name.trim();
	}
	
	public static String getEnchantmentName(ResourceLocation location) {
		String name = getName(location);
		if (name.contains(" Of ")) {
			return name.replace(" Of ", " of ");
		}
		return name;
	}
	
	private String getPotionName(ResourceLocation location) {
		if (location.getPath().startsWith("long_")) {
			String path = location.getPath();
			return getName(new ResourceLocation(location.getNamespace(), path.replace("long_", "")));
		} else if (location.getPath().startsWith("strong_")) {
			String path = location.getPath();
			return getName(new ResourceLocation(location.getNamespace(), path.replace("strong_", "")));
		}
		return getName(location);
	}
	
	@Override
	public String getName() {
		return "XSurvive Languages";
	}

}

