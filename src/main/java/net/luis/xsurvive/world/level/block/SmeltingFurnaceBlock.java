package net.luis.xsurvive.world.level.block;

import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.luis.xsurvive.world.level.block.entity.SmeltingFurnaceBlockEntity;
import net.luis.xsurvive.world.level.block.entity.XSBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-st
 *
 */

public class SmeltingFurnaceBlock extends AbstractFurnaceBlock implements WikiFileEntry {
	
	public SmeltingFurnaceBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new SmeltingFurnaceBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return createFurnaceTicker(level, type, XSBlockEntityTypes.SMELTING_FURNACE.get());
	}
	
	@Override
	protected void openContainer(@NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
		if (level.getBlockEntity(pos) instanceof SmeltingFurnaceBlockEntity smeltingBlockEntity) {
			player.openMenu(smeltingBlockEntity);
		}
	}
	
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource rng) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY();
			double z = pos.getZ() + 0.5;
			if (rng.nextDouble() < 0.1) {
				level.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			double offset = rng.nextDouble() * 0.6D - 0.3D;
			double xOffset = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : offset;
			double yOffset = rng.nextDouble() * 9.0D / 16.0D;
			double zOffset = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : offset;
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public void add(@NotNull WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("The smelting furnace works similarly to the vanilla blast furnace or the smoker.").endLine();
			builder.append("The furnace provides faster recipes for the following blocks:").endLine();
		});
		wikiBuilder.pointList((builder) -> {
			for (String recipe : this.getRecipes()) {
				builder.append(recipe).endLine();
			}
		});
	}
	
	private @NotNull List<String> getRecipes() {
		return ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(XSRecipeTypes.SMELTING.get()).stream().map(SmeltingRecipe::getId).map(XSLanguageProvider::getName).map((name) -> {
			return name.replace("From Smelting", "").trim();
		}).collect(Collectors.toList());
	}
}
