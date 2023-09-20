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

public record MonsterSpawnerConfig(MonsterSpawnerConfig.Settings settings, AllowDestroy allowDestroy) implements XSConfig {
	
	public static final Codec<MonsterSpawnerConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		MonsterSpawnerConfig.Settings.CODEC.fieldOf("settings").forGetter(MonsterSpawnerConfig::settings),
		MonsterSpawnerConfig.AllowDestroy.CODEC.fieldOf("allowDestroy").forGetter(MonsterSpawnerConfig::allowDestroy)
	).apply(instance, MonsterSpawnerConfig::new));
	public static final MonsterSpawnerConfig DEFAULT = new MonsterSpawnerConfig(new MonsterSpawnerConfig.Settings(50, 200, 4, 64, 12), new AllowDestroy(false, false));
	
	@Override
	public void loaded() {
		LOGGER.info("Monster spawner config loaded");
		LOGGER.debug("settings.minSpawnDelay: {}", this.settings.minSpawnDelay());
		LOGGER.debug("settings.maxSpawnDelay: {}", this.settings.maxSpawnDelay());
		LOGGER.debug("settings.spawnCount: {}", this.settings.spawnCount());
		LOGGER.debug("settings.requiredPlayerRange: {}", this.settings.requiredPlayerRange());
		LOGGER.debug("settings.maxNearbyEntities: {}", this.settings.maxNearbyEntities());
		LOGGER.debug("allowDestroy.byPlayer: {}", this.allowDestroy.byPlayer());
		LOGGER.debug("allowDestroy.byExplosion: {}", this.allowDestroy.byExplosion());
	}
	
	public static record Settings(int minSpawnDelay, int maxSpawnDelay, int spawnCount, int requiredPlayerRange, int maxNearbyEntities) {
		
		public static final Codec<MonsterSpawnerConfig.Settings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("minSpawnDelay").forGetter(Settings::minSpawnDelay),
			Codec.INT.fieldOf("maxSpawnDelay").forGetter(Settings::maxSpawnDelay),
			Codec.INT.fieldOf("spawnCount").forGetter(Settings::spawnCount),
			Codec.INT.fieldOf("requiredPlayerRange").forGetter(Settings::requiredPlayerRange),
			Codec.INT.fieldOf("maxNearbyEntities").forGetter(Settings::maxNearbyEntities)
		).apply(instance, Settings::new));
	}
	
	public static record AllowDestroy(boolean byPlayer, boolean byExplosion) {
		
		public static final Codec<MonsterSpawnerConfig.AllowDestroy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.fieldOf("byPlayer").forGetter(AllowDestroy::byPlayer),
			Codec.BOOL.fieldOf("byExplosion").forGetter(AllowDestroy::byExplosion)
		).apply(instance, AllowDestroy::new));
	}
}
