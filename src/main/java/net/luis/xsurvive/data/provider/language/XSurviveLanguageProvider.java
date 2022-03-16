package net.luis.xsurvive.data.provider.language;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.init.XSurviveEnchantments;
import net.luis.xsurvive.init.XSurviveItems;
import net.luis.xsurvive.init.XSurviveMobEffects;
import net.luis.xsurvive.init.XSurvivePotions;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveLanguageProvider extends LanguageProvider {

	public XSurviveLanguageProvider(DataGenerator generator) {
		super(generator, XSurvive.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		for (Enchantment enchantment : XSurviveEnchantments.ENCHANTMENTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(enchantment, this.getName(enchantment.getRegistryName()));
		}
		for (Item item : XSurviveItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, this.getName(item.getRegistryName()));
		}
		for (MobEffect mobEffect : XSurviveMobEffects.MOB_EFFECTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(mobEffect, this.getName(mobEffect.getRegistryName()));
		}
		for (Potion potion : XSurvivePotions.POTIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(potion);
		}
		this.add(XSurvive.TAB.getDisplayName().getString(), XSurvive.MOD_NAME);
	}
	
	public void add(Potion potion) {
		ResourceLocation location = potion.getRegistryName();
		String potionName = potion.getRegistryName().getPath();
		this.add("item.minecraft.potion.effect." + potionName, this.getPotionName(location));
		this.add("item.minecraft.splash_potion.effect." + potionName, this.getPotionName(location));
		this.add("item.minecraft.lingering_potion.effect." + potionName, this.getPotionName(location));
	}
	
	protected String getPotionName(ResourceLocation location) {
		if (location.getPath().startsWith("long_")) {
			String path = location.getPath();
			return this.getName(new ResourceLocation(location.getNamespace(), path.replace("long_", "")));
		}
		return this.getName(location);
	}
	
	protected String getName(ResourceLocation location) { 
		String[] nameParts = location.getPath().split("_");
		String name = "";
		for (String namePart : nameParts) {
			String startChar = namePart.substring(0, 1).toUpperCase();
			name += startChar + namePart.substring(1, namePart.length()) + " ";
		}
		return name.trim();
	}
	
	@Override
	public String getName() {
		return "XSurvive Languages";
	}

}

