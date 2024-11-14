/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.core.components.XSDataComponents;
import net.luis.xsurvive.server.capability.ServerPlayerHandler;
import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.luis.xsurvive.world.entity.EntityHelper;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.inventory.EnderChestMenu;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.enchantment.*;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class PlayerEventHandler {
	
	private static final String ATTACK_SPEED_TRANSLATION_KEY = Component.translatable(Attributes.ATTACK_SPEED.value().getDescriptionId()).getString();
	private static final Component ENDER_CHEST = Component.translatable("container.enderchest");
	
	@SubscribeEvent
	public static void anvilUpdate(@NotNull AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if (!left.isEmpty()) {
			ItemStack result = left.copy();
			if (left.getItem() instanceof EnchantedGoldenBookItem) {
				event.setCanceled(true);
			} else if ((left.isEnchantable() || left.isEnchanted()) && right.getItem() instanceof EnchantedGoldenBookItem) {
				Holder<Enchantment> enchantment = EnchantedGoldenBookItem.getEnchantment(right);
				if (enchantment != null && GoldenEnchantmentHelper.isEnchantment(enchantment)) {
					EnchantedItem enchantedItem = GoldenEnchantmentHelper.isUpgradeEnchantment(enchantment) ? GoldenEnchantmentHelper.upgrade(left, right) : GoldenEnchantmentHelper.merge(left, right);
					event.setOutput(enchantedItem.stack());
					event.setCost(enchantedItem.cost());
				}
			} else if ((left.isEnchanted() || left.getItem().isFoil(left)) && right.has(XSDataComponents.GLINT_COLOR.get()) && !left.is(Items.ENCHANTED_BOOK)) {
				int color = right.getOrDefault(XSDataComponents.GLINT_COLOR.get(), -1);
				if (17 >= color && color >= 0) {
					result.set(XSDataComponents.GLINT_COLOR.get(), color);
					event.setOutput(result);
					event.setCost(5);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void anvilRepair(@NotNull AnvilRepairEvent event) {
		if (event.getRight().getItem() instanceof EnchantedGoldenBookItem) {
			event.setBreakChance(0.0F);
		} else {
			event.setBreakChance(0.06F);
		}
	}
	
	@SubscribeEvent
	public static void breakSpeed(@NotNull BreakSpeed event) {
		if (event.getState().is(Blocks.SPAWNER)) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void itemTooltip(@NotNull ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof PotionItem || stack.getItem() instanceof TippedArrowItem) {
			Optional<Holder<Potion>> optional = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion();
			if (optional.isPresent()) {
				Potion potion = optional.get().value();
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
							components.set(index, Component.literal(String.format("+%d", (effects.getFirst().getAmplifier() + 1) * 20) + "% Dig Speed").withStyle(ChatFormatting.BLUE));
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerBreakSpeed(PlayerEvent.@NotNull BreakSpeed event) {
		Player player = event.getEntity();
		if (!player.onGround() && XSEnchantmentHelper.getEnchantmentLevel(player, XSEnchantments.VOID_WALKER, player.getItemBySlot(EquipmentSlot.FEET)) > 0) {
			event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
		}
	}
	
	@SubscribeEvent
	public static void playerChangedDimension(@NotNull PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			PlayerProvider.getServer(player).broadcastChanges();
			LevelProvider.getServer(player.serverLevel()).broadcastChanges();
		}
	}
	
	@SubscribeEvent
	public static void playerClone(PlayerEvent.@NotNull Clone event) {
		if (event.getOriginal() instanceof ServerPlayer original && event.getEntity() instanceof ServerPlayer player) {
			original.reviveCaps();
			IPlayer originalHandler = PlayerProvider.get(original);
			IPlayer handler = PlayerProvider.get(player);
			HolderLookup.Provider lookup = original.registryAccess();
			if (event.isWasDeath()) {
				handler.deserializePersistent(lookup, originalHandler.serializePersistent(lookup));
			} else {
				handler.deserializeDisk(lookup, originalHandler.serializeDisk(lookup));
			}
			original.invalidateCaps();
		}
	}
	
	@SubscribeEvent
	public static void playerRespawn(@NotNull PlayerRespawnEvent event) {
		PlayerProvider.get(event.getEntity()).broadcastChanges();
	}
	
	@SubscribeEvent
	public static void playerSleepInBed(@NotNull PlayerSleepInBedEvent event) {
		if (!event.getEntity().getAbilities().instabuild) {
			event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
			event.getEntity().displayClientMessage(Component.translatable("message.xsurvive.sleeping"), true);
			if (event.getEntity().level() instanceof ServerLevel level && (level.isRaining() || level.isThundering())) {
				level.setWeatherParameters(6000, 0, false, false);
			}
			if (event.getEntity() instanceof ServerPlayer player) {
				ServerPlayerHandler handler = PlayerProvider.getServer(player);
				if (handler.canResetPhantomSpawn()) {
					player.getStats().setValue(player, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), 0);
					handler.setNextPhantomReset(player.level().getDifficulty().getId() + Mth.nextInt(player.getRandom(), 6, 10));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void playerTick(TickEvent.@NotNull PlayerTickEvent event) {
		Player player = event.player;
		if (event.phase == TickEvent.Phase.START) {
			player.getCapability(XSCapabilities.PLAYER, null).ifPresent(IPlayer::tick);
		}
		boolean hasVoidWalker = XSEnchantmentHelper.getEnchantmentLevel(player, XSEnchantments.VOID_WALKER, player.getItemBySlot(EquipmentSlot.FEET)) > 0;
		BlockPos pos = new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ());
		if (hasVoidWalker) {
			player.fallDistance = 0.0F;
		}
		if (player.isShiftKeyDown() && hasVoidWalker && player.level().getBlockState(pos).isAir()) {
			player.setDeltaMovement(player.getDeltaMovement().x(), -0.25, player.getDeltaMovement().z());
		} else if (!player.isShiftKeyDown() && hasVoidWalker && player.getDeltaMovement().y() != 0.0) {
			player.setDeltaMovement(player.getDeltaMovement().x(), 0.0, player.getDeltaMovement().z());
		}
	}
	
	@SubscribeEvent
	public static void rightClickItem(PlayerInteractEvent.@NotNull RightClickItem event) {
		LivingEntity entity = event.getEntity();
		Holder<Enchantment> enchantment = XSEnchantments.ASPECT_OF_THE_END.getOrThrow(entity);
		Entry<EquipmentSlot, ItemStack> entry = XSEnchantmentHelper.getItemWithEnchantment(enchantment, entity);
		int aspectOfTheEnd = EnchantmentHelper.getItemEnchantmentLevel(enchantment, entry.getValue());
		if (event.getEntity() instanceof ServerPlayer player && aspectOfTheEnd > 0 && entry.getValue().getItem() instanceof SwordItem) {
			if (0 >= PlayerProvider.getServer(player).getEndAspectCooldown()) {
				Vec3 clipVector = EntityHelper.clipWithDistance(player, player.level(), 6.0 * aspectOfTheEnd);
				player.teleportTo(clipVector.x, clipVector.y, clipVector.z);
				entry.getValue().hurtAndBreak(aspectOfTheEnd * 2, player, entry.getKey());
				PlayerProvider.getServer(player).setEndAspectCooldown(20);
			}
		}
	}
	
	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent.@NotNull RightClickBlock event) {
		BlockPos pos = event.getPos();
		Level level = event.getLevel();
		BlockState state = level.getBlockState(pos);
		if (event.getEntity() instanceof ServerPlayer player) {
			ServerLevel serverLevel = player.serverLevel();
			PlayerProvider.getServer(player).setContainerPos(pos);
			if (state.getBlock() == Blocks.ENDER_CHEST) {
				if (player.isShiftKeyDown()) {
					event.setCanceled(true);
				} else {
					event.setUseBlock(Event.Result.DENY);
					if (!player.getItemInHand(event.getHand()).isEmpty()) {
						event.setUseItem(Event.Result.DENY);
					}
					player.openMenu(new SimpleMenuProvider((id, inventory, playerIn) -> new EnderChestMenu(id, inventory), ENDER_CHEST));
					player.awardStat(Stats.OPEN_ENDERCHEST);
					PiglinAi.angerNearbyPiglins(serverLevel, player, true);
					level.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, player.getRandom().nextFloat() * 0.1F + 0.9F);
				}
			} else if (state.getBlock() == Blocks.BELL) {
				Raid raid = serverLevel.getRaidAt(pos);
				if (raid != null) {
					raid.getAllRaiders().forEach(raider -> raider.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 1)));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void arrowLoose(@NotNull ArrowLooseEvent event) {
		if (event.getEntity().isUnderWater()) {
			event.setCharge((int) (event.getCharge() * 0.35));
		}
	}
	
	@SubscribeEvent
	public static void open(PlayerContainerEvent.@NotNull Open event) {
		Player player = event.getEntity();
		if (!(event.getContainer() instanceof InventoryMenu) && player instanceof ServerPlayer) {
			PlayerProvider.getServer(player).confirmContainerPos();
		}
	}
	
	@SubscribeEvent
	public static void close(PlayerContainerEvent.@NotNull Close event) {
		Player player = event.getEntity();
		if (!(event.getContainer() instanceof InventoryMenu) && player instanceof ServerPlayer) {
			PlayerProvider.getSafe(player).ifPresent(handler -> {
				if (handler instanceof ServerPlayerHandler serverHandler) {
					serverHandler.resetContainerPos();
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void startTracking(PlayerEvent.@NotNull StartTracking event) {
		if (event.getEntity() instanceof ServerPlayer) {
			EntityProvider.getServer(event.getTarget()).broadcastChanges();
		}
	}
	
	@SubscribeEvent
	public static void playerSpawnPhantoms(@NotNull PlayerSpawnPhantomsEvent event) {
		Player player = event.getEntity();
		DifficultyInstance instance = player.level().getCurrentDifficultyAt(player.blockPosition());
		event.setPhantomsToSpawn(2 + player.getRandom().nextInt(instance.getDifficulty().getId() + 2));
	}
}
