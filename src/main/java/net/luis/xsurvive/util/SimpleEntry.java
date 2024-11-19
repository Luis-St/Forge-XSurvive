/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.util;

import net.luis.xsurvive.XSurvive;
import org.jetbrains.annotations.Nullable;

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
	
	public SimpleEntry(@Nullable K key, @Nullable V value) {
		this(key, value, false);
	}
	
	public SimpleEntry(@Nullable K key, @Nullable V value, boolean muted) {
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
		if (this.muted) {
			XSurvive.LOGGER.info("Unable to set value of entry to {}, since the entry is muted", value);
		} else {
			this.value = value;
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
