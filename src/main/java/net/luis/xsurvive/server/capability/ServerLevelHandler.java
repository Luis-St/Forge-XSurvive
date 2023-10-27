package net.luis.xsurvive.server.capability;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.capability.handler.AbstractLevelHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateLevelCapabilityPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.core.registries.BuiltInRegistries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("deprecation")
public class ServerLevelHandler extends AbstractLevelHandler {
	
	public ServerLevelHandler(Level level) {
		super(level);
	}
	
	public void addBeaconPosition(@NotNull BlockPos pos) {
		this.beaconPositions.add(pos);
		this.broadcastChanges();
	}
	
	public void removeBeaconPosition(BlockPos pos) {
		this.beaconPositions.remove(pos);
		this.broadcastChanges();
	}
	
	public void setBeaconEffects(@NotNull BlockPos pos, MobEffect primaryEffect, MobEffect secondaryEffect) {
		int primaryId = MOB_EFFECT.getId(primaryEffect);
		int secondaryId = MOB_EFFECT.getId(secondaryEffect);
		if (this.beaconEffects.containsKey(pos)) {
			Pair<Integer, Integer> pair = this.beaconEffects.get(pos);
			if (pair.getFirst() == primaryId && pair.getSecond() == secondaryId) {
				return;
			}
		}
		this.beaconEffects.put(pos, new Pair<>(primaryId, secondaryId));
		this.broadcastChanges();
	}
	
	public void removeBeaconEffects(BlockPos pos) {
		this.beaconEffects.remove(pos);
		this.broadcastChanges();
	}
	
	@Override
	public void broadcastChanges() {
		XSNetworkHandler.INSTANCE.sendToPlayersInLevel((ServerLevel) this.getLevel(), new UpdateLevelCapabilityPacket(this.serializeNetwork()));
	}
	
	//region NBT
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
	
	}
	//endregion
}
