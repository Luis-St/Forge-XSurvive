package net.luis.xsurvive.wiki.file;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xores.world.item.ElytraChestplateItem;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
		builder.header1("Vanilla Enchantment Modifications");
		addVanillaEnchantments(builder);
		return builder;
	}
	
	private static void addEnchantments(WikiFileBuilder builder, List<Enchantment> enchantments) {
		for (Enchantment enchantment : enchantments) {
			builder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)));
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
		List<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
			return canEnchant(enchantment, item);
		}).collect(Collectors.toList());
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
		if (containsArmorItem(items, EquipmentSlot.HEAD) && containsArmorItem(items, EquipmentSlot.CHEST) && containsArmorItem(items, EquipmentSlot.LEGS) && containsArmorItem(items, EquipmentSlot.FEET)) {
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
			if (containsArmorItem(items, EquipmentSlot.HEAD)) {
				enchantableItems.add("Helmet");
			}
			if (containsArmorItem(items, EquipmentSlot.CHEST)) {
				enchantableItems.add("Chestplate");
			}
			if (containsItem(items, ElytraItem.class, ElytraChestplateItem.class)) {
				enchantableItems.add("Elytra");
			}
			if (containsItem(items, ElytraChestplateItem.class)) {
				enchantableItems.add("Elytra Chestplate");
			}
			if (containsArmorItem(items, EquipmentSlot.LEGS)) {
				enchantableItems.add("Leggings");
			}
			if (containsArmorItem(items, EquipmentSlot.FEET)) {
				enchantableItems.add("Boots");
			}
		}
		return enchantableItems.toString().replace("[", "").replace("]", "").trim();
	}
	
	private static boolean canEnchant(Enchantment enchantment, Item item) {
		ItemStack stack = new ItemStack(item);
		if (enchantment.canEnchant(stack) || enchantment.canApplyAtEnchantingTable(stack)) {
			return true;
		}
		return false;
	}
	
	private static boolean containsItem(List<Item> items, Class<? extends Item> clazz, Class<? extends Item> excludeClazz) {
		for (Item item : items) {
			if (clazz.isInstance(item) && !excludeClazz.isInstance(item)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsItem(List<Item> items, Class<? extends Item> clazz) {
		for (Item item : items) {
			if (clazz.isInstance(item)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsArmorItem(List<Item> items, EquipmentSlot slot) {
		return items.stream().filter(ArmorItem.class::isInstance).map(ArmorItem.class::cast).anyMatch((item) -> {
			return item.getSlot() == slot;
		});
	}
	
	private static String getIncompatibleEnchantments(Enchantment enchantment) {
		List<Enchantment> enchantments = Lists.newArrayList();
		for (Enchantment ench : ForgeRegistries.ENCHANTMENTS.getValues()) {
			if (!enchantment.isCompatibleWith(ench) && enchantment != ench) {
				enchantments.add(ench);
			}
		}
		return enchantments.stream().map(ForgeRegistries.ENCHANTMENTS::getKey).map(XSLanguageProvider::getEnchantmentName).toList().toString().replace("[", "").replace("]", "").trim();
	}
	
	private static void addVanillaEnchantments(WikiFileBuilder wikiBuilder) {
		wikiBuilder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.PUNCH_ARROWS)));
		wikiBuilder.header3("Modified Properties");
		wikiBuilder.line((builder) -> {
			builder.append("Max level:").append(Enchantments.PUNCH_ARROWS.getMaxLevel()).endLine();
		});
		addProtectionEnchantments(wikiBuilder, (ProtectionEnchantment) Enchantments.ALL_DAMAGE_PROTECTION);
		addProtectionEnchantments(wikiBuilder, (ProtectionEnchantment) Enchantments.FIRE_PROTECTION);
		addProtectionEnchantments(wikiBuilder, (ProtectionEnchantment) Enchantments.BLAST_PROTECTION);
		addProtectionEnchantments(wikiBuilder, (ProtectionEnchantment) Enchantments.PROJECTILE_PROTECTION);
		wikiBuilder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.QUICK_CHARGE)));
		wikiBuilder.header3("Modified Properties");
		wikiBuilder.line((builder) -> {
			builder.append("Max level:").append(Enchantments.QUICK_CHARGE.getMaxLevel()).endLine();
		});
		wikiBuilder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.THORNS)));
		wikiBuilder.header3("Modified Properties");
		wikiBuilder.line((builder) -> {
			builder.append("Max level:").append(Enchantments.THORNS.getMaxLevel()).endLine();
		});
		wikiBuilder.header3("Modified Usage");
		wikiBuilder.lines((builder) -> {
			builder.append("The damage of the Thorns Enchantment is increased,").endLine();
			builder.append("the damage is now calculated based on all armor pieces with the Thorns Enchantment.").endLine();
			builder.append("The damage is calculated as follows:").endLine();
			builder.appendFormatted("(0.2 * pieceThornsLevel) * armorThornsLevel", WikiFormat.CODE).endLine();
		});
		wikiBuilder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.IMPALING)));
		wikiBuilder.header3("Modified Properties");
		wikiBuilder.lines((builder) -> {
			builder.append("Enchantable items:").append(getEnchantableItems(Enchantments.IMPALING)).endLine();
			String incompatibleEnchantments = getIncompatibleEnchantments(Enchantments.IMPALING);
			if (!incompatibleEnchantments.isBlank()) {
				builder.append("Not compatible with:").append(getIncompatibleEnchantments(Enchantments.IMPALING)).endLine();
			}
		});
		wikiBuilder.header3("Modified Usage");
		wikiBuilder.lines((builder) -> {
			builder.append("When an Water Entity is hit by an Item with this Enchantment,").endLine();
			builder.append("the damage will be multiplied by 2.5.").endLine();
			builder.append("Water Entities:").append("All Fish types,").append("Squid,").append("Glow Squid,").append("Guardian,").append("Elder Guardian,").append("Drowned and").append("Turtle").endLine();
		});
	}
	
	private static void addProtectionEnchantments(WikiFileBuilder wikiBuilder, ProtectionEnchantment enchantment) {
		wikiBuilder.header2(XSLanguageProvider.getEnchantmentName(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)));
		wikiBuilder.header3("Modified Properties");
		wikiBuilder.line((builder) -> {
			builder.append("Enchantable items:").append(getEnchantableItems(enchantment)).endLine();
		});
	}
	
}
