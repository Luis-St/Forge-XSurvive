package net.luis.xsurvive.config.util;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.luis.xsurvive.config.configs.ClientConfig;
import net.luis.xsurvive.config.configs.blocks.*;
import net.luis.xsurvive.config.scripts.blocks.BeaconScript;
import net.luis.xsurvive.config.scripts.blocks.ConduitScript;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class XSConfigManager {
	
	private static final List<ConfigFile<?>> CONFIG_FILES = Lists.newArrayList();
	private static final List<ScriptFile> SCRIPT_FILES = Lists.newArrayList();
	private static final MutableBoolean SKIP_FIRST_RELOAD = new MutableBoolean(true);
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public static final Supplier<ClientConfig> CLIENT_CONFIG = register(new ConfigFile<>("client", XSConfigType.CLIENT, ClientConfig.CODEC, ClientConfig.DEFAULT));
	
	public static final Supplier<BeaconConfig> BEACON_CONFIG = register(new ConfigFile<>("beacon", XSConfigType.BLOCK, BeaconConfig.CODEC, BeaconConfig.DEFAULT));
	public static final Supplier<ConduitConfig> CONDUIT_CONFIG = register(new ConfigFile<>("conduit", XSConfigType.BLOCK, ConduitConfig.CODEC, ConduitConfig.DEFAULT));
	public static final Supplier<MonsterSpawnerConfig> MONSTER_SPAWNER_CONFIG = register(new ConfigFile<>("monster_spawner", XSConfigType.BLOCK, MonsterSpawnerConfig.CODEC, MonsterSpawnerConfig.DEFAULT));
	
	public static void register() {
		register(BeaconScript.SCRIPT);
		register(ConduitScript.SCRIPT);
	}
	
	public static void reload() {
		if (SKIP_FIRST_RELOAD.isTrue()) {
			SKIP_FIRST_RELOAD.setFalse();
			return;
		}
		LOGGER.info("Reloading config manager");
		CONFIG_FILES.forEach(ConfigFile::reload);
		LOGGER.info("Reloaded {} configs", CONFIG_FILES.size());
		SCRIPT_FILES.forEach(ScriptFile::reload);
		LOGGER.info("Reloaded {} scripts", SCRIPT_FILES.size());
	}
	
	//region Internal
	private static <T extends XSConfig> @NotNull ConfigFile<T> register(@NotNull ConfigFile<T> configFile) {
		CONFIG_FILES.add(Objects.requireNonNull(configFile, "Config file must not be null"));
		return configFile;
	}
	
	private static void register(@NotNull ScriptFile scriptFile) {
		SCRIPT_FILES.add(Objects.requireNonNull(scriptFile, "Script file must not be null"));
	}
	//endregion
}
