package net.luis.xsurvive.world.level;

import net.luis.xsurvive.capability.XSCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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
	
	public static @NotNull ILevel get(@NotNull ServerLevel level) {
		return level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
	}
	
	public static @NotNull LazyOptional<ILevel> getSafe(@NotNull ServerLevel level) {
		return level.getCapability(XSCapabilities.LEVEL, null);
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.LEVEL.orEmpty(capability, this.optional);
	}
}
