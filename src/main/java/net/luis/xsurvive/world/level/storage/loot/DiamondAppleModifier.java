package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * 
 * @author Luis-st
 *
 */

public class DiamondAppleModifier extends LootModifier {
	
	public static final Codec<DiamondAppleModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).apply(instance, DiamondAppleModifier::new);
	});
	private static final Random RNG = new Random();
	
	public DiamondAppleModifier(LootItemCondition[] conditions) {
		super(conditions);
	}
	
	@Override
	public Codec<DiamondAppleModifier> codec() {
		return XSurviveGlobalLootModifiers.DIAMOND_APPLE_MODIFIER.get();
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server != null) {
			if (server.getLootTables() instanceof ILootTables lootTables) {
				this.addItem(generatedLoot, context, lootTables, Items.GOLDEN_APPLE, XSurviveItems.DIAMOND_APPLE.get(), 5, 10);
				this.addItem(generatedLoot, context, lootTables, Items.ENCHANTED_GOLDEN_APPLE, XSurviveItems.ENCHANTED_DIAMOND_APPLE.get(), 3, 5);
			} else {
				XSurvive.LOGGER.error("Server LootTables is not a instance of ILootTables");
				XSurvive.LOGGER.info("Enchanted diamond Apple will not be generated in any loot tables");
			}
		}
		return generatedLoot;
	}
	
	private void addItem(ObjectArrayList<ItemStack> generatedLoot, LootContext context, ILootTables lootTables, Item itemToCheck, Item additionalItem, int hasChance, int hasNotChance) {
		List<ResourceLocation> tables = lootTables.getTablesWith(itemToCheck);
		if (tables.contains(context.getQueriedLootTableId())) {
			if (this.containsItem(generatedLoot, itemToCheck) && hasChance - 1 >= RNG.nextInt(100)) {
				generatedLoot.add(new ItemStack(additionalItem));
			} else if (hasNotChance - 1 >= RNG.nextInt(100)) {
				generatedLoot.add(new ItemStack(additionalItem));
			}
		}
	}
	
	private boolean containsItem(ObjectArrayList<ItemStack> generatedLoot, Item itemToCheck) {
		return generatedLoot.stream().map(ItemStack::getItem).anyMatch((item) -> {
			return item == itemToCheck;
		});
	}
	
}
