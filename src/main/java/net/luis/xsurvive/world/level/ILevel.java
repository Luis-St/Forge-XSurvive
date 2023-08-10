package net.luis.xsurvive.world.level;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface ILevel extends INetworkCapability {
	
	@NotNull Level getLevel();
	
	default @NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range) {
		return this.getBeaconPositions(new AABB(pos).inflate(range).setMinY(this.getLevel().getMinBuildHeight()).setMaxY(this.getLevel().getMaxBuildHeight()));
	}
	
	@NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area);
	
	@NotNull Pair<@Nullable MobEffect, @Nullable MobEffect> getBeaconEffects(@NotNull BlockPos pos);
	
	default @NotNull Optional<MobEffect> getPrimaryBeaconEffect(@NotNull BlockPos pos) {
		return Optional.ofNullable(this.getBeaconEffects(pos).getFirst());
	}
	
}
