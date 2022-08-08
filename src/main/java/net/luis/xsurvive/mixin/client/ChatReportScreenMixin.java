package net.luis.xsurvive.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.network.chat.Component;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(value = ChatReportScreen.class, priority = Integer.MIN_VALUE)
public abstract class ChatReportScreenMixin extends Screen {
	
	private ChatReportScreenMixin(Component component) {
		super(component);
	}
	
	@Inject(method = "sendReport", at = @At("HEAD"))
	private void sendReport(CallbackInfo callback) {
		Component component = Component.literal("Due to the chat reporting system not working properly and many players being banned for no reason, this system will be disabled pending a Mojang overhaul.").withStyle(ChatFormatting.RED);
		this.minecraft.player.sendSystemMessage(component);
	}

}
