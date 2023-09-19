package net.luis.xsurvive.config.scripts.blocks;

import net.luis.xsurvive.config.util.ScriptFile;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;

/**
 *
 * @author Luis-St
 *
 */

public class BeaconScript {
	
	@ApiStatus.Internal
	public static final ScriptFile SCRIPT = new ScriptFile("block", "beacon");
	
	public static int getRange(int beaconLevel) {
		return (int) (double) SCRIPT.invoke("range", beaconLevel);
	}
}
