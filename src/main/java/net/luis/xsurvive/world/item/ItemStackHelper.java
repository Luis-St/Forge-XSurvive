package net.luis.xsurvive.world.item;

import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ItemStackHelper {
	
	public static @NotNull ItemStack setupItemForSlot(@NotNull LivingEntity entity, @NotNull EquipmentSlot slot, @NotNull ItemStack stack, double specialMultiplier) {
		RandomSource rng = entity.getRandom();
		if (stack.canEquip(slot, entity)) {
			XSEnchantmentHelper.enchantItem(rng, stack, (int) (2 + (specialMultiplier * 2.0)), (int) (20 + specialMultiplier * rng.nextInt(18)), false, false);
		}
		return stack;
	}
	
	public static @NotNull ItemStack setupRandomItemForSlot(@NotNull LivingEntity entity, @NotNull EquipmentSlot slot, @NotNull List<Item> items, double specialMultiplier) {
		RandomSource rng = entity.getRandom();
		ItemStack stack = ItemStack.EMPTY;
		int tries = 0;
		do {
			ItemStack tempStack = setupItemForSlot(entity, slot, new ItemStack(items.get(rng.nextInt(items.size()))), specialMultiplier);
			if (tempStack.canEquip(slot, entity)) {
				stack = tempStack;
				break;
			}
			++tries;
		} while (stack.isEmpty() && 10 > tries);
		return stack;
	}
	
	public static @NotNull ItemStack getSwordForDifficulty(@NotNull LivingEntity entity, @NotNull DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = ItemEquipmentHelper.getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			List<Item> weapons = itemWeights.next();
			weapons.removeAll(ItemHelper.getWoodWeapons());
			weapons.removeAll(ItemHelper.getGoldWeapons());
			weapons.removeAll(ItemHelper.getStoneWeapons());
			weapons.removeIf((item) -> !(item instanceof SwordItem));
			if (!weapons.isEmpty()) {
				return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
			}
		}
		return new ItemStack(Items.IRON_SWORD);
	}
	
	public static @NotNull ItemStack getCrossbowForDifficulty(@NotNull DifficultyInstance instance) {
		WeightCollection<Item> itemWeights = ItemEquipmentHelper.getCrossbowWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			return new ItemStack(itemWeights.next());
		}
		return new ItemStack(Items.CROSSBOW);
	}
	
	public static @NotNull ItemStack getAxeForDifficulty(@NotNull LivingEntity entity, @NotNull DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = ItemEquipmentHelper.getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			List<Item> weapons = itemWeights.next();
			weapons.removeAll(ItemHelper.getWoodWeapons());
			weapons.removeAll(ItemHelper.getGoldWeapons());
			weapons.removeAll(ItemHelper.getStoneWeapons());
			weapons.removeIf((item) -> !(item instanceof AxeItem));
			if (!weapons.isEmpty()) {
				return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
			}
		}
		return new ItemStack(Items.IRON_AXE);
	}
}
