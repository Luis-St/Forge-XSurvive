package net.luis.xsurvive.world.item;

import net.luis.xsurvive.capability.XSurviveCapabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class GlintColorProvider implements ICapabilityProvider {
	
	private final LazyOptional<IGlintColor> optional;
	
	public GlintColorProvider(IGlintColor glintColor) {
		this.optional = LazyOptional.of(() -> glintColor);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return XSurviveCapabilities.GLINT_COLOR.orEmpty(capability, this.optional);
	}
	
}
