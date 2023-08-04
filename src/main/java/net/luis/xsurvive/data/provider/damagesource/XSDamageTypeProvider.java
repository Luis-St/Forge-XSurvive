package net.luis.xsurvive.data.provider.damagesource;

import net.luis.xsurvive.world.damagesource.XSDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeProvider {
	
	public static void create(@NotNull BootstapContext<DamageType> context) {
		context.register(XSDamageTypes.CURSE_OF_HARMING, new DamageType("curse_of_harming", DamageScaling.NEVER, 0.1F));
	}
}
