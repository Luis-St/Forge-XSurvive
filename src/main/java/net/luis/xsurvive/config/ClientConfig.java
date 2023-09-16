package net.luis.xsurvive.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.luis.xsurvive.config.util.XSConfig;
import net.luis.xsurvive.config.util.XSConfigManager;

import static net.luis.xsurvive.config.util.XSConfigManager.*;

/**
 *
 * @author Luis-St
 *
 */

public record ClientConfig(ClientConfig.Chat chat, ClientConfig.Options options) implements XSConfig {
	
	public static final Codec<ClientConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ClientConfig.Chat.CODEC.fieldOf("chat").forGetter(ClientConfig::chat),
			ClientConfig.Options.CODEC.fieldOf("options").forGetter(ClientConfig::options)
		).apply(instance, ClientConfig::new)
	);
	public static final ClientConfig DEFAULT = new ClientConfig(new ClientConfig.Chat(true, true), new ClientConfig.Options(true, true, true));
	
	
	public void loaded() {
		LOGGER.info("Client config loaded:");
		LOGGER.debug("chat.enableGammaCommand: {}", this.chat.enableGammaCommand());
		LOGGER.debug("chat.disableChatReport: {}", this.chat.disableChatReport());
		LOGGER.debug("options.replaceGamma: {}", this.options.replaceGamma());
		LOGGER.debug("options.replaceGlintSpeed: {}", this.options.replaceGlintSpeed());
		LOGGER.debug("options.replaceGlintStrength: {}", this.options.replaceGlintStrength());
	}
	
	public static record Options(boolean replaceGamma, boolean replaceGlintSpeed, boolean replaceGlintStrength) {
		
		public static final Codec<Options> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.BOOL.fieldOf("replaceGamma").forGetter(Options::replaceGamma),
				Codec.BOOL.fieldOf("replaceGlintSpeed").forGetter(Options::replaceGlintSpeed),
				Codec.BOOL.fieldOf("replaceGlintStrength").forGetter(Options::replaceGlintStrength)
			).apply(instance, Options::new)
		);
	}
	
	public static record Chat(boolean enableGammaCommand, boolean disableChatReport) {
		
		public static final Codec<Chat> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.BOOL.fieldOf("enableGammaCommand").forGetter(Chat::enableGammaCommand),
				Codec.BOOL.fieldOf("disableChatReport").forGetter(Chat::disableChatReport)
			).apply(instance, Chat::new)
		);
	}
}
