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
		wikiBuilder.header2("Frost Effect");
		wikiBuilder.header3("Applied on Player");
		wikiBuilder.lines((builder) -> {
			builder.append("When this effect is applied to the player, it is slowed by 30% per potion level.").endLine();
			builder.append("If the player has the frost effect the vanilla frost overlay (from powder snow) is displayed.").endLine();
		});
		wikiBuilder.header3("Applied on Entity");
		wikiBuilder.lines((builder) -> {
			builder.append("If the frost effect is applied on a freeze affected Entity, the Entity is hurt by the freeze damage every 1.5 seconds.").endLine();
			builder.append("The damage amount is calculated as follows:").endLine();
			builder.appendFormatted("(amplifier + 1) * 2", WikiFormat.CODE).endLine();
			builder.append("Freeze affected Entities:").append("Magma Cube,").append("Ghast,").append("Blaze and").append("Strider").endLine();
		});
		return wikiBuilder;
	}
	
}
