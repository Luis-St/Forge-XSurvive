package net.luis.xsurvive.util;

import net.luis.xsurvive.XSurvive;

import java.util.Map;

/**
 *
 * @author Luis-St
 *
 */

public final class SimpleEntry<K, V> implements Map.Entry<K, V> {
	
	private final K key;
	private V value;
	private boolean muted;
	
	public SimpleEntry(K key, V value) {
		this(key, value, false);
	}
	
	public SimpleEntry(K key, V value, boolean muted) {
		this.key = key;
		this.value = value;
		this.muted = muted;
	}
	
	@Override
	public K getKey() {
		return this.key;
	}
	
	@Override
	public V getValue() {
		return this.value;
	}
	
	@Override
	public V setValue(V value) {
		V oldValue = this.value;
		if (!this.muted) {
			this.value = value;
		} else {
			XSurvive.LOGGER.info("Unable to set value of entry to {}, since the entry is muted", value);
		}
		return oldValue;
	}
	
	public boolean isMuted() {
		return this.muted;
	}
	
	public void setMuted() {
		this.muted = true;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof SimpleEntry<?, ?> entry) {
			if (!this.key.equals(entry.getKey())) {
				return false;
			} else if (!this.value.equals(entry.getValue())) {
				return false;
			} else {
				return this.muted == entry.isMuted();
			}
		}
		return false;
	}
}
