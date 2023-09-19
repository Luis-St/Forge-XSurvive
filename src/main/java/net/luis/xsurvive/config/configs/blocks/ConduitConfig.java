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

public record ConduitConfig(int requiredBlocksForDefence, int attackRange, float defaultDamage, float maxLevelDamage) implements XSConfig {
	
	public static final Codec<ConduitConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("requiredBlocksForDefence").forGetter(ConduitConfig::requiredBlocksForDefence),
		Codec.INT.fieldOf("attackRange").forGetter(ConduitConfig::attackRange),
		Codec.FLOAT.fieldOf("defaultDamage").forGetter(ConduitConfig::defaultDamage),
		Codec.FLOAT.fieldOf("maxLevelDamage").forGetter(ConduitConfig::maxLevelDamage)
	).apply(instance, ConduitConfig::new));
	public static final ConduitConfig DEFAULT = new ConduitConfig(30, 24, 4.0F, 8.0F);
	
	@Override
	public void loaded() {
		LOGGER.info("Conduit config loaded");
		LOGGER.debug("requiredBlocksForDefence: {}", this.requiredBlocksForDefence());
		LOGGER.debug("attackRange: {}", this.attackRange());
		LOGGER.debug("defaultDamage: {}", this.defaultDamage());
		LOGGER.debug("maxLevelDamage: {}", this.maxLevelDamage());
	}
}
