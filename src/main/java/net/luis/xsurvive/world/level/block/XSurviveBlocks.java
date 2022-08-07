package net.luis.xsurvive.world.level.block;

import java.util.function.Supplier;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, XSurvive.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	
	public static final RegistryObject<SmeltingFurnaceBlock> SMELTING_FURNACE = register("smelting_furnace", () -> {
		return new SmeltingFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).lightLevel((state) -> {
			return state.getValue(BlockStateProperties.LIT) ? 13 : 0;
		}));
	}, CreativeModeTab.TAB_DECORATIONS);
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, CreativeModeTab tab) {
		RegistryObject<T> blockObject = BLOCKS.register(name, blockSupplier);
		ITEMS.register(name, () -> {
			return new BlockItem(blockObject.get(), new Item.Properties().tab(tab));
		});
		return blockObject;
	}
	
}
