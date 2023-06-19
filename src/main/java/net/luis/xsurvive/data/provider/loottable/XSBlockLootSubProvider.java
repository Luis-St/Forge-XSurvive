package net.luis.xsurvive.data.provider.loottable;

import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
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
 * @author Luis-st
 *
 */

public class XSBlockLootSubProvider extends BlockLootSubProvider {
	
	XSBlockLootSubProvider() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}
	
	@Override
	protected void generate() {
		this.dropSelf(XSBlocks.SMELTING_FURNACE.get());
		this.add(XSBlocks.HONEY_MELON.get(), (block) -> {
			return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(XSItems.HONEY_MELON_SLICE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F)))
					.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(LimitCount.limitCount(IntRange.upperBound(9)))));
		});
		this.add(XSBlocks.HONEY_MELON_STEM.get(), (block) -> {
			return createStemDrops(block, XSItems.HONEY_MELON_SEEDS.get());
		});
		this.add(XSBlocks.ATTACHED_HONEY_MELON_STEM.get(), (block) -> {
			return createAttachedStemDrops(block, XSItems.HONEY_MELON_SEEDS.get());
		});
		this.add(XSBlocks.MYSTIC_FIRE.get(), noDrop());
	}
	
	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return XSBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
	}
}