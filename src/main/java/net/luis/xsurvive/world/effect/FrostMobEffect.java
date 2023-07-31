package net.luis.xsurvive.world.effect;

import net.luis.xsurvive.world.entity.EntityHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class FrostMobEffect extends MobEffect {
	
	public FrostMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
		if (EntityHelper.isAffectedByFrost(entity)) {
			entity.hurt(entity.damageSources().freeze(), (amplifier + 1) * 2.0F);
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return duration % 30 == 0;
	}
}
