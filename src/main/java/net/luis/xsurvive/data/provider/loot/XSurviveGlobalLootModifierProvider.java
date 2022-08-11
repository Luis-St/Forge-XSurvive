package net.luis.xsurvive.data.provider.loot;

import java.io.IOException;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.WeightCollection;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveGlobalLootModifierProvider extends GlobalLootModifierProvider {
	
	private final DataGenerator generator;
	
	public XSurviveGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, XSurvive.MOD_ID);
		this.generator = generator;
	}
	
	@Override
	public void run(CachedOutput cache) throws IOException {
		WeightCollection<String> collection = new WeightCollection<>();
		collection.add(100, "Test Entry 1");
		collection.add(50, "Test Entry 2");
		collection.add(25, "Test Entry 3");
		DataProvider.saveStable(cache, WeightCollection.codec(Codec.STRING).encodeStart(JsonOps.INSTANCE, collection).getOrThrow(false, s -> {}), this.generator.getOutputFolder().resolve("test.json"));
	}
	
	@Override
	protected void start() {
		
		
		
		
		
//		this.add("multi_drop_modifier", new MultiDropModifier(new LootItemCondition[] {
//			new MatchTool(ItemPredicate.ANY)
//		}));
//		this.add("smelting_modifier", new SmeltingModifier(new LootItemCondition[] {
//			new MatchTool(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(XSurviveEnchantments.SMELTING.get(), MinMaxBounds.Ints.atLeast(1))).build())
//		}));
//		this.add("rune_item_modifier", new RuneItemModifier(new LootItemCondition[] {
//			new LootTableIdsCondition.Builder("chests/stronghold_library").add("chests/stronghold_crossing").add("chests/stronghold_corridor").add("chests/bastion_bridge").add("chests/bastion_hoglin_stable").add("chests/bastion_other")
//				.add("chests/bastion_treasure").add("chests/end_city_treasure").add("chests/ancient_city").add("chests/ancient_city_ice_box").build()
//		}));
//		// Not final solution
//		this.add("golden_book_modifier", new GoldenBookModifier(new LootItemCondition[] {
//			new LootTableIdsCondition.Builder("chests/stronghold_library").add("chests/bastion_treasure").add("chests/end_city_treasure").add("chests/ancient_city").add("chests/ancient_city_ice_box").build()
//		}, Util.make(new WeightCollection<>(), (collection) -> {
//			collection.add(50, EnchantmentList.of(EnchantmentRarity.COMMON, LootModifierHelper.getCommonEnchantments()));
//			collection.add(25, EnchantmentList.of(EnchantmentRarity.RARE, LootModifierHelper.getRareEnchantments()));
//			collection.add(15, EnchantmentList.of(EnchantmentRarity.VERY_RARE, LootModifierHelper.getVeryRareEnchantments()));
//			collection.add(10, EnchantmentList.of(EnchantmentRarity.TREASURE, LootModifierHelper.getTreasureEnchantments()));
//		}), EnchantmentList.of(EnchantmentRarity.TREASURE, LootModifierHelper.getNetherTreasureEnchantments()), EnchantmentList.of(EnchantmentRarity.TREASURE, LootModifierHelper.getEndTreasureEnchantments())));
//		this.add("diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {}, Items.GOLDEN_APPLE, XSurviveItems.DIAMOND_APPLE.get(), Chance.of(0.07)));
//		this.add("enchanted_diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {}, Items.ENCHANTED_GOLDEN_APPLE, XSurviveItems.ENCHANTED_DIAMOND_APPLE.get(), Chance.of(0.03)));
	}
	
	@Override
	public String getName() {
		return "XSurvive Global Loot Modifiers";
	}
	
}