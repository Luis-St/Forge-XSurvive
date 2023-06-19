package net.luis.xsurvive.wiki.file;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface WikiFileEntry {
	
	void add(WikiFileBuilder wikiBuilder);
}
