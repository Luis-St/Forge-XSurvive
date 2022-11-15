package net.luis.xsurvive.mixin.entity.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.ResetProfession;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ResetProfession.class)
public abstract class ResetProfessionMixin {
	
	@Inject(method = "start", at = @At("HEAD"), cancellable = true)
	protected void start(ServerLevel level, Villager villager, long gameTime, CallbackInfo callback) {
		villager.setVillagerData(villager.getVillagerData().setProfession(VillagerProfession.NONE));
		VillagerProvider.get(villager).increaseResetCount();
		villager.refreshBrain(level);
		callback.cancel();
	}
	
}
