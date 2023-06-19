package net.luis.xsurvive.data.provider.language;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.entity.npc.XSVillagerProfessions;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.XSBlocks;
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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class XSLanguageProvider extends LanguageProvider {
	
	public XSLanguageProvider(DataGenerator generator) {
		super(generator.getPackOutput(), XSurvive.MOD_ID, "en_us");
	}
	
	public static String getName(ResourceLocation location) {
		StringBuilder name = new StringBuilder();
		if (location != null) {
			String[] nameParts = location.getPath().split("_");
			for (String namePart : nameParts) {
				String startChar = namePart.substring(0, 1).toUpperCase();
				name.append(startChar).append(namePart.substring(1)).append(" ");
			}
		}
		return name.toString().trim();
	}
	
	public static String getLocalizedName(ResourceLocation location) {
		String name = getName(location);
		if (name.contains(" Of ")) {
			return name.replace(" Of ", " of ");
		}
		if (name.contains(" The ")) {
			return name.replace(" The ", " the ");
		}
		return name;
	}
	
	@Override
	protected void addTranslations() {
		for (Enchantment enchantment : XSEnchantments.ENCHANTMENTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(enchantment, getLocalizedName(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)));
		}
		for (Item item : XSItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getLocalizedName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (Item item : XSBlocks.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (MobEffect mobEffect : XSMobEffects.MOB_EFFECTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(mobEffect, getName(ForgeRegistries.MOB_EFFECTS.getKey(mobEffect)));
		}
		for (Potion potion : XSPotions.POTIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(potion);
		}
		for (VillagerProfession profession : XSVillagerProfessions.VILLAGER_PROFESSIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			ResourceLocation location = Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession));
			this.add("entity.minecraft.villager." + XSurvive.MOD_ID + "." + location.getPath(), getName(location));
		}
		this.add("item_tab." + XSurvive.MOD_ID + ".runes", "Runes");
		this.add("item_tab." + XSurvive.MOD_ID + ".golden_book", "Golden Books");
		this.add("death.attack.curse_of_harming", "{0} die by his own weapon");
		this.add("death.attack.curse_of_harming.player", "{0} die by his own weapon whilst fighting {1}");
		this.add(XSurvive.MOD_ID + ".container.smelting_furnace", "Smelting Furnace");
		this.add(XSurvive.MOD_ID + ".gui.recipebook.toggleRecipes.smeltable", "Showing Smeltable");
		this.add("options." + XSurvive.MOD_ID + ".gamma.infinite", "Infinite");
		
		this.add("options." + XSurvive.MOD_ID + ".glint.defaultVanilla", "Default vanilla");
		this.add("options." + XSurvive.MOD_ID + ".glint.maxVanilla", "Max vanilla");
		this.add("options." + XSurvive.MOD_ID + ".glint.original", "Original");
		
		this.add(XSurvive.MOD_ID + ".commands.gamma.get", "Gamma value is set to {0}");
		this.add(XSurvive.MOD_ID + ".commands.gamma.failure", "Fail to set gamma value to {0}");
		this.add(XSurvive.MOD_ID + ".commands.gamma.success", "Successfully sets the gamma value to {0}");
		this.add("message.xsurvive.sleeping", "Sleeping was disabled to increase the difficulty");
	}
	
	private void add(Potion potion) {
		ResourceLocation location = Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(potion));
		String potionName = location.getPath();
		this.add("item.minecraft.potion.effect." + potionName, "Potion of " + this.getPotionName(location));
		this.add("item.minecraft.splash_potion.effect." + potionName, "Splash Potion of " + this.getPotionName(location));
		this.add("item.minecraft.lingering_potion.effect." + potionName, "Lingering Potion of " + this.getPotionName(location));
		this.add("item.minecraft.tipped_arrow.effect." + potionName, "Arrow of " + this.getPotionName(location));
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
	public @NotNull String getName() {
		return "XSurvive Languages";
	}
}
