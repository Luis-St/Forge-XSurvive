/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
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
		source.sendSuccess(() -> Component.translatable(XSurvive.MOD_ID + ".commands.gamma.success", Double.toString(minecraft.options.gamma.get())), false);
		return 1;
	}
}
