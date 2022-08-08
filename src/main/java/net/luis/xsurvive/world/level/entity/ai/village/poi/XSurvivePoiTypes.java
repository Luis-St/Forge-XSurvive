package net.luis.xsurvive.world.level.entity.ai.village.poi;

import java.util.Set;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurvivePoiTypes {
	
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
		return new PoiType(Set.copyOf(XSurviveBlocks.SMELTING_FURNACE.get().getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> MOB_HUNTER = POI_TYPES.register("mob_hunter", () -> {
		return new PoiType(Set.copyOf(Blocks.ANVIL.getStateDefinition().getPossibleStates()), 1, 1);
	});
	public static final RegistryObject<PoiType> NETHER_TRADER = POI_TYPES.register("nether_trader", () -> {
		return new PoiType(Set.copyOf(Blocks.RESPAWN_ANCHOR.getStateDefinition().getPossibleStates()), 1, 1);
	});
	
}
