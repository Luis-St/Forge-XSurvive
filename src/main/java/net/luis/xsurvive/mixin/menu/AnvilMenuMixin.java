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

package net.luis.xsurvive.mixin.menu;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.common.ForgeHooks;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(AnvilMenu.class)
@SuppressWarnings({ "DataFlowIssue", "DuplicatedCode" })
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	
	//region Mixin
	@Shadow private String itemName;
	@Shadow private DataSlot cost;
	@Shadow public int repairItemCountCost;
	
	private AnvilMenuMixin(@Nullable MenuType<?> menuType, int id, @NotNull Inventory inventory, @NotNull ContainerLevelAccess containerAccess, @NotNull ItemCombinerMenuSlotDefinition definition) {
		super(menuType, id, inventory, containerAccess, definition);
	}
	
	@Shadow
	public static int calculateIncreasedRepairCost(int cost) {
		return 0;
	}
	//endregion
	
	@Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
	public void createResult(CallbackInfo callback) {
		ItemStack leftStack = this.inputSlots.getItem(0);
		this.cost.set(1);
		int enchantCost = 0;
		int repairCost = 0;
		int renameCost = 0;
		if (leftStack.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.cost.set(0);
		} else {
			ItemStack resultStack = leftStack.copy();
			ItemStack rightStack = this.inputSlots.getItem(1);
			ItemEnchantments.Mutable resultEnchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(resultStack));
			repairCost += leftStack.getOrDefault(DataComponents.REPAIR_COST, 0) + rightStack.getOrDefault(DataComponents.REPAIR_COST, 0);
			this.repairItemCountCost = 0;
			boolean enchantedBook = false;
			if (leftStack.getItem() instanceof EnchantedGoldenBookItem) {
				this.resultSlots.setItem(0, ItemStack.EMPTY);
				this.cost.set(0);
				callback.cancel();
				return;
			} else if (!rightStack.isEmpty()) {
				if (!ForgeHooks.onAnvilChange((AnvilMenu) (Object) this, leftStack, rightStack, this.resultSlots, this.itemName, repairCost, this.player)) {
					return;
				}
				enchantedBook = rightStack.has(DataComponents.STORED_ENCHANTMENTS);
				if (resultStack.isDamageableItem() && resultStack.isValidRepairItem(rightStack)) {
					int damage = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					if (damage <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						callback.cancel();
						return;
					}
					int currentRepairCost;
					for (currentRepairCost = 0; damage > 0 && currentRepairCost < rightStack.getCount(); ++currentRepairCost) {
						int currentDamage = resultStack.getDamageValue() - damage;
						resultStack.setDamageValue(currentDamage);
						++enchantCost;
						damage = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					}
					this.repairItemCountCost = currentRepairCost;
				} else {
					if (!enchantedBook && (!resultStack.is(rightStack.getItem()) || !resultStack.isDamageableItem())) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						callback.cancel();
						return;
					}
					if (resultStack.isDamageableItem() && !enchantedBook) {
						int leftDamage = leftStack.getMaxDamage() - leftStack.getDamageValue();
						int rightDamage = rightStack.getMaxDamage() - rightStack.getDamageValue();
						int resultDamage = rightDamage + resultStack.getMaxDamage() * 12 / 100;
						int combinedDamage = leftDamage + resultDamage;
						int damage = resultStack.getMaxDamage() - combinedDamage;
						if (damage < 0) {
							damage = 0;
						}
						if (damage < resultStack.getDamageValue()) {
							resultStack.setDamageValue(damage);
							enchantCost += 2;
						}
					}
					boolean survival = false;
					ItemEnchantments rightEnchantments = rightStack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
					for (Object2IntMap.Entry<Holder<Enchantment>> entry : rightEnchantments.entrySet()) {
						Holder<Enchantment> rightEnchantment = entry.getKey();
						int resultLevel = resultEnchantments.getLevel(rightEnchantment);
						int rightLevel = entry.getIntValue();
						if (GoldenEnchantmentHelper.isGoldenEnchantment(rightEnchantment)) {
							if (resultLevel == rightLevel) {
								if (!GoldenEnchantmentHelper.isGoldenLevel(rightEnchantment, resultLevel) && rightEnchantment.value().getMaxLevel() > rightLevel) {
									rightLevel++;
								}
							} else {
								rightLevel = Math.max(rightLevel, resultLevel);
							}
						} else {
							rightLevel = resultLevel == rightLevel ? rightLevel + 1 : Math.max(rightLevel, resultLevel);
						}
						boolean canEnchantOrCreative = rightEnchantment.value().canEnchant(leftStack);
						if (this.player.getAbilities().instabuild || leftStack.is(Items.ENCHANTED_BOOK)) {
							canEnchantOrCreative = true;
						}
						for (Holder<Enchantment> resultEnchantment : resultEnchantments.keySet()) {
							if (resultEnchantment != rightEnchantment && !Enchantment.areCompatible(rightEnchantment, resultEnchantment)) {
								canEnchantOrCreative = false;
								++enchantCost;
							}
						}
						if (canEnchantOrCreative) {
							resultEnchantments.set(rightEnchantment, rightLevel);
							int anvilCost = rightEnchantment.value().getAnvilCost();
							if (enchantedBook) {
								anvilCost = Math.max(1, anvilCost / 2);
							}
							enchantCost += anvilCost * rightLevel;
							if (leftStack.getCount() > 1) {
								enchantCost = 40;
							}
						} else {
							survival = true;
						}
					}
					if (survival) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
						callback.cancel();
						return;
					}
				}
			}
			if (StringUtils.isBlank(this.itemName)) {
				Component hoverName = leftStack.get(DataComponents.CUSTOM_NAME);
				if (hoverName != null) {
					renameCost = 1;
					enchantCost += renameCost;
					resultStack.remove(DataComponents.CUSTOM_NAME);
				}
			} else if (!this.itemName.equals(leftStack.getHoverName().getString())) {
				renameCost = 1;
				enchantCost += renameCost;
				resultStack.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
			}
			if (enchantedBook && !resultStack.isBookEnchantable(rightStack)) {
				resultStack = ItemStack.EMPTY;
			}
			this.cost.set(repairCost + enchantCost);
			if (0 >= enchantCost) {
				resultStack = ItemStack.EMPTY;
			}
			if (renameCost == enchantCost && renameCost > 0 && this.cost.get() >= 60) {
				this.cost.set(59);
			}
			if (!rightStack.isEmpty() && !resultStack.isEmpty()) {
				List<Holder<Enchantment>> enchantments = new ArrayList<>();
				enchantments.addAll(XSEnchantmentHelper.getGoldenEnchantments(leftStack));
				enchantments.addAll(XSEnchantmentHelper.getGoldenEnchantments(rightStack));
				if (!enchantments.isEmpty()) {
					int cost = enchantments.size() * 10;
					if (renameCost > 0) {
						cost += renameCost;
					}
					this.cost.set(cost);
				}
			}
			if (!resultStack.isEmpty()) {
				int baseRepairCost = resultStack.getOrDefault(DataComponents.REPAIR_COST, 0);
				if (!rightStack.isEmpty() && baseRepairCost < rightStack.getOrDefault(DataComponents.REPAIR_COST, 0)) {
					baseRepairCost = rightStack.getOrDefault(DataComponents.REPAIR_COST, 0);
				}
				baseRepairCost = calculateIncreasedRepairCost(baseRepairCost);
				resultStack.set(DataComponents.REPAIR_COST, baseRepairCost);
				EnchantmentHelper.setEnchantments(resultStack, resultEnchantments.toImmutable());
			}
			this.resultSlots.setItem(0, resultStack);
			this.broadcastChanges();
		}
		callback.cancel();
	}
}
