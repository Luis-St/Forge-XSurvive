package net.luis.xsurvive.config.util;

import net.minecraftforge.fml.loading.FMLPaths;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptFile {
	
	private static final NashornScriptEngineFactory ENGINE_FACTORY = new NashornScriptEngineFactory();
	private static final Path SCRIPT_FOLDER = FMLPaths.GAMEDIR.get().resolve("xsurvive/script/");
	
	private final Path scriptFile;
	private final URL defaultFile;
	private Invocable function;
	
	public ScriptFile(String folder, String scriptName) {
		this.scriptFile = SCRIPT_FOLDER.resolve(folder).resolve(scriptName + ".js");
		this.defaultFile = ScriptFile.class.getResource("/scripts/" + folder + "/" + scriptName + ".js");
	}
	
	public void initialize() {
		XSConfigManager.LOGGER.info("Loading script '{}'", this.scriptFile.getFileName());
		try {
			if (Files.notExists(this.scriptFile)) {
				Files.createDirectories(this.scriptFile.getParent());
				Files.copy(this.defaultFile.openStream(), this.scriptFile);
			}
			this.reload();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void reload() {
		try (Reader reader = new FileReader(this.scriptFile.toFile())) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			try {
				engine.eval(reader);
				this.function = (Invocable) engine;
			} catch (ScriptException e) {
				XSConfigManager.LOGGER.warn("Invalid script '{}', keep file but use default script instead", this.scriptFile.getFileName());
				this.loadDefaultScript();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void loadDefaultScript() throws IOException, ScriptException {
		try (Reader reader = new InputStreamReader(this.defaultFile.openStream())) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			engine.eval(reader);
			this.function = (Invocable) engine;
		}
	}
	
	public Object invoke(String functionName, Object... args) {
		try {
			return this.function.invokeFunction(functionName, args);
		} catch (Exception e) {
			XSConfigManager.LOGGER.error("Error while invoking function {} in script {}", functionName, this.scriptFile.getFileName());
			throw new RuntimeException("Error while invoking function in script", e);
		}
	}
}
