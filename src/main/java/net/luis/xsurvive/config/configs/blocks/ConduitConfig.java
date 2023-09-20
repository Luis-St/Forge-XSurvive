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

public record ConduitConfig(int attackRange, ConduitConfig.RequiredBlocks requiredBlocks, ConduitConfig.Damage damage) implements XSConfig {
	
	public static final Codec<ConduitConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("attackRange").forGetter(ConduitConfig::attackRange),
		ConduitConfig.RequiredBlocks.CODEC.fieldOf("requiredBlocks").forGetter(ConduitConfig::requiredBlocks),
		ConduitConfig.Damage.CODEC.fieldOf("damage").forGetter(ConduitConfig::damage)
	).apply(instance, ConduitConfig::new));
	public static final ConduitConfig DEFAULT = new ConduitConfig(30, new ConduitConfig.RequiredBlocks(42, 24), new ConduitConfig.Damage(4.0F, 8.0F));
	
	@Override
	public void loaded() {
		LOGGER.info("Conduit config loaded");
		LOGGER.debug("attackRange: {}", this.attackRange());
		LOGGER.debug("requiredBlocks.defence: {}", this.requiredBlocks.defence());
		LOGGER.debug("requiredBlocks.maxOut: {}", this.requiredBlocks.maxOut());
		LOGGER.debug("damage.default {}", this.damage.normal());
		LOGGER.debug("damage.maxLevel: {}", this.damage.maxLevel());
	}
	
	public static record RequiredBlocks(int defence, int maxOut) {
		
		public static final Codec<ConduitConfig.RequiredBlocks> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("defence").forGetter(ConduitConfig.RequiredBlocks::defence),
			Codec.INT.fieldOf("maxOut").forGetter(ConduitConfig.RequiredBlocks::maxOut)
		).apply(instance, ConduitConfig.RequiredBlocks::new));
	}
	
	public static record Damage(float normal, float maxLevel) {
		
		public static final Codec<ConduitConfig.Damage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("default").forGetter(ConduitConfig.Damage::normal),
			Codec.FLOAT.fieldOf("maxLevel").forGetter(ConduitConfig.Damage::maxLevel)
		).apply(instance, ConduitConfig.Damage::new));
	}
}
