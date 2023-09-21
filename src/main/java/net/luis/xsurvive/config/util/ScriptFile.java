package net.luis.xsurvive.config.util;

import com.google.common.base.Suppliers;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.luis.xsurvive.config.util.XSConfigManager.*;

public class ScriptFile {
	
	private static final NashornScriptEngineFactory ENGINE_FACTORY = new NashornScriptEngineFactory();
	private static final Path SCRIPT_FOLDER = FMLPaths.GAMEDIR.get().resolve("xsurvive/script/");
	
	private final Path scriptFile;
	private final URL defaultFile;
	private final Function<String, Invocable> operation;
	private Supplier<Invocable> function;
	
	public ScriptFile(String folder, String name) {
		this.scriptFile = SCRIPT_FOLDER.resolve(folder).resolve(name + ".js");
		this.defaultFile = ScriptFile.class.getResource("/scripts/" + folder + "/" + name + ".js");
		Objects.requireNonNull(this.defaultFile, "There is no default script inside the jar, report this to the mod author");
		this.operation = (mode) -> {
			try {
				Path file = SCRIPT_FOLDER.resolve(folder).resolve(name + ".js");
				if (Files.notExists(file)) {
					Files.createDirectories(file.getParent());
					return createNewScript(file, this.defaultFile);
				}
				try {
					return loadScript(file);
				} catch (Exception e) {
					LOGGER.error("Failed to {} script '{}' from file '{}' (maybe the script is outdated?)", mode.toLowerCase(), this.scriptFile.getFileName(), file, e);
					Files.copy(file, file.resolveSibling(file.getFileName() + ".error"));
					LOGGER.info("Copied invalid script file to '{}'", file.getFileName() + ".error");
					Files.deleteIfExists(file);
					return createNewScript(file, this.defaultFile);
				}
			} catch (Exception e) {
				throw new RuntimeException("Failed to load script '" + this.scriptFile.getFileName() + "' from script folder and jar, report this to the mod author", e);
			}
		};
		this.load();
	}
	
	//region Internal
	private static @NotNull Invocable createNewScript(Path file, @NotNull URL defaultFile) throws IOException, ScriptException {
		Files.copy(defaultFile.openStream(), file);
		try (Reader reader = new FileReader(file.toFile())) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			engine.eval(reader);
			return (Invocable) engine;
		}
	}
	
	private static @NotNull Invocable loadScript(Path file) throws IOException, ScriptException {
		try (Reader reader = new FileReader(file.toFile())) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			engine.eval(reader);
			return (Invocable) engine;
		}
	}
	//endregion
	
	private void load() {
		LOGGER.info("Loading script '{}'", this.scriptFile.getFileName());
		CompletableFuture<Invocable> future = CompletableFuture.supplyAsync(() -> this.operation.apply("Load"));
		future.thenRun(() -> LOGGER.info("Loading script '{}'", this.scriptFile.getFileName()));
		this.function = Suppliers.memoize(future::join);
	}
	
	public void reload() {
		CompletableFuture<Invocable> future = CompletableFuture.supplyAsync(() -> this.operation.apply("Reload"));
		future.thenRun(() -> LOGGER.info("Reloading script '{}'", this.scriptFile.getFileName()));
		this.function = Suppliers.memoize(future::join);
	}
	
	public Object invoke(String functionName, Object... args) {
		try {
			return this.function.get().invokeFunction(functionName, args);
		} catch (Exception e) {
			LOGGER.error("Error while invoking function {} in script '{}'", functionName, this.scriptFile.getFileName());
			throw new RuntimeException("Error while invoking function in script", e);
		}
	}
}
