package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class GoldenBookModifier extends LootModifier {
	
	public static final Codec<GoldenBookModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).apply(instance, GoldenBookModifier::new);
	});
	private static final Random RNG = new Random();

	private final List<Enchantment> trashEnchantments = LootModifierHelper.getTrashEnchantments();
	private final List<Enchantment> enchantments = LootModifierHelper.getEnchantments();
	private final List<Enchantment> rareEnchantments = LootModifierHelper.getRareEnchantments();
	private final List<Enchantment> veryRareEnchantments = LootModifierHelper.getVeryRareEnchantments();
	private final List<Enchantment> endVeryRareEnchantments = LootModifierHelper.getEndVeryRareEnchantments();
	private final List<Enchantment> treasureEnchantments = LootModifierHelper.getTreasureEnchantments();
	private final List<Enchantment> netherTreasureEnchantments = LootModifierHelper.getNetherTreasureEnchantments();
	
	public GoldenBookModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public Codec<GoldenBookModifier> codec() {
		return XSurviveGlobalLootModifiers.GOLDEN_BOOK_MODIFIER.get();
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.add(this.getGoldenBook(context));
		return generatedLoot;
	}
	
	private ItemStack getGoldenBook(LootContext context) {
		ItemStack stack = new ItemStack(XSurviveItems.ENCHANTED_GOLDEN_BOOK.get());
		Enchantment enchantment = this.getRandomEnchantment(context.getQueriedLootTableId());
		if (enchantment != null) {
			if (stack.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
				goldenBook.setEnchantment(stack, enchantment);
			}
			return stack;
		}
		XSurvive.LOGGER.error("Fail to get a golden enchantment for the enchanted golden book in loot table {}", context.getQueriedLootTableId());
		return ItemStack.EMPTY;
	}
	
	private Enchantment getRandomEnchantment(ResourceLocation location) {
		Enchantment enchantment;
		int i = RNG.nextInt(29);
		if (i == 0) {
			enchantment = this.trashEnchantments.get(RNG.nextInt(this.trashEnchantments.size()));
		} else if (10 >= i) {
			enchantment = this.enchantments.get(RNG.nextInt(this.enchantments.size()));
		} else if (18 >= i) {
			enchantment = this.rareEnchantments.get(RNG.nextInt(this.rareEnchantments.size()));
		} else if (24 >= i) {
			if (location.equals(BuiltInLootTables.END_CITY_TREASURE)) {
				enchantment = this.endVeryRareEnchantments.get(RNG.nextInt(this.endVeryRareEnchantments.size()));
			} else {
				enchantment = this.veryRareEnchantments.get(RNG.nextInt(this.veryRareEnchantments.size()));
			}
		} else {
			if (location.equals(BuiltInLootTables.BASTION_TREASURE)) {
				enchantment = this.netherTreasureEnchantments.get(RNG.nextInt(this.netherTreasureEnchantments.size()));
			} else {
				enchantment = this.treasureEnchantments.get(RNG.nextInt(this.treasureEnchantments.size()));
			}
		}
		if (enchantment instanceof IEnchantment ench) {
			if (ench.isAllowedOnGoldenBooks()) {
				return enchantment;
			} else {
				XSurvive.LOGGER.warn("Enchantment {} is not allowed on golden books", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return getRandomEnchantment(location);
			}
		}
		XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
		return null;
	}

}
