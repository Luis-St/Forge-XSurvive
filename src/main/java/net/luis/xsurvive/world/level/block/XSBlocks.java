package net.luis.xsurvive.world.level.block;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, XSurvive.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<SmeltingFurnaceBlock> SMELTING_FURNACE = register("smelting_furnace", () -> {
		return new SmeltingFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).lightLevel((state) -> {
			return state.getValue(BlockStateProperties.LIT) ? 13 : 0;
		}));
	}, true);
	public static final RegistryObject<Block> HONEY_MELON = register("honey_melon", () -> {
		return new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MELON));
	}, true);
	public static final RegistryObject<StemBlock> HONEY_MELON_STEM = register("honey_melon_stem", () -> {
		return new StemBlock(HONEY_MELON.getKey(), null, null, BlockBehaviour.Properties.ofFullCopy(Blocks.MELON_STEM));
	});
	public static final RegistryObject<AttachedStemBlock> ATTACHED_HONEY_MELON_STEM = register("attached_honey_melon_stem", () -> {
		return new AttachedStemBlock(null, HONEY_MELON.getKey(), null, BlockBehaviour.Properties.ofFullCopy(Blocks.ATTACHED_MELON_STEM));
	});
	public static final RegistryObject<MysticFireBlock> MYSTIC_FIRE = register("mystic_fire", () -> {
		return new MysticFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE).lightLevel((state) -> 15));
	});
	
	private static <T extends Block> @NotNull RegistryObject<T> register(@NotNull String name, @NotNull Supplier<T> blockSupplier) {
		return register(name, blockSupplier, false);
	}
	
	private static <T extends Block> @NotNull RegistryObject<T> register(@NotNull String name, @NotNull Supplier<T> blockSupplier, boolean registerItem) {
		RegistryObject<T> blockObject = BLOCKS.register(name, blockSupplier);
		if (registerItem) {
			ITEMS.register(name, () -> new BlockItem(blockObject.get(), new Item.Properties()));
		}
		return blockObject;
	}
}
