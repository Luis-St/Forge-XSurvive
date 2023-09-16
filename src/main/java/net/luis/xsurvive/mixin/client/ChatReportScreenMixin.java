package net.luis.xsurvive.mixin.client;

import net.luis.xsurvive.config.util.XSConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(value = ChatReportScreen.class, priority = Integer.MIN_VALUE)
public abstract class ChatReportScreenMixin extends Screen {
	
	//region Mixin
	private ChatReportScreenMixin(Component component) {
		super(component);
	}
	//endregion
	
	@Inject(method = "sendReport", at = @At("HEAD"))
	private void sendReport(CallbackInfo callback) {
		if (XSConfigManager.CLIENT_CONFIG.get().chat().disableChatReport()) {
			Component component = Component.literal("Due to the chat reporting system not working properly and many players being banned for no reason, this system will be disabled pending a Mojang overhaul.").withStyle(ChatFormatting.RED);
			if (this.getMinecraft().player != null) {
				this.getMinecraft().player.sendSystemMessage(component);
			}
			callback.cancel();
		}
	}
}
