package net.luis.xsurvive.capability;

import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.level.entity.player.IPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveCapabilities {
	
	public static final Capability<IGlintColor> GLINT_COLOR = CapabilityManager.get(new CapabilityToken<IGlintColor>() {});
	public static final Capability<IPlayer> PLAYER = CapabilityManager.get(new CapabilityToken<IPlayer>() {});
	
}
