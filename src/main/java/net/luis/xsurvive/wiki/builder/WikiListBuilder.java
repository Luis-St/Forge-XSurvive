package net.luis.xsurvive.wiki.builder;

import com.google.common.collect.Lists;
import net.luis.xsurvive.wiki.WikiList;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class WikiListBuilder extends AbstractWikiBuilder<WikiListBuilder> {
	
	private final WikiList list;
	
	public WikiListBuilder(WikiFileBuilder fileBuilder, WikiList list) {
		super(fileBuilder);
		this.list = list;
	}
	
	private String getEntryPrefix(int lineNumber) {
		return switch (this.list) {
			case NUMBER -> lineNumber + ". ";
			case POINT -> "- ";
		};
	}
	
	@Override
	protected String getLinePrefix() {
		return this.getEntryPrefix(this.getLineCount());
	}
	
	private boolean isListEntryEmpty(String line, int lineNumber) {
		return line.replace(this.getEntryPrefix(lineNumber), "").trim().isEmpty();
	}
	
	@Override
	protected List<String> modifyLines(List<String> lines) {
		List<String> newLines = Lists.newArrayList();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.endsWith("\\")) {
				line = line.substring(0, line.length() - 1);
			}
			if (i == lines.size() - 1) {
				if (!this.isListEntryEmpty(line, i + 1)) {
					newLines.add(line);
				}
			} else {
				newLines.add(line);
			}
		}
		newLines.add("");
		return newLines;
	}
	
	@Override
	protected boolean shouldRemoveLineEnd() {
		return true;
	}
	
}
