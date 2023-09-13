package net.luis.xsurvive.config;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface ModConfig {
	
	@NotNull ConfigType getType();
	
	@NotNull Codec<? extends ModConfig> codec();
}
