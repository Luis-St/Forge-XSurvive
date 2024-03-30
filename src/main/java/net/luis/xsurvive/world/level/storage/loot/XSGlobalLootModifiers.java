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

package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;
import net.luis.xsurvive.XSurvive;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSGlobalLootModifiers {
	
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, XSurvive.MOD_ID);
	
	public static final RegistryObject<Codec<SmeltingModifier>> SMELTING_MODIFIER = LOOT_MODIFIERS.register("smelting_modifier", () -> {
		return SmeltingModifier.CODEC;
	});
	public static final RegistryObject<Codec<MultiDropModifier>> MULTI_DROP_MODIFIER = LOOT_MODIFIERS.register("multi_drop_modifier", () -> {
		return MultiDropModifier.CODEC;
	});
	public static final RegistryObject<Codec<RuneItemModifier>> RUNE_ITEM_MODIFIER = LOOT_MODIFIERS.register("rune_item_modifier", () -> {
		return RuneItemModifier.CODEC;
	});
	public static final RegistryObject<Codec<GoldenBookModifier>> GOLDEN_BOOK_MODIFIER = LOOT_MODIFIERS.register("golden_book_modifier", () -> {
		return GoldenBookModifier.CODEC;
	});
	public static final RegistryObject<Codec<AdditionalChanceItemModifier>> ADDITIONAL_CHANCE_ITEM_MODIFIER = LOOT_MODIFIERS.register("additional_chance_item_modifier", () -> {
		return AdditionalChanceItemModifier.CODEC;
	});
}
