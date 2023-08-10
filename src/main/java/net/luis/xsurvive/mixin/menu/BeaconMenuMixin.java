package net.luis.xsurvive.mixin.menu;

import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BeaconMenu.class)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class BeaconMenuMixin {
	
	//region Mixin
	@Shadow private ContainerLevelAccess access;
	//endregion
	
	@Inject(method = "updateEffects", at = @At("TAIL"))
	public void updateEffects(Optional<MobEffect> primary, Optional<MobEffect> secondary, CallbackInfo callback) {
		if (this.access != ContainerLevelAccess.NULL) {
			this.access.execute((level, pos) -> LevelProvider.getServer(level).setBeaconEffects(pos, primary.orElse(null), secondary.orElse(null)));
		}
	}
}
