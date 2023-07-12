package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.util.Chance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class AdditionalChanceItemModifier extends LootModifier {
	
	public static final Codec<AdditionalChanceItemModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).and(instance.group(ForgeRegistries.ITEMS.getCodec().fieldOf("additional_item").forGetter((modifier) -> {
			return modifier.additionalItem;
		}), Chance.CODEC.fieldOf("chance").forGetter((modifier) -> {
			return modifier.chance;
		}))).apply(instance, AdditionalChanceItemModifier::new);
	});
	
	private final Item additionalItem;
	private final Chance chance;
	
	public AdditionalChanceItemModifier(LootItemCondition[] conditions, Item additionalItem, Chance chance) {
		super(conditions);
		this.additionalItem = additionalItem;
		this.chance = chance;
	}
	
	@Override
	public @NotNull Codec<AdditionalChanceItemModifier> codec() {
		return XSGlobalLootModifiers.ADDITIONAL_CHANCE_ITEM_MODIFIER.get();
	}
	
	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
		if (this.chance.result()) {
			generatedLoot.add(new ItemStack(this.additionalItem));
		}
		return generatedLoot;
	}
}
