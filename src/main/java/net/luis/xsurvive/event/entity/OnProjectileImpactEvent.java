package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.projectile.IArrow;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnProjectileImpactEvent {
	
	@SubscribeEvent
	public static void projectileImpact(ProjectileImpactEvent event) {
		if (event.getProjectile() instanceof IArrow arrow) {
			int explosionLevel = arrow.getExplosionLevel();
			if (explosionLevel > 0 && event.getRayTraceResult() instanceof BlockHitResult hitResult) {
				Vec3 location = hitResult.getLocation();
				event.getProjectile().level.explode(event.getProjectile().getOwner(), location.x(), location.y(), location.z(), explosionLevel, Explosion.BlockInteraction.BREAK);
			}
		}
	}
	
}
