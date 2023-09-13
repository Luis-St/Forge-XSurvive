package net.luis.xsurvive.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.luis.xsurvive.world.entity.EntityEquipmentHelper;
import net.minecraft.commands.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class TestCommand {
	
	public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("test").requires((stack) -> {
			return stack.hasPermission(2);
		}).executes((stack) -> {
			return execute(stack.getSource());
		}));
	}
	
	private static int execute(@NotNull CommandSourceStack source) {
		ServerPlayer player = source.getPlayer();
		if (player == null) {
			source.sendFailure(Component.literal("Can not execute this command from a none player context"));
			return 0;
		}
		return 1;
	}
}
