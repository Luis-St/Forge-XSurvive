package net.luis.xsurvive.mixin.entity.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Vex;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(targets = "net.minecraft.world.entity.monster.Evoker$EvokerSummonSpellGoal")
public abstract class EvokerSummonSpellGoalMixin {
	
/*	@Shadow
	private Evoker this$0;*/
	
	@Inject(method = "performSpellCasting", at = @At("HEAD"), cancellable = true)
	protected void performSpellCasting(CallbackInfo callback) {
		System.out.println("This is a test");
/*		if (this.this$0.level instanceof ServerLevel level) {
			int i = this.this$0.getRandom().nextInt(4);
			for (int j = 0; j < 3 + i; ++j) {
				BlockPos pos = this.this$0.blockPosition().offset(-2 + this.this$0.getRandom().nextInt(5), 1, -2 + this.this$0.getRandom().nextInt(5));
				Vex vex = Objects.requireNonNull(EntityType.VEX.create(level));
				vex.moveTo(pos, 0.0F, 0.0F);
				vex.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
				vex.setOwner(this.this$0);
				vex.setBoundOrigin(pos);
				level.addFreshEntityWithPassengers(vex);
			}
		}*/
		callback.cancel();
	}
	
}
