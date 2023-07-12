package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.world.level.ILevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ServerLevelHandler implements ILevel {
	
	private final ServerLevel level;
	
	public ServerLevelHandler(ServerLevel level) {
		this.level = level;
	}
	
	@Override
	public @NotNull ServerLevel getLevel() {
		return this.level;
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions() {
		return null;
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range) {
		return null;
	}
	
	@Override
	public void addBeaconPosition(@NotNull BlockPos pos) {
	
	}
	
	@Override
	public void removeBeaconPosition(BlockPos pos) {
	
	}
}
