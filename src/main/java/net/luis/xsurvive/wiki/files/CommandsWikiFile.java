package net.luis.xsurvive.wiki.files;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 *
 * @author Luis-st
 *
 */

public class CommandsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder wikiBuilder = new WikiFileBuilder("CommandsWiki");
		wikiBuilder.header1("Commands");
		addGammaCommand(wikiBuilder);
		return wikiBuilder;
	}
	
	private static void addGammaCommand(WikiFileBuilder wikiBuilder) {
		wikiBuilder.header2("Gamma command");
		wikiBuilder.header3("Usage");
		wikiBuilder.line("The gamma command is a client-side command that can be used to change the in-game gamma value to a predefined or custom value.");
		wikiBuilder.header3("Minimum");
		wikiBuilder.line("The minimum gamma value is equal to 0.0 (moody) in the minecraft settings.");
		wikiBuilder.header3("Default");
		wikiBuilder.line("The default gamma value is equal to 0.5 (default) in the minecraft settings.");
		wikiBuilder.header3("Maximum");
		wikiBuilder.line("The maximum gamma value is equal to 1.0 (bright) in the minecraft settings.");
		wikiBuilder.header3("Infinite");
		wikiBuilder.lines((builder) -> {
			builder.append("The infinite gamma value is equal to 20.0 (infinite) in the minecraft settings.").endLine();
			builder.append("Note: Infinite is not a vanilla setting, it is added by the mod.").endLine();
		});
		wikiBuilder.header3("Syntax");
		wikiBuilder.lines((builder) -> {
			builder.append("The command is syntaxed as follows:").endLine();
			builder.appendFormatted("/gamma <value|min|default|max|infinite>", WikiFormat.CODE).endLine();
		});
	}
}
