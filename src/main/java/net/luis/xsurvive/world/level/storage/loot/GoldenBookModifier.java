package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class GoldenBookModifier extends LootModifier {
	
	private static final Random RNG = new Random();

	private final List<Enchantment> trashEnchantments = Lists.newArrayList(Enchantments.FIRE_PROTECTION, Enchantments.BLAST_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.KNOCKBACK);
	private final List<Enchantment> enchantments = Lists.newArrayList(Enchantments.SHARPNESS, Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.BLOCK_EFFICIENCY, Enchantments.UNBREAKING, Enchantments.PIERCING, Enchantments.SMITE,
		Enchantments.PUNCH_ARROWS, Enchantments.POWER_ARROWS, Enchantments.FISHING_SPEED, Enchantments.BANE_OF_ARTHROPODS, XSurviveEnchantments.BLASTING.get(), XSurviveEnchantments.GROWTH.get(), XSurviveEnchantments.ENDER_SLAYER.get());
	private final List<Enchantment> rareEnchantments = Lists.newArrayList(Enchantments.FALL_PROTECTION, Enchantments.RESPIRATION, Enchantments.DEPTH_STRIDER, Enchantments.SWEEPING_EDGE, Enchantments.FISHING_LUCK, Enchantments.FIRE_ASPECT,
		Enchantments.QUICK_CHARGE, XSurviveEnchantments.FROST_ASPECT.get(), XSurviveEnchantments.POISON_ASPECT.get());
	private final List<Enchantment> veryRareEnchantments = Lists.newArrayList(Enchantments.BLOCK_FORTUNE, Enchantments.MOB_LOOTING, Enchantments.LOYALTY, Enchantments.RIPTIDE);
	private final List<Enchantment> endVeryRareEnchantments = Util.make(Lists.newArrayList(), (list) -> {
		list.addAll(this.veryRareEnchantments);
		list.add(XSurviveEnchantments.VOID_PROTECTION.get());
	});
	private final List<Enchantment> treasureEnchantments = Lists.newArrayList(Enchantments.SWIFT_SNEAK, XSurviveEnchantments.MULTI_DROP.get());
	private final List<Enchantment> netherTreasureEnchantments = Util.make(Lists.newArrayList(), (list) -> {
		list.addAll(this.treasureEnchantments);
		list.add(Enchantments.SOUL_SPEED);
	});
	
	public GoldenBookModifier(LootItemCondition[] conditions) {
		super(conditions);
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
		if (enchantment instanceof IEnchantment) {
			return enchantment;
		}
		XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
		return null;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<GoldenBookModifier> {

		@Override
		public GoldenBookModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new GoldenBookModifier(conditions);
		}

		@Override
		public JsonObject write(GoldenBookModifier instance) {
			return this.makeConditions(instance.conditions);
		}
		
	}

}
