package net.luis.xsurvive.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EnderMan.class)
public class EnderManMixin extends Monster {

	private EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	public boolean startRiding(Entity vehicle) {
		return super.startRiding(vehicle);
	}
	
	@Override
	public boolean startRiding(Entity vehicle, boolean forceRide) {
		if (forceRide) {
			return super.startRiding(vehicle, forceRide);
		} else if (vehicle instanceof Boat || vehicle instanceof AbstractMinecart) {
			return false;
		} else {
			return super.startRiding(vehicle, forceRide);
		}
	}
	
}
