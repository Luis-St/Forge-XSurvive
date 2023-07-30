package net.luis.xsurvive.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.luis.xsurvive.XSurvive;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class GammaCommand {
	
	public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("gamma").executes((command) -> {
			return getGamma(command.getSource(), Minecraft.getInstance());
		}).then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 100.0)).executes((command) -> {
			return setGamma(command.getSource(), Minecraft.getInstance(), DoubleArgumentType.getDouble(command, "value"));
		})).then(Commands.literal("min").executes((command) -> {
			return setGamma(command.getSource(), Minecraft.getInstance(), 0.0);
		})).then(Commands.literal("default").executes((command) -> {
			return setGamma(command.getSource(), Minecraft.getInstance(), 0.5);
		})).then(Commands.literal("max").executes((command) -> {
			return setGamma(command.getSource(), Minecraft.getInstance(), 1.0);
		})).then(Commands.literal("infinite").executes((command) -> {
			return setGamma(command.getSource(), Minecraft.getInstance(), 20.0);
		})));
	}
	
	private static int getGamma(@NotNull CommandSourceStack source, @NotNull Minecraft minecraft) {
		source.sendSuccess(() -> Component.translatable(XSurvive.MOD_ID + ".commands.gamma.get", String.valueOf(minecraft.options.gamma.get())), false);
		return 1;
	}
	
	private static int setGamma(@NotNull CommandSourceStack source, @NotNull Minecraft minecraft, double value) {
		minecraft.options.gamma.set(value);
		if (minecraft.options.gamma.get() == value) {
			source.sendSuccess(() -> Component.translatable(XSurvive.MOD_ID + ".commands.gamma.success", Double.toString(minecraft.options.gamma.get())), false);
			return 1;
		} else {
			source.sendFailure(Component.translatable(XSurvive.MOD_ID + ".commands.gamma.failure", Double.toString(value)));
			return 0;
		}
	}
}
