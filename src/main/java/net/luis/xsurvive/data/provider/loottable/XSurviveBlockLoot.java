package net.luis.xsurvive.data.provider.loottable;

import java.util.stream.Collectors;

import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveBlockLoot extends BlockLoot {
	
	XSurviveBlockLoot() {
		
	}
	
	@Override
	protected void addTables() {
		this.dropSelf(XSurviveBlocks.SMELTING_FURNACE.get());
		this.add(XSurviveBlocks.HONEY_MELON.get(), (block) -> {
			return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(XSurviveItems.HONEY_MELON_SLICE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F)))
				.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(LimitCount.limitCount(IntRange.upperBound(9)))));
		});
		this.add(XSurviveBlocks.HONEY_MELON_STEM.get(), (block) -> {
			return createStemDrops(block, XSurviveItems.HONEY_MELON_SEEDS.get());
		});
		this.add(XSurviveBlocks.ATTACHED_HONEY_MELON_STEM.get(), (block) -> {
			return createAttachedStemDrops(block, XSurviveItems.HONEY_MELON_SEEDS.get());
		});
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return XSurviveBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
	}

}