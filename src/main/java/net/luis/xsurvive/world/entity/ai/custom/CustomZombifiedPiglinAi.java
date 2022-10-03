package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;

/**
 *
 * @author Luis-st
 *
 */

public class CustomZombifiedPiglinAi extends AbstractCustomAi<ZombifiedPiglin> {
	
	public CustomZombifiedPiglinAi(ZombifiedPiglin entity, ServerLevel level) {
		super(entity, level);
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() instanceof Player && this.entity.isAngry();
	}

	@Override
	public void tick() {
		if (this.entity.getTarget() instanceof Player player) {
			if (player.isCreative() || player.isSpectator()) {
				this.entity.stopBeingAngry();
			}
		}
	}
	
}
