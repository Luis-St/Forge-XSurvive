package net.luis.xsurvive.wiki.file;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface WikiFileEntry {
	
	void add(WikiFileBuilder wikiBuilder);
	
}
