package net.luis.xsurvive.wiki.files;

import com.google.common.collect.Lists;
import net.luis.xores.world.item.ElytraChestplateItem;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-st
 *
 */

public class EnchantmentWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder builder = new WikiFileBuilder("EnchantmentWiki");
		builder.header1("Enchantments");
		addEnchantments(builder, Lists.newArrayList(XSEnchantments.ENCHANTMENTS.getEntries().stream().map(RegistryObject::get).iterator()));
		return builder;
	}
	
	private static void addEnchantments(WikiFileBuilder builder, List<Enchantment> enchantments) {
		for (Enchantment enchantment : enchantments) {
			builder.header2(XSLanguageProvider.getLocalizedName(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)));
			builder.header3("Properties");
			addEnchantmentProperties(builder, enchantment);
			if (enchantment instanceof WikiFileEntry wikiEntry) {
				builder.header3("Usage");
				wikiEntry.add(builder);
			}
		}
	}
	
	private static void addEnchantmentProperties(WikiFileBuilder wikiBuilder, Enchantment enchantment) {
		wikiBuilder.lines((builder) -> {
			builder.append("Max level:").append(enchantment.getMaxLevel()).endLine();
			builder.append("Enchantable items:").append(getEnchantableItems(enchantment)).endLine();
			String incompatibleEnchantments = getIncompatibleEnchantments(enchantment);
			if (!incompatibleEnchantments.isBlank()) {
				builder.append("Not compatible with:").append(getIncompatibleEnchantments(enchantment)).endLine();
			}
			if (enchantment.isCurse()) {
				builder.append("Curse:").append(true).endLine();
			}
			builder.append("Tradeable:").append(enchantment.isTradeable()).endLine();
			builder.append("Discoverable:").append(enchantment.isDiscoverable()).endLine();
			if (enchantment.isTreasureOnly()) {
				builder.append("Treasure:").append(true).endLine();
			}
			if (enchantment instanceof IEnchantment ench && ench.isAllowedOnGoldenBooks()) {
				builder.append("Golden book level:").append(ench.getMaxGoldenBookLevel()).endLine();
				if (ench.isUpgradeEnchantment()) {
					builder.append("Upgradeable:").append(true).endLine();
					builder.append("Upgrade level:").append(ench.getMaxUpgradeLevel()).endLine();
				}
			}
		});
	}
	
	private static String getEnchantableItems(Enchantment enchantment) {
		List<String> enchantableItems = Lists.newArrayList();
		List<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter((item) -> canEnchant(enchantment, item)).collect(Collectors.toList());
		if (containsItem(items, SwordItem.class)) {
			enchantableItems.add("Sword");
		}
		if (containsItem(items, ShieldItem.class)) {
			enchantableItems.add("Shield");
		}
		if (containsItem(items, BowItem.class)) {
			enchantableItems.add("Bow");
		}
		if (containsItem(items, CrossbowItem.class)) {
			enchantableItems.add("Crossbow");
		}
		if (containsItem(items, PickaxeItem.class) && containsItem(items, AxeItem.class) && containsItem(items, ShovelItem.class) && containsItem(items, HoeItem.class)) {
			enchantableItems.add("all Tools");
		} else {
			if (containsItem(items, PickaxeItem.class)) {
				enchantableItems.add("Pickaxe");
			}
			if (containsItem(items, AxeItem.class)) {
				enchantableItems.add("Axe");
			}
			if (containsItem(items, ShovelItem.class)) {
				enchantableItems.add("Shovel");
			}
			if (containsItem(items, HoeItem.class)) {
				enchantableItems.add("Hoe");
			}
		}
		if (containsArmorItem(items, ArmorItem.Type.HELMET) && containsArmorItem(items, ArmorItem.Type.CHESTPLATE) && containsArmorItem(items, ArmorItem.Type.LEGGINGS) && containsArmorItem(items, ArmorItem.Type.BOOTS)) {
			if (containsItem(items, ElytraItem.class, ElytraChestplateItem.class) && containsItem(items, ElytraChestplateItem.class)) {
				enchantableItems.add("all Armor pieces");
			} else if (containsItem(items, ElytraItem.class, ElytraChestplateItem.class)) {
				enchantableItems.add("vanilla Armor pieces");
				enchantableItems.add("Elytra");
			} else if (containsItem(items, ElytraChestplateItem.class)) {
				enchantableItems.add("vanilla Armor pieces");
				enchantableItems.add("Elytra Chestplate");
			} else {
				enchantableItems.add("vanilla Armor pieces");
			}
		} else {
			if (containsArmorItem(items, ArmorItem.Type.HELMET)) {
				enchantableItems.add("Helmet");
			}
			if (containsArmorItem(items, ArmorItem.Type.CHESTPLATE)) {
				enchantableItems.add("Chestplate");
			}
			if (containsItem(items, ElytraItem.class, ElytraChestplateItem.class)) {
				enchantableItems.add("Elytra");
			}
			if (containsItem(items, ElytraChestplateItem.class)) {
				enchantableItems.add("Elytra Chestplate");
			}
			if (containsArmorItem(items, ArmorItem.Type.LEGGINGS)) {
				enchantableItems.add("Leggings");
			}
			if (containsArmorItem(items, ArmorItem.Type.BOOTS)) {
				enchantableItems.add("Boots");
			}
		}
		return enchantableItems.toString().replace("[", "").replace("]", "").trim();
	}
	
	private static boolean canEnchant(Enchantment enchantment, Item item) {
		ItemStack stack = new ItemStack(item);
		return enchantment.canEnchant(stack) || enchantment.canApplyAtEnchantingTable(stack);
	}
	
	private static boolean containsItem(List<Item> items, Class<?> clazz, Class<?> excludeClazz) {
		for (Item item : items) {
			if (clazz != null && clazz.isInstance(item) && excludeClazz != null && !excludeClazz.isInstance(item)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsItem(List<Item> items, Class<?> clazz) {
		for (Item item : items) {
			if (clazz != null && clazz.isInstance(item)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsArmorItem(List<Item> items, ArmorItem.Type type) {
		return items.stream().filter(ArmorItem.class::isInstance).map(ArmorItem.class::cast).anyMatch((item) -> item.getType() == type);
	}
	
	private static String getIncompatibleEnchantments(Enchantment enchantment) {
		List<Enchantment> enchantments = Lists.newArrayList();
		for (Enchantment ench : ForgeRegistries.ENCHANTMENTS.getValues()) {
			if (!enchantment.isCompatibleWith(ench) && enchantment != ench) {
				enchantments.add(ench);
			}
		}
		return enchantments.stream().map(ForgeRegistries.ENCHANTMENTS::getKey).filter(Objects::nonNull).map(ResourceLocation::getPath).map((string) -> {
			return string.replace("_", " ");
		}).map(StringUtils::capitalize).toList().toString().replace("[", "").replace("]", "").trim();
	}
	
}
