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

package net.luis.xsurvive.world.entity.ai.village;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.*;

import java.util.Set;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSPoiTypes {
	
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<PoiType> BEEKEEPER = POI_TYPES.register("beekeeper", () -> {
		return new PoiType(Set.copyOf(Blocks.BEEHIVE.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> ENCHANTER = POI_TYPES.register("enchanter", () -> {
		return new PoiType(Set.copyOf(Blocks.ENCHANTING_TABLE.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> END_TRADER = POI_TYPES.register("end_trader", () -> {
		return new PoiType(Set.copyOf(Blocks.ENDER_CHEST.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> LUMBERJACK = POI_TYPES.register("lumberjack", () -> {
		return new PoiType(Set.copyOf(Blocks.CRAFTING_TABLE.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> MINER = POI_TYPES.register("miner", () -> {
		return new PoiType(Set.copyOf(XSBlocks.SMELTING_FURNACE.get().getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> MOB_HUNTER = POI_TYPES.register("mob_hunter", () -> {
		return new PoiType(Set.copyOf(Blocks.ANVIL.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> NETHER_TRADER = POI_TYPES.register("nether_trader", () -> {
		return new PoiType(Set.copyOf(Blocks.RESPAWN_ANCHOR.getStateDefinition().getPossibleStates()), 1, 1);
	});
}
