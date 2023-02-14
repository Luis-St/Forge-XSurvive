package net.luis.xsurvive.wiki.files;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 *
 * @author Luis-st
 *
 */

public class DimensionsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder builder = new WikiFileBuilder("DimensionsWiki");
		builder.header1("Overworld");
		addOverworld(builder);
		builder.header1("Nether");
		addNether(builder);
		builder.header1("End");
		addEnd(builder);
		return builder;
	}
	
	private static void addOverworld(WikiFileBuilder builder) {
		
	}
	
	private static void addNether(WikiFileBuilder builder) {
		
	}
	
	private static void addEnd(WikiFileBuilder builder) {
		builder.header2("Dragon fight");
		addDragonFight(builder);
	}
	
	private static void addDragonFight(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("The End Stone has been removed from the end biome.").endLine();
			builder.append("The crystal pillars, the spawn platform and the exit portal are still in the same positions.").endLine();
			builder.append("Hint: You should take some (more) blocks to the end.").endLine();
		});
	}
	
}
