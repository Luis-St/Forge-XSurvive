package net.luis.xsurvive.dependency.xbackpack;

import com.google.common.collect.Lists;
import net.luis.xbackpack.network.XBNetworkHandler;
import net.luis.xbackpack.network.packet.extension.UpdateAnvilPacket;
import net.luis.xbackpack.world.capability.BackpackProvider;
import net.luis.xbackpack.world.inventory.AbstractExtensionContainerMenu;
import net.luis.xbackpack.world.inventory.extension.AnvilExtensionMenu;
import net.luis.xbackpack.world.inventory.extension.slot.ExtensionSlot;
import net.luis.xbackpack.world.inventory.handler.CraftingHandler;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

public class XSAnvilExtensionMenu extends AnvilExtensionMenu {
	
	private final CraftingHandler handler;
	private int repairItemCountCost;
	private int cost;
	
	public XSAnvilExtensionMenu(AbstractExtensionContainerMenu menu, Player player) {
		super(menu, player);
		this.handler = BackpackProvider.get(this.player).getAnvilHandler();
	}
	
	private static int calculateIncreasedRepairCost(int cost) {
		return cost * 2 + 1;
	}
	
	@Override
	public void open() {
		if (!this.handler.getInputHandler().getStackInSlot(0).isEmpty() && !this.handler.getInputHandler().getStackInSlot(1).isEmpty()) {
			this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
			this.createResult();
		}
	}
	
	@Override
	public void addSlots(@NotNull Consumer<Slot> consumer) {
		consumer.accept(new ExtensionSlot(this, this.handler.getInputHandler(), 0, 225, 73));
		consumer.accept(new ExtensionSlot(this, this.handler.getInputHandler(), 1, 260, 73));
		consumer.accept(new ExtensionSlot(this, this.handler.getResultHandler(), 0, 304, 73) {
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return false;
			}
			
			@Override
			public boolean mayPickup(Player player) {
				return XSAnvilExtensionMenu.this.mayPickup(player);
			}
			
			@Override
			public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
				XSAnvilExtensionMenu.this.onTake(player, stack);
				super.onTake(player, stack);
			}
		});
	}
	
	public boolean mayPickup(@NotNull Player player) {
		return (player.getAbilities().instabuild || player.experienceLevel >= this.cost) && this.cost > 0;
	}
	
	private void onTake(@NotNull Player player, @NotNull ItemStack stack) {
		if (player instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.getAbilities().instabuild) {
				serverPlayer.giveExperienceLevels(-this.cost);
			}
			this.handler.getInputHandler().setStackInSlot(0, ItemStack.EMPTY);
			if (this.repairItemCountCost > 0) {
				ItemStack rightStack = this.handler.getInputHandler().getStackInSlot(1);
				if (!rightStack.isEmpty() && rightStack.getCount() > this.repairItemCountCost) {
					rightStack.shrink(this.repairItemCountCost);
					this.handler.getInputHandler().setStackInSlot(1, rightStack);
				} else {
					this.handler.getInputHandler().setStackInSlot(1, ItemStack.EMPTY);
				}
			} else {
				this.handler.getInputHandler().setStackInSlot(1, ItemStack.EMPTY);
			}
			this.cost = 0;
			this.playSound(serverPlayer, serverPlayer.serverLevel());
		}
		this.menu.broadcastChanges();
		this.broadcastChanges();
		this.createResult();
	}
	
	private void playSound(@NotNull ServerPlayer player, @NotNull ServerLevel level) {
		player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ANVIL_USE), SoundSource.BLOCKS, player.getX(), player.getY(), player.getZ(), 1.0F, level.random.nextFloat() * 0.1F + 0.9F,
			level.random.nextLong()));
	}
	
	@Override
	public void slotsChanged() {
		this.createResult();
	}
	
	private void createResult() {
		ItemStack leftStack = this.handler.getInputHandler().getStackInSlot(0);
		this.cost = 1;
		int enchantCost = 0;
		int repairCost = 0;
		int renameCost = 0;
		if (leftStack.isEmpty()) {
			this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
			this.cost = 0;
		} else {
			ItemStack resultStack = leftStack.copy();
			ItemStack rightStack = this.handler.getInputHandler().getStackInSlot(1);
			Map<Enchantment, Integer> resultEnchantments = EnchantmentHelper.getEnchantments(resultStack);
			repairCost += leftStack.getBaseRepairCost() + (rightStack.isEmpty() ? 0 : rightStack.getBaseRepairCost());
			this.repairItemCountCost = 0;
			boolean enchantedBook;
			boolean decreaseRepairCost = false;
			if (leftStack.getItem() instanceof EnchantedGoldenBookItem) {
				this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
				this.cost = 0;
				return;
			} else {
				if (!this.onAnvilUpdate(leftStack, rightStack, repairCost)) {
					return;
				}
				enchantedBook = rightStack.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(rightStack).isEmpty();
				if (resultStack.isDamageableItem() && resultStack.getItem().isValidRepairItem(leftStack, rightStack)) {
					int damage = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					if (damage <= 0) {
						this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
						this.cost = 0;
						return;
					}
					int currentRepairCost;
					for (currentRepairCost = 0; damage > 0 && currentRepairCost < rightStack.getCount(); ++currentRepairCost) {
						int currentDamage = resultStack.getDamageValue() - damage;
						resultStack.setDamageValue(currentDamage);
						++enchantCost;
						damage = Math.min(resultStack.getDamageValue(), resultStack.getMaxDamage() / 4);
					}
					this.repairItemCountCost = currentRepairCost;
				} else {
					if (!enchantedBook && (!resultStack.is(rightStack.getItem()) || !resultStack.isDamageableItem())) {
						this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
						this.cost = 0;
						return;
					}
					if (resultStack.isDamageableItem() && !enchantedBook) {
						int leftDamage = leftStack.getMaxDamage() - leftStack.getDamageValue();
						int rightDamage = rightStack.getMaxDamage() - rightStack.getDamageValue();
						int resultDamage = rightDamage + resultStack.getMaxDamage() * 12 / 100;
						int combindedDamage = leftDamage + resultDamage;
						int damage = resultStack.getMaxDamage() - combindedDamage;
						if (damage < 0) {
							damage = 0;
						}
						if (damage < resultStack.getDamageValue()) {
							resultStack.setDamageValue(damage);
							enchantCost += 2;
						}
					}
					Map<Enchantment, Integer> rightEnchantments = EnchantmentHelper.getEnchantments(rightStack);
					boolean canEnchant = false;
					boolean survival = false;
					for (Enchantment rightEnchantment : rightEnchantments.keySet()) {
						if (rightEnchantment != null) {
							int resultLevel = resultEnchantments.getOrDefault(rightEnchantment, 0);
							int rightLevel = rightEnchantments.get(rightEnchantment);
							if (rightEnchantment instanceof IEnchantment ench) {
								if (resultLevel == rightLevel) {
									if (!ench.isGoldenLevel(resultLevel) && rightEnchantment.getMaxLevel() > rightLevel) {
										rightLevel++;
									}
								} else {
									rightLevel = Math.max(rightLevel, resultLevel);
								}
							} else {
								XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(rightEnchantment));
								XSurvive.LOGGER.info("A deprecate vanilla logic is called");
								rightLevel = resultLevel == rightLevel ? rightLevel + 1 : Math.max(rightLevel, resultLevel);
							}
							boolean canEnchantOrCreative = rightEnchantment.canEnchant(leftStack);
							if (this.player.getAbilities().instabuild || leftStack.is(Items.ENCHANTED_BOOK)) {
								canEnchantOrCreative = true;
							}
							for (Enchantment resultEnchantment : resultEnchantments.keySet()) {
								if (resultEnchantment != rightEnchantment && !rightEnchantment.isCompatibleWith(resultEnchantment)) {
									canEnchantOrCreative = false;
									++enchantCost;
								}
							}
							if (!canEnchantOrCreative) {
								survival = true;
							} else {
								resultEnchantments.put(rightEnchantment, rightLevel);
								int rarityCost = switch (rightEnchantment.getRarity()) {
									case COMMON -> 1;
									case UNCOMMON -> 2;
									case RARE -> 4;
									case VERY_RARE -> 8;
								};
								if (enchantedBook) {
									rarityCost = Math.max(1, rarityCost / 2);
								}
								enchantCost += rarityCost * rightLevel;
								if (leftStack.getCount() > 1) {
									enchantCost = 40;
								}
							}
						}
					}
					if (survival && !canEnchant) {
						this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
						this.cost = 0;
						return;
					}
					
				}
			}
			if (enchantedBook && !resultStack.isBookEnchantable(rightStack)) {
				resultStack = ItemStack.EMPTY;
			}
			if (!decreaseRepairCost) {
				this.cost = repairCost + enchantCost;
			}
			if (enchantCost <= 0 && !decreaseRepairCost) {
				resultStack = ItemStack.EMPTY;
			}
			if (!rightStack.isEmpty() && !resultStack.isEmpty()) {
				List<Enchantment> enchantments = Lists.newArrayList();
				enchantments.addAll(XSEnchantmentHelper.getGoldenEnchantments(leftStack));
				enchantments.addAll(XSEnchantmentHelper.getGoldenEnchantments(rightStack));
				if (!enchantments.isEmpty()) {
					this.cost = enchantments.size() * 10;
				}
			}
			if (!resultStack.isEmpty()) {
				int baseRepairCost = resultStack.getBaseRepairCost();
				if (!rightStack.isEmpty() && baseRepairCost < rightStack.getBaseRepairCost()) {
					baseRepairCost = rightStack.getBaseRepairCost();
				}
				baseRepairCost = calculateIncreasedRepairCost(baseRepairCost);
				if (!decreaseRepairCost) {
					resultStack.setRepairCost(baseRepairCost);
				}
				EnchantmentHelper.setEnchantments(resultEnchantments, resultStack);
			}
			this.handler.getResultHandler().setStackInSlot(0, resultStack);
			this.broadcastChanges();
		}
	}
	
	private void broadcastChanges() {
		XBNetworkHandler.INSTANCE.sendToPlayer(player, new UpdateAnvilPacket(this.cost));
	}
	
	public int getCost() {
		return this.cost;
	}
	
	private boolean onAnvilUpdate(@NotNull ItemStack leftStack, @NotNull ItemStack rightStack, int repairCost) {
		AnvilUpdateEvent event = new AnvilUpdateEvent(leftStack, rightStack, leftStack.getDisplayName().getString(), repairCost, this.player);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		} else if (event.getOutput().isEmpty()) {
			return true;
		}
		this.handler.getResultHandler().setStackInSlot(0, event.getOutput());
		this.cost = event.getCost();
		this.repairItemCountCost = event.getMaterialCost();
		this.broadcastChanges();
		return false;
	}
	
	@Override
	public void close() {
		this.handler.getResultHandler().setStackInSlot(0, ItemStack.EMPTY);
	}
}
