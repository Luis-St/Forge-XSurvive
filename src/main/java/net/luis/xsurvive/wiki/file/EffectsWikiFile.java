package net.luis.xsurvive.wiki.file;

import net.luis.xsurvive.wiki.WikiFormat;

/**
 *
 * @author Luis-st
 *
 */

public class EffectsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder wikiBuilder = new WikiFileBuilder("EffectsWiki");
		wikiBuilder.header1("Effects");
		wikiBuilder.header2("Frost effect");
		wikiBuilder.header3("Applied to player");
		wikiBuilder.lines((builder) -> {
			builder.append("When applied to the player, this effect slows the player by 30% per potion level.").endLine();
			builder.append("If the player has the frost effect, the vanilla frost overlay (from powder snow) will be displayed.").endLine();
		});
		wikiBuilder.header3("Applied to entity");
		wikiBuilder.lines((builder) -> {
			builder.append("When the frost effect is applied to an entity that is affected by freezing, the entity will be damaged by the freezing damage every 1.5 seconds.").endLine();
			builder.append("The amount of damage is calculated as follows:").endLine();
			builder.appendFormatted("(amplifier + 1) * 2", WikiFormat.CODE).endLine();
			builder.append("Freeze affected entities:").append("Magma Cube,").append("Ghast,").append("Blaze and").append("Strider").endLine();
		});
		return wikiBuilder;
	}
	
}
