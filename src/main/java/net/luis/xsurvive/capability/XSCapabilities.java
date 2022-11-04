package net.luis.xsurvive.capability;

import net.luis.xsurvive.world.entity.IEntity;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.item.IGlintColor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * 
 * @author Luis-st
 *
 */

public class XSCapabilities {
	
	public static final Capability<IGlintColor> GLINT_COLOR = CapabilityManager.get(new CapabilityToken<IGlintColor>() {});
	public static final Capability<IPlayer> PLAYER = CapabilityManager.get(new CapabilityToken<IPlayer>() {});
	public static final Capability<IEntity> ENTITY = CapabilityManager.get(new CapabilityToken<IEntity>() {});
	
}
