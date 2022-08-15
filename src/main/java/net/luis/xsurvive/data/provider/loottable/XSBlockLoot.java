package net.luis.xsurvive.data.provider.loottable;

import java.util.stream.Collectors;

import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
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

public class XSBlockLoot extends BlockLoot {
	
	XSBlockLoot() {
		
	}
	
	@Override
	protected void addTables() {
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
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return XSBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
	}

}