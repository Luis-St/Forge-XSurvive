package net.luis.xsurvive.network;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveNetworkHandler {
	
	private static final String VERSION = "1";
	private static int id = 0;
	private static SimpleChannel simpleChannel;
	
	public static void init() {
		XSurvive.LOGGER.info("Register {} Network Channel", XSurvive.MOD_NAME);
		simpleChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(XSurvive.MOD_ID, "simple_chnanel"), () -> VERSION, VERSION::equals, VERSION::equals);
		simpleChannel.messageBuilder(UpdatePlayerCapabilityPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(UpdatePlayerCapabilityPacket::encode).decoder(UpdatePlayerCapabilityPacket::new)
				.consumerMainThread(UpdatePlayerCapabilityPacket.Handler::handle).add();
	}
	
	public static SimpleChannel getChannel() {
		return simpleChannel;
	}
	
	public static <P> void sendToServer(P packet) {
		getChannel().sendToServer(packet);
	}
	
	public static <P> void sendToPlayer(ServerPlayer player, P packet) {
		getChannel().send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
	
	public static <P> void sendToPlayers(P packet) {
		getChannel().send(PacketDistributor.ALL.noArg(), packet);
	}
	
	public static <P> void sendToPlayers(P packet, ServerPlayer... players) {
		List<Connection> connections = Lists.newArrayList(players).stream().map((player) -> {
			return player.connection.connection;
		}).collect(Collectors.toList());
		getChannel().send(PacketDistributor.NMLIST.with(() -> connections), packet);
	}
	
	public static <P> void sendToPlayersInLevel(ServerLevel level, P packet) {
		getChannel().send(PacketDistributor.DIMENSION.with(level::dimension), packet);
	}
	
}
