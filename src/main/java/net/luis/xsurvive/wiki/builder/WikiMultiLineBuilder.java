package net.luis.xsurvive.wiki.builder;

import java.util.ArrayList;
import java.util.List;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;

/**
 * 
 * @author Luis-st
 *
 */

public class WikiMultiLineBuilder extends AbstractWikiLineBuilder<WikiMultiLineBuilder> {

	public WikiMultiLineBuilder(WikiFileBuilder fileBuilder) {
		super(fileBuilder);
	}
	
	public void emptyLine() {
		this.append("").endLine();
	}
	
	@Override
	protected List<String> modifyLines(List<String> lines) {
		List<String> newLines = new ArrayList<>();
		for (int i = 0; i < lines.size() - 1; i++) {
			newLines.add(lines.get(i));
		}
		return newLines;
	}
	
}
