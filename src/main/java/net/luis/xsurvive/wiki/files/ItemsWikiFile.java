package net.luis.xsurvive.wiki.files;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.WikiList;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.luis.xsurvive.world.item.GlintColorItem;
import net.luis.xsurvive.world.item.XSItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class ItemsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder builder = new WikiFileBuilder("ItemsWiki");
		builder.header1("Items");
		addItems(builder);
		return builder;
	}
	
	private static void addItems(WikiFileBuilder wikiBuilder) {
		addItem(wikiBuilder, XSItems.ENCHANTED_GOLDEN_BOOK.get());
		addRuneItem(wikiBuilder, XSItems.WHITE_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.ORANGE_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.MAGENTA_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.LIGHT_BLUE_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.YELLOW_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.LIME_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.PINK_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.GRAY_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.LIGHT_GRAY_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.CYAN_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.PURPLE_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.BLUE_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.BROWN_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.GREEN_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.RED_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.BLACK_RUNE.get());
		addRuneItem(wikiBuilder, XSItems.RAINBOW_RUNE.get());
		addItem(wikiBuilder, XSItems.HONEY_MELON_SEEDS.get(), (builder) -> {
			builder.line("Can be used to plant the honey melon on farmland.");
			builder.line("Can be craft from honey melon slices.");
		});
		addItem(wikiBuilder, XSItems.HONEY_MELON_SLICE.get(), null);
		addItem(wikiBuilder, XSItems.DIAMOND_APPLE.get(), (builder) -> {
			builder.line("Rarer variety of the vanilla golden apple with stronger effects.");
		});
		addItem(wikiBuilder, XSItems.ENCHANTED_DIAMOND_APPLE.get(), (builder) -> {
			builder.line("Rarer variant of the vanilla enchanted golden apple with stronger effects.");
		});
	}
	
	private static <T extends Item & WikiFileEntry> void addItem(WikiFileBuilder builder, T item) {
		addItem(builder, item, item);
	}
	
	private static void addRuneItem(WikiFileBuilder builder, GlintColorItem item) {
		addItem(builder, item, getRuneUsage(item));
	}
	
	private static void addItem(WikiFileBuilder builder, Item item, @Nullable WikiFileEntry wikiEntry) {
		builder.header2(XSLanguageProvider.getLocalizedName(ForgeRegistries.ITEMS.getKey(item)));
		builder.header3("Properties");
		addItemProperties(builder, item);
		if (item.foodProperties != null) {
			builder.header3("Food properties");
			addFoodProperties(builder, item, item.foodProperties);
			if (!item.foodProperties.getEffects().isEmpty()) {
				builder.header3("Food effects");
				addFoodEffects(builder, item.foodProperties.getEffects());
			}
		}
		if (wikiEntry != null) {
			builder.header3("Usage");
			wikiEntry.add(builder);
		}
	}
	
	private static void addItemProperties(WikiFileBuilder wikiBuilder, Item item) {
		wikiBuilder.lines((builder) -> {
			builder.append("Rarity:").append(XSLanguageProvider.getName(new ResourceLocation(item.rarity.name().toLowerCase()))).endLine();
			builder.append("Max stack size:").append(item.maxStackSize).endLine();
			if (item.canBeDepleted()) {
				builder.append("Durability:").append(item.maxDamage).endLine();
			}
			if (item.isFireResistant) {
				builder.append("Fire resistant:").append(true).endLine();
			}
			if (item.craftingRemainingItem != null) {
				builder.append("Crafting remaining item:").append(item.craftingRemainingItem).endLine();
			}
		});
		
	}
	
	private static void addFoodProperties(WikiFileBuilder wikiBuilder, Item item, FoodProperties properties) {
		wikiBuilder.lines((builder) -> {
			builder.append("Nutrition:").append(properties.getNutrition()).endLine();
			builder.append("Saturation modifier:").append(properties.getSaturationModifier()).endLine();
			builder.append("Eat duration:").append(item.getUseDuration(item.getDefaultInstance())).endLine();
			if (properties.isMeat()) {
				builder.append("Meat:").append(true).endLine();
			}
			if (properties.canAlwaysEat()) {
				builder.append("Always edible:").append(true).endLine();
			}
		});
	}
	
	private static void addFoodEffects(WikiFileBuilder wikiBuilder, List<Pair<MobEffectInstance, Float>> effects) {
		wikiBuilder.pointList((builder) -> {
			for (Pair<MobEffectInstance, Float> pair : effects) {
				MobEffectInstance instance = pair.getFirst();
				builder.append("Effect").appendFormatted(XSLanguageProvider.getName(ForgeRegistries.MOB_EFFECTS.getKey(instance.getEffect())), WikiFormat.CODE);
				builder.append("with duration").append(instance.getDuration()).append("and amplifier").append(instance.getAmplifier());
				builder.append("has apply chance of").append((int) (pair.getSecond() * 100) + "%").endLine();
			}
		});
	}
	
	private static WikiFileEntry getRuneUsage(GlintColorItem item) {
		return (wikiBuilder) -> {
			wikiBuilder.lines((builder) -> {
				builder.append("The").append(XSLanguageProvider.getName(ForgeRegistries.ITEMS.getKey(item))).append("can be used to change the glint color of an enchanted item to").append(getRuneColor(item)).append(".").endLine();
			});
		};
	}
	
	private static String getRuneColor(GlintColorItem item) {
		int color = item.getGlintColor(item.getDefaultInstance());
		if (15 >= color && color >= 0) {
			return XSLanguageProvider.getName(new ResourceLocation(DyeColor.byId(color).getName().toLowerCase())).toLowerCase();
		} else if (color == 16) {
			return "rainbow";
		}
		throw new RuntimeException("Fail to get run color for id: " + color);
	}
	
}
