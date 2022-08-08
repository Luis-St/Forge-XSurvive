package net.luis.xsurvive.world.level.block.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveBlockEntityTypes {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<SmeltingFurnaceBlockEntity>> SMELTING_FURNACE = BLOCK_ENTITY_TYPES.register("smelting_furnace", () -> {
		return BlockEntityType.Builder.of(SmeltingFurnaceBlockEntity::new, XSurviveBlocks.SMELTING_FURNACE.get()).build(null);
	});
	
}
