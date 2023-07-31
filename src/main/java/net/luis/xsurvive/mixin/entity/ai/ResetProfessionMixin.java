package net.luis.xsurvive.mixin.entity.ai;

import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.ResetProfession;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ResetProfession.class)
public abstract class ResetProfessionMixin {
	
	@Inject(method = "create", at = @At("HEAD"), cancellable = true)
	private static void create(@NotNull CallbackInfoReturnable<BehaviorControl<Villager>> callback) {
		callback.setReturnValue(BehaviorBuilder.create((builder) -> builder.group(builder.absent(MemoryModuleType.JOB_SITE)).apply(builder, (memory) -> (level, villager, seed) -> {
			VillagerData villagerData = villager.getVillagerData();
			if (villagerData.getProfession() != VillagerProfession.NONE && villagerData.getProfession() != VillagerProfession.NITWIT && villager.getVillagerXp() == 0 && villagerData.getLevel() <= 1) {
				villager.setVillagerData(villager.getVillagerData().setProfession(VillagerProfession.NONE));
				VillagerProvider.get(villager).increaseResetCount();
				villager.refreshBrain(level);
				return true;
			} else {
				return false;
			}
		})));
	}
}
