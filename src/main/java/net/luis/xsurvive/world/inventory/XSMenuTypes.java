package net.luis.xsurvive.world.inventory;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSMenuTypes {
	
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<MenuType<SmeltingFurnaceMenu>> SMELTING_FURNACE = MENU_TYPES.register("smelting_furnace", () -> {
		return IForgeMenuType.create(SmeltingFurnaceMenu::new);
	});
	public static final RegistryObject<MenuType<EnderChestMenu>> ENDER_CHEST = MENU_TYPES.register("ender_chest", () -> {
		return IForgeMenuType.create(EnderChestMenu::new);
	});
}
