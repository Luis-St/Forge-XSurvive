package net.luis.xsurvive.config.scripts.blocks;

import net.luis.xsurvive.config.util.ScriptFile;
import org.jetbrains.annotations.ApiStatus;

/**
 *
 * @author Luis-St
 *
 */

public class ConduitScript {
	
	@ApiStatus.Internal
	public static final ScriptFile SCRIPT = new ScriptFile("block", "conduit");
	
	public static int getRange(int conduitBlockCount) {
		return (int) (double) SCRIPT.invoke("range", conduitBlockCount);
	}
}
