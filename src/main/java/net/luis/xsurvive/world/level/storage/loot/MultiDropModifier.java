package net.luis.xsurvive.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class MultiDropModifier extends LootModifier {
	
	public static final Codec<MultiDropModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).apply(instance, MultiDropModifier::new);
	});
	
	public MultiDropModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public @NotNull Codec<MultiDropModifier> codec() {
		return XSGlobalLootModifiers.MULTI_DROP_MODIFIER.get();
	}
	
	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
		ObjectArrayList<ItemStack> loot = new ObjectArrayList<>();
		generatedLoot.forEach((stack) -> {
			if (context.hasParam(LootContextParams.TOOL)) {
				loot.addAll(MultiDropModifier.this.multiplyItem(stack, context.getParam(LootContextParams.TOOL).getEnchantmentLevel(XSEnchantments.MULTI_DROP.get())));
			} else {
				XSurvive.LOGGER.error("Could not apply the MultiDrop logic on LootTable {}, since there is no Tool in the LootContext present", context.getQueriedLootTableId());
			}
		});
		return loot;
	}
	
	private @NotNull List<ItemStack> multiplyItem(@NotNull ItemStack stack, int level) {
		List<ItemStack> loot = Lists.newArrayList();
		if (level == 0) {
			loot.add(stack);
		} else if (!stack.isStackable()) {
			for (int i = 0; i <= level; i++) {
				loot.add(stack);
			}
		} else {
			int count = stack.getCount() * (level + 1);
			if (count > 64) {
				while (count > 0) {
					if (count > 64) {
						loot.add(this.copy(stack, 64));
						count -= 64;
					} else {
						loot.add(this.copy(stack, count));
						count = 0;
					}
				}
			} else {
				loot.add(this.copy(stack, count));
			}
		}
		return loot;
	}
	
	private @NotNull ItemStack copy(@NotNull ItemStack stack, int count) {
		ItemStack lootStack = stack.copy();
		lootStack.setCount(count);
		return lootStack;
	}
}
