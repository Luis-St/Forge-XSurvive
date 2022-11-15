package net.luis.xsurvive.mixin.entity.ai;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.AssignProfessionFromJobSite;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(AssignProfessionFromJobSite.class)
public abstract class AssignProfessionFromJobSiteMixin {
	
	@Inject(method = "start", at = @At(value = "HEAD"), cancellable = true)
	protected void start(ServerLevel level, Villager villager, long gameTime, CallbackInfo callback) {
		if (villager.getVillagerData().getProfession() == VillagerProfession.NONE && VillagerProvider.get(villager).getResetCount() > 7) {
			villager.setVillagerData(villager.getVillagerData().setProfession(VillagerProfession.NITWIT));
			villager.refreshBrain(level);
			callback.cancel();
		}
	}
	
	
}
