/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.event.village;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.luis.xores.world.item.XOItems;
import net.luis.xores.world.level.block.XOBlocks;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.world.entity.npc.XSVillagerProfessions;
import net.luis.xsurvive.world.item.trading.AdvancedTradeBuilder;
import net.luis.xsurvive.world.item.trading.SimpleTradeBuilder;
import net.luis.xsurvive.world.item.trading.dynamic.DynamicEnchantedTrades;
import net.luis.xsurvive.world.item.trading.dynamic.DynamicPotionTrades;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class VillagerTradesEventHandler {
	
	private static final List<Rarity> ALLOWED_RARITIES = Lists.newArrayList(Rarity.COMMON, Rarity.RARE);
	
	@SubscribeEvent
	public static void villagerTrades(@NotNull VillagerTradesEvent event) {
		Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
		List<ItemListing> trade1 = Lists.newArrayList();
		List<ItemListing> trade2 = Lists.newArrayList();
		List<ItemListing> trade3 = Lists.newArrayList();
		List<ItemListing> trade4 = Lists.newArrayList();
		List<ItemListing> trade5 = Lists.newArrayList();
		if (event.getType() == VillagerProfession.ARMORER) {
			trade1.add(SimpleTradeBuilder.emerald(Items.COAL, 15, 1).maxUses(16).villagerXp(2).multiplier(0.05F).build());
			trade1.add(SimpleTradeBuilder.item(3, Items.CHAINMAIL_BOOTS, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(SimpleTradeBuilder.item(6, Items.CHAINMAIL_LEGGINGS, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(SimpleTradeBuilder.item(4, Items.CHAINMAIL_HELMET, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(SimpleTradeBuilder.item(8, Items.CHAINMAIL_CHESTPLATE, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			
			trade2.add(SimpleTradeBuilder.emerald(Items.IRON_INGOT, 4, 1).maxUses(12).villagerXp(10).multiplier(0.05F).build());
			trade2.add(SimpleTradeBuilder.item(36, Items.BELL, 1).maxUses(12).villagerXp(5).multiplier(0.2F).build());
			trade2.add(SimpleTradeBuilder.item(12, Items.IRON_LEGGINGS, 1).maxUses(12).villagerXp(5).multiplier(0.2F).build());
			trade2.add(SimpleTradeBuilder.item(9, Items.IRON_BOOTS, 1).maxUses(12).villagerXp(5).multiplier(0.2F).build());
			
			trade3.add(SimpleTradeBuilder.emerald(Items.LAVA_BUCKET, 1, 1).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade3.add(SimpleTradeBuilder.emerald(Items.DIAMOND, 1, 3).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade3.add(SimpleTradeBuilder.item(10, Items.IRON_HELMET, 1).maxUses(12).villagerXp(10).multiplier(0.2F).build());
			trade3.add(SimpleTradeBuilder.item(14, Items.IRON_CHESTPLATE, 1).maxUses(12).villagerXp(10).multiplier(0.2F).build());
			trade3.add(SimpleTradeBuilder.item(6, Items.SHIELD, 1).maxUses(12).villagerXp(10).multiplier(0.2F).build());
			
			trade4.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_LEGGINGS, 29, 3, 4, 0.2F));
			trade4.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_BOOTS, 24, 3, 4, 0.2F));
			
			trade5.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_HELMET, 26, 3, 5, 0.2F));
			trade5.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_CHESTPLATE, 34, 3, 5, 0.2F));
		} else if (event.getType() == VillagerProfession.LIBRARIAN) {
			trade1.add(SimpleTradeBuilder.emerald(Items.PAPER, 24, 1).maxUses(16).villagerXp(2).multiplier(0.05F).build());
			trade1.add(DynamicEnchantedTrades.randomEnchantedBook(1, ALLOWED_RARITIES));
			trade1.add(SimpleTradeBuilder.item(8, Items.BOOKSHELF, 1).maxUses(12).villagerXp(2).multiplier(0.05F).build());
			
			trade2.add(SimpleTradeBuilder.emerald(Items.BOOK, 4, 1).maxUses(12).villagerXp(10).multiplier(0.05F).build());
			trade2.add(DynamicEnchantedTrades.randomEnchantedBook(2, ALLOWED_RARITIES));
			trade2.add(SimpleTradeBuilder.item(1, Items.LANTERN, 1).maxUses(12).villagerXp(5).multiplier(0.05F).build());
			
			trade3.add(SimpleTradeBuilder.emerald(Items.INK_SAC, 5, 1).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade3.add(DynamicEnchantedTrades.randomEnchantedBook(3, ALLOWED_RARITIES));
			trade3.add(SimpleTradeBuilder.item(1, Items.GLASS, 4).maxUses(12).villagerXp(10).multiplier(0.05F).build());
			
			trade4.add(SimpleTradeBuilder.emerald(Items.WRITABLE_BOOK, 2, 1).maxUses(12).villagerXp(30).multiplier(0.05F).build());
			trade4.add(DynamicEnchantedTrades.randomEnchantedBook(4, ALLOWED_RARITIES));
			trade4.add(SimpleTradeBuilder.item(5, Items.CLOCK, 1).maxUses(12).villagerXp(15).multiplier(0.05F).build());
			trade4.add(SimpleTradeBuilder.item(4, Items.COMPASS, 1).maxUses(12).villagerXp(15).multiplier(0.05F).build());
			
			trade5.add(SimpleTradeBuilder.item(20, Items.NAME_TAG, 1).maxUses(12).villagerXp(30).multiplier(0.05F).build());
		} else if (event.getType() == VillagerProfession.WEAPONSMITH) {
			trade1.add(SimpleTradeBuilder.emerald(Items.COAL, 15, 1).maxUses(16).villagerXp(2).multiplier(0.05F).build());
			trade1.add(SimpleTradeBuilder.item(7, Items.STONE_AXE, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(DynamicEnchantedTrades.randomEnchantedItem(Items.STONE_SWORD, 6, 3, 1, 0.2F)); // TEST
			
			trade2.add(SimpleTradeBuilder.emerald(Items.IRON_INGOT, 4, 1).maxUses(12).villagerXp(10).multiplier(0.05F).build());
			trade2.add(SimpleTradeBuilder.item(36, Items.BELL, 1).maxUses(12).villagerXp(5).multiplier(0.2F).build());
			trade2.add(SimpleTradeBuilder.item(13, Items.IRON_AXE, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			
			trade3.add(SimpleTradeBuilder.emerald(Items.FLINT, 24, 1).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade3.add(DynamicEnchantedTrades.randomEnchantedItem(Items.IRON_SWORD, 12, 3, 1, 0.2F)); // TEST
			
			trade4.add(SimpleTradeBuilder.emerald(Items.DIAMOND, 1, 3).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade4.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_AXE, 33, 3, 4, 0.2F)); // TEST
			
			trade5.add(DynamicEnchantedTrades.randomEnchantedItem(Items.DIAMOND_SWORD, 31, 3, 5, 0.2F)); // TEST
		} else if (event.getType() == VillagerProfession.TOOLSMITH) {
			trade1.add(SimpleTradeBuilder.emerald(Items.COAL, 15, 1).maxUses(16).villagerXp(2).multiplier(0.05F).build());
			trade1.add(SimpleTradeBuilder.item(5, Items.STONE_SHOVEL, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(SimpleTradeBuilder.item(5, Items.STONE_PICKAXE, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			trade1.add(SimpleTradeBuilder.item(5, Items.STONE_HOE, 1).maxUses(12).villagerXp(1).multiplier(0.2F).build());
			
			trade2.add(SimpleTradeBuilder.emerald(Items.IRON_INGOT, 4, 1).maxUses(12).villagerXp(10).multiplier(0.05F).build());
			trade2.add(SimpleTradeBuilder.item(36, Items.BELL, 1).maxUses(12).villagerXp(5).multiplier(0.2F).build());
			
			trade3.add(SimpleTradeBuilder.emerald(Items.FLINT, 30, 1).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade3.add(DynamicEnchantedTrades.randomEnchantedItem(Items.IRON_SHOVEL, 10, 3, 3, 0.2F));
			trade3.add(DynamicEnchantedTrades.randomEnchantedItem(Items.IRON_PICKAXE, 10, 3, 3, 0.2F));
			trade3.add(DynamicEnchantedTrades.randomEnchantedItem(Items.IRON_HOE, 10, 3, 3, 0.2F));
			
			trade4.add(SimpleTradeBuilder.emerald(Items.DIAMOND, 1, 3).maxUses(12).villagerXp(20).multiplier(0.05F).build());
			trade4.add(SimpleTradeBuilder.emerald(Items.DIAMOND_HOE, 1, 18).maxUses(12).villagerXp(15).multiplier(0.02F).build());
			trade4.add(SimpleTradeBuilder.emerald(Items.DIAMOND_SHOVEL, 1, 19).maxUses(12).villagerXp(15).multiplier(0.02F).build());
			
			trade5.add(SimpleTradeBuilder.emerald(Items.DIAMOND_PICKAXE, 1, 25).maxUses(12).villagerXp(25).multiplier(0.02F).build());
		} else if (event.getType() == XSVillagerProfessions.BEEKEEPER.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.DANDELION, 10, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.POPPY, 10, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.OXEYE_DAISY, 7, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.AZURE_BLUET, 10, 1).defaultBuild(1));
			
			trade2.add(SimpleTradeBuilder.emerald(Items.RED_TULIP, 7, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.ORANGE_TULIP, 7, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.WHITE_TULIP, 7, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.PINK_TULIP, 7, 1).defaultBuild(2));
			
			trade3.add(SimpleTradeBuilder.emerald(Items.SUNFLOWER, 5, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.LILAC, 5, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.ROSE_BUSH, 5, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.PEONY, 5, 1).defaultBuild(3));
			
			trade4.add(SimpleTradeBuilder.emerald(Items.ALLIUM, 3, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.CORNFLOWER, 3, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.LILY_OF_THE_VALLEY, 3, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.BLUE_ORCHID, 3, 1).defaultBuild(4));
			
			trade5.add(SimpleTradeBuilder.emerald(Items.HONEYCOMB, 1, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(Items.HONEY_BOTTLE, 1, 3).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(4, Items.HONEYCOMB_BLOCK, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(6, Items.HONEY_BLOCK, 1).defaultBuild(5));
		} else if (event.getType() == XSVillagerProfessions.ENCHANTER.get()) {
			trade1.add(SimpleTradeBuilder.item(1, Items.LAPIS_LAZULI, 2).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.LAPIS_LAZULI, 2, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.BOOK, 4, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(1, Items.EXPERIENCE_BOTTLE, 2).defaultBuild(1));
			
			trade2.add(DynamicEnchantedTrades.randomEnchantedBook(2, ALLOWED_RARITIES));
			trade2.add(DynamicEnchantedTrades.randomEnchantedBook(2, ALLOWED_RARITIES));
			
			trade3.add(DynamicEnchantedTrades.randomEnchantedBook(3, ALLOWED_RARITIES));
			trade3.add(DynamicEnchantedTrades.randomEnchantedBook(3, ALLOWED_RARITIES));
			
			trade4.add(DynamicEnchantedTrades.randomEnchantedBook(4, ALLOWED_RARITIES));
			trade4.add(DynamicEnchantedTrades.randomEnchantedBook(4, ALLOWED_RARITIES));
			
			trade5.add(DynamicEnchantedTrades.randomEnchantedGoldenBook(5));
		} else if (event.getType() == XSVillagerProfessions.END_TRADER.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.END_STONE, 12, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.CHORUS_FRUIT, 10, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(3, Items.CHORUS_FLOWER, 1).defaultBuild(1));
			
			trade2.add(SimpleTradeBuilder.emerald(Items.END_STONE_BRICKS, 4, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.item(1, Items.POPPED_CHORUS_FRUIT, 4).defaultBuild(2));
			trade2.add(AdvancedTradeBuilder.firework(1, 3, 1).defaultBuild(2));
			
			trade3.add(SimpleTradeBuilder.emerald(Items.ENDER_PEARL, 4, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(2, Items.PURPUR_BLOCK, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.DRAGON_BREATH, 1, 2).defaultBuild(3));
			
			trade4.add(SimpleTradeBuilder.item(6, Items.END_CRYSTAL, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.OBSIDIAN, 4, 1).defaultBuild(4));
			trade4.add(AdvancedTradeBuilder.firework(2, 3, 2).defaultBuild(4));
			
			trade5.add(SimpleTradeBuilder.item(4, Items.ENDER_EYE, 1).defaultBuild(5));
			trade5.add(AdvancedTradeBuilder.firework(3, 3, 3).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(XOItems.ENDERITE_SCRAP.get(), 1, 16).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(32, Items.SHULKER_SHELL, 1).defaultBuild(5));
		} else if (event.getType() == XSVillagerProfessions.LUMBERJACK.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.STICK, 32, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(4, Items.APPLE, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.OAK_SAPLING, 8, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.OAK_LOG, 8, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(1, Items.OAK_LOG, 4).defaultBuild(1));
			
			trade2.add(SimpleTradeBuilder.emerald(Items.BIRCH_SAPLING, 8, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.BIRCH_LOG, 8, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.item(1, Items.BIRCH_LOG, 4).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.SPRUCE_SAPLING, 8, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.SPRUCE_LOG, 8, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.item(1, Items.SPRUCE_LOG, 4).defaultBuild(2));
			
			trade3.add(SimpleTradeBuilder.item(2, Items.DARK_OAK_SAPLING, 4).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.DARK_OAK_LOG, 8, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(1, Items.DARK_OAK_LOG, 4).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(4, Items.ACACIA_SAPLING, 4).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.ACACIA_LOG, 8, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(1, Items.ACACIA_LOG, 4).defaultBuild(3));
			
			trade4.add(SimpleTradeBuilder.item(4, Items.JUNGLE_SAPLING, 4).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.JUNGLE_LOG, 8, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.item(1, Items.JUNGLE_LOG, 4).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.item(6, Items.MANGROVE_PROPAGULE, 4).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.MANGROVE_LOG, 8, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.item(1, Items.MANGROVE_LOG, 4).defaultBuild(4));
			
			trade5.add(SimpleTradeBuilder.emerald(Items.CRIMSON_FUNGUS, 4, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(Items.CRIMSON_STEM, 8, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(1, Items.CRIMSON_STEM, 4).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(Items.WARPED_FUNGUS, 4, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(Items.WARPED_STEM, 8, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(1, Items.WARPED_STEM, 4).defaultBuild(5));
		} else if (event.getType() == XSVillagerProfessions.MINER.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.COBBLESTONE, 16, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.COBBLED_DEEPSLATE, 16, 1).defaultBuild(1));
			trade1.add(AdvancedTradeBuilder.processItem(Items.RAW_GOLD, 1, 3, Items.GOLD_INGOT, 2).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.GOLD_INGOT, 3, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(3, Items.GOLD_INGOT, 1).defaultBuild(1));
			
			trade2.add(AdvancedTradeBuilder.processItem(Items.RAW_IRON, 1, 5, Items.IRON_INGOT, 2).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.IRON_INGOT, 4, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.item(4, Items.IRON_INGOT, 1).defaultBuild(2));
			
			trade3.add(SimpleTradeBuilder.item(6, Items.OBSIDIAN, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.LAVA_BUCKET, 1, 2).defaultBuild(3));
			
			trade4.add(AdvancedTradeBuilder.processItem(Items.DIAMOND_ORE, 1, 3, Items.DIAMOND, 1).defaultBuild(4));
			trade4.add(AdvancedTradeBuilder.processItem(Items.DEEPSLATE_DIAMOND_ORE, 1, 3, Items.DIAMOND, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.DIAMOND, 1, 3).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.item(16, Items.DIAMOND, 1).defaultBuild(4));
			
			trade5.add(AdvancedTradeBuilder.processItem(XOBlocks.SAPHIRE_ORE.get(), 1, 6, XOItems.SAPHIRE_INGOT.get(), 1).defaultBuild(5));
			trade5.add(AdvancedTradeBuilder.processItem(XOBlocks.DEEPSLATE_SAPHIRE_ORE.get(), 1, 6, XOItems.SAPHIRE_INGOT.get(), 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(XOItems.SAPHIRE_INGOT.get(), 1, 2).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(32, XOItems.SAPHIRE_INGOT.get(), 1).defaultBuild(5));
		} else if (event.getType() == XSVillagerProfessions.MOB_HUNTER.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.ROTTEN_FLESH, 8, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(1, Items.BONE, 4).defaultBuild(1));
			
			trade2.add(SimpleTradeBuilder.emerald(Items.STRING, 8, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.SPIDER_EYE, 6, 1).defaultBuild(2));
			
			trade3.add(AdvancedTradeBuilder.processItem(Items.SPIDER_EYE, 1, 1, Items.FERMENTED_SPIDER_EYE, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(4, Items.SLIME_BALL, 1).defaultBuild(3));
			
			trade4.add(SimpleTradeBuilder.emerald(Items.GLOW_INK_SAC, 4, 1).defaultBuild(4));
			
			trade5.add(SimpleTradeBuilder.item(8, Items.ECHO_SHARD, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(64, Items.SKELETON_SKULL, 1).defaultBuild(5));
			trade5.add(AdvancedTradeBuilder.expensiveItem(96, Items.ZOMBIE_HEAD, 1).defaultBuild(5));
			trade5.add(AdvancedTradeBuilder.expensiveItem(96, Items.CREEPER_HEAD, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(Items.TOTEM_OF_UNDYING, 1, 32).defaultBuild(5));
		} else if (event.getType() == XSVillagerProfessions.NETHER_TRADER.get()) {
			trade1.add(SimpleTradeBuilder.emerald(Items.NETHERRACK, 16, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.NETHER_BRICKS, 8, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.emerald(Items.BLACKSTONE, 8, 1).defaultBuild(1));
			trade1.add(SimpleTradeBuilder.item(1, Items.QUARTZ, 4).defaultBuild(1));
			
			trade2.add(SimpleTradeBuilder.emerald(Items.SOUL_SAND, 16, 1).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.emerald(Items.BASALT, 8, 1).defaultBuild(2));
			trade2.add(AdvancedTradeBuilder.processItem(Items.SOUL_SAND, 1, 1, Items.SOUL_SOIL, 4).defaultBuild(2));
			trade2.add(SimpleTradeBuilder.item(4, Items.IRON_INGOT, 1).defaultBuild(2));
			
			trade3.add(SimpleTradeBuilder.emerald(Items.OBSIDIAN, 4, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.item(4, Items.CRYING_OBSIDIAN, 1).defaultBuild(3));
			trade3.add(AdvancedTradeBuilder.processItem(Items.OBSIDIAN, 1, 1, Items.CRYING_OBSIDIAN, 2).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(XOItems.POLISHED_ROSE_QUARTZ.get(), 1, 1).defaultBuild(3));
			trade3.add(SimpleTradeBuilder.emerald(Items.NETHER_WART, 4, 1).defaultBuild(3));
			
			trade4.add(SimpleTradeBuilder.item(1, Items.NETHER_WART, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.emerald(Items.BLAZE_POWDER, 4, 1).defaultBuild(4));
			trade4.add(SimpleTradeBuilder.item(4, Items.MAGMA_CREAM, 1).defaultBuild(4));
			trade4.add(DynamicPotionTrades.randomPotion(4));
			
			trade5.add(SimpleTradeBuilder.item(6, Items.GHAST_TEAR, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(4, Items.BLAZE_ROD, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(24, Items.MUSIC_DISC_PIGSTEP, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.emerald(XOItems.BLAZING_INGOT.get(), 1, 18).defaultBuild(5));
			trade5.add(DynamicPotionTrades.randomPotion(5));
			trade5.add(DynamicPotionTrades.randomPotion(5));
			trade5.add(DynamicPotionTrades.randomPotion(5));
			trade5.add(SimpleTradeBuilder.item(16, Items.WITHER_ROSE, 1).defaultBuild(5));
			trade5.add(SimpleTradeBuilder.item(64, Items.WITHER_SKELETON_SKULL, 1).defaultBuild(5));
		}
		if (!trade1.isEmpty()) {
			trades.put(1, trade1);
		}
		if (!trade2.isEmpty()) {
			trades.put(2, trade2);
		}
		if (!trade3.isEmpty()) {
			trades.put(3, trade3);
		}
		if (!trade4.isEmpty()) {
			trades.put(4, trade4);
		}
		if (!trade5.isEmpty()) {
			trades.put(5, trade5);
		}
	}
}
