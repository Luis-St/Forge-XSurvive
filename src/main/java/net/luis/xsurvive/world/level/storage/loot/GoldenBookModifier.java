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

package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.RarityList;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class GoldenBookModifier extends LootModifier {
	
	private static final Random RNG = new Random();
	public static final MapCodec<GoldenBookModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
		return instance.group(LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter((modifier) -> {
			return modifier.conditions;
		}), Codec.INT.fieldOf("golden_book_count").forGetter((modifier) -> {
			return modifier.goldenBookCount;
		}), WeightCollection.codec(RarityList.codec(Enchantment.CODEC)).fieldOf("enchantments").forGetter((modifier) -> {
			return modifier.enchantmentWeights;
		}), RarityList.codec(Enchantment.CODEC).fieldOf("extra_overworld_treasure").forGetter((modifier) -> {
			return modifier.extraOverworldTreasure;
		}), RarityList.codec(Enchantment.CODEC).fieldOf("extra_nether_treasure").forGetter((modifier) -> {
			return modifier.extraNetherTreasure;
		}), RarityList.codec(Enchantment.CODEC).fieldOf("extra_end_treasure").forGetter((modifier) -> {
			return modifier.extraEndTreasure;
		})).apply(instance, GoldenBookModifier::new);
	});
	private final int goldenBookCount;
	private final WeightCollection<RarityList<Holder<Enchantment>>> enchantmentWeights;
	private final RarityList<Holder<Enchantment>> extraOverworldTreasure;
	private final RarityList<Holder<Enchantment>> extraNetherTreasure;
	private final RarityList<Holder<Enchantment>> extraEndTreasure;
	
	public GoldenBookModifier(
		LootItemCondition @NotNull [] conditions,
		int goldenBookCount,
		@NotNull WeightCollection<RarityList<Holder<Enchantment>>> enchantmentWeights,
		@NotNull RarityList<Holder<Enchantment>> extraOverworldTreasure,
		@NotNull RarityList<Holder<Enchantment>> extraNetherTreasure,
		@NotNull RarityList<Holder<Enchantment>> extraEndTreasure
	) {
		super(conditions);
		this.goldenBookCount = goldenBookCount;
		this.enchantmentWeights = enchantmentWeights;
		this.extraOverworldTreasure = extraOverworldTreasure;
		this.extraNetherTreasure = extraNetherTreasure;
		this.extraEndTreasure = extraEndTreasure;
	}
	
	@Override
	public @NotNull MapCodec<GoldenBookModifier> codec() {
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
		Holder<Enchantment> enchantment = this.getRandomEnchantment(context.getQueriedLootTableId(), 0);
		if (enchantment != null && stack.getItem() instanceof EnchantedGoldenBookItem) {
			EnchantedGoldenBookItem.setEnchantment(stack, enchantment);
			return stack;
		}
		XSurvive.LOGGER.error("Fail to get a golden enchantment for the enchanted golden book in loot table '{}'", context.getQueriedLootTableId());
		return ItemStack.EMPTY;
	}
	
	private @Nullable Holder<Enchantment> getRandomEnchantment(@NotNull ResourceLocation location, int tries) {
		RarityList<Holder<Enchantment>> enchantments = RarityList.copy(this.enchantmentWeights.next());
		if (enchantments.getRarity() == this.extraNetherTreasure.getRarity() && location.equals(BuiltInLootTables.BASTION_TREASURE.location())) {
			enchantments.addAll(this.extraNetherTreasure);
		} else if (enchantments.getRarity() == this.extraEndTreasure.getRarity() && location.equals(BuiltInLootTables.END_CITY_TREASURE.location())) {
			enchantments.addAll(this.extraEndTreasure);
		} else if (enchantments.getRarity() == this.extraOverworldTreasure.getRarity()) {
			enchantments.addAll(this.extraOverworldTreasure);
		}
		Holder<Enchantment> enchantment = enchantments.get(RNG.nextInt(enchantments.size()));
		if (GoldenEnchantmentHelper.isGoldenEnchantment(enchantment) || GoldenEnchantmentHelper.isUpgradeEnchantment(enchantment)) {
			return enchantment;
		} else if (10 > tries) {
			XSurvive.LOGGER.warn("Enchantment '{}' is not allowed on golden books", enchantment.getRegisteredName());
			return this.getRandomEnchantment(location, tries + 1);
		} else {
			XSurvive.LOGGER.error("Found no valid enchantment for the enchanted golden book in loot table {} after 10 tries", location);
			return null;
		}
	}
}
