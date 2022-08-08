package net.luis.xsurvive.event.level;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.EnchantmentHandler;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
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
		int experience = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.EXPERIENCE.get(), player);
		int multiDrop = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.MULTI_DROP.get(), player);
		int fortune = EnchantmentHandler.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player);
		int blasting = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.BLASTING.get(), player);
		int harvesting = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.HARVESTING.get(), player);
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
