package net.luis.xsurvive.config.configs.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.luis.xsurvive.config.util.XSConfig;

import static net.luis.xsurvive.config.util.XSConfigManager.*;

/**
 *
 * @author Luis-St
 *
 */

public record BeaconConfig(boolean allowAmplifierStacking, BeaconConfig.Range range) implements XSConfig {
	
	public static final Codec<BeaconConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.BOOL.fieldOf("allowAmplifierStacking").forGetter(BeaconConfig::allowAmplifierStacking),
		BeaconConfig.Range.CODEC.fieldOf("range").forGetter(BeaconConfig::range)
	).apply(instance, BeaconConfig::new));
	public static final BeaconConfig DEFAULT = new BeaconConfig(true, new BeaconConfig.Range(2, 3));
	
	@Override
	public void loaded() {
		LOGGER.info("Beacon config loaded");
		LOGGER.debug("allowAmplifierStacking: {}", this.allowAmplifierStacking());
		LOGGER.debug("range.fullDiamondMultiplier: {}", this.range.fullDiamondMultiplier());
		LOGGER.debug("range.fullNetheriteMultiplier: {}", this.range.fullNetheriteMultiplier());
	}
	
	public static record Range(int fullDiamondMultiplier, int fullNetheriteMultiplier) {
		
		public static final Codec<BeaconConfig.Range> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("fullDiamondMultiplier").forGetter(BeaconConfig.Range::fullDiamondMultiplier),
			Codec.INT.fieldOf("fullNetheriteMultiplier").forGetter(BeaconConfig.Range::fullNetheriteMultiplier)
		).apply(instance, BeaconConfig.Range::new));
	}
}
