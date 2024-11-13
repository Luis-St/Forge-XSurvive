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

package net.luis.xsurvive.network.packet;

import net.luis.xsurvive.client.XSClientPacketHandler;
import net.luis.xsurvive.network.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class UpdateTridentGlintColorPacket implements NetworkPacket {
	
	private final int tridentEntityId;
	private final ItemStack tridentStack;
	
	public UpdateTridentGlintColorPacket(int tridentEntityId, @NotNull ItemStack tridentStack) {
		this.tridentEntityId = tridentEntityId;
		this.tridentStack = tridentStack;
	}
	
	public UpdateTridentGlintColorPacket(@NotNull FriendlyByteBuf buffer) {
		this.tridentEntityId = buffer.readInt();
		this.tridentStack = buffer.readJsonWithCodec(ItemStack.CODEC);
	}
	
	@Override
	public void encode(@NotNull FriendlyByteBuf buffer) {
		buffer.writeInt(this.tridentEntityId);
		buffer.writeJsonWithCodec(ItemStack.CODEC, this.tridentStack);
	}
	
	@Override
	public void handle(@NotNull CustomPayloadEvent.Context context) {
		context.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleTridentGlintColorUpdate(this.tridentEntityId, this.tridentStack);
			});
		});
	}
}
