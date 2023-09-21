package net.luis.xsurvive.config.util;

import com.google.common.base.Suppliers;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static net.luis.xsurvive.config.util.XSConfigManager.*;

/**
 *
 * @author Luis-St
 *
 */

public class ConfigFile<T extends XSConfig> implements Supplier<T> {
	// TODO: Copy logging logic from ScriptFile -> Remove logging statements
	// TODO: Skip first vanilla reload
	// TODO: Sync config and script files to client into folder "xsurvive/synced"
	
	private static final Path CONFIG_FOLDER = FMLPaths.GAMEDIR.get().resolve("xsurvive/config/");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private final String name;
	private final XSConfigType type;
	private final Function<String, T> operation;
	private Supplier<T> config;
	
	public ConfigFile(String name, XSConfigType type, Codec<T> codec, T defaultConfig) {
		this.name = Objects.requireNonNull(name, "Name must not be null") + ".json";
		this.type = Objects.requireNonNull(type, "Type must not be null");
		this.operation = (mode) -> {
			try {
				Path file = CONFIG_FOLDER.resolve(type.getName()).resolve(name + ".json");
				if (Files.notExists(file)) {
					Files.createDirectories(file.getParent());
					return createNewConfig(file, codec, defaultConfig);
				}
				try {
					return loadConfig(file, codec);
				} catch (Exception e) {
					LOGGER.error("Failed to {} config '{}' of type '{}' from file '{}' (maybe the config is outdated?)", mode.toLowerCase(), this.name, type, file, e);
					Files.copy(file, file.resolveSibling(file.getFileName() + ".error"));
					LOGGER.info("Copied invalid config file to '{}'", file.getFileName() + ".error");
					Files.deleteIfExists(file);
					return createNewConfig(file, codec, defaultConfig);
				}
			} catch (Exception e) {
				LOGGER.error("Failed to load config '{}' of type '{}' using default config instead", this.name, type, e);
				return defaultConfig;
			}
		};
		this.load();
	}
	
	//region Internal
	private static <T extends XSConfig> @NotNull T createNewConfig(Path file, @NotNull Codec<T> codec, @NotNull T defaultConfig) throws Exception {
		Files.createFile(file);
		JsonElement element = codec.encodeStart(JsonOps.INSTANCE, defaultConfig).getOrThrow(false, s -> {});
		try (Writer writer = Files.newBufferedWriter(file)) {
			GSON.toJson(element, writer);
			writer.flush();
		}
		return defaultConfig;
	}
	
	private static <T extends XSConfig> @NotNull T loadConfig(@NotNull Path file, @NotNull Codec<T> codec) throws Exception {
		JsonElement element = GSON.fromJson(Files.newBufferedReader(file), JsonElement.class);
		return codec.decode(JsonOps.INSTANCE, element).getOrThrow(false, s -> {}).getFirst();
	}
	//endregion
	
	private void load() {
		CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> this.operation.apply("Load"));
		future.thenAccept(config -> {
			LOGGER.info("Loading config '{}'", this.name);
			config.loaded();
		});
		this.config = Suppliers.memoize(future::join);
	}
	
	public void reload() {
		CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> this.operation.apply("Reload"));
		future.thenAccept(config -> {
			LOGGER.info("Reloading config '{}'", this.name);
			config.loaded();
		});
		this.config = Suppliers.memoize(future::join);
	}
	
	@Override
	public @NotNull T get() {
		if (this.config == null) {
			LOGGER.warn("Config '{}' of type '{}' was accessed before it was loaded", this.name, this.type);
			this.load();
		}
		return this.config.get();
	}
	
	public <V> @NotNull V getSub(@NotNull Function<T, V> function) {
		return function.apply(this.get());
	}
	
	@Override
	public String toString() {
		return "Config '" + this.name + "' of type '" + this.type + "'";
	}
}
