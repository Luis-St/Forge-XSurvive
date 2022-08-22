package net.luis.xsurvive.dependency;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class DependencyBlocks {
	
	public static final RegistryObject<Block> SAPHIRE_ORE = RegistryObject.create(new ResourceLocation("xores", "saphire_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> DEEPSLATE_SAPHIRE_ORE = RegistryObject.create(new ResourceLocation("xores", "deepslate_saphire_ore"), ForgeRegistries.BLOCKS);
	
	public static void register() {
		
	}
	
}
