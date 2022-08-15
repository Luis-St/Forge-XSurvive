package net.luis.xsurvive.event.level;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.WoodHarvester;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnBlockBreakEvent {
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockPos pos = new BlockPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
		int xp = event.getExpToDrop();
		int experience = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPERIENCE.get(), player);
		int multiDrop = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.MULTI_DROP.get(), player);
		int fortune = XSEnchantmentHelper.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player);
		int blasting = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.BLASTING.get(), player);
		int harvesting = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.HARVESTING.get(), player);
		if (xp > 0 && experience > 0) {
			event.setExpToDrop((xp * ((experience + 1) * ((experience * 2) + fortune))) * (multiDrop + 1));
		}
		if (blasting > 0) {
			player.level.explode(player, pos.getX(), pos.getY(), pos.getZ(), 2.0F * (blasting + 1), Explosion.BlockInteraction.BREAK);
		}
		if (harvesting > 0) {
			if (event.getLevel().getBlockState(pos).is(BlockTags.LOGS)) {
				WoodHarvester harvester = new WoodHarvester((Level) event.getLevel(), pos, event.getLevel().getBlockState(pos), harvesting, player);
				harvester.harvest();
			}
		}
	}
	
}
