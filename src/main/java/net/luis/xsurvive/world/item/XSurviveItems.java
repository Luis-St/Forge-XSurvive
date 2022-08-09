package net.luis.xsurvive.world.item;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<EnchantedGoldenBookItem> ENCHANTED_GOLDEN_BOOK = ITEMS.register("enchanted_golden_book", () -> {
		return new EnchantedGoldenBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	});
	public static final RegistryObject<GlintColorItem> WHITE_RUNE = ITEMS.register("white_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 0);
	});
	public static final RegistryObject<GlintColorItem> ORANGE_RUNE = ITEMS.register("orange_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 1);
	});
	public static final RegistryObject<GlintColorItem> MAGENTA_RUNE = ITEMS.register("magenta_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 2);
	});
	public static final RegistryObject<GlintColorItem> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 3);
	});
	public static final RegistryObject<GlintColorItem> YELLOW_RUNE = ITEMS.register("yellow_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 4);
	});
	public static final RegistryObject<GlintColorItem> LIME_RUNE = ITEMS.register("lime_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 5);
	});
	public static final RegistryObject<GlintColorItem> PINK_RUNE = ITEMS.register("pink_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 6);
	});
	public static final RegistryObject<GlintColorItem> GRAY_RUNE = ITEMS.register("gray_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 7);
	});
	public static final RegistryObject<GlintColorItem> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 8);
	});
	public static final RegistryObject<GlintColorItem> CYAN_RUNE = ITEMS.register("cyan_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 9);
	});
	public static final RegistryObject<GlintColorItem> PURPLE_RUNE = ITEMS.register("purple_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 10);
	});
	public static final RegistryObject<GlintColorItem> BLUE_RUNE = ITEMS.register("blue_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 11);
	});
	public static final RegistryObject<GlintColorItem> BROWN_RUNE = ITEMS.register("brown_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 12);
	});
	public static final RegistryObject<GlintColorItem> GREEN_RUNE = ITEMS.register("green_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 13);
	});
	public static final RegistryObject<GlintColorItem> RED_RUNE = ITEMS.register("red_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 14);
	});
	public static final RegistryObject<GlintColorItem> BLACK_RUNE = ITEMS.register("black_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 15);
	});
	public static final RegistryObject<GlintColorItem> RAINBOW_RUNE = ITEMS.register("rainbow_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), 16);
	});
	public static final RegistryObject<ItemNameBlockItem> HONEY_MELON_SEEDS = ITEMS.register("honey_melon_seeds", () -> {
		return new ItemNameBlockItem(XSurviveBlocks.HONEY_MELON_STEM.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC));
	});
	public static final RegistryObject<Item> HONEY_MELON_SLICE = ITEMS.register("honey_melon_slice", () -> {
		return new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.7F).build()));
	});
	
}
