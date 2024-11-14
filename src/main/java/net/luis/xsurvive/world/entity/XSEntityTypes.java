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

package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.core.XSResourceKeys;
import net.luis.xsurvive.world.entity.projectile.CursedEyeOfEnder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<EntityType<CursedEyeOfEnder>> CURSED_ENDER_EYE = register("cursed_ender_eye", (key) -> {
		return EntityType.Builder.<CursedEyeOfEnder>of(CursedEyeOfEnder::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4).build(key);
	});
	
	private static <T extends Entity> @NotNull RegistryObject<EntityType<T>> register(@NotNull String name, Function<ResourceKey<EntityType<?>>, @NotNull EntityType<T>> entityType) {
		return ENTITY_TYPES.register(name, () -> entityType.apply(XSResourceKeys.createEntityTypeKey(name)));
	}
}
