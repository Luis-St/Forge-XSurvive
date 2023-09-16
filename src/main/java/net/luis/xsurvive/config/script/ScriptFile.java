package net.luis.xsurvive.config.script;

import net.luis.xsurvive.XSurvive;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class ScriptFile {
	
	private static final NashornScriptEngineFactory ENGINE_FACTORY = new NashornScriptEngineFactory();
	
	private final File scriptFile;
	private final URL defaultFile;
	private final String functionName;
	private Invocable function;
	
	public ScriptFile(File scriptFile, String functionName) {
		this.scriptFile = Objects.requireNonNull(scriptFile, "Script file must not be null");
		this.defaultFile = ScriptFile.class.getResource("scripts/" + scriptFile.getName());
		this.functionName = Objects.requireNonNull(functionName, "Function name must not be null");
	}
	
	public void reload() {
		try (Reader reader = new FileReader(this.scriptFile)) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			Invocable invocable = (Invocable) engine.eval(reader);
			if (this.isValidScript(invocable)) {
				this.function = invocable;
			} else {
				XSurvive.LOGGER.warn("Invalid script '{}', keep file but use default script instead", this.scriptFile.getName());
				this.loadDefaultScript();
			}
		} catch (IOException | ScriptException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isValidScript(Invocable invocable) {
		if (invocable == null) {
			return false;
		}
		try {
			invocable.invokeFunction(this.functionName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private void loadDefaultScript() throws IOException, ScriptException {
		try (Reader reader = new InputStreamReader(this.defaultFile.openStream())) {
			ScriptEngine engine = ENGINE_FACTORY.getScriptEngine();
			this.function = (Invocable) engine.eval(reader);
		}
	}
	
	public Object invoke(Object... args) {
		try {
			return this.function.invokeFunction(this.functionName, args);
		} catch (Exception e) {
			XSurvive.LOGGER.error("Error while invoking function {} in script {}", this.functionName, this.scriptFile.getName());
			throw new RuntimeException("Error while invoking function in script", e);
		}
	}
}
