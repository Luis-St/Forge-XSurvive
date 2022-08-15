package net.luis.xsurvive.world.level.storage.loot;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.util.RarityList;
import net.luis.xsurvive.util.WeightCollection;
import net.minecraft.world.item.Item;
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
		return LootModifier.codecStart(instance).and(instance.group(Codec.INT.fieldOf("rune_count").forGetter((modifier) -> {
			return modifier.runeCount;
		}), WeightCollection.codec(RarityList.codec(ForgeRegistries.ITEMS.getCodec())).fieldOf("runes").forGetter((modifier) -> {
			return modifier.runeWeights;
		}))).apply(instance, RuneItemModifier::new);
	});
	private static final Random RNG = new Random();
	
	private final int runeCount;
	private final WeightCollection<RarityList<Item>> runeWeights;
	
	public RuneItemModifier(LootItemCondition[] conditions, int runeCount, WeightCollection<RarityList<Item>> runeWeights) {
		super(conditions);
		this.runeCount = runeCount;
		this.runeWeights = runeWeights;
	}
	
	@Override
	public Codec<RuneItemModifier> codec() {
		return XSGlobalLootModifiers.RUNE_ITEM_MODIFIER.get();
	}
	
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		for (int i = 0; i < this.runeCount; i++) {
			generatedLoot.add(this.getRandomRune());
		}
		return generatedLoot;
	}
	
	private ItemStack getRandomRune() {
		RarityList<Item> runes = RarityList.copy(this.runeWeights.next());
		return new ItemStack(runes.get(RNG.nextInt(runes.size())));
	}
	
}
