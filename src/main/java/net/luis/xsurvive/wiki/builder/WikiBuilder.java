package net.luis.xsurvive.wiki.builder;

import net.luis.xsurvive.wiki.WikiFormat;

/**
 *
 * @author Luis-st
 *
 */

public interface WikiBuilder<T extends WikiBuilder<T>> {
	
	T append(String string);
	
	T append(Object object);
	
	T appendFormatted(String string, WikiFormat format);
	
	T appendFormatted(Object object, WikiFormat format);
	
	void end();
	
}
