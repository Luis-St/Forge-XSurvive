package net.luis.xsurvive.world.item;

import net.luis.xsurvive.capability.XSCapabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class GlintColorProvider implements ICapabilityProvider {
	
	private final LazyOptional<IGlintColor> optional;
	
	public GlintColorProvider(IGlintColor glintColor) {
		this.optional = LazyOptional.of(() -> glintColor);
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @NotNull Direction side) {
		return XSCapabilities.GLINT_COLOR.orEmpty(capability, this.optional);
	}
}
