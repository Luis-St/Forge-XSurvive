package net.luis.xsurvive.world.item.alchemy;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ForgeRegistries;

public class XSurviveBrewingRecipe implements IBrewingRecipe {
	
	public static final List<Item> DEFAULT_INPUT = Lists.newArrayList(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);
	
	protected final List<Item> input;
	protected final Item ingredient;
	protected final Potion potion;
	protected final boolean waterApplyable;
	protected final Optional<PotionUpgrade> timeUpgrade; 
	protected final Optional<PotionUpgrade> powerUpgrade;
	
	protected XSurviveBrewingRecipe(List<Item> input, Item ingredient, Potion potion, boolean waterApplyable, Optional<PotionUpgrade> timeUpgrade, Optional<PotionUpgrade> powerUpgrade) {
		this.input = input.isEmpty() ? DEFAULT_INPUT : input;
		this.ingredient = ingredient;
		this.potion = potion;
		this.waterApplyable = waterApplyable;
		this.timeUpgrade = timeUpgrade;
		this.powerUpgrade = powerUpgrade;
	}
	
	@Override
	public boolean isInput(ItemStack input) {
		if (this.input.contains(input.getItem())) {
			Potion inputPotion = PotionUtils.getPotion(input);
			if (inputPotion == Potions.AWKWARD) {
				return true;
			} if (this.isUpgradeValid(this.timeUpgrade, inputPotion)) {
				return true;
			} else if (this.isUpgradeValid(this.powerUpgrade, inputPotion)) {
				return true;
			}
			return inputPotion == Potions.WATER && this.waterApplyable;
		}
		return false;
	}
	
	protected boolean isUpgradeValid(Optional<PotionUpgrade> optional, Potion inputPotion) {
		if (optional.isPresent()) {
			PotionUpgrade upgrade = optional.get();
			Potion basePotion = upgrade.basePotion(); 
			return basePotion == this.potion && basePotion == inputPotion;
		}
		return false;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		if (this.ingredient == ingredient.getItem()) {
			return true;
		} else if (this.timeUpgrade.isPresent()) {
			return this.timeUpgrade.get().upgradeItem() == ingredient.getItem();
		} else if (this.powerUpgrade.isPresent()) {
			return this.powerUpgrade.get().upgradeItem() == ingredient.getItem();
		}
		return false;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (!input.isEmpty() && !ingredient.isEmpty() && this.isInput(input) && this.isIngredient(ingredient)) {
			if (PotionUtils.getPotion(input) != this.potion) {
				return PotionUtils.setPotion(this.getPotionItem(input), this.potion);
			} else if (this.isValidUpgradeOutput(this.timeUpgrade, ingredient.getItem())) {
				return this.getUpgradeOutput(this.timeUpgrade, input);
			} else if (this.isValidUpgradeOutput(this.powerUpgrade, ingredient.getItem())) {
				return this.getUpgradeOutput(this.powerUpgrade, input);
			}
		}
		return ItemStack.EMPTY;
	}
	
	protected boolean isValidUpgradeOutput(Optional<PotionUpgrade> optional, Item ingredient) {
		if (optional.isPresent()) {
			return optional.get().upgradeItem() == ingredient;
		}
		return false;
	}
	
	protected ItemStack getPotionItem(ItemStack input) {
		if (input.getItem() instanceof PotionItem item) {
			return new ItemStack(item);
		}
		throw new IllegalArgumentException("Expected a potion item but got: " + ForgeRegistries.ITEMS.getKey(input.getItem()));
	}
	
	protected ItemStack getUpgradeOutput(Optional<PotionUpgrade> optional, ItemStack input) {
		PotionUpgrade upgrade = optional.orElseThrow();
		return PotionUtils.setPotion(this.getPotionItem(input), upgrade.resultPotion());
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof XSurviveBrewingRecipe recipe) {
			if (!this.input.equals(recipe.input)) {
				return false;
			} else if (this.ingredient != recipe.ingredient) {
				return false;
			} else if (this.potion != recipe.potion) {
				return false;
			} else if (this.waterApplyable != recipe.waterApplyable) {
				return false;
			} else if (!this.timeUpgrade.equals(recipe.timeUpgrade)) {
				return false;
			} else {
				return this.powerUpgrade.equals(recipe.powerUpgrade);
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSurviveBrewingRecipe{");
		builder.append("input=").append(this.input).append(",");
		builder.append("ingredient=").append(this.ingredient).append(",");
		builder.append("potion=").append(this.potion).append(",");
		builder.append("waterApplyable=").append(this.waterApplyable).append(",");
		Util.ifElse(this.timeUpgrade, (potionUpgrade) -> {
			builder.append("timeUpgrade=").append(this.toSting(potionUpgrade)).append(",");
		}, () -> {
			builder.append("timeUpgrade=").append("NO").append(",");
		});
		Util.ifElse(this.powerUpgrade, (potionUpgrade) -> {
			builder.append("timeUpgrade=").append(this.toSting(potionUpgrade)).append("}");
		}, () -> {
			builder.append("powerUpgrade=").append("NO").append("}");
		});
		return builder.toString();
	}
	
	protected String toSting(PotionUpgrade potionUpgrade) {
		StringBuilder builder = new StringBuilder("PotionUpgrade{");
		builder.append("basePotion=").append(potionUpgrade.basePotion()).append(",");
		builder.append("upgradeItem=").append(potionUpgrade.upgradeItem()).append(",");
		builder.append("resultPotion=").append(potionUpgrade.resultPotion()).append("}");
		return builder.toString();
	}
	
	public static class Builder {
		
		protected final List<Item> input;
		protected final Item ingredient;
		protected final Potion potion;
		protected boolean waterApplyable = false;
		protected Optional<PotionUpgrade> timeUpgrade = Optional.empty();
		protected Optional<PotionUpgrade> powerUpgrade = Optional.empty();
		
		public Builder(Item ingredient, Potion potion) {
			this.input = Lists.newArrayList();
			this.ingredient = ingredient;
			this.potion = potion;
		}
		
		public Builder addInput(Item input) {
			this.input.add(input);
			return this;
		}
		
		public Builder useDefaultInput() {
			this.input.addAll(XSurviveBrewingRecipe.DEFAULT_INPUT);
			return this;
		}
		
		public Builder setWaterApplyable() {
			this.waterApplyable = true;
			return this;
		}
		
		public Builder addTimeUpgrade(Potion resultPotion) {
			return this.addTimeUpgrade(Items.REDSTONE, resultPotion);
		}
		
		public Builder addTimeUpgrade(Item upgradeItem, Potion resultPotion) {
			this.timeUpgrade = Optional.of(new PotionUpgrade(this.potion, upgradeItem, resultPotion));
			return this;
		}
		
		public Builder addPowerUpgrade(Potion resultPotion) {
			return this.addTimeUpgrade(Items.GLOWSTONE_DUST, resultPotion);
		}
		
		public Builder addPowerUpgrade(Item upgradeItem, Potion resultPotion) {
			this.powerUpgrade = Optional.of(new PotionUpgrade(this.potion, upgradeItem, resultPotion));
			return this;
		}
		
		public IBrewingRecipe build() {
			return new XSurviveBrewingRecipe(this.input, this.ingredient, this.potion, this.waterApplyable, this.timeUpgrade, this.powerUpgrade);
		}
		
	}
	
	protected static record PotionUpgrade(Potion basePotion, Item upgradeItem, Potion resultPotion) {
		
	}

}
