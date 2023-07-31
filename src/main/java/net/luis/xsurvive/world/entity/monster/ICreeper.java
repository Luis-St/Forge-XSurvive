package net.luis.xsurvive.world.entity.monster;

/**
 *
 * @author Luis-St
 *
 */

public interface ICreeper {
	
	int getExplosionRadius();
	
	void setExplosionRadius(int explosionRadius);
	
	void setPowered(boolean powered);
}
