package net.luis.xsurvive.mixin.entity.villager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

	private VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}
	
	@Shadow
	public abstract void setVillagerData(VillagerData villagerData);
	
	@Shadow
	public abstract VillagerData getVillagerData();
	
	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	public void mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callback) {
		ItemStack stack = player.getItemInHand(hand);
		if (this.getVillagerData().getProfession() == VillagerProfession.NITWIT && stack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
			this.setVillagerData(this.getVillagerData().setProfession(VillagerProfession.NONE));
			callback.setReturnValue(InteractionResult.SUCCESS);
		}
	}
	
}
