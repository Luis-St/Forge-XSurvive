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
	
	public static final RegistryObject<Block> JADE_ORE = RegistryObject.create(new ResourceLocation("xores", "jade_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> DEEPSLATE_JADE_ORE = RegistryObject.create(new ResourceLocation("xores", "deepslate_jade_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> SAPHIRE_ORE = RegistryObject.create(new ResourceLocation("xores", "saphire_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> DEEPSLATE_SAPHIRE_ORE = RegistryObject.create(new ResourceLocation("xores", "deepslate_saphire_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> LIMONITE_ORE = RegistryObject.create(new ResourceLocation("xores", "limonite_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> DEEPSLATE_LIMONITE_ORE = RegistryObject.create(new ResourceLocation("xores", "deepslate_limonite_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> ROSITE_ORE = RegistryObject.create(new ResourceLocation("xores", "rosite_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> DEEPSLATE_ROSITE_ORE = RegistryObject.create(new ResourceLocation("xores", "deepslate_rosite_ore"), ForgeRegistries.BLOCKS);
	public static final RegistryObject<Block> ENDERITE_ORE = RegistryObject.create(new ResourceLocation("xores", "enderite_ore"), ForgeRegistries.BLOCKS);
	
	public static void register() {
		
	}
	
}
