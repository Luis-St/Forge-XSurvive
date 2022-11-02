package net.luis.xsurvive.world.level.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, XSurvive.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<SmeltingFurnaceBlock> SMELTING_FURNACE = register("smelting_furnace", () -> {
		return new SmeltingFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).lightLevel((state) -> {
			return state.getValue(BlockStateProperties.LIT) ? 13 : 0;
		}));
	}, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<HoneyMelonBlock> HONEY_MELON = register("honey_melon", () -> {
		return new HoneyMelonBlock(BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_LIGHT_GREEN).strength(1.0F).sound(SoundType.WOOD));
	}, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<StemBlock> HONEY_MELON_STEM = register("honey_melon_stem", () -> {
		return new StemBlock(HONEY_MELON.get(), () -> {
			return XSItems.HONEY_MELON_SEEDS.get();
		}, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.HARD_CROP));
	});
	public static final RegistryObject<AttachedStemBlock> ATTACHED_HONEY_MELON_STEM = register("attached_honey_melon_stem", () -> {
		return new AttachedStemBlock(HONEY_MELON.get(), () -> {
			return XSItems.HONEY_MELON_SEEDS.get();
		}, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.WOOD));
	});
	public static final RegistryObject<MysticFireBlock> MYSTIC_FIRE = register("mystic_fire", () ->  {
		return new MysticFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().instabreak().lightLevel((state) -> {
			return 15;
		}).sound(SoundType.WOOL));
	});
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier) {
		return register(name, blockSupplier, false, null);
	}
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable CreativeModeTab tab) {
		return register(name, blockSupplier, true, tab);
	}
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, boolean registerItem, @Nullable CreativeModeTab tab) {
		RegistryObject<T> blockObject = BLOCKS.register(name, blockSupplier);
		if (registerItem) {
			ITEMS.register(name, () -> {
				return new BlockItem(blockObject.get(), new Item.Properties().tab(tab));
			});
		}
		return blockObject;
	}
	
}
