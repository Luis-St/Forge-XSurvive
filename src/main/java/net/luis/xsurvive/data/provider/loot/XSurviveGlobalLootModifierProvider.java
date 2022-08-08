package net.luis.xsurvive.data.provider.loot;

import net.luis.xores.world.level.storage.loot.SmeltingModifier;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.luis.xsurvive.world.level.storage.loot.GoldenBookModifier;
import net.luis.xsurvive.world.level.storage.loot.MultiDropModifier;
import net.luis.xsurvive.world.level.storage.loot.RuneItemModifier;
import net.luis.xsurvive.world.level.storage.loot.predicates.LootTableIdsCondition;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveGlobalLootModifierProvider extends GlobalLootModifierProvider {

	public XSurviveGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, XSurvive.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("multi_drop_modifier", new MultiDropModifier(new LootItemCondition[] {
				new MatchTool(ItemPredicate.ANY)
		}));
		this.add("smelting_modifier", new SmeltingModifier(new LootItemCondition[] {
				new MatchTool(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(XSurviveEnchantments.SMELTING.get(), MinMaxBounds.Ints.atLeast(1))).build())
		}));
		this.add("rune_item_modifier", new RuneItemModifier(new LootItemCondition[] {
				new LootTableIdsCondition.Builder("chests/stronghold_library").add("minecraft:chests/stronghold_crossing").add("minecraft:chests/stronghold_corridor").add("minecraft:chests/bastion_bridge").add("minecraft:chests/bastion_hoglin_stable")
					.add("minecraft:chests/bastion_other").add("minecraft:chests/bastion_treasure").add("minecraft:chests/end_city_treasure").add("minecraft:chests/ancient_city").add("minecraft:chests/ancient_city_ice_box").build()
		}));
		this.add("golden_book_modifier", new GoldenBookModifier(new LootItemCondition[] {
				new LootTableIdsCondition.Builder(new ResourceLocation("chests/stronghold_library")).add("minecraft:chests/bastion_treasure").add("minecraft:chests/end_city_treasure").add("minecraft:chests/ancient_city")
					.add("minecraft:chests/ancient_city_ice_box").build()
		}));
	}
	
	@Override
	public String getName() {
		return "XSurvive Global Loot Modifiers";
	}

}