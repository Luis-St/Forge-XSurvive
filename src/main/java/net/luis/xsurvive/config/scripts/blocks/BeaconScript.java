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
	public static final ScriptFile SCRIPT = new ScriptFile("blocks", "beacon");
	
	public static double getRange(int beaconLevel) {
		return (double) SCRIPT.invoke("range", beaconLevel);
	}
	
	public static double getNetheriteRangeModifier(int beaconLevel, double effectRange) {
		return (double) SCRIPT.invoke("netheriteRangeModifier", beaconLevel);
	}
	
	public static int getNetheriteEffectDuration(int beaconLevel, double effectRange) {
		return (int) (double) SCRIPT.invoke("netheriteEffectDuration", beaconLevel);
	}
	
	public static int getNetheriteEffectAmplifier(int beaconLevel, double effectRange) {
		return (int) (double) SCRIPT.invoke("netheriteEffectAmplifier", beaconLevel);
	}
	
	public static int getDiamondRangeModifier(int beaconLevel, double effectRange) {
		return (int) (double) SCRIPT.invoke("diamondRangeModifier", beaconLevel);
	}
	
	public static int getVanillaEffectAmplifier(int beaconLevel, double effectRange, boolean sameEffectSelected) {
		return (int) (double) SCRIPT.invoke("vanillaEffectAmplifier", beaconLevel, effectRange, sameEffectSelected);
	}
	
	public static int getPrimaryEffectAmplifier(int beaconLevel, double effectRange, boolean sameEffectSelected, boolean diamond, int stackedAmplifier, int vanillaAmplifier) {
		return (int) (double) SCRIPT.invoke("primaryEffectAmplifier", beaconLevel, effectRange, sameEffectSelected, stackedAmplifier, vanillaAmplifier);
	}
	
	public static int getPrimaryEffectDuration(int beaconLevel, double effectRange, boolean sameEffectSelected, boolean diamond) {
		return (int) (double) SCRIPT.invoke("primaryEffectDuration", beaconLevel, effectRange, sameEffectSelected);
	}
	
	public static int getSecondaryEffectAmplifier(int beaconLevel, double effectRange, boolean diamond, int vanillaAmplifier) {
		return (int) (double) SCRIPT.invoke("secondaryEffectAmplifier", beaconLevel, effectRange, vanillaAmplifier);
	}
	
	public static int getSecondaryEffectDuration(int beaconLevel, double effectRange, boolean diamond) {
		return (int) (double) SCRIPT.invoke("secondaryEffectDuration", beaconLevel, effectRange);
	}
}
