package net.luis.xsurvive.mixin.entity;

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.Util;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {
	
	@Shadow(remap = false) private final Player unlimitedLastHurtByPlayer = null;
	@Shadow private EndDragonFight dragonFight;
	@Shadow public EndCrystal nearestCrystal;
	@Shadow public int dragonDeathTime;
	
	private EnderDragonMixin(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	protected void tickDeath(CallbackInfo callback) {
		if (this.dragonFight != null) {
			this.dragonFight.updateDragon((EnderDragon) (Object) this);
		}
		++this.dragonDeathTime;
		if (this.dragonDeathTime >= 180 && this.dragonDeathTime <= 200) {
			float f = (this.random.nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
			this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + f, this.getY() + 2.0D + f1, this.getZ() + f2, 0.0D, 0.0D, 0.0D);
		}
		boolean doMobDrop = this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
		int i = 12000;
		if (this.level() instanceof ServerLevel) {
			if (this.dragonDeathTime > 150 && this.dragonDeathTime % 5 == 0 && doMobDrop) {
				this.addDragonExperience(Lists.newArrayList(this.dragonFight.dragonEvent.getPlayers()), ForgeEventFactory.getExperienceDrop(this, this.unlimitedLastHurtByPlayer, Mth.floor(i * 0.08F)));
			}
			if (this.dragonDeathTime == 1 && !this.isSilent()) {
				this.level().globalLevelEvent(1028, this.blockPosition(), 0);
			}
		}
		this.move(MoverType.SELF, new Vec3(0.0, 0.1, 0.0));
		this.setYRot(this.getYRot() + 20.0F);
		this.yBodyRot = this.getYRot();
		if (this.dragonDeathTime == 200 && this.level() instanceof ServerLevel) {
			if (doMobDrop) {
				this.addDragonExperience(Lists.newArrayList(this.dragonFight.dragonEvent.getPlayers()), ForgeEventFactory.getExperienceDrop(this, this.unlimitedLastHurtByPlayer, Mth.floor(i * 0.2F)));
			}
			if (this.dragonFight != null) {
				this.dragonFight.setDragonKilled((EnderDragon) (Object) this);
			}
			this.remove(Entity.RemovalReason.KILLED);
			this.gameEvent(GameEvent.ENTITY_DIE);
		}
		callback.cancel();
	}
	
	private void addDragonExperience(@NotNull List<Player> players, int experience) {
		for (Player player : players) {
			int xp = experience;
			for (ItemStack stack : Util.shufflePreferred(EquipmentSlot.MAINHAND, XSEnchantmentHelper.getItemsWith(Enchantments.MENDING, player, ItemStack::isDamaged))) {
				int repair = (int) Math.min(experience * stack.getXpRepairRatio(), stack.getDamageValue());
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
	private void checkCrystals(CallbackInfo callback) {
		if (this.nearestCrystal != null) {
			if (this.nearestCrystal.isRemoved()) {
				this.nearestCrystal = null;
			} else if (this.getHealth() < this.getMaxHealth()) {
				this.heal(1.0F);
			}
		}
		EndCrystal nearestCrystal = null;
		double distance = Double.MAX_VALUE;
		for (EndCrystal endCrystal : this.level().getEntitiesOfClass(EndCrystal.class, this.getBoundingBox().inflate(32.0D))) {
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
