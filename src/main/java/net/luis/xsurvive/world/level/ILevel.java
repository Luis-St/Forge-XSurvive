package net.luis.xsurvive.world.level;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface ILevel extends INetworkCapability {
	
	@NotNull Level getLevel();
	
	@Unmodifiable @NotNull List<BlockPos> getBeaconPositions();
	
	default @NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range) {
		return this.getBeaconPositions(new AABB(pos).inflate(range).setMinY(this.getLevel().getMinBuildHeight()).setMaxY(this.getLevel().getMaxBuildHeight()));
	}
	
	@NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area);
	
	@NotNull Map<BlockPos, Pair<@Nullable MobEffect, @Nullable MobEffect>> getBeaconEffects();
	
	@NotNull Pair<@Nullable MobEffect, @Nullable MobEffect> getBeaconEffects(@NotNull BlockPos pos);
	
	default @NotNull Optional<MobEffect> getPrimaryBeaconEffect(@NotNull BlockPos pos) {
		return Optional.ofNullable(this.getBeaconEffects(pos).getFirst());
	}
	
	default @NotNull Optional<MobEffect> getSecondaryBeaconEffect(@NotNull BlockPos pos) {
		return Optional.ofNullable(this.getBeaconEffects(pos).getSecond());
	}
}
