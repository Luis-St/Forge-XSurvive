package net.luis.xsurvive.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface ILevel {
	
	@NotNull ServerLevel getLevel();
	
	@NotNull List<BlockPos> getBeaconPositions();
	
	@NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range);
	
	@NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area);
	
	void addBeaconPosition(@NotNull BlockPos pos);
	
	void removeBeaconPosition(BlockPos pos);
}
