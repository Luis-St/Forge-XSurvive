package net.luis.xsurvive.world.level.storage.loot;

import static net.luis.xsurvive.world.item.XSurviveItems.BLACK_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.BROWN_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.LIGHT_GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.WHITE_RUNE;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.world.item.RuneItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class RuneItemModifier extends LootModifier {
	
	public static final Codec<RuneItemModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).apply(instance, RuneItemModifier::new);
	});
	private static final Random RNG = new Random();
	
	private final List<RuneItem> runes = Lists.newArrayList(WHITE_RUNE.get(), GRAY_RUNE.get(), LIGHT_GRAY_RUNE.get(), BROWN_RUNE.get(), BLACK_RUNE.get());
	private final List<RuneItem> coloredRunes = ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
		return item instanceof RuneItem runeItem && !this.runes.contains(runeItem);
	}).map((item) -> {
		return (RuneItem) item;
	}).collect(Collectors.toList());
	
	public RuneItemModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public Codec<RuneItemModifier> codec() {
		return XSurviveGlobalLootModifiers.RUNE_ITEM_MODIFIER.get();
	}
	
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		for (int i = 0; i < 2; i++) {
			generatedLoot.add(this.getRandomRune());
		}
		return generatedLoot;
	}
	
	private ItemStack getRandomRune() {
		int i = RNG.nextInt(4);
		if (i > 0) {
			return new ItemStack(this.coloredRunes.get(RNG.nextInt(this.coloredRunes.size())));
		}
		return new ItemStack(this.runes.get(RNG.nextInt(this.runes.size())));
	}
	
}
