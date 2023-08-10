package net.luis.xsurvive.data.provider.block;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("SameParameterValue")
public class XSBlockStateProvider extends BlockStateProvider {
	
	protected final ExistingFileHelper existingFileHelper;
	
	public XSBlockStateProvider(@NotNull DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), XSurvive.MOD_ID, existingFileHelper);
		this.existingFileHelper = existingFileHelper;
	}
	
	@Override
	protected void registerStatesAndModels() {
		this.litFacingBlock(XSBlocks.SMELTING_FURNACE.get());
		this.cubeColumnBlock(XSBlocks.HONEY_MELON.get());
		this.stemAgeBlock(XSBlocks.HONEY_MELON_STEM.get(), 7);
		this.attachedStemBlock(XSBlocks.ATTACHED_HONEY_MELON_STEM.get());
		this.floorAndSideFireBlock(XSBlocks.MYSTIC_FIRE.get());
		for (BlockItem item : XSBlocks.ITEMS.getEntries().stream().map(RegistryObject::get).filter(BlockItem.class::isInstance).map(BlockItem.class::cast).toList()) {
			this.simpleBlockItem(item.getBlock(), this.blockModel(item.getBlock()));
		}
	}
	
	private void litFacingBlock(Block block) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		this.models().orientable(name, this.modLoc("block/" + name + "_side"), this.modLoc("block/" + name + "_front"), this.modLoc("block/" + name + "_top"));
		this.models().orientable(name + "_on", this.modLoc("block/" + name + "_side"), this.modLoc("block/" + name + "_front_on"), this.modLoc("block/" + name + "_top"));
		VariantBlockStateBuilder builder = this.getVariantBuilder(block);
		for (Direction direction : Lists.newArrayList(Direction.Plane.HORIZONTAL.iterator())) {
			builder.partialState().with(AbstractFurnaceBlock.FACING, direction).with(AbstractFurnaceBlock.LIT, true).modelForState().modelFile(this.blockModel(block, "on")).rotationY(this.getYRotation(direction)).addModel();
			builder.partialState().with(AbstractFurnaceBlock.FACING, direction).with(AbstractFurnaceBlock.LIT, false).modelForState().modelFile(this.blockModel(block)).rotationY(this.getYRotation(direction)).addModel();
		}
	}
	
	private void cubeColumnBlock(Block block) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		this.simpleBlock(block, this.models().cubeColumn(name, this.modLoc("block/" + name + "_side"), this.modLoc("block/" + name + "_top")));
	}
	
	private void stemAgeBlock(Block block, int maxAge) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		for (int age = 0; age <= maxAge; age++) {
			this.models().getBuilder(name + "_stage" + age).parent(new ExistingModelFile(this.mcLoc("block/stem_growth" + age), this.existingFileHelper)).texture("stem", this.mcLoc("block/melon_stem")).renderType("cutout");
		}
		VariantBlockStateBuilder builder = this.getVariantBuilder(block);
		for (int age = 0; age <= maxAge; age++) {
			builder.partialState().with(StemBlock.AGE, age).modelForState().modelFile(this.blockModel(block, "stage" + age)).addModel();
		}
	}
	
	private void attachedStemBlock(Block block) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		this.models().getBuilder(name).parent(new ExistingModelFile(this.mcLoc("block/stem_fruit"), this.existingFileHelper)).texture("stem", this.mcLoc("block/melon_stem")).texture("upperstem", this.mcLoc("block/attached_melon_stem"))
			.renderType("cutout");
		VariantBlockStateBuilder builder = this.getVariantBuilder(block);
		for (Direction direction : Lists.newArrayList(Direction.Plane.HORIZONTAL.iterator())) {
			builder.partialState().with(AttachedStemBlock.FACING, direction).modelForState().modelFile(this.blockModel(block)).rotationY((this.getYRotation(direction) + 90) % 360).addModel();
		}
	}
	
	private void floorAndSideFireBlock(Block block) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		// @formatter:off
		this.models().getBuilder(name + "_floor0")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_floor"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_0")).renderType("cutout");
		this.models().getBuilder(name + "_floor1")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_floor"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_1")).renderType("cutout");
		this.models().getBuilder(name + "_side0")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_side"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_0")).renderType("cutout");
		this.models().getBuilder(name + "_side1")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_side"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_1")).renderType("cutout");
		this.models().getBuilder(name + "_side_alt0")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_side_alt"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_0")).renderType("cutout");
		this.models().getBuilder(name + "_side_alt1")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_side_alt"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_1")).renderType("cutout");
		this.models().getBuilder(name + "_up0")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_up"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_0")).renderType("cutout");
		this.models().getBuilder(name + "_up1")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_up"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_1")).renderType("cutout");
		this.models().getBuilder(name + "_up_alt0")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_up_alt"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_0")).renderType("cutout");
		this.models().getBuilder(name + "_up_alt1")
			.parent(new ExistingModelFile(new ResourceLocation("block/template_fire_up_alt"), this.existingFileHelper))
			.texture("fire", this.modLoc("block/" + name + "_1")).renderType("cutout");
		this.getMultipartBuilder(block)
				.part().modelFile(this.blockModel(block, "floor0")).nextModel()
				.modelFile(this.blockModel(block, "floor1")).addModel()
				.condition(MysticFireBlock.NORTH, false)
				.condition(MysticFireBlock.SOUTH, false)
				.condition(MysticFireBlock.EAST, false)
				.condition(MysticFireBlock.WEST, false)
				.condition(MysticFireBlock.UP, false)
				.end().part().modelFile(this.blockModel(block, "side0")).nextModel()
				.modelFile(this.blockModel(block, "side1")).nextModel()
				.modelFile(this.blockModel(block, "side_alt0")).nextModel()
				.modelFile(this.blockModel(block, "side_alt1")).addModel()
				.useOr().nestedGroup()
				.condition(MysticFireBlock.NORTH, true)
				.end().nestedGroup()
				.condition(MysticFireBlock.NORTH, false)
				.condition(MysticFireBlock.SOUTH, false)
				.condition(MysticFireBlock.EAST, false)
				.condition(MysticFireBlock.WEST, false)
				.condition(MysticFireBlock.UP, false)
				.end()
				.end().part().modelFile(this.blockModel(block, "side0")).rotationY(180).nextModel()
				.modelFile(this.blockModel(block, "side1")).rotationY(180).nextModel()
				.modelFile(this.blockModel(block, "side_alt0")).rotationY(180).nextModel()
				.modelFile(this.blockModel(block, "side_alt1")).rotationY(180).addModel()
				.useOr().nestedGroup()
				.condition(MysticFireBlock.SOUTH, true)
				.end().nestedGroup()
				.condition(MysticFireBlock.NORTH, false)
				.condition(MysticFireBlock.SOUTH, false)
				.condition(MysticFireBlock.EAST, false)
				.condition(MysticFireBlock.WEST, false)
				.condition(MysticFireBlock.UP, false)
				.end()
				.end().part().modelFile(this.blockModel(block, "side0")).rotationY(90).nextModel()
				.modelFile(this.blockModel(block, "side1")).rotationY(90).nextModel()
				.modelFile(this.blockModel(block, "side_alt0")).rotationY(90).nextModel()
				.modelFile(this.blockModel(block, "side_alt1")).rotationY(90).addModel()
				.useOr().nestedGroup()
				.condition(MysticFireBlock.EAST, true)
				.end().nestedGroup()
				.condition(MysticFireBlock.NORTH, false)
				.condition(MysticFireBlock.SOUTH, false)
				.condition(MysticFireBlock.EAST, false)
				.condition(MysticFireBlock.WEST, false)
				.condition(MysticFireBlock.UP, false)
				.end()
				.end().part().modelFile(this.blockModel(block, "side0")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "side1")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "side_alt0")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "side_alt1")).rotationY(270).addModel()
				.useOr().nestedGroup()
				.condition(MysticFireBlock.WEST, true)
				.end().nestedGroup()
				.condition(MysticFireBlock.NORTH, false)
				.condition(MysticFireBlock.SOUTH, false)
				.condition(MysticFireBlock.EAST, false)
				.condition(MysticFireBlock.WEST, false)
				.condition(MysticFireBlock.UP, false)
				.end()
				.end().part().modelFile(this.blockModel(block, "up0")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "up1")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "up_alt0")).rotationY(270).nextModel()
				.modelFile(this.blockModel(block, "up_alt1")).rotationY(270).addModel()
				.condition(MysticFireBlock.UP, true)
				.end();
		// @formatter:on
	}
	
	private int getYRotation(@NotNull Direction direction) {
		return switch (direction) {
			case NORTH -> 0;
			case EAST -> 90;
			case SOUTH -> 180;
			case WEST -> 270;
			default -> throw new IllegalStateException();
		};
	}
	
	private @NotNull ModelFile blockModel(Block block) {
		return this.blockModel(block, null);
	}
	
	private @NotNull ModelFile blockModel(Block block, String addition) {
		ResourceLocation name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
		if (addition == null || addition.isEmpty()) {
			return new UncheckedModelFile(new ResourceLocation(name.getNamespace(), "block/" + name.getPath()));
		}
		return new UncheckedModelFile(new ResourceLocation(name.getNamespace(), "block/" + name.getPath() + "_" + addition));
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Block States";
	}
}
