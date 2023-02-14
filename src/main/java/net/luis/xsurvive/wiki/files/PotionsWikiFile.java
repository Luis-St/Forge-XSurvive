package net.luis.xsurvive.wiki.files;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 *
 * @author Luis-st
 *
 */

public class PotionsWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder wikiBuilder = new WikiFileBuilder("PotionWiki");
		wikiBuilder.header1("Potions");
		wikiBuilder.header2("Frost Potion");
		wikiBuilder.header3("Frost Potion");
		wikiBuilder.header3("Long Frost Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding redstone dust to the frost potion.");
		wikiBuilder.header3("Strong Frost Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding glowstone dust to the frost potion with.");
		wikiBuilder.header2("Wither Potion");
		wikiBuilder.header3("Wither Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand using a wither rose as an input.");
		wikiBuilder.header3("Long Wither Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding redstone dust to the wither potion.");
		wikiBuilder.header3("Strong Wither Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding redstone dust to the wither potion.");
		wikiBuilder.header2("Haste Potion");
		wikiBuilder.header3("Haste Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand using an iron pickaxe as an input.");
		wikiBuilder.header3("Long Haste Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding redstone dust to the haste potion.");
		wikiBuilder.header3("Strong Haste Potion");
		wikiBuilder.line("Brewable in the vanilla brew stand by adding redstone dust to the haste potion.");
		return wikiBuilder;
	}
	
}
