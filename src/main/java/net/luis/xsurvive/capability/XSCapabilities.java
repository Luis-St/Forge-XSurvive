/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.capability;

import net.luis.xsurvive.world.entity.IEntity;
import net.luis.xsurvive.world.entity.npc.IVillager;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.level.ILevel;
import net.minecraftforge.common.capabilities.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSCapabilities {
	
	public static final Capability<IGlintColor> GLINT_COLOR = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IPlayer> PLAYER = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IEntity> ENTITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IVillager> VILLAGER = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<ILevel> LEVEL = CapabilityManager.get(new CapabilityToken<>() {});
}
