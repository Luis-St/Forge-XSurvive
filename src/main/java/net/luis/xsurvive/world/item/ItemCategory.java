package net.luis.xsurvive.world.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import net.luis.xsurvive.dependency.DependencyHelper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

public enum ItemCategory {
	
	ITEM(Item.class),
	SWORD(SwordItem.class),
	BOW(BowItem.class),
	CROSSBOW(CrossbowItem.class),
	TRIDENT(TridentItem.class),
	PICKAXE(PickaxeItem.class),
	AXE(AxeItem.class),
	SHOVEL(ShovelItem.class),
	HOE(HoeItem.class),
	FLINT_AND_STEEL(FlintAndSteelItem.class),
	HELMET(ArmorItem.class),
	HEAD_BLOCK(AbstractSkullBlock.class),
	CHESTPLATE(ArmorItem.class),
	ELYTRA(ElytraItem.class),
	@SuppressWarnings("unchecked")
	ELYTRA_CHESTPLATE((Class<? extends ItemLike>) DependencyHelper.getDependencyClass("net.luis.xores.world.item.ElytraChestplateItem")),
	LEGGINGS(ArmorItem.class),
	BOOTS(ArmorItem.class),
	HORSE_ARMOR(HorseArmorItem.class);
	
	@Nullable
	private final Class<? extends ItemLike> clazz;
	
	private ItemCategory(Class<? extends ItemLike> clazz) {
		this.clazz = clazz;
	}
	
	public Optional<Class<? extends ItemLike>> asOptional() {
		return Optional.ofNullable(this.clazz);
	}
	
	public List<Item> getItems() {
		if (this == ITEM) {
			return Lists.newArrayList(ForgeRegistries.ITEMS.getValues());
		}
		return this.asOptional().map((clazz) -> {
			List<Item> items = Lists.newArrayList();
			for (Item item : ForgeRegistries.ITEMS.getValues()) {
				if (clazz.isInstance(item) || hasInterface(item, clazz)) {
					items.add(item);
				}
			}
			return items;
		}).orElseGet(Lists::newArrayList);
	}
	
	private static boolean hasInterface(Item item, Class<? extends ItemLike> clazz) {
		if (clazz.isInterface()) {
			return Lists.newArrayList(item.getClass().getInterfaces()).contains(clazz);
		}
		return false;
	}
	
}
