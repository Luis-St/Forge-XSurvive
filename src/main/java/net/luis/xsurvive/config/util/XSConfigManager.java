package net.luis.xsurvive.config.util;

import com.google.common.base.Suppliers;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.luis.xsurvive.config.ClientConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class XSConfigManager {
	
	public static final Logger LOGGER = LogUtils.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FOLDER = FMLPaths.GAMEDIR.get().resolve("xsurvive/config");
	
	
	public static void register() {}
	
	public static <T extends XSConfig> @NotNull Supplier<T> register(String name, @NotNull XSConfigType type, Codec<T> codec, T defaultConfig) {
		try {
			Path file = FOLDER.resolve(type.getName()).resolve(name + ".json");
			if (Files.notExists(file)) {
				LOGGER.info("Config '{}' of type '{}' does not exist, creating new config", name, type.getName());
				Files.createDirectories(file.getParent());
				return createNewConfig(file, codec, defaultConfig);
			}
			try {
				LOGGER.info("Loading config '{}' of type '{}'", name, type);
				return loadConfig(name, type, file, codec, defaultConfig);
			} catch (Exception e) {
				LOGGER.error("Failed to load config '{}' of type '{}' from file '{}'", name, type, file, e);
				Files.copy(file, file.resolveSibling(file.getFileName() + ".error"));
				LOGGER.info("Copied invalid config file to '{}'", file.getFileName() + ".error");
				Files.deleteIfExists(file);
				return createNewConfig(file, codec, defaultConfig);
			}
		} catch (Exception e) {
			LOGGER.error("Failed to load config '{}' of type '{}' using default config instead", name, type, e);
			return Suppliers.memoize(() -> {
				defaultConfig.loaded();
				return defaultConfig;
			});
		}
	}
	
	private static <T extends XSConfig> @NotNull Supplier<T> createNewConfig(Path file, @NotNull Codec<T> codec, @NotNull T defaultConfig) throws Exception {
		Files.createFile(file);
		JsonElement element = codec.encodeStart(JsonOps.INSTANCE, defaultConfig).getOrThrow(false, s -> {});
		try (Writer writer = Files.newBufferedWriter(file)) {
			GSON.toJson(element, writer);
			writer.flush();
		}
		return Suppliers.memoize(() -> {
			defaultConfig.loaded();
			return defaultConfig;
		});
	}
	
	private static <T extends XSConfig> @NotNull Supplier<T> loadConfig(String name, XSConfigType type, @NotNull Path file, @NotNull Codec<T> codec, @NotNull T defaultConfig) throws Exception {
		JsonElement element = GSON.fromJson(Files.newBufferedReader(file), JsonElement.class);
		T config = codec.decode(JsonOps.INSTANCE, element).getOrThrow(false, s -> {}).getFirst();
		return Suppliers.memoize(() -> {
			config.loaded();
			return config;
		});
	}
}
