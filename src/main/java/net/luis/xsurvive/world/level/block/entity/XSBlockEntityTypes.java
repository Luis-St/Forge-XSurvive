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

package net.luis.xsurvive.world.level.block.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings({ "CodeBlock2Expr", "DataFlowIssue" })
public class XSBlockEntityTypes {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<SmeltingFurnaceBlockEntity>> SMELTING_FURNACE = BLOCK_ENTITY_TYPES.register("smelting_furnace", () -> {
		return BlockEntityType.Builder.of(SmeltingFurnaceBlockEntity::new, XSBlocks.SMELTING_FURNACE.get()).build(null);
	});
}
