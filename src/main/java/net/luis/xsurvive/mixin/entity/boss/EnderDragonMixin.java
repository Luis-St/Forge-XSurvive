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

package net.luis.xsurvive.mixin.entity.boss;

import net.luis.xsurvive.util.Util;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(EnderDragon.class)
@SuppressWarnings("DataFlowIssue")
public abstract class EnderDragonMixin extends Mob {
	
	//region Mixin
	@Shadow(remap = false) private Player unlimitedLastHurtByPlayer;
	@Shadow private EndDragonFight dragonFight;
	@Shadow
	public @Nullable EndCrystal nearestCrystal;
	@Shadow public int dragonDeathTime;
	
	private EnderDragonMixin(@NotNull EntityType<? extends Mob> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	protected void tickDeath(@NotNull CallbackInfo callback) {
		if (this.dragonFight != null) {
			this.dragonFight.updateDragon((EnderDragon) (Object) this);
		}
		++this.dragonDeathTime;
		if (this.dragonDeathTime >= 180 && this.dragonDeathTime <= 200) {
			float x = (this.random.nextFloat() - 0.5F) * 8.0F;
			float y = (this.random.nextFloat() - 0.5F) * 4.0F;
			float z = (this.random.nextFloat() - 0.5F) * 8.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + x, this.getY() + 2.0 + y, this.getZ() + z, 0.0, 0.0, 0.0);
		}
		if (this.level() instanceof ServerLevel serverLevel) {
			boolean doMobDrop = serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
			if (this.level() instanceof ServerLevel) {
				if (this.dragonDeathTime > 150 && this.dragonDeathTime % 5 == 0 && doMobDrop) {
					this.addDragonExperience(new ArrayList<>(this.dragonFight.dragonEvent.getPlayers()), ForgeEventFactory.getExperienceDrop(this, this.unlimitedLastHurtByPlayer, Mth.floor(12000 * 0.08)));
				}
				if (this.dragonDeathTime == 1 && !this.isSilent()) {
					this.level().globalLevelEvent(1028, this.blockPosition(), 0);
				}
			}
		}
		
		this.move(MoverType.SELF, new Vec3(0.0, 0.1, 0.0));
		this.setYRot(this.getYRot() + 20.0F);
		this.yBodyRot = this.getYRot();
		if (this.dragonDeathTime == 200 && this.level() instanceof ServerLevel serverLevel) {
			boolean doMobDrop = serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
			if (doMobDrop) {
				this.addDragonExperience(new ArrayList<>(this.dragonFight.dragonEvent.getPlayers()), ForgeEventFactory.getExperienceDrop(this, this.unlimitedLastHurtByPlayer, Mth.floor(12000 * 0.2)));
			}
			if (this.dragonFight != null) {
				this.dragonFight.setDragonKilled((EnderDragon) (Object) this);
			}
			this.remove(Entity.RemovalReason.KILLED);
			this.gameEvent(GameEvent.ENTITY_DIE);
		}
		callback.cancel();
	}
	
	private void addDragonExperience(@NotNull List<ServerPlayer> players, int experience) {
		for (ServerPlayer player : players) {
			int xp = experience;
			for (ItemStack stack : Util.shufflePreferred(EquipmentSlot.MAINHAND, XSEnchantmentHelper.getItemsWith(Enchantments.MENDING.getOrThrow(player), player, ItemStack::isDamaged))) {
				int value = EnchantmentHelper.modifyDurabilityToRepairFromXp(player.serverLevel(), stack, experience);
				int repair = (int) Math.min(experience * value, stack.getDamageValue());
				stack.setDamageValue(stack.getDamageValue() - repair);
				xp -= repair / 2;
				if (0 > xp) {
					break;
				}
			}
			if (xp > 0) {
				player.giveExperiencePoints(xp);
			}
		}
	}
	
	@Inject(method = "checkCrystals", at = @At("HEAD"), cancellable = true)
	private void checkCrystals(@NotNull CallbackInfo callback) {
		if (this.nearestCrystal != null) {
			if (this.nearestCrystal.isRemoved()) {
				this.nearestCrystal = null;
			} else if (this.getHealth() < this.getMaxHealth()) {
				this.heal(1.0F);
			}
		}
		EndCrystal nearestCrystal = null;
		double distance = Double.MAX_VALUE;
		for (EndCrystal endCrystal : this.level().getEntitiesOfClass(EndCrystal.class, this.getBoundingBox().inflate(64.0))) {
			double distanceTo = endCrystal.distanceToSqr(this);
			if (distanceTo < distance) {
				distance = distanceTo;
				nearestCrystal = endCrystal;
			}
		}
		this.nearestCrystal = nearestCrystal;
		callback.cancel();
	}
}
