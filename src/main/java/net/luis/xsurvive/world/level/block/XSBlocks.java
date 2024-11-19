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

package net.luis.xsurvive.world.level.block;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.core.XSResourceKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, XSurvive.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<SmeltingFurnaceBlock> SMELTING_FURNACE = register("smelting_furnace", BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE), (properties) -> {
		return new SmeltingFurnaceBlock(properties.lightLevel((state) -> {
			return state.getValue(BlockStateProperties.LIT) ? 13 : 0;
		}));
	});
	public static final RegistryObject<Block> HONEY_MELON = register("honey_melon", BlockBehaviour.Properties.ofFullCopy(Blocks.MELON), Block::new);
	public static final RegistryObject<Block> HONEY_MELON_STEM = registerNoBlockItem("honey_melon_stem", BlockBehaviour.Properties.ofFullCopy(Blocks.MELON_STEM), (properties) -> {
		return new StemBlock(XSResourceKeys.HONEY_MELON, XSResourceKeys.ATTACHED_HONEY_MELON_STEM, XSResourceKeys.HONEY_MELON_SEEDS, properties);
	});
	public static final RegistryObject<Block> ATTACHED_HONEY_MELON_STEM = registerNoBlockItem("attached_honey_melon_stem", BlockBehaviour.Properties.ofFullCopy(Blocks.ATTACHED_MELON_STEM), (properties) -> {
		return new AttachedStemBlock(XSResourceKeys.HONEY_MELON_STEM, XSResourceKeys.HONEY_MELON, XSResourceKeys.HONEY_MELON_SEEDS, properties);
	});
	public static final RegistryObject<MysticFireBlock> MYSTIC_FIRE = registerNoBlockItem("mystic_fire", BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE), (properties) -> {
		return new MysticFireBlock(properties.lightLevel((state) -> 15));
	});
	
	private static <T extends Block> @NotNull RegistryObject<T> register(@NotNull String name, BlockBehaviour.@NotNull Properties properties, @NotNull Function<BlockBehaviour.Properties, T> block) {
		RegistryObject<T> blockObject = BLOCKS.register(name, () -> block.apply(properties.setId(XSResourceKeys.createBlockKey(name))));
		ITEMS.register(name, () -> new BlockItem(blockObject.get(), new Item.Properties().setId(XSResourceKeys.createItemKey(name))));
		return blockObject;
	}
	
	private static <T extends Block> @NotNull RegistryObject<T> registerNoBlockItem(@NotNull String name, BlockBehaviour.@NotNull Properties properties, @NotNull Function<BlockBehaviour.Properties, T> block) {
		return BLOCKS.register(name, () -> block.apply(properties.setId(XSResourceKeys.createBlockKey(name))));
	}
}
