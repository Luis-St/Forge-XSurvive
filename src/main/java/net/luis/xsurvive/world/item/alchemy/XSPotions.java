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

package net.luis.xsurvive.world.item.alchemy;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSPotions {
	
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, XSurvive.MOD_ID);
	
	public static final RegistryObject<Potion> FROST = POTIONS.register("frost", () -> {
		return new Potion(new MobEffectInstance(XSMobEffects.FROST.getHolder().orElseThrow(), 3600));
	});
	public static final RegistryObject<Potion> LONG_FROST = POTIONS.register("long_frost", () -> {
		return new Potion(new MobEffectInstance(XSMobEffects.FROST.getHolder().orElseThrow(), 9600));
	});
	public static final RegistryObject<Potion> STRONG_FROST = POTIONS.register("strong_frost", () -> {
		return new Potion(new MobEffectInstance(XSMobEffects.FROST.getHolder().orElseThrow(), 1800, 1));
	});
	public static final RegistryObject<Potion> WITHER = POTIONS.register("wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 600));
	});
	public static final RegistryObject<Potion> LONG_WITHER = POTIONS.register("long_wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 1200));
	});
	public static final RegistryObject<Potion> STRONG_WITHER = POTIONS.register("strong_wither", () -> {
		return new Potion(new MobEffectInstance(MobEffects.WITHER, 400, 1));
	});
	public static final RegistryObject<Potion> DIG_SPEED = POTIONS.register("haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600));
	});
	public static final RegistryObject<Potion> LONG_DIG_SPEED = POTIONS.register("long_haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 9600));
	});
	public static final RegistryObject<Potion> STRONG_DIG_SPEED = POTIONS.register("strong_haste", () -> {
		return new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 1));
	});
}
