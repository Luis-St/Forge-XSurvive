package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Chance;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class EndEnchantmentsModifier extends LootModifier {
	
	public static final Codec<EndEnchantmentsModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).and(instance.group(ForgeRegistries.ENCHANTMENTS.getCodec().fieldOf("enchantment").forGetter((modifier) -> {
			return modifier.enchantment;
		}), Chance.CODEC.fieldOf("chance").forGetter((modifier) -> {
			return modifier.chance;
		}), Codec.BOOL.fieldOf("allow_golden").forGetter((modifier) -> {
			return modifier.allowGolden;
		}))).apply(instance, EndEnchantmentsModifier::new);
	});
	
	private final Enchantment enchantment;
	private final Chance chance;
	private final boolean allowGolden;
	
	public EndEnchantmentsModifier(LootItemCondition[] conditions, Enchantment enchantment, Chance chance, boolean allowGolden) {
		super(conditions);
		this.enchantment = enchantment;
		this.chance = chance;
		this.allowGolden = allowGolden;
	}
	
	@Override
	public @NotNull Codec<EndEnchantmentsModifier> codec() {
		return XSGlobalLootModifiers.END_ENCHANTMENTS_MODIFIER.get();
	}
	
	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
		if (this.chance.result()) {
			if (this.allowGolden) {
				if (this.enchantment instanceof IEnchantment ench) {
					generatedLoot.add(EnchantedGoldenBookItem.createForEnchantment(this.enchantment));
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment));
					generatedLoot.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(this.enchantment, 1)));
				}
			} else {
				generatedLoot.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(this.enchantment, 1)));
			}
		}
		return generatedLoot;
	}
}
