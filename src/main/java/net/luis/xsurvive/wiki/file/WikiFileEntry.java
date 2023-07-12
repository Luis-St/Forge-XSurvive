package net.luis.xsurvive.wiki.file;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface WikiFileEntry {
	
	void add(@NotNull WikiFileBuilder wikiBuilder);
}
