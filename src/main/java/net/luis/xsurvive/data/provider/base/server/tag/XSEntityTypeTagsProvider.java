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

package net.luis.xsurvive.data.provider.base.server.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Luis-St
 *
 */

public class XSEntityTypeTagsProvider extends EntityTypeTagsProvider {
	
	public XSEntityTypeTagsProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider, @NotNull ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider lookup) {
		this.tag(XSEntityTypeTags.SENSITIVE_TO_ENDER_SLAYER).add(EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.SHULKER);
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Entity Type Tags";
	}
}
