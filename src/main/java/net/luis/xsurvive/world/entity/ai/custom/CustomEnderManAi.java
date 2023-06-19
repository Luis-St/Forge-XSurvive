package net.luis.xsurvive.world.entity.ai.custom;

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

public class CustomEnderManAi extends AbstractCustomAi<EnderMan> {
	
	public CustomEnderManAi(EnderMan entity, ServerLevel level) {
		super(entity, level);
	}
	
	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null && this.entity.getTarget() instanceof Player;
	}
	
	@Override
	public void tick() {
		if (this.entity.tickCount % 50 == 0 && !this.entity.isInWater() && ForgeEventFactory.getMobGriefingEvent(this.level, this.entity)) {
			this.freezeNearbyWater();
		}
	}
	
	private void freezeNearbyWater() {
		BlockPos.betweenClosedStream(this.entity.getBoundingBox().inflate(5.0, 1.0, 5.0).setMaxY(this.entity.getY() + 1.0)).map(this::immutable).filter((pos) -> {
			return this.level.getBlockState(pos).is(Blocks.WATER) && 0.75 > this.entity.getRandom().nextDouble();
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
