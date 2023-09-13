package net.luis.xsurvive.config;

import org.jetbrains.annotations.NotNull;

public enum ConfigType {
	
	CLIENT("client"),
	OPTIONS("options"),
	RENDERING("rendering"),
	SERVER("server"),
	ENTITY("entity"),
	ANIMAL("animal"),
	MONSTER("monster"),
	PLAYER("player"),
	ITEM("item"),
	BLOCK("block"),
	FLUID("fluid"),
	DIMENSION("dimension"),
	BIOME("biome"),
	STRUCTURE("structure"),
	FEATURE("feature"),
	LOOT("loot"),
	TRADE("trade"),
	ENCHANTMENT("enchantment");
	
	private final String name;
	
	private ConfigType(@NotNull String name) {
		this.name = name;
	}
	
	public @NotNull String getName() {
		return this.name;
	}
}
