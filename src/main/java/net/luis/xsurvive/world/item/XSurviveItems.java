package net.luis.xsurvive.world.item;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	
	public static final RegistryObject<EnchantedGoldenBookItem> ENCHANTED_GOLDEN_BOOK = ITEMS.register("enchanted_golden_book", () -> {
		return new EnchantedGoldenBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	});
	public static final RegistryObject<RuneItem> WHITE_RUNE = ITEMS.register("white_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 0);
	});
	public static final RegistryObject<RuneItem> ORANGE_RUNE = ITEMS.register("orange_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 1);
	});
	public static final RegistryObject<RuneItem> MAGENTA_RUNE = ITEMS.register("magenta_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 2);
	});
	public static final RegistryObject<RuneItem> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 3);
	});
	public static final RegistryObject<RuneItem> YELLOW_RUNE = ITEMS.register("yellow_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 4);
	});
	public static final RegistryObject<RuneItem> LIME_RUNE = ITEMS.register("lime_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 5);
	});
	public static final RegistryObject<RuneItem> PINK_RUNE = ITEMS.register("pink_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 6);
	});
	public static final RegistryObject<RuneItem> GRAY_RUNE = ITEMS.register("gray_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 7);
	});
	public static final RegistryObject<RuneItem> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 8);
	});
	public static final RegistryObject<RuneItem> CYAN_RUNE = ITEMS.register("cyan_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 9);
	});
	public static final RegistryObject<RuneItem> PURPLE_RUNE = ITEMS.register("purple_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 10);
	});
	public static final RegistryObject<RuneItem> BLUE_RUNE = ITEMS.register("blue_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 11);
	});
	public static final RegistryObject<RuneItem> BROWN_RUNE = ITEMS.register("brown_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 12);
	});
	public static final RegistryObject<RuneItem> GREEN_RUNE = ITEMS.register("green_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 13);
	});
	public static final RegistryObject<RuneItem> RED_RUNE = ITEMS.register("red_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 14);
	});
	public static final RegistryObject<RuneItem> BLACK_RUNE = ITEMS.register("black_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 15);
	});
	public static final RegistryObject<RuneItem> RAINBOW_RUNE = ITEMS.register("rainbow_rune", () -> {
		return new RuneItem(new Item.Properties().stacksTo(1).tab(XSurvive.RUNE_TAB), true, 16);
	});
	
}
