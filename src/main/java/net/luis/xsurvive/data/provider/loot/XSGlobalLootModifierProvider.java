package net.luis.xsurvive.data.provider.loot;

import net.luis.xores.world.level.storage.loot.SmeltingModifier;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Chance;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.storage.loot.AdditionalChanceItemModifier;
import net.luis.xsurvive.world.level.storage.loot.GoldenBookModifier;
import net.luis.xsurvive.world.level.storage.loot.LootModifierHelper;
import net.luis.xsurvive.world.level.storage.loot.MultiDropModifier;
import net.luis.xsurvive.world.level.storage.loot.RuneItemModifier;
import net.luis.xsurvive.world.level.storage.loot.predicates.LootTableIdsCondition;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

/**
 * 
 * @author Luis-st
 *
 */

public class XSGlobalLootModifierProvider extends GlobalLootModifierProvider {
	
	public XSGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, XSurvive.MOD_ID);
	}
	
	@Override
	protected void start() {
		this.add("multi_drop_modifier", new MultiDropModifier(new LootItemCondition[] {
			new MatchTool(ItemPredicate.ANY)
		}));
		this.add("smelting_modifier", new SmeltingModifier(new LootItemCondition[] {
			new MatchTool(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(XSEnchantments.SMELTING.get(), MinMaxBounds.Ints.atLeast(1))).build())
		}));
		this.add("rune_item_modifier", new RuneItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder("chests/stronghold_library").add("chests/stronghold_crossing").add("chests/stronghold_corridor").add("chests/bastion_bridge").add("chests/bastion_hoglin_stable").add("chests/bastion_other")
				.add("chests/bastion_treasure").add("chests/end_city_treasure").add("chests/ancient_city").add("chests/ancient_city_ice_box").build()
		}, 2, Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(10, LootModifierHelper.getCommonRunes());
			collection.add(70, LootModifierHelper.getRareRunes());
			collection.add(20, LootModifierHelper.getTreasureRunes());
		})));
		this.add("golden_book_modifier", new GoldenBookModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder("chests/stronghold_library").add("chests/bastion_treasure").add("chests/end_city_treasure").add("chests/ancient_city").add("chests/ancient_city_ice_box").build()
		}, 2, Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(20, LootModifierHelper.getCommonEnchantments());
			collection.add(40, LootModifierHelper.getRareEnchantments());
			collection.add(25, LootModifierHelper.getVeryRareEnchantments());
			collection.add(15, LootModifierHelper.getTreasureEnchantments());
		}), LootModifierHelper.getExtraOverworldTreasure(), LootModifierHelper.getExtraNetherTreasure(), LootModifierHelper.getExtraEndTreasure()));
		this.add("diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {}, Items.GOLDEN_APPLE, XSItems.DIAMOND_APPLE.get(), Chance.of(0.07)));
		this.add("enchanted_diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {}, Items.ENCHANTED_GOLDEN_APPLE, XSItems.ENCHANTED_DIAMOND_APPLE.get(), Chance.of(0.03)));
	}
	
	@Override
	public String getName() {
		return "XSurvive Global Loot Modifiers";
	}
	
}