package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.capability.ICapability;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface IEntity extends ICapability, INetworkCapability {
	
	@NotNull Entity getEntity();
	
	default Level getLevel() {
		return this.getEntity().level();
	}
	
	void tick();
	
	@NotNull EntityFireType getFireType();
	
	default void setFireType(@NotNull EntityFireType fireType) {}
}
