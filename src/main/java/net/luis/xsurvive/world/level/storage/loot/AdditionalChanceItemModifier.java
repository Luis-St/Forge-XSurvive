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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.util.Chance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class AdditionalChanceItemModifier extends LootModifier {
	
	public static final MapCodec<AdditionalChanceItemModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
		return LootModifier.codecStart(instance).and(instance.group(ForgeRegistries.ITEMS.getCodec().fieldOf("additional_item").forGetter((modifier) -> {
			return modifier.additionalItem;
		}), Chance.CODEC.fieldOf("chance").forGetter((modifier) -> {
			return modifier.chance;
		}))).apply(instance, AdditionalChanceItemModifier::new);
	});
	
	private final Item additionalItem;
	private final Chance chance;
	
	public AdditionalChanceItemModifier(LootItemCondition @NotNull [] conditions, @NotNull Item additionalItem, @NotNull Chance chance) {
		super(conditions);
		this.additionalItem = additionalItem;
		this.chance = chance;
	}
	
	@Override
	public @NotNull MapCodec<AdditionalChanceItemModifier> codec() {
		return XSGlobalLootModifiers.ADDITIONAL_CHANCE_ITEM_MODIFIER.get();
	}
	
	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
		if (this.chance.result()) {
			generatedLoot.add(new ItemStack(this.additionalItem));
		}
		return generatedLoot;
	}
}
