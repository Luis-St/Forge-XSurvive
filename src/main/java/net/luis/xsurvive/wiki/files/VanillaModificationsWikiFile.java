package net.luis.xsurvive.wiki.files;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.minecraft.locale.Language;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Luis-st
 *
 */

public class VanillaModificationsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder wikiBuilder = new WikiFileBuilder("VanillaModificationsWiki");
		wikiBuilder.header1("Vanilla modifications");
		wikiBuilder.header2("Modifications");
		addModifications(wikiBuilder);
		wikiBuilder.header2("Additions");
		addAdditions(wikiBuilder);
		/*wikiBuilder.header2("Activations");*/
		wikiBuilder.header2("Deactivations");
		addDeactivations(wikiBuilder);
		return wikiBuilder;
	}
	
	private static void addModifications(WikiFileBuilder wikiBuilder) {
		wikiBuilder.header3("Shulker box");
		wikiBuilder.lines((builder) -> {
			builder.append("A shulker box can be placed inside another shulker box.").endLine();
			builder.append("The shulker box tooltip displays the full contents of the stored inventory.").endLine();
		});
		wikiBuilder.header3("Anvil xp cost reduction");
		wikiBuilder.lines((builder) -> {
			builder.append("Adding too many enchantments to an item will increase the xp cost in the anvil for the next enchantment.").endLine();
			builder.append("To reduce this cost, place the item alone in the anvil and you will be able to halve the repair cost for a cost of 5 xp.").endLine();
		});
		wikiBuilder.header3("Anvil break chance reduction");
		wikiBuilder.lines((builder) -> {
			builder.append("Reduced the default anvil break chance from 12% to 6%.").endLine();
			builder.append("If a golden book is used in the anvil, the break chance is 0%.").endLine();
		});
		wikiBuilder.header3("Monster spawner");
		wikiBuilder.line("Made the monster spawner unbreakable");
		wikiBuilder.header3("Block break speed");
		wikiBuilder.line("The block break speed has been reduced by 75%.");
		wikiBuilder.header3("Sleeping");
		wikiBuilder.lines((builder) -> {
			builder.append("Sleeping in bed to skip the night has been completely disabled.").endLine();
			builder.append("The only way to skip the night is to use the /time command.").endLine();
			builder.append("The bed has another use, it can be used (right-clicked) to clear the weather when it rains or thunders.").endLine();
			builder.append("It is possible to reset the phantom spawn after a certain amount of time by right-clicking on the bed.").endLine();
			builder.append("The time is based on the difficulty:").endLine();
		});
		wikiBuilder.pointList((builder) -> {
			builder.append("Peaceful: 6 - 10 minecraft days").endLine();
			builder.append("Easy: 7 - 11 minecraft days").endLine();
			builder.append("Normal: 8 - 12 minecraft days").endLine();
			builder.append("Hard: 9 - 13 minecraft days").endLine();
		});
		wikiBuilder.header3("Armor items");
		wikiBuilder.lines((builder) -> {
			builder.append("Right-clicking an armor item while wearing an armor item in the corresponding slot will swap the items.").endLine();
			builder.append("Note that this feature will be removed in 1.20, as it will be added by minecraft.").endLine();
		});
		wikiBuilder.header3("Ender chest");
		wikiBuilder.line("The number of slots in the ender chest has been increased from 27 to 54.");
		wikiBuilder.header3("Entities");
		wikiBuilder.line("Increased the attributes of all entities");
		wikiBuilder.formattedLine("Max health", WikiFormat.BOLD);
		wikiBuilder.line("Sets the maximum health of the following entities to 1000:");
		wikiBuilder.pointList((builder) -> {
			builder.append("Ender dragon").endLine();
			builder.append("Wither").endLine();
			builder.append("Elder guardian").endLine();
			builder.append("Warden").endLine();
		});
		wikiBuilder.line("The maximum health of all hostile entities is multiplied by 5.0 and the maximum health of all friendly entities is multiplied by 2.0.");
		wikiBuilder.line("Golems are an exception. Their maximum health is also multiplied by 5.0.");
		wikiBuilder.emptyLine();
		wikiBuilder.formattedLine("Follow range", WikiFormat.BOLD);
		wikiBuilder.line("The follow range of all hostile entities is multiplied by 2.0.");
		wikiBuilder.emptyLine();
		wikiBuilder.formattedLine("Attack damage", WikiFormat.BOLD);
		wikiBuilder.line("The attack damage of all entities is multiplied by 2.0.");
		wikiBuilder.emptyLine();
		wikiBuilder.formattedLine("Armor", WikiFormat.BOLD);
		wikiBuilder.line("The armor strength of all entities is multiplied by 2.0.");
		wikiBuilder.emptyLine();
		wikiBuilder.header3("Blaze");
		wikiBuilder.line("The number of fireballs that can be fired from a blaze has been increased from 1 to 3.");
		wikiBuilder.header3("Creeper");
		wikiBuilder.line("Charged creeper will be able to spawn naturally in a world.");
		wikiBuilder.header3("Spider");
		wikiBuilder.line("Spiders will be hostile to players during the day as well.");
		wikiBuilder.header3("Vex");
		wikiBuilder.line("The iron sword of the vexes will be enchanted based on the local chunk difficulty.");
		wikiBuilder.header3("Pillager");
		wikiBuilder.line("The crossbow of the pillagers will be enchanted based on the local chunk difficulty.");
		wikiBuilder.header3("Vindicator");
		wikiBuilder.line("The iron axe of the vindicators will be enchanted based on the local chunk difficulty.");
		wikiBuilder.header3("Game settings");
		wikiBuilder.line("The flight allowed game option is automatically set to true, as a false value would cause problems with the void walker enchantment.");
		wikiBuilder.header3("Lava");
		wikiBuilder.line("Lava will be able to create source blocks like water in the nether.");
		wikiBuilder.header3("World spawn");
		wikiBuilder.line("The world spawn point will be placed near a village.");
		wikiBuilder.header3("Bedrock");
		wikiBuilder.line("The bedrock that is part of the top layer in the nether can be removed by igniting tnt on the block.");
		wikiBuilder.header3("Conduit");
		wikiBuilder.lines((builder) -> {
			builder.append("The conduit will attack hostile entities if you use at least 30 valid blocks instead of 42.").endLine();
			builder.append("Increased damage range to 24 blocks in each direction, and it is based on number of blocks:").endLine();
		});
		wikiBuilder.table((builder) -> {
			builder.append("Block count").append("Damage").endLine();
			builder.append("30 - 41").append("4").endLine();
			builder.append(">41").append("8").endLine();
		});
		wikiBuilder.lines((builder) -> {
			builder.append("Doubled the range in which players get the conduit effect.").endLine();
			builder.append("Using the conduit with the maximum block count of 42 will also double the duration of the effect.").endLine();
		});
		wikiBuilder.header3("Enchantment compatibility");
		wikiBuilder.line("The following enchantments are compatible with each other:");
		wikiBuilder.pointList((builder) -> {
			builder.append("Infinity").append("&").append("Mending").endLine();
			builder.append("Piercing").append("&").append("Multishot").endLine();
			builder.append("Loyalty").append("&").append("Channeling").endLine();
		});
		wikiBuilder.header3("Punch enchantment");
		wikiBuilder.line("Increased the maximum level of punch enchantment from 2 to 3.");
		wikiBuilder.header3("Protection enchantments");
		wikiBuilder.line("Protection enchantments can be applied to the elytra");
		wikiBuilder.header3("Quick charge enchantment");
		wikiBuilder.line("Increased the maximum level of quick charge enchantment from 3 to 4.");
		wikiBuilder.header3("Thorns enchantment");
		wikiBuilder.lines((builder) -> {
			builder.append("Increased the maximum level of thorns enchantment from 3 to 4.").endLine();
			builder.append("The damage of the thorns enchantment has been increased. The damage is now calculated based on all armor items with the thorns enchantment.").endLine();
		});
		wikiBuilder.header3("Impaling enchantment");
		wikiBuilder.line("The impaling enchantment can be applied to swords and axes to inflict 2.5 times the amount of damage to water entities.");
		wikiBuilder.pointList((builder) -> {
			builder.append("Cod").endLine();
			builder.append("Salmon").endLine();
			builder.append("Tropical fish").endLine();
			builder.append("Pufferfish").endLine();
			builder.append("Squid").endLine();
			builder.append("Glow squid").endLine();
			builder.append("Guardian").endLine();
			builder.append("Elder guardian").endLine();
			builder.append("Drowned").endLine();
			builder.append("Turtle").endLine();
			builder.append("Dolphin").endLine();
		});
		wikiBuilder.header3("Evoker");
		wikiBuilder.line("The evoker can spawn 3 to 8 vexes.");
		wikiBuilder.header3("Villager trades");
		wikiBuilder.lines((builder) -> {
			builder.append("The number of maximum use has been quintupled if the number is not 1.").endLine();
			builder.append("The minimum number of cost items is limited to 85% of the base cost, and the maximum number is limited to 150%.").endLine();
		});
		wikiBuilder.header3("Villager");
		wikiBuilder.lines((builder) -> {
			builder.append("Villagers with the profession nitwit can be converted back by giving them an enchanted golden apple.").endLine();
			builder.append("Quintupled the amount of XP required for a villager to reach the next level.").endLine();
		});
		wikiBuilder.table((builder) -> {
			Language language = Language.getInstance();
			builder.append("Level").append("Old xp").append("New xp").endLine();
			builder.append(StringUtils.capitalize(language.getOrDefault("merchant.level." + 2))).append("10").append("50").endLine();
			builder.append(StringUtils.capitalize(language.getOrDefault("merchant.level." + 3))).append("70").append("350").endLine();
			builder.append(StringUtils.capitalize(language.getOrDefault("merchant.level." + 4))).append("150").append("750").endLine();
			builder.append(StringUtils.capitalize(language.getOrDefault("merchant.level." + 5))).append("250").append("1.250").endLine();
		});
		wikiBuilder.header3("End crystal");
		wikiBuilder.line("End crystals have been made invulnerable to indirect damage and any type of projectile.");
		wikiBuilder.header3("Ender dragon");
		wikiBuilder.lines((builder) -> {
			builder.append("The ender dragon's xp is added directly to the player, but the logic of the mending enchantment is taken into account.").endLine();
			builder.append("In each battle, the ender dragon will drop the same amount of xp.").endLine();
			builder.append("Increased the speed at which the ender dragon regenerates health.").endLine();
		});
		wikiBuilder.header3("Phantom");
		wikiBuilder.line("Phantoms will not burn in the sun.");
		wikiBuilder.header3("Wither");
		wikiBuilder.lines((builder) -> {
			builder.append("The wither can only take damage from an arrow fired from a piercing 5 crossbow.").endLine();
			builder.append("Reduced the wither's invulnerability spawn time to 4 seconds.").endLine();
		});
		wikiBuilder.header3("Wither skeleton");
		wikiBuilder.lines((builder) -> {
			builder.append("Wither skeletons have a rare percentage to spawn with a bow.").endLine();
			builder.append("If wither skeletons are equipped with a bow, the bow will shoot arrows with the wither effect.").endLine();
			builder.append("The wither skeleton's main weapon is enchanted based on the local chunk difficulty.").endLine();
		});
		wikiBuilder.header3("Wither skull projectile");
		wikiBuilder.lines((builder) -> {
			builder.append("When a target is hit by a wither skull, the shoot wither heals 10 hearts.").endLine();
			builder.append("The explosion that occurs when the wither skull hits a block is increased from 1 to 4.").endLine();
			builder.append("The duration and the amplifier of the wither effect caused by the wither skull is now based on the difficulty:").endLine();
		});
		wikiBuilder.table((builder) -> {
			builder.append("Difficulty").append("Duration").append("Amplifier").endLine();
			builder.append("Easy").append("10 seconds").append("0").endLine();
			builder.append("Normal").append("25 seconds").append("1").endLine();
			builder.append("Hard").append("40 seconds").append("2").endLine();
		});
		wikiBuilder.header3("Zombie villager");
		wikiBuilder.line("The convention from a zombie villager to a villager requires an enchanted golden apple instead of a normal golden apple.");
		wikiBuilder.header3("Shulker box");
		wikiBuilder.lines((builder) -> {
			builder.append("Shulker boxes can be placed inside another shulker box.").endLine();
			builder.append("The full inventory is displayed in the shulker box tooltip.").endLine();
		});
		wikiBuilder.header3("Ender eye");
		wikiBuilder.line("The eye of ender has a 40% chance of spawning an cursed eye of ender that does not point to the next stronghold.");
		wikiBuilder.header2("Dragon fight");
		wikiBuilder.lines((builder) -> {
			builder.append("The End Stone has been removed from the end biome.").endLine();
			builder.append("The crystal pillars, the spawn platform and the exit portal are still in the same positions.").endLine();
			builder.append("Hint: You should take some (more) blocks to the end.").endLine();
		});
		wikiBuilder.header3("Phantom spawn");
		wikiBuilder.line("Increased the number of phantom spawn in each difficulty.");
		wikiBuilder.table((builder) -> {
			builder.append("Difficulty").append("Old spawns").append("New spawns").endLine();
			builder.append("Easy").append("1 - 3").append("2 - 5").endLine();
			builder.append("Normal").append("1 - 4").append("2 - 6").endLine();
			builder.append("Hard").append("1 - 5").append("2 - 7").endLine();
		});
		wikiBuilder.header3("Raid");
		wikiBuilder.line("Increased spawn rate of all raiders.");
		wikiBuilder.header3("Wither spawn");
		wikiBuilder.line("The wither cannot spawn under the following conditions:");
		wikiBuilder.pointList((builder) -> {
			builder.append("In the end dimension").endLine();
			builder.append("In the nether below 5 and between 121 and 128").endLine();
			builder.append("In the overworld below -59").endLine();
			builder.append("In a cube which is smaller than 5x4x5 (x, y, z)").endLine();
		});
	}
	
	private static void addAdditions(WikiFileBuilder wikiBuilder) {
		wikiBuilder.header3("Zombies");
		wikiBuilder.lines((builder) -> {
			builder.append("Spawn equipment for zombies is based on the local chunk difficulty.").endLine();
			builder.append("The equipment is applied to the following types of zombies:").append("Zombie,").append("Drowned,").append("Husk").endLine();
		});
		wikiBuilder.header3("Skeletons");
		wikiBuilder.lines((builder) -> {
			builder.append("Spawn equipment for skeletons is based on the local chunk difficulty.").endLine();
			builder.append("The equipment is applied to the following types of skeletons:").append("Skeleton,").append("Stray").endLine();
		});
		wikiBuilder.header3("Zombiefied piglins");
		wikiBuilder.line("Spawn equipment for zombiefied piglins is based on the local chunk difficulty.");
		wikiBuilder.header3("Raid command");
		wikiBuilder.line("Vanilla's raid command has been activated.");
		wikiBuilder.header3("Fire color");
		wikiBuilder.line("The color of the fire will be the color of the fire that has been in contact.");
		wikiBuilder.header3("Sugar cane");
		wikiBuilder.line("Sugar cane can be bonemealed up to a height of 5 blocks.");
		wikiBuilder.header3("Elder guardian");
		wikiBuilder.line("The Elder Guardian will destroy any blocks that come in contact with him.");
		wikiBuilder.header3("Ender man");
		wikiBuilder.line("Ender men will be able to freeze water to reach players trying to save themselves in the water.");
		
	}
	
	private static void addDeactivations(WikiFileBuilder wikiBuilder) {
		wikiBuilder.header3("Chat reporting system");
		wikiBuilder.lines((builder) -> {
			builder.append("We have decided to disable the chat reporting system because it is inappropriate in a game like Minecraft where we have a friendly community.").endLine();
			builder.append("In our opinion, the chat reporting system needs to be overhauled, if you want to report players in the game, you need to remove our mod.").endLine();
		});
		wikiBuilder.header3("Blaze");
		wikiBuilder.line("The blocking of fireballs from the blaze has been disabled.");
		wikiBuilder.header3("Ender man");
		wikiBuilder.line("The ability to trap ender mans in boats and minecarts has been disabled.");
		wikiBuilder.header3("Raiders");
		wikiBuilder.line("Raiders such as Pillagers, Vindicators, Evokers, Ravagers, and Witches cannot be trapped in boats and minecarts during a raid.");
	}
	
}
