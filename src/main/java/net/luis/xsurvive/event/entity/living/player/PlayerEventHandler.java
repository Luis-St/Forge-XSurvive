package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.world.entity.EntityHelper;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.inventory.EnderChestMenu;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.enchantment.EnchantedItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class PlayerEventHandler {
	
	private static final String ATTACK_SPEED_TRANSLATION_KEY = Component.translatable(Attributes.ATTACK_SPEED.getDescriptionId()).getString();
	private static final Component ENDER_CHEST = Component.translatable("container.enderchest");
	
	@SubscribeEvent
	public static void anvilUpdate(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if (!left.isEmpty()) {
			ItemStack result = left.copy();
			if (right.isEmpty()) {
				if ((left.isDamageableItem() || left.isEnchantable() || left.isEnchanted()) && left.getBaseRepairCost() > 0) {
					event.setCost(1);
					result.setRepairCost(result.getBaseRepairCost() / 2);
					event.setOutput(result);
				}
			} else if (left.getItem() instanceof EnchantedGoldenBookItem) {
				event.setCanceled(true);
			} else if ((left.isEnchantable() || left.isEnchanted()) && right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
				Enchantment enchantment = goldenBook.getEnchantment(right);
				if (enchantment instanceof IEnchantment ench) {
					EnchantedItem enchantedItem;
					if (ench.isUpgradeEnchantment()) {
						enchantedItem = IEnchantment.upgrade(left, right);
					} else {
						enchantedItem = IEnchantment.merge(left, right);
					}
					event.setOutput(enchantedItem.stack());
					event.setCost(enchantedItem.cost());
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				}
			} else if (left.isEnchanted() || left.getItem().isFoil(left)) {
				if (right.getItem() instanceof IGlintColor glintColor && !left.is(Items.ENCHANTED_BOOK)) {
					int color = glintColor.getGlintColor(right);
					if (17 >= color && color >= 0) {
						CompoundTag tag = result.getOrCreateTag();
						if (tag.contains(XSurvive.MOD_NAME)) {
							CompoundTag modTag = tag.getCompound(XSurvive.MOD_NAME);
							tag.remove(XSurvive.MOD_NAME);
							modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
							tag.put(XSurvive.MOD_NAME, modTag);
						} else {
							CompoundTag modTag = new CompoundTag();
							modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
							tag.put(XSurvive.MOD_NAME, modTag);
						}
						result.setTag(tag);
						event.setOutput(result);
						event.setCost(5);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void anvilRepair(AnvilRepairEvent event) {
		if (event.getRight().getItem() instanceof EnchantedGoldenBookItem) {
			event.setBreakChance(0.0F);
		} else {
			event.setBreakChance(0.06F);
		}
	}
	
	@SubscribeEvent
	public static void anvilRepair(BreakSpeed event) {
		if (event.getState().is(Blocks.SPAWNER)) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void itemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof PotionItem || stack.getItem() instanceof TippedArrowItem) {
			Potion potion = PotionUtils.getPotion(stack);
			if (potion == XSPotions.DIG_SPEED.get() || potion == XSPotions.LONG_DIG_SPEED.get() || potion == XSPotions.STRONG_DIG_SPEED.get()) {
				List<MobEffectInstance> effects = potion.getEffects();
				if (effects.size() == 1) {
					List<Component> components = event.getToolTip();
					int index = -1;
					for (int i = 0; i < components.size(); i++) {
						String string = components.get(i).getString();
						if (string.contains(ATTACK_SPEED_TRANSLATION_KEY)) {
							index = i;
							break;
						}
					}
					if (index >= 0) {
						components.set(index, Component.literal(String.format("+%d", (effects.get(0).getAmplifier() + 1) * 20) + "% Dig Speed").withStyle(ChatFormatting.BLUE));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
		Player player = event.getEntity();
		if (!player.isOnGround() && player.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(XSEnchantments.VOID_WALKER.get()) > 0) {
			event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
		}
		float breakSpeed = event.getOriginalSpeed();
		if (breakSpeed != event.getNewSpeed() && event.getNewSpeed() > 0.0F) {
			breakSpeed = event.getNewSpeed();
		}
		event.setNewSpeed(breakSpeed * 0.75F);
	}
	
	@SubscribeEvent
	public static void playerChangedDimension(PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			PlayerProvider.getServer(player).broadcastChanges();
		}
	}
	
	@SubscribeEvent
	public static void playerClone(PlayerEvent.Clone event) {
		if (event.getOriginal() instanceof ServerPlayer original && event.getEntity() instanceof ServerPlayer player) {
			original.reviveCaps();
			IPlayer originalHandler = PlayerProvider.get(original);
			IPlayer handler = PlayerProvider.get(player);
			if (event.isWasDeath()) {
				handler.deserializePersistent(originalHandler.serializePersistent());
			} else {
				handler.deserializeDisk(originalHandler.serializeDisk());
			}
			original.invalidateCaps();
		}
	}
	
	@SubscribeEvent
	public static void playerRespawn(PlayerRespawnEvent event) {
		PlayerProvider.get(event.getEntity()).broadcastChanges();
	}
	
	@SubscribeEvent
	public static void playerSleepInBed(PlayerSleepInBedEvent event) {
		if (!event.getEntity().getAbilities().instabuild) {
			event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
		}
	}
	
	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent event) {
		Player player = event.player;
		if (event.phase == TickEvent.Phase.START) {
			player.getCapability(XSCapabilities.PLAYER, null).ifPresent(IPlayer::tick);
		}
		boolean hasVoidWalker = player.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(XSEnchantments.VOID_WALKER.get()) > 0;
		BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
		if (hasVoidWalker) {
			player.fallDistance = 0.0F;
		}
		if (player.isShiftKeyDown() && hasVoidWalker && player.getLevel().getBlockState(pos).isAir()) {
			player.setDeltaMovement(player.getDeltaMovement().x(), -0.25, player.getDeltaMovement().z());
		} else if (!player.isShiftKeyDown() && hasVoidWalker && player.getDeltaMovement().y() != 0.0) {
			player.setDeltaMovement(player.getDeltaMovement().x(), 0.0, player.getDeltaMovement().z());
		}
	}
	
	@SubscribeEvent
	public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
		Entry<EquipmentSlot, ItemStack> entry = XSEnchantmentHelper.getItemWithEnchantment(XSEnchantments.ASPECT_OF_THE_END.get(), event.getEntity());
		int aspectOfTheEnd = entry.getValue().getEnchantmentLevel(XSEnchantments.ASPECT_OF_THE_END.get());
		if (event.getEntity() instanceof ServerPlayer player) {
			if (aspectOfTheEnd > 0) {
				if (0 >= PlayerProvider.getServer(player).getEndAspectCooldown()) {
					Vec3 clipVector = EntityHelper.clipWithDistance(player, player.level, 6.0 * aspectOfTheEnd);
					player.teleportToWithTicket(clipVector.x, clipVector.y, clipVector.z);
					entry.getValue().hurtAndBreak(aspectOfTheEnd * 2, player, (p) -> p.broadcastBreakEvent(entry.getKey()));
					PlayerProvider.getServer(player).setEndAspectCooldown(20);
				}
			}
			if (event.getItemStack().getItem() instanceof Wearable) { // TODO: remove when added by minecraft
				EquipmentSlot slot = Mob.getEquipmentSlotForItem(event.getItemStack());
				ItemStack slotStack = player.getItemBySlot(slot).copy();
				if (!slotStack.isEmpty() && slot.isArmor()) {
					player.setItemSlot(slot, event.getItemStack());
					player.setItemInHand(event.getHand(), slotStack);
				}
				SoundEvent sound = event.getItemStack().getEquipSound();
				if (sound != null) {
					Level level = player.getLevel();
					player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, level.random.nextFloat() * 0.1F + 0.9F, level.random.nextLong()));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		BlockPos pos = event.getPos();
		Level level = event.getLevel();
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() == Blocks.ENDER_CHEST && event.getEntity() instanceof ServerPlayer player) {
			if (!player.isShiftKeyDown()) {
				event.setUseBlock(Event.Result.DENY);
				if (!player.getItemInHand(event.getHand()).isEmpty()) {
					event.setUseItem(Event.Result.DENY);
				}
				NetworkHooks.openScreen(player, new SimpleMenuProvider((id, inventory, playerIn) -> new EnderChestMenu(id, inventory), ENDER_CHEST), pos);
				player.awardStat(Stats.OPEN_ENDERCHEST);
				PiglinAi.angerNearbyPiglins(player, true);
				level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, player.getRandom().nextFloat() * 0.1F + 0.9F);
			} else {
				event.setCanceled(true);
			}
		}
	}
	
}
