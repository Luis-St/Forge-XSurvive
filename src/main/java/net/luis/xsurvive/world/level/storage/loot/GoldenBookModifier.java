package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.RarityList;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 *
 * @author Luis-St
 *
 */

public class GoldenBookModifier extends LootModifier {
	
	private static final Random RNG = new Random();
	public static final Codec<GoldenBookModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter((modifier) -> {
			return modifier.conditions;
		}), Codec.INT.fieldOf("golden_book_count").forGetter((modifier) -> {
			return modifier.goldenBookCount;
		}), WeightCollection.codec(RarityList.codec(ForgeRegistries.ENCHANTMENTS.getCodec())).fieldOf("enchantments").forGetter((modifier) -> {
			return modifier.enchantmentWeights;
		}), RarityList.codec(ForgeRegistries.ENCHANTMENTS.getCodec()).fieldOf("extra_overworld_treasure").forGetter((modifier) -> {
			return modifier.extraOverworldTreasure;
		}), RarityList.codec(ForgeRegistries.ENCHANTMENTS.getCodec()).fieldOf("extra_nether_treasure").forGetter((modifier) -> {
			return modifier.extraNetherTreasure;
		}), RarityList.codec(ForgeRegistries.ENCHANTMENTS.getCodec()).fieldOf("extra_end_treasure").forGetter((modifier) -> {
			return modifier.extraEndTreasure;
		})).apply(instance, GoldenBookModifier::new);
	});
	private final int goldenBookCount;
	private final WeightCollection<RarityList<Enchantment>> enchantmentWeights;
	private final RarityList<Enchantment> extraOverworldTreasure;
	private final RarityList<Enchantment> extraNetherTreasure;
	private final RarityList<Enchantment> extraEndTreasure;
	
	public GoldenBookModifier(LootItemCondition[] conditions, int goldenBookCount, WeightCollection<RarityList<Enchantment>> enchantmentWeights, RarityList<Enchantment> extraOverworldTreasure, RarityList<Enchantment> extraNetherTreasure,
							  RarityList<Enchantment> extraEndTreasure) {
		super(conditions);
		this.goldenBookCount = goldenBookCount;
		this.enchantmentWeights = enchantmentWeights;
		this.extraOverworldTreasure = extraOverworldTreasure;
		this.extraNetherTreasure = extraNetherTreasure;
		this.extraEndTreasure = extraEndTreasure;
	}
	
	@Override
	public @NotNull Codec<GoldenBookModifier> codec() {
		return XSGlobalLootModifiers.GOLDEN_BOOK_MODIFIER.get();
	}
	
	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
		for (int i = 0; i < this.goldenBookCount; i++) {
			generatedLoot.add(this.getGoldenBook(context));
		}
		return generatedLoot;
	}
	
	private @NotNull ItemStack getGoldenBook(@NotNull LootContext context) {
		ItemStack stack = new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		Enchantment enchantment = this.getRandomEnchantment(context.getQueriedLootTableId(), 0);
		if (enchantment != null && stack.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
			goldenBook.setEnchantment(stack, enchantment);
			return stack;
		}
		XSurvive.LOGGER.error("Fail to get a golden enchantment for the enchanted golden book in loot table {}", context.getQueriedLootTableId());
		return ItemStack.EMPTY;
	}
	
	private @Nullable Enchantment getRandomEnchantment(@NotNull ResourceLocation location, int tries) {
		RarityList<Enchantment> enchantments = RarityList.copy(this.enchantmentWeights.next());
		if (enchantments.getRarity() == this.extraNetherTreasure.getRarity() && location.equals(BuiltInLootTables.BASTION_TREASURE)) {
			enchantments.addAll(this.extraNetherTreasure);
		} else if (enchantments.getRarity() == this.extraEndTreasure.getRarity() && location.equals(BuiltInLootTables.END_CITY_TREASURE)) {
			enchantments.addAll(this.extraEndTreasure);
		} else if (enchantments.getRarity() == this.extraOverworldTreasure.getRarity()) {
			enchantments.addAll(this.extraOverworldTreasure);
		}
		Enchantment enchantment = enchantments.get(RNG.nextInt(enchantments.size()));
		if (enchantment instanceof IEnchantment ench) {
			if (ench.isAllowedOnGoldenBooks()) {
				return enchantment;
			} else if (10 > tries) {
				XSurvive.LOGGER.warn("Enchantment {} is not allowed on golden books", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return this.getRandomEnchantment(location, tries + 1);
			} else {
				XSurvive.LOGGER.error("Found no valid enchantment for the enchanted golden book in loot table {} after 10 tries", location);
				return null;
			}
		}
		XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
		return null;
	}
}
