package net.luis.xsurvive.world.level.block;

import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;

/**
 * 
 * @author Luis-st
 *
 */

public class HoneyMelonBlock extends StemGrownBlock {

	public HoneyMelonBlock(Properties properties) {
		super(properties);
	}

	@Override
	public StemBlock getStem() {
		return XSBlocks.HONEY_MELON_STEM.get();
	}

	@Override
	public AttachedStemBlock getAttachedStem() {
		return XSBlocks.ATTACHED_HONEY_MELON_STEM.get();
	}

}
