package net.luis.xsurvive.event.entity.living;

import java.util.UUID;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingEquipmentChangeEvent {
	
	public static final UUID GRAVITY_MODIFIER_UUID = UUID.fromString("715AF01B-DED3-45ED-8812-C8878C7F98CC");
	public static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("CD14D16D-82ED-474E-97E9-403DE2439D01");
	
	@SubscribeEvent
	public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof Player player && event.getSlot().getType() != EquipmentSlot.Type.HAND) {
			ItemStack toStack = event.getTo();
			ItemStack fromStack = event.getFrom();
			if (event.getSlot() == EquipmentSlot.FEET) {
				int voidWalkerTo = toStack.getEnchantmentLevel(XSEnchantments.VOID_WALKER.get());
				int voidWalkerFrom = fromStack.getEnchantmentLevel(XSEnchantments.VOID_WALKER.get());
				EntityHelper.updateAttributeModifier(player, ForgeMod.ENTITY_GRAVITY.get(), Operation.MULTIPLY_TOTAL, GRAVITY_MODIFIER_UUID, "EntityGravity", voidWalkerTo, voidWalkerFrom);
			}
			int growthTo = EntityHelper.getGrowthLevel(player, event.getSlot(), toStack);
			int growthFrom = EntityHelper.getGrowthLevel(player, event.getSlot(), fromStack);
			EntityHelper.updateAttributeModifier(player, Attributes.MAX_HEALTH, Operation.ADDITION, HEALTH_MODIFIER_UUID, "MaxHealth", growthTo, growthFrom);
			player.setHealth(Math.min(player.getHealth(), (float) player.getAttribute(Attributes.MAX_HEALTH).getValue()));
		}
	}
	
}