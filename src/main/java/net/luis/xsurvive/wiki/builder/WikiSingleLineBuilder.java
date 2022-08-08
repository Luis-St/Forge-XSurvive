package net.luis.xsurvive.wiki.builder;

import java.util.List;

import com.google.common.collect.Lists;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 * 
 * @author Luis-st
 *
 */

public class WikiSingleLineBuilder extends AbstractWikiLineBuilder<WikiSingleLineBuilder> {
	
	public WikiSingleLineBuilder(WikiFileBuilder fileBuilder) {
		super(fileBuilder);
	}
	
	@Override
	public void endLine() {
		super.endLine();
		this.end();
	}
	
	@Override
	protected List<String> modifyLines(List<String> lines) {
		List<String> newLines = Lists.newArrayList();
		newLines.add(lines.get(0));
		return newLines;
	}
	
}
