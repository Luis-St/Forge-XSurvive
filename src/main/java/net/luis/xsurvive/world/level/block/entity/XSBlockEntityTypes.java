package net.luis.xsurvive.world.level.block.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings({"CodeBlock2Expr", "DataFlowIssue"})
public class XSBlockEntityTypes {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<SmeltingFurnaceBlockEntity>> SMELTING_FURNACE = BLOCK_ENTITY_TYPES.register("smelting_furnace", () -> {
		return BlockEntityType.Builder.of(SmeltingFurnaceBlockEntity::new, XSBlocks.SMELTING_FURNACE.get()).build(null);
	});
}
