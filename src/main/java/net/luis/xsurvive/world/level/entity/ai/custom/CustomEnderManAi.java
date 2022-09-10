package net.luis.xsurvive.world.level.entity.ai.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.ForgeEventFactory;

/**
 *
 * @author Luis-st
 *
 */

public class CustomEnderManAi implements CustomAi {
	
	private final EnderMan enderMan;
	private final ServerLevel level;
	
	public CustomEnderManAi(EnderMan enderMan, ServerLevel level) {
		this.enderMan = enderMan;
		this.level = level;
	}
	
	@Override
	public boolean canUse() {
		return this.enderMan.getTarget() != null && this.enderMan.getTarget() instanceof Player;
	}
	
	@Override
	public void tick() {
		if (this.enderMan.tickCount % 50 == 0 && !this.enderMan.isInWater() && ForgeEventFactory.getMobGriefingEvent(this.level, this.enderMan)) {
			this.freezeNearbyWater();
		}
	}
	
	private void freezeNearbyWater() {
		BlockPos.betweenClosedStream(this.enderMan.getBoundingBox().inflate(5.0, 1.0, 5.0).setMaxY(this.enderMan.getY() + 1.0)).map(this::immutable).filter((pos) -> {
			return this.level.getBlockState(pos).is(Blocks.WATER) && 0.75 > this.enderMan.getRandom().nextDouble();
		}).toList().forEach((pos) -> {
			this.level.setBlock(pos, Blocks.FROSTED_ICE.defaultBlockState(), Block.UPDATE_ALL);
		});
	}
	
	private BlockPos immutable(BlockPos pos) {
		if (pos instanceof MutableBlockPos mutablePos) {
			return mutablePos.immutable();
		}
		return pos;
	}
	
}
