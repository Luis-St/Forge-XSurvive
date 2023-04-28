package net.luis.xsurvive.world.damagesource;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypes {
	
	public static final ResourceKey<DamageType> CURSE_OF_HARMING = createKey("curse_of_harming");
	
	public static void register() {
	
	}
	
	private static ResourceKey<DamageType> createKey(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(XSurvive.MOD_ID, name));
	}

}
