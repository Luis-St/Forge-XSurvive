package net.luis.xsurvive.capability;

import net.luis.xsurvive.world.entity.IEntity;
import net.luis.xsurvive.world.entity.npc.IVillager;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.level.ILevel;
import net.minecraftforge.common.capabilities.*;

/**
 *
 * @author Luis-st
 *
 */

public class XSCapabilities {
	
	public static final Capability<IGlintColor> GLINT_COLOR = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IPlayer> PLAYER = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IEntity> ENTITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IVillager> VILLAGER = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<ILevel> LEVEL = CapabilityManager.get(new CapabilityToken<>() {});
}
