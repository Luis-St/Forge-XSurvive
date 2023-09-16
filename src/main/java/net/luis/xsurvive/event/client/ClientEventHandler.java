package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.config.util.XSConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
	
	@SubscribeEvent
	public static void screenInit(@NotNull ScreenEvent.Init.Post event) {
		if (event.getScreen() instanceof ChatReportScreen screen && XSConfigManager.CLIENT_CONFIG.get().chat().disableChatReport()) {
			Minecraft minecraft = screen.getMinecraft();
			screen.sendButton = Button.builder(Component.translatable("gui.chatReport.send"), (button) -> {
				Component comp = Component.literal("Due to the chat reporting system not working properly and many players being banned for no reason, this system will be disabled pending a Mojang overhaul.").withStyle(ChatFormatting.RED);
				if (minecraft.player != null) {
					minecraft.player.sendSystemMessage(comp);
				}
			}).pos((screen.width / 2) + 10, Math.min((screen.height + 300) / 2, screen.height) - 30).size(120, 20).tooltip(Tooltip.create(Component.literal("Disabled for several reasons").withStyle(ChatFormatting.RED))).build();
		}
	}
}
