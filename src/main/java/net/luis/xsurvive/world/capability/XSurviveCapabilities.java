package net.luis.xsurvive.world.capability;

import net.luis.xsurvive.world.level.entity.player.IPlayerCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveCapabilities {
	
	public static final Capability<IPlayerCapability> PLAYER = CapabilityManager.get(new CapabilityToken<IPlayerCapability>() {});
	
}
