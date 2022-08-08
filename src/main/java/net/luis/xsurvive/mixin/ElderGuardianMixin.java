package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.luis.xsurvive.tag.XSurviveBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.phys.AABB;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(ElderGuardian.class)
public abstract class ElderGuardianMixin extends Guardian {

	private ElderGuardianMixin(EntityType<? extends Guardian> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
	private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> callback) {
		callback.setReturnValue(Guardian.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.45).add(Attributes.ATTACK_DAMAGE, 12.0).add(Attributes.MAX_HEALTH, 120.0));
	}
	
	@Inject(method = "customServerAiStep", at = @At("TAIL"))
	protected void customServerAiStep(CallbackInfo callback) {
		this.destroyBlocks(this.getBoundingBox().inflate(1.5));
	}
	
	private void destroyBlocks(AABB boundingBox) {
		if (this.level instanceof ServerLevel level) {
			StructureManager manager = level.structureManager();
			if (manager.structureHasPieceAt(this.blockPosition(), manager.getStructureWithPieceAt(this.blockPosition(), BuiltinStructures.OCEAN_MONUMENT))) {
				for (int x = Mth.floor(boundingBox.minX); x <= Mth.floor(boundingBox.maxX); ++x) {
					for (int y = Mth.floor(boundingBox.minY); y <= Mth.floor(boundingBox.maxY); ++y) {
						for (int z = Mth.floor(boundingBox.minZ); z <= Mth.floor(boundingBox.maxZ); ++z) {
							BlockPos pos = new BlockPos(x, y, z);
							BlockState state = this.level.getBlockState(pos);
							if (!state.isAir() && !state.is(Blocks.WATER) && !state.is(XSurviveBlockTags.OCEAN_MONUMENT_BLOCKS)) {
								this.level.destroyBlock(pos, true, this);
								this.level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
							}
						}
					}
				}
			}
		}
	}
	
}
