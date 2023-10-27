package net.luis.xsurvive.network;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.network.packet.*;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

public enum XSNetworkHandler {
	
	INSTANCE();
	
	private int id;
	private SimpleChannel simpleChannel;
	
	public void initChannel() {
		this.simpleChannel = ChannelBuilder.named(new ResourceLocation(XSurvive.MOD_ID, "simple_channel")).acceptedVersions((status, version) -> true).simpleChannel();
	}
	
	public void registerPackets() {
		this.registerPacket(UpdatePlayerCapabilityPacket.class, UpdatePlayerCapabilityPacket::encode, UpdatePlayerCapabilityPacket::new, UpdatePlayerCapabilityPacket::handle);
		this.registerPacket(UpdateTridentGlintColorPacket.class, UpdateTridentGlintColorPacket::encode, UpdateTridentGlintColorPacket::new, UpdateTridentGlintColorPacket::handle);
		this.registerPacket(UpdateEntityCapabilityPacket.class, UpdateEntityCapabilityPacket::encode, UpdateEntityCapabilityPacket::new, UpdateEntityCapabilityPacket::handle);
		this.registerPacket(UpdateLevelCapabilityPacket.class, UpdateLevelCapabilityPacket::encode, UpdateLevelCapabilityPacket::new, UpdateLevelCapabilityPacket::handle);
	}
	
	private <T extends NetworkPacket> void registerPacket(Class<T> clazz, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, CustomPayloadEvent.Context> consumer) {
		this.simpleChannel.messageBuilder(clazz, this.id++, NetworkDirection.PLAY_TO_CLIENT).encoder(encoder).decoder(decoder).consumerMainThread(consumer).add();
	}
	
	public @NotNull SimpleChannel getChannel() {
		return this.simpleChannel;
	}
	
	public <T extends NetworkPacket> void sendToServer(@NotNull T packet) {
		this.getChannel().send(packet, PacketDistributor.SERVER.noArg());
	}
	
	public <T extends NetworkPacket> void sendToPlayer(@NotNull Player player, @NotNull T packet) {
		if (player instanceof ServerPlayer serverPlayer) {
			this.sendToPlayer(serverPlayer, packet);
		}
	}
	
	public <T extends NetworkPacket> void sendToPlayer(@NotNull ServerPlayer player, @NotNull T packet) {
		this.getChannel().send(packet, PacketDistributor.PLAYER.with(player));
	}
	
	public <T extends NetworkPacket> void sendToPlayers(@NotNull T packet) {
		this.getChannel().send(packet, PacketDistributor.ALL.noArg());
	}
	
	public <T extends NetworkPacket> void sendToPlayers(@NotNull T packet, ServerPlayer... players) {
		List<Connection> connections = Lists.newArrayList(players).stream().map((player) -> player.connection.getConnection()).collect(Collectors.toList());
		this.getChannel().send(packet, PacketDistributor.NMLIST.with(connections));
	}
	
	public <T extends NetworkPacket> void sendToPlayersInLevel(@NotNull ServerLevel level, @NotNull T packet) {
		this.getChannel().send(packet, PacketDistributor.DIMENSION.with(level.dimension()));
	}
}
