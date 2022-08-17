package net.luis.xsurvive.wiki.file;

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
		builder.header2("Dragon Fight");
		addDragonFight(builder);
	}
	
	private static void addDragonFight(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("Removed the End Stone from The End Biome.").endLine();
			builder.append("The Crystal pillars, the Spawn platform and the exit portal are still at the same positions.").endLine();
			builder.append("Hint: You should take some (more) Blocks into the end.").endLine();
		});
	}
	
}
