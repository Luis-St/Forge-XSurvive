package net.luis.xsurvive.world.item;

import java.util.List;

import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;

/**
 *
 * @author Luis-st
 *
 */

public class ItemStackHelper {
	
	public static ItemStack setupItemForSlot(LivingEntity entity, EquipmentSlot slot, ItemStack stack, double specialMultiplier) {
		RandomSource rng = entity.getRandom();
		if (stack.canEquip(slot, entity)) {
			XSEnchantmentHelper.enchantItem(rng, stack, (int) (2 + (specialMultiplier * 2.0)), (int) (20 + specialMultiplier * rng.nextInt(18)), false, false);
		}
		return stack;
	}
	
	public static ItemStack setupRandomItemForSlot(LivingEntity entity, EquipmentSlot slot, List<Item> items, double specialMultiplier) {
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
	
	public static ItemStack getSwordForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = ItemWeightHelper.getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
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
	
	public static ItemStack getCrossbowForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<Item> itemWeights = ItemWeightHelper.getCrossbowWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			return new ItemStack(itemWeights.next());
		}
		return new ItemStack(Items.CROSSBOW);
	}
	
	public static ItemStack getAxeForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = ItemWeightHelper.getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
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
