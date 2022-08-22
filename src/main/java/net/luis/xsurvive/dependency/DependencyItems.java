package net.luis.xsurvive.dependency;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class DependencyItems {
	
	public static final RegistryObject<Item> SAPHIRE_INGOT = RegistryObject.create(new ResourceLocation("xores", "saphire_ingot"), ForgeRegistries.ITEMS);
	
	public static final RegistryObject<Item> BLAZING_INGOT = RegistryObject.create(new ResourceLocation("xores", "blazing_ingot"), ForgeRegistries.ITEMS);
	
	public static final RegistryObject<Item> POLISHED_ROSE_QUARTZ = RegistryObject.create(new ResourceLocation("xores", "polished_rose_quartz"), ForgeRegistries.ITEMS);
	
	public static final RegistryObject<Item> ENDERITE_SCRAP = RegistryObject.create(new ResourceLocation("xores", "enderite_scrap"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_HELMET = RegistryObject.create(new ResourceLocation("xores", "enderite_helmet"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_CHESTPLATE = RegistryObject.create(new ResourceLocation("xores", "enderite_chestplate"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_LEGGINGS = RegistryObject.create(new ResourceLocation("xores", "enderite_leggings"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_BOOTS = RegistryObject.create(new ResourceLocation("xores", "enderite_boots"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_SWORD = RegistryObject.create(new ResourceLocation("xores", "enderite_sword"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> ENDERITE_AXE = RegistryObject.create(new ResourceLocation("xores", "enderite_axe"), ForgeRegistries.ITEMS);
	
	public static void register() {
		
	}
	
}
