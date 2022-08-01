package net.luis.xsurvive.data.provider.block;

import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class XSurviveBlockStateProvider extends BlockStateProvider {

	protected final ExistingFileHelper existingFileHelper;
	
	public XSurviveBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
		this.existingFileHelper = existingFileHelper;
	}
	
	@Override
	protected void registerStatesAndModels() {
		this.litFacingBlock(XSurviveBlocks.SMELTING_FURNACE.get());
		
		for (Block block : XSurviveBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList())) {
			this.simpleBlockItem(block, this.blockModel(block, null));
		}
	}
	
	private void litFacingBlock(Block block) {
		String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
		this.models().orientable(name, this.modLoc("block/" + name + "_side"), this.modLoc("block/" + name + "_front"), this.modLoc("block/" + name + "_top"));
		this.models().orientable(name + "_on", this.modLoc("block/" + name + "_side"), this.modLoc("block/" + name + "_front_on"), this.modLoc("block/" + name + "_top"));
		VariantBlockStateBuilder builder = this.getVariantBuilder(block);
		for (Direction direction : Lists.newArrayList(Direction.Plane.HORIZONTAL.iterator())) {
			builder.partialState().with(AbstractFurnaceBlock.FACING, direction).with(AbstractFurnaceBlock.LIT, true).modelForState().modelFile(this.blockModel(block, "on")).rotationY(getYRotation(direction)).addModel();
			builder.partialState().with(AbstractFurnaceBlock.FACING, direction).with(AbstractFurnaceBlock.LIT, false).modelForState().modelFile(this.blockModel(block, null)).rotationY(getYRotation(direction)).addModel();
		}
	}
	
	private int getYRotation(Direction direction) {
		return switch (direction) {
			case NORTH -> 0;
			case EAST -> 90;
			case SOUTH -> 180;
			case WEST -> 270;
			default -> throw new IllegalStateException();
		};
	}
	
	private ModelFile blockModel(Block block, String addition) {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
        if (addition == null || addition.isEmpty()) {
        	return new UncheckedModelFile(new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath()));
        }
        return new UncheckedModelFile(new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_" + addition));
    }
	
	@Override
	public String getName() {
		return "XSurvive Block States";
	}
	
}

