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
import net.luis.xsurvive.core.components.XSDataComponents;
import net.luis.xsurvive.network.NetworkPacket;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class UpdateTridentGlintColorPacket implements NetworkPacket {
	
	private final int tridentEntityId;
	private final int glintColor;
	private final Set<ResourceLocation> enchantments;
	
	public UpdateTridentGlintColorPacket(int tridentEntityId, @NotNull ItemStack tridentStack, @NotNull Function<Holder<Enchantment>, ResourceLocation> enchantmentMapper) {
		this.tridentEntityId = tridentEntityId;
		this.glintColor = tridentStack.getOrDefault(XSDataComponents.GLINT_COLOR.get(), -1);
		ItemEnchantments enchantments = tridentStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		this.enchantments = enchantments.keySet().stream().map(enchantmentMapper).collect(Collectors.toSet());
	}
	
	public UpdateTridentGlintColorPacket(@NotNull FriendlyByteBuf buffer) {
		this.tridentEntityId = buffer.readInt();
		this.glintColor = buffer.readInt();
		this.enchantments = new HashSet<>(buffer.readList(FriendlyByteBuf::readResourceLocation));
	}
	
	@Override
	public void encode(@NotNull FriendlyByteBuf buffer) {
		buffer.writeInt(this.tridentEntityId);
		buffer.writeInt(this.glintColor);
		buffer.writeCollection(this.enchantments, FriendlyByteBuf::writeResourceLocation);
	}
	
	@Override
	public void handle(@NotNull CustomPayloadEvent.Context context) {
		context.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleTridentGlintColorUpdate(this.tridentEntityId, this.glintColor, this.enchantments);
			});
		});
	}
}
