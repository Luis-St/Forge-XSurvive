package net.luis.xsurvive.world.effect;

import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrostMobEffect extends MobEffect {

	public FrostMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (EntityHelper.isAffectedByFrost(entity)) {
			entity.hurt(DamageSource.FREEZE, (amplifier + 1) * 2.0F);
		}
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return duration % 30 == 0;
	}

}
