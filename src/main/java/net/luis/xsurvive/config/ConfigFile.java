package net.luis.xsurvive.config;

import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public record ConfigFile(@NotNull String fileName, @NotNull ModConfig config) {}
