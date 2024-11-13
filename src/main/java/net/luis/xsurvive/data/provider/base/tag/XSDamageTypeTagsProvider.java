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

package net.luis.xsurvive.data.provider.base.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSDamageTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.damagesource.DamageTypes.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeTagsProvider extends DamageTypeTagsProvider {
	
	public XSDamageTypeTagsProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider, @NotNull ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider lookup) { // ToDo: Update Tags
		this.tag(XSDamageTypeTags.DAMAGE_FROM_ABOVE).add(LIGHTNING_BOLT).addTag(DamageTypeTags.DAMAGES_HELMET);
		this.tag(XSDamageTypeTags.DAMAGE_FROM_FRONT).add(STING).addTag(DamageTypeTags.IS_PROJECTILE);
		this.tag(XSDamageTypeTags.DAMAGE_FROM_BELOW).add(IN_FIRE, ON_FIRE, LAVA, SWEET_BERRY_BUSH);
		this.tag(XSDamageTypeTags.FULL_BODY_DAMAGE).add(IN_WALL, CRAMMING, DROWN, STARVE, CACTUS, FELL_OUT_OF_WORLD, GENERIC, MAGIC, WITHER, DRY_OUT, FREEZE, MOB_ATTACK, MOB_ATTACK_NO_AGGRO, PLAYER_ATTACK, INDIRECT_MAGIC, THORNS, EXPLOSION)
			.add(PLAYER_EXPLOSION, SONIC_BOOM, BAD_RESPAWN_POINT, OUTSIDE_BORDER, GENERIC_KILL);
		this.tag(XSDamageTypeTags.HEAD_ONLY_DAMAGE).add(FLY_INTO_WALL);
		this.tag(XSDamageTypeTags.FEET_ONLY_DAMAGE).add(HOT_FLOOR, FALL, DRAGON_BREATH, STALAGMITE);
		this.tag(DamageTypeTags.BYPASSES_ARMOR).replace().add(ON_FIRE, IN_WALL, CRAMMING, DROWN, GENERIC, WITHER, DRAGON_BREATH, STARVE, FREEZE, MAGIC, INDIRECT_MAGIC, FELL_OUT_OF_WORLD, GENERIC_KILL, SONIC_BOOM, OUTSIDE_BORDER)
			.add(/*FLY_INTO_WALL, FALL, STALAGMITE*/);
	}
}
