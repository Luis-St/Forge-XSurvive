/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.mixin.client;

import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.block.entity.IBeaconBlockEntity;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
	@Shadow private Holder<MobEffect> effect;
	
	private BeaconPowerButtonMixin(int x, int y) {
		super(x, y);
	}
	
	@Shadow
	protected abstract @NotNull MutableComponent createEffectDescription(@NotNull Holder<MobEffect> effect);
	//endregion
	
	@Inject(at = @At("HEAD"), method = "updateStatus")
	public void updateStatus(int beaconLevel, @NotNull CallbackInfo callback) {
		this.setTooltip(Tooltip.create(this.createEffectDescription(this.effect), null));
	}
	
	@Inject(at = @At("HEAD"), method = "createEffectDescription", cancellable = true)
	protected void createEffectDescription(@NotNull Holder<MobEffect> effect, @NotNull CallbackInfoReturnable<MutableComponent> callback) {
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
				callback.setReturnValue(Component.translatable(effect.value().getDescriptionId()).append(suffix));
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
