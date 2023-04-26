package net.luis.xsurvive.wiki.files;

import net.luis.xores.world.fixer.ToolFixer;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.tags.ITagManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class BlocksWikiFile {
	
	public static WikiFileBuilder create() {
		WikiFileBuilder builder = new WikiFileBuilder("BlocksWiki");
		builder.header1("Blocks");
		for (Block block : XSBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(BlocksWikiFile::hasItem).toList()) {
			builder.header2(StringUtils.capitalize(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString()));
			builder.header3("Properties");
			addBlockProperties(builder, block);
			if (block instanceof WikiFileEntry wikiEntry) {
				builder.header3("Usage");
				wikiEntry.add(builder);
			}
		}
		return builder;
	}
	
	private static boolean hasItem(Block block) {
		for (Item item : XSBlocks.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			if (item instanceof BlockItem blockItem && blockItem.getBlock() == block) {
				return true;
			}
		}
		return false;
	}
	
	private static void addBlockProperties(WikiFileBuilder wikiBuilder, Block block) {
		Properties properties = block.properties;
		wikiBuilder.lines((builder) -> {
			builder.append("Destroy time:").append(properties.destroyTime).endLine();
			builder.append("Explosion resistance:").append(properties.explosionResistance).endLine();
			builder.append("Tool:").append(getToolForBlock(block)).endLine();
			int blockLevel = ToolFixer.INSTANCE.getLevelForBlock(block);
			builder.append("Required tool level:").append(blockLevel == Integer.MAX_VALUE ? 0 : blockLevel).endLine();
			if (properties.requiresCorrectToolForDrops) {
				builder.append("Requires correct tool for drops:").append(true).endLine();
			}
			if (properties.friction != 0.6F) {
				builder.append("Friction:").append(properties.friction).endLine();
			}
			if (properties.speedFactor != 1.0F) {
				builder.append("Speed factor:").append(properties.speedFactor).endLine();
			}
			if (properties.jumpFactor != 1.0F) {
				builder.append("Jump factor:").append(properties.jumpFactor).endLine();
			}
			Material material = properties.material;
			if (material.isFlammable()) {
				builder.append("Flammable:").append(true).endLine();
			}
			if (material.blocksMotion()) {
				builder.append("Motion blocking:").append(true).endLine();
			}
			builder.append("Piston push reaction:").append(XSLanguageProvider.getName(new ResourceLocation(material.getPushReaction().name().toLowerCase()))).endLine();
		});
	}
	
	private static @NotNull String getToolForBlock(Block block) {
		ITagManager<Block> tagManager = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags());
		if (tagManager.getTag(BlockTags.MINEABLE_WITH_PICKAXE).contains(block)) {
			return "Pickaxe";
		} else if (tagManager.getTag(BlockTags.MINEABLE_WITH_AXE).contains(block)) {
			return "Axe";
		} else if (tagManager.getTag(BlockTags.MINEABLE_WITH_SHOVEL).contains(block)) {
			return "Shovel";
		} else if (tagManager.getTag(BlockTags.MINEABLE_WITH_HOE).contains(block)) {
			return "Hoe";
		}
		return "No";
	}
	
}