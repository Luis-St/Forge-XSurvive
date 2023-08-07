package net.luis.xsurvive.world.level;

import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.capability.ClientLevelHandler;
import net.luis.xsurvive.server.capability.ServerLevelHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class LevelProvider implements ICapabilityProvider {
	
	private final LazyOptional<ILevel> optional;
	
	public LevelProvider(ILevel levelCapability) {
		this.optional = LazyOptional.of(() -> levelCapability);
	}
	
	public static @NotNull ILevel get(@NotNull Level level) {
		return level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
	}
	
	public static @NotNull LazyOptional<ILevel> getSafe(@NotNull Level level) {
		return level.getCapability(XSCapabilities.LEVEL, null);
	}
	
	public static @NotNull ClientLevelHandler getClient(@NotNull Level level) {
		ILevel capability = level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientLevelHandler handler) {
			return handler;
		} else if (capability instanceof ServerLevelHandler handler) {
			throw new RuntimeException("Fail to get LocalPlayerHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static @NotNull ServerLevelHandler getServer(@NotNull Level level) {
		ILevel capability = level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientLevelHandler handler) {
			throw new RuntimeException("Fail to get ServerPlayerHandler from client");
		} else if (capability instanceof ServerLevelHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.LEVEL.orEmpty(capability, this.optional);
	}
}
