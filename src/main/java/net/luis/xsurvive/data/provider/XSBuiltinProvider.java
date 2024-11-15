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

package net.luis.xsurvive.data.provider;

import net.luis.xsurvive.data.provider.additions.XSAdditionsEnchantmentProvider;
import net.luis.xsurvive.data.provider.base.server.XSDamageTypeProvider;
import net.luis.xsurvive.data.provider.base.server.XSEnchantmentProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSBuiltinProvider {
	
	public static @NotNull RegistrySetBuilder createProvider() {
		RegistrySetBuilder builder = new RegistrySetBuilder();
		builder.add(Registries.DAMAGE_TYPE, XSDamageTypeProvider::create);
		builder.add(Registries.ENCHANTMENT, XSEnchantmentProvider::create);
		return builder;
	}
	
	public static @NotNull RegistrySetBuilder createAdditionsProvider() {
		RegistrySetBuilder builder = new RegistrySetBuilder();
		builder.add(Registries.ENCHANTMENT, XSAdditionsEnchantmentProvider::create);
		return builder;
	}
}
