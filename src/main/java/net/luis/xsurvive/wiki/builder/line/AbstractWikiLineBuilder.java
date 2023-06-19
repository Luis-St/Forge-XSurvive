package net.luis.xsurvive.wiki.builder.line;


import net.luis.xsurvive.wiki.builder.AbstractWikiBuilder;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;

public abstract class AbstractWikiLineBuilder<T extends AbstractWikiLineBuilder<T>> extends AbstractWikiBuilder<T> {
	
	protected AbstractWikiLineBuilder(WikiFileBuilder fileBuilder) {
		super(fileBuilder);
	}
}
