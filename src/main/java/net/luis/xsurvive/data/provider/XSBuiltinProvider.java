package net.luis.xsurvive.data.provider;

import net.luis.xsurvive.data.provider.damagesource.XSDamageTypeProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSBuiltinProvider {
	
	public static @NotNull RegistrySetBuilder createProvider() {
		RegistrySetBuilder builder = new RegistrySetBuilder();
		builder.add(Registries.DAMAGE_TYPE, XSDamageTypeProvider::create);
		return builder;
	}
}
