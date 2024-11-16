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

package net.luis.xsurvive.data;

import com.google.common.collect.Lists;
import net.luis.xores.tags.XOItemTags;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.additions.XSAdditionsRecipeProvider;
import net.luis.xsurvive.data.provider.base.client.*;
import net.luis.xsurvive.data.provider.base.server.*;
import net.luis.xsurvive.data.provider.base.server.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static net.luis.xsurvive.data.provider.XSBuiltinProvider.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings({ "resource", "ResultOfMethodCallIgnored" })
@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class GatherDataEventHandler {
	
	private static final Path ROOT = new File(Paths.get(".").toAbsolutePath().toString()).getParentFile().getParentFile().toPath();
	
	@SubscribeEvent
	public static void gatherData(@NotNull GatherDataEvent event) throws IOException {
		DataGenerator generator = event.getGenerator();
		String type = System.getProperty("xsurvive.data.include");
		if (event.includeDev()) {
			if ("mod".equalsIgnoreCase(type)) {
				var provider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), createProvider(), Set.of(XSurvive.MOD_ID)));
				CompletableFuture<HolderLookup.Provider> lookupProvider = provider.getFullRegistries();
				generator.addProvider(event.includeClient(), new XSBlockStateProvider(generator, event.getExistingFileHelper()));
				generator.addProvider(event.includeClient(), new XSItemModelProvider(generator, event.getExistingFileHelper()));
				generator.addProvider(event.includeClient(), new XSLanguageProvider(generator));
				generator.addProvider(event.includeServer(), new XSLootTableProvider(generator, lookupProvider));
				generator.addProvider(event.includeServer(), new XSRecipeProvider.Runner(generator, lookupProvider));
				XSBlockTagsProvider blockTagsProvider = new XSBlockTagsProvider(generator, lookupProvider, event.getExistingFileHelper());
				generator.addProvider(event.includeServer(), blockTagsProvider);
				generator.addProvider(event.includeServer(), new XSItemTagsProvider(generator, lookupProvider, CompletableFuture.completedFuture(createXOresItemTagLookup()), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSPoiTypeTagsProvider(generator, lookupProvider, event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSBiomeTagsProvider(generator, lookupProvider, event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSDamageTypeTagsProvider(generator, lookupProvider, event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSEnchantmentTagsProvider(generator, lookupProvider, event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSEntityTypeTagsProvider(generator, lookupProvider, event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSGlobalLootModifierProvider(generator, lookupProvider));
				
			}
			if ("additions".equalsIgnoreCase(type)) {
				setupDatapackGeneration("xsurvive_additions");
				var provider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), createAdditionsProvider(), Set.of(XSurvive.MOD_ID, "minecraft")));
				CompletableFuture<HolderLookup.Provider> lookupProvider = provider.getFullRegistries();
				generator.addProvider(event.includeServer(), new XSAdditionsRecipeProvider.Runner(generator, lookupProvider));
			}
		}
	}
	
	private static TagsProvider.@NotNull TagLookup<Item> createXOresItemTagLookup() {
		List<TagKey<Item>> tags = Lists.newArrayList(XOItemTags.ELYTRA_CHESTPLATES);
		return tag -> {
			if (tags.contains(tag)) {
				return Optional.of(TagBuilder.create());
			}
			return Optional.empty();
		};
	}
	
	private static void setupDatapackGeneration(@NotNull String packName) throws IOException {
		Path temp = Files.createTempDirectory(XSurvive.MOD_ID);
		move(ROOT.resolve("src/generated/resources"), temp);
		addCopyHook(packName, temp);
	}
	
	private static void move(@NotNull Path from, @NotNull Path to) throws IOException {
		Files.walk(from).filter(path -> path.toFile().isFile()).forEach(path -> {
			try {
				Files.createDirectories(to.resolve(from.relativize(path).getParent()));
				Files.move(path, to.resolve(from.relativize(path)), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	private static void addCopyHook(@NotNull String packName, @NotNull Path temp) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Path output = ROOT.resolve("src/generated/resources");
			Path pack = ROOT.resolve("src/main/resources/" + packName);
			try {
				Files.walk(output.resolve(".cache")).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				Files.walk(pack).filter(path -> !"pack.mcmeta".equals(path.toFile().getName())).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				List<Path> generated = Files.walk(output).filter(path -> path.toFile().isFile()).map(output::relativize).toList();
				for (Path path : generated) {
					Files.createDirectories(pack.resolve(path).getParent());
					Files.copy(output.resolve(path), pack.resolve(path), StandardCopyOption.REPLACE_EXISTING);
				}
				Files.walk(output).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				move(temp, output);
				Files.walk(temp).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				Files.deleteIfExists(temp);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}));
	}
}
