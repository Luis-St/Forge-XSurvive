package net.luis.xsurvive.data;

import net.luis.xores.data.provider.DatapackBuiltinEntriesProvider;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.XSBuiltinProvider;
import net.luis.xsurvive.data.provider.block.XSBlockStateProvider;
import net.luis.xsurvive.data.provider.item.XSItemModelProvider;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.data.provider.loot.XSGlobalLootModifierProvider;
import net.luis.xsurvive.data.provider.loottable.XSLootTableProvider;
import net.luis.xsurvive.data.provider.recipe.XSRecipeProvider;
import net.luis.xsurvive.data.provider.recipe.additions.XSAdditionsRecipeProvider;
import net.luis.xsurvive.data.provider.tag.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings({"resource", "ResultOfMethodCallIgnored"})
@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class GatherDataEventHandler {
	
	private static final Path ROOT = new File(Paths.get(".").toAbsolutePath().toString()).getParentFile().getParentFile().toPath();
	
	@SubscribeEvent
	public static void gatherData(@NotNull GatherDataEvent event) throws IOException {
		DataGenerator generator = event.getGenerator();
		String type = System.getProperty("xsurvive.data.include");
		if (event.includeDev()) {
			if ("mod".equalsIgnoreCase(type)) {
				generator.addProvider(event.includeClient(), new XSBlockStateProvider(generator, event.getExistingFileHelper()));
				generator.addProvider(event.includeClient(), new XSItemModelProvider(generator, event.getExistingFileHelper()));
				generator.addProvider(event.includeClient(), new XSLanguageProvider(generator));
				generator.addProvider(event.includeServer(), new XSLootTableProvider(generator));
				generator.addProvider(event.includeServer(), new XSRecipeProvider(generator));
				XSBlockTagsProvider blockTagsProvider = new XSBlockTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper());
				generator.addProvider(event.includeServer(), blockTagsProvider);
				generator.addProvider(event.includeServer(), new XSItemTagsProvider(generator, event.getLookupProvider(), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSPoiTypeTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSBiomeTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSDamageTypeTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper()));
				generator.addProvider(event.includeServer(), new XSGlobalLootModifierProvider(generator));
				generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), XSBuiltinProvider.createProvider(), Set.of(XSurvive.MOD_ID)));
			}
			if ("additions".equalsIgnoreCase(type)) {
				setupDatapackGeneration("xsurvive_additions");
				generator.addProvider(event.includeServer(), new XSAdditionsRecipeProvider(generator));
			}
		}
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
