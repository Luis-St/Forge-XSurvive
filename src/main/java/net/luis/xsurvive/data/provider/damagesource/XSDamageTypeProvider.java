package net.luis.xsurvive.data.provider.damagesource;

import net.luis.xsurvive.world.damagesource.XSDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeProvider {
	
	public static void create(BootstapContext<DamageType> context) {
		context.register(XSDamageTypes.CURSE_OF_HARMING, new DamageType("curse_of_harming", DamageScaling.NEVER, 0.1F));
	}
}
