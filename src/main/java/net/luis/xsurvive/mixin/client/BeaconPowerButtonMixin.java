package net.luis.xsurvive.mixin.client;

import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.block.entity.IBeaconBlockEntity;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static net.luis.xsurvive.world.level.block.entity.IBeaconBlockEntity.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("DollarSignInName")
@Mixin(targets = "net.minecraft.client.gui.screens.inventory.BeaconScreen$BeaconPowerButton")
public abstract class BeaconPowerButtonMixin extends BeaconScreen.BeaconScreenButton {
	
	//region Mixin
	@Shadow private BeaconScreen this$0;
	@Shadow private MobEffect effect;
	
	private BeaconPowerButtonMixin(int x, int y) {
		super(x, y);
	}
	
	@Shadow
	protected abstract MutableComponent createEffectDescription(MobEffect effect);
	//endregion
	
	@Inject(at = @At("HEAD"), method = "updateStatus")
	public void updateStatus(int beaconLevel, CallbackInfo callback) {
		this.setTooltip(Tooltip.create(this.createEffectDescription(this.effect), null));
	}
	
	@Inject(at = @At("HEAD"), method = "createEffectDescription", cancellable = true)
	protected void createEffectDescription(MobEffect effect, CallbackInfoReturnable<MutableComponent> callback) {
		LocalPlayer player = this.this$0.getMinecraft().player;
		if (player != null) {
			Level level = player.level();
			BlockPos containerPos = PlayerProvider.getSafe(player).map(IPlayer::getContainerPos).orElseGet(Optional::empty).orElse(null);
			if (containerPos != null && level.getBlockEntity(containerPos) instanceof IBeaconBlockEntity beacon) {
				String suffix = "";
				if (!beacon.isBeaconBaseShared()) {
					if (beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK)) {
						suffix = this.getLevel(beacon.getBeaconLevel());
					} else if (beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK)) {
						suffix = this.getLevel(beacon.getBeaconLevel());
					} else {
						int beaconLevel = beacon.getBeaconLevel();
						suffix = this.getLevel(getAmplifier(player.getOnPos(), level, containerPos, beaconLevel, beaconLevel * 20 + 20, effect, 0));
					}
				}
				callback.setReturnValue(Component.translatable(effect.getDescriptionId()).append(suffix));
			}
		}
	}
	
	private @NotNull String getLevel(int beaconLevel) {
		return switch (beaconLevel) {
			case 1 -> " II";
			case 2 -> " III";
			case 3 -> " IV";
			case 4 -> " V";
			default -> "";
		};
	}
}
