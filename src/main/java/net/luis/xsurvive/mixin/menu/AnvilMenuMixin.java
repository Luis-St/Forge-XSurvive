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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

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
	
	private AnvilMenuMixin(MenuType<?> menuType, int id, Inventory inventory, ContainerLevelAccess levelAccess) {
		super(menuType, id, inventory, levelAccess);
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
			Map<Enchantment, Integer> resultEnchantments = EnchantmentHelper.getEnchantments(resultStack);
			repairCost += leftStack.getBaseRepairCost() + (rightStack.isEmpty() ? 0 : rightStack.getBaseRepairCost());
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
				enchantedBook = rightStack.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(rightStack).isEmpty();
				if (resultStack.isDamageableItem() && resultStack.getItem().isValidRepairItem(leftStack, rightStack)) {
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
					Map<Enchantment, Integer> rightEnchantments = EnchantmentHelper.getEnchantments(rightStack);
					for (Map.Entry<Enchantment, Integer> entry : rightEnchantments.entrySet()) {
						Enchantment rightEnchantment = entry.getKey();
						if (rightEnchantment != null) {
							int resultLevel = resultEnchantments.getOrDefault(rightEnchantment, 0);
							int rightLevel = entry.getValue();
							if (rightEnchantment instanceof IEnchantment ench) {
								if (resultLevel == rightLevel) {
									if (!ench.isGoldenLevel(resultLevel) && rightEnchantment.getMaxLevel() > rightLevel) {
										rightLevel++;
									}
								} else {
									rightLevel = Math.max(rightLevel, resultLevel);
								}
							} else {
								XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(rightEnchantment));
								XSurvive.LOGGER.info("A deprecate vanilla logic is called");
								rightLevel = resultLevel == rightLevel ? rightLevel + 1 : Math.max(rightLevel, resultLevel);
							}
							boolean canEnchantOrCreative = rightEnchantment.canEnchant(leftStack);
							if (this.player.getAbilities().instabuild || leftStack.is(Items.ENCHANTED_BOOK)) {
								canEnchantOrCreative = true;
							}
							for (Enchantment resultEnchantment : resultEnchantments.keySet()) {
								if (resultEnchantment != rightEnchantment && !rightEnchantment.isCompatibleWith(resultEnchantment)) {
									canEnchantOrCreative = false;
									++enchantCost;
								}
							}
							if (canEnchantOrCreative) {
								resultEnchantments.put(rightEnchantment, rightLevel);
								int rarityCost = switch (rightEnchantment.getRarity()) {
									case COMMON -> 1;
									case UNCOMMON -> 2;
									case RARE -> 4;
									case VERY_RARE -> 8;
								};
								if (enchantedBook) {
									rarityCost = Math.max(1, rarityCost / 2);
								}
								enchantCost += rarityCost * rightLevel;
								if (leftStack.getCount() > 1) {
									enchantCost = 40;
								}
							} else {
								survival = true;
							}
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
				if (leftStack.hasCustomHoverName()) {
					renameCost = 1;
					enchantCost += renameCost;
					resultStack.resetHoverName();
				}
			} else if (!this.itemName.equals(leftStack.getHoverName().getString())) {
				renameCost = 1;
				enchantCost += renameCost;
				resultStack.setHoverName(Component.literal(this.itemName));
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
				List<Enchantment> enchantments = new ArrayList<>();
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
				int baseRepairCost = resultStack.getBaseRepairCost();
				if (!rightStack.isEmpty() && baseRepairCost < rightStack.getBaseRepairCost()) {
					baseRepairCost = rightStack.getBaseRepairCost();
				}
				if (renameCost != enchantCost || renameCost == 0) {
					baseRepairCost = calculateIncreasedRepairCost(baseRepairCost);
				}
				resultStack.setRepairCost(baseRepairCost);
				EnchantmentHelper.setEnchantments(resultEnchantments, resultStack);
			}
			this.resultSlots.setItem(0, resultStack);
			this.broadcastChanges();
		}
		callback.cancel();
	}
}