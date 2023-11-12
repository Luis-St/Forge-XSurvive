package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeTags {
	
	public static void register() {
	
	}
	
	public static final TagKey<DamageType> DAMAGE_FROM_ABOVE = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_above"));
	public static final TagKey<DamageType> DAMAGE_FROM_FRONT = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_front"));
	public static final TagKey<DamageType> DAMAGE_FROM_BELOW = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_below"));
	
	public static final TagKey<DamageType> FULL_BODY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "full_body_damage"));
	
	public static final TagKey<DamageType> HEAD_ONLY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "head_only_damage"));
	public static final TagKey<DamageType> FEET_ONLY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "feet_only_damage"));
	
	private static @NotNull TagKey<DamageType> bind(@NotNull ResourceLocation location) {
		return TagKey.create(Registries.DAMAGE_TYPE, location);
	}
}
