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

package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateTridentGlintColorPacket;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {
	
	private static final Map<ThrownTrident, ItemStack> TRIDENT_STACK_REFERENCES = new WeakHashMap<>();
	
	//region Mixin
	@Shadow private Entity entity;
	
	@Shadow
	protected abstract void broadcastAndSend(@NotNull Packet<?> packet);
	//endregion
	
	@Inject(method = "sendDirtyEntityData", at = @At("HEAD"))
	private void sendDirtyEntityData(@NotNull CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, false);
		}
	}
	
	@Inject(method = "sendPairingData", at = @At("HEAD"))
	private void sendPairingData(@NotNull ServerPlayer player, @NotNull Consumer<Packet<?>> send, @NotNull CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, true);
		}
	}
	
	private void broadcastThrownTrident(@NotNull ThrownTrident trident, boolean forced) {
		ItemStack tridentStack = trident.getPickupItemStackOrigin().copy();
		ItemStack previousStack = TRIDENT_STACK_REFERENCES.get(trident);
		if (forced || previousStack == null || ItemStack.isSameItemSameComponents(tridentStack, previousStack)) {
			if (trident.level() instanceof ServerLevel level) {
				Registry<Enchantment> registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
				XSNetworkHandler.INSTANCE.sendToPlayersInLevel(level, new UpdateTridentGlintColorPacket(trident.getId(), tridentStack, holder -> registry.getKey(holder.value())));
			}
		}
		TRIDENT_STACK_REFERENCES.put(trident, tridentStack);
	}
}
