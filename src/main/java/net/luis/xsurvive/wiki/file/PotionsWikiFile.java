package net.luis.xsurvive.wiki.file;

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
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by using a snowball as input.");
		wikiBuilder.header3("Long Frost Potion");
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by extending the Frost Potion with Redstone Dust.");
		wikiBuilder.header3("Strong Frost Potion");
		wikiBuilder.line("Brewable in the Vanilla Brewing Stand by amplifying the Frost Potion with Glowstone Dust.");
		wikiBuilder.header2("Wither Potion");
		wikiBuilder.header3("Wither Potion");
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by using a wither rose as input.");
		wikiBuilder.header3("Long Wither Potion");
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by extending the Wither Potion with Redstone Dust.");
		wikiBuilder.header3("Strong Wither Potion");
		wikiBuilder.line("Brewable in the Vanilla Brewing Stand by amplifying the Wither Potion with Glowstone Dust.");
		wikiBuilder.header2("Haste Potion");
		wikiBuilder.header3("Haste Potion");
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by using a iron pickaxe as input.");
		wikiBuilder.header3("Long Haste Potion");
		wikiBuilder.line("Brewable in the vanilla Brewing Stand by extending the Haste Potion with Redstone Dust.");
		wikiBuilder.header3("Strong Haste Potion");
		wikiBuilder.line("Brewable in the Vanilla Brewing Stand by amplifying the Haste Potion with Glowstone Dust.");
		return wikiBuilder;
	}
	
}
