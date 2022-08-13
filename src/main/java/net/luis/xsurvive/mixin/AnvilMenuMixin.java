package net.luis.xsurvive.mixin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.EnchantmentHandler;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	
	private AnvilMenuMixin(MenuType<?> menuType, int id, Inventory inventory, ContainerLevelAccess levelAccess) {
		super(menuType, id, inventory, levelAccess);
	}
	
	@Shadow
	public int repairItemCountCost;
	@Shadow
	private String itemName;
	@Shadow
	private DataSlot cost;
	
	@Shadow
	public static int calculateIncreasedRepairCost(int cost) {
		return 0;
	}
	
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
			boolean decreaseRepairCost = false;
			if (rightStack.isEmpty()) {
				if ((leftStack.isDamageableItem() || leftStack.isEnchantable() || leftStack.isEnchanted()) && leftStack.getBaseRepairCost() > 0) {
					this.cost.set(1);
					resultStack.setRepairCost(resultStack.getBaseRepairCost() / 2);
					decreaseRepairCost = true;
				}
			} else if (leftStack.getItem() instanceof EnchantedGoldenBookItem) {
				this.resultSlots.setItem(0, ItemStack.EMPTY);
				this.cost.set(0);
			} else {
				if (!net.minecraftforge.common.ForgeHooks.onAnvilChange((AnvilMenu) (Object) this, leftStack, rightStack, this.resultSlots, this.itemName, repairCost, this.player)) {
					return;
				}
				enchantedBook = rightStack.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(rightStack).isEmpty();
				if (resultStack.isDamageableItem() && resultStack.getItem().isValidRepairItem(leftStack, rightStack)) {
					int damage = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					if (damage <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
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
						return;
					}
					if (resultStack.isDamageableItem() && !enchantedBook) {
						int leftDamage = leftStack.getMaxDamage() - leftStack.getDamageValue();
						int rightDamage = rightStack.getMaxDamage() - rightStack.getDamageValue();
						int resultDamage = rightDamage + resultStack.getMaxDamage() * 12 / 100;
						int combindedDamage = leftDamage + resultDamage;
						int damage = resultStack.getMaxDamage() - combindedDamage;
						if (damage < 0) {
							damage = 0;
						}
						if (damage < resultStack.getDamageValue()) {
							resultStack.setDamageValue(damage);
							enchantCost += 2;
						}
					}
					Map<Enchantment, Integer> rightEnchantments = EnchantmentHelper.getEnchantments(rightStack);
					boolean canEnchant = false;
					boolean survival = false;
					for (Enchantment rightEnchantment : rightEnchantments.keySet()) {
						if (rightEnchantment != null) {
							int resultLevel = resultEnchantments.getOrDefault(rightEnchantment, 0);
							int rightLevel = rightEnchantments.get(rightEnchantment);
							if (rightEnchantment instanceof IEnchantment ench) {
								if (resultLevel == rightLevel) {
									if (!ench.isGolden(resultLevel) && rightEnchantment.getMaxLevel() > rightLevel) {
										rightLevel++;
									}
								} else {
									rightLevel = Math.max(rightLevel, resultLevel);
								}
							} else {
								XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(rightEnchantment));
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
							if (!canEnchantOrCreative) {
								survival = true;
							} else {
								resultEnchantments.put(rightEnchantment, rightLevel);
								int rarityCost = 0;
								switch (rightEnchantment.getRarity()) {
									case COMMON:
										rarityCost = 1;
										break;
									case UNCOMMON:
										rarityCost = 2;
										break;
									case RARE:
										rarityCost = 4;
										break;
									case VERY_RARE:
										rarityCost = 8;
										break;
								}
								if (enchantedBook) {
									rarityCost = Math.max(1, rarityCost / 2);
								}
								enchantCost += rarityCost * rightLevel;
								if (leftStack.getCount() > 1) {
									enchantCost = 40;
								}
							}
						}
					}
					if (survival && !canEnchant) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.cost.set(0);
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
			if (!decreaseRepairCost) {
				this.cost.set(repairCost + enchantCost);
			}
			if (enchantCost <= 0 && !decreaseRepairCost) {
				resultStack = ItemStack.EMPTY;
			}
			if (renameCost == enchantCost && renameCost > 0 && this.cost.get() >= 60) {
				this.cost.set(59);
			}
			if (!rightStack.isEmpty() && !resultStack.isEmpty()) {
				List<Enchantment> enchantments = Lists.newArrayList();
				enchantments.addAll(EnchantmentHandler.getGoldenEnchantments(leftStack));
				enchantments.addAll(EnchantmentHandler.getGoldenEnchantments(rightStack));
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
				if (!decreaseRepairCost) {
					resultStack.setRepairCost(baseRepairCost);
				}
				EnchantmentHelper.setEnchantments(resultEnchantments, resultStack);
			}
			this.resultSlots.setItem(0, resultStack);
			this.broadcastChanges();
		}
		callback.cancel();
	}
	
}
