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

package net.luis.xsurvive.data.provider.base.server;

import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSBlockLootSubProvider extends BlockLootSubProvider {
	
	XSBlockLootSubProvider(HolderLookup.@NotNull Provider lookup) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
	}
	
	@Override
	protected void generate() {
		this.dropSelf(XSBlocks.SMELTING_FURNACE.get());
		this.add(XSBlocks.HONEY_MELON.get(), (block) -> {
			return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(XSItems.HONEY_MELON_SLICE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F)))
				.apply(ApplyBonusCount.addUniformBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))).apply(LimitCount.limitCount(IntRange.upperBound(9)))));
		});
		this.add(XSBlocks.HONEY_MELON_STEM.get(), (block) -> {
			return this.createStemDrops(block, XSItems.HONEY_MELON_SEEDS.get());
		});
		this.add(XSBlocks.ATTACHED_HONEY_MELON_STEM.get(), (block) -> {
			return this.createAttachedStemDrops(block, XSItems.HONEY_MELON_SEEDS.get());
		});
		this.add(XSBlocks.MYSTIC_FIRE.get(), noDrop());
	}
	
	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return XSBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
	}
}
