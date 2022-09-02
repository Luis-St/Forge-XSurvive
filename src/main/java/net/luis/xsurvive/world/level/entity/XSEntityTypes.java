package net.luis.xsurvive.world.level.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.projectile.CursedEyeOfEnder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class XSEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<EntityType<CursedEyeOfEnder>> CURSED_ENDER_EYE = ENTITY_TYPES.register("cursed_ender_eye", () -> {
		return EntityType.Builder.<CursedEyeOfEnder>of(CursedEyeOfEnder::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4).build("cursed_ender_eye");
	});
	
}
