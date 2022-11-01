package net.luis.xsurvive.network;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.network.packet.UpdateTridentGlintColorPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * 
 * @author Luis-st
 *
 */

public enum XSNetworkHandler {
	
	INSTANCE();
	
	private static final String VERSION = "2";
	private int id = 0;
	private SimpleChannel simpleChannel;
	
	public void initChannel() {
		this.simpleChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(XSurvive.MOD_ID, "simple_chnanel"), () -> VERSION, VERSION::equals, VERSION::equals);
	}
	
	public void registerPackets() {
		this.registerPacket(UpdatePlayerCapabilityPacket.class, NetworkDirection.PLAY_TO_CLIENT, UpdatePlayerCapabilityPacket::encode, UpdatePlayerCapabilityPacket::new, UpdatePlayerCapabilityPacket::handle);
		this.registerPacket(UpdateTridentGlintColorPacket.class, NetworkDirection.PLAY_TO_CLIENT, UpdateTridentGlintColorPacket::encode, UpdateTridentGlintColorPacket::new, UpdateTridentGlintColorPacket::handle);
	}
	
	private <T extends NetworkPacket> void registerPacket(Class<T> clazz, NetworkDirection direction, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> consumer) {
		this.simpleChannel.messageBuilder(clazz, id++, direction).encoder(encoder).decoder(decoder).consumerMainThread(consumer).add();
	}
	
	public SimpleChannel getChannel() {
		return this.simpleChannel;
	}
	
	public <T extends NetworkPacket> void sendToServer(T packet) {
		this.getChannel().sendToServer(packet);
	}
	
	public <T extends NetworkPacket> void sendToPlayer(Player player, T packet) {
		if (player instanceof ServerPlayer serverPlayer) {
			this.sendToPlayer(serverPlayer, packet);
		}
	}
	
	public <T extends NetworkPacket> void sendToPlayer(ServerPlayer player, T packet) {
		this.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
	
	public <T extends NetworkPacket> void sendToPlayers(T packet) {
		this.getChannel().send(PacketDistributor.ALL.noArg(), packet);
	}
	
	public <T extends NetworkPacket> void sendToPlayers(T packet, ServerPlayer... players) {
		List<Connection> connections = Lists.newArrayList(players).stream().map((player) -> {
			return player.connection.connection;
		}).collect(Collectors.toList());
		this.getChannel().send(PacketDistributor.NMLIST.with(() -> connections), packet);
	}
	
	public <T extends NetworkPacket> void sendToPlayersInLevel(ServerLevel level, T packet) {
		this.getChannel().send(PacketDistributor.DIMENSION.with(level::dimension), packet);
	}
	
}
