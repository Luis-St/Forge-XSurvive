package net.luis.xsurvive.event.entity.living.player;

import java.util.List;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.alchemy.XSurvivePotions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnItemTooltipEvent {
	
	@SubscribeEvent
	public static void itemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof PotionItem) {
			Potion potion = PotionUtils.getPotion(stack);
			if (potion == XSurvivePotions.DIG_SPEED.get() || potion == XSurvivePotions.LONG_DIG_SPEED.get() || potion == XSurvivePotions.STRONG_DIG_SPEED.get()) {
				List<MobEffectInstance> effects = potion.getEffects();
				if (effects.size() == 1) {
					List<Component> components = event.getToolTip();
					components.remove(4);
					components.add(4, Component.literal(String.format("+%d", (effects.get(0).getAmplifier() + 1) * 20) + "% Dig Speed").withStyle(ChatFormatting.BLUE));
				}
			}
		}
	}
	
}
