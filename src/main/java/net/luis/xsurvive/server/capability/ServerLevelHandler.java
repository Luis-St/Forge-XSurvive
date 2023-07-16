package net.luis.xsurvive.server.capability;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.ILevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ServerLevelHandler implements ILevel {
	
	private final ServerLevel level;
	private final List<BlockPos> beaconPositions = Lists.newArrayList();
	
	public ServerLevelHandler(ServerLevel level) {
		this.level = level;
	}
	
	@Override
	public @NotNull ServerLevel getLevel() {
		return this.level;
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions() {
		return List.copyOf(this.beaconPositions);
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range) {
		return this.getBeaconPositions(new AABB(pos).inflate(range).setMinY(this.level.getMinBuildHeight()).setMaxY(this.level.getMaxBuildHeight()));
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area) {
		List<BlockPos> positions = Lists.newArrayList();
		for (BlockPos position : this.beaconPositions) {
			if (area.contains(position.getX(), position.getY(), position.getZ())) {
				positions.add(position);
			}
		}
		return positions;
	}
	
	@Override
	public void addBeaconPosition(@NotNull BlockPos pos) {
		this.beaconPositions.add(pos);
	}
	
	@Override
	public void removeBeaconPosition(BlockPos pos) {
		this.beaconPositions.remove(pos);
	}
}
