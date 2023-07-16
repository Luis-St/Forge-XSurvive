package net.luis.xsurvive.world.level.block.entity;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public interface IBeaconBlockEntity {
	
	int getBeaconLevel();
	
	MobEffect getPrimaryEffect();
	
	MobEffect getSecondaryEffect();
	
	List<AABB> getBeaconBase();
	
	boolean isBeaconBaseShared();
	
	List<Block> getBeaconBaseBlocks();
	
	boolean isBaseFullOf(Block block);
}
