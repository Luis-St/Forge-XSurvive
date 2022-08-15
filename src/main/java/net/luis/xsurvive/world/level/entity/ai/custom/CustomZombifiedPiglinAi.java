package net.luis.xsurvive.world.level.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;

/**
 *
 * @author Luis-st
 *
 */

public class CustomZombifiedPiglinAi implements CustomAi {
	
	private final ZombifiedPiglin zombifiedPiglin;
	
	public CustomZombifiedPiglinAi(ZombifiedPiglin zombifiedPiglin, ServerLevel level) {
		this.zombifiedPiglin = zombifiedPiglin;
	}
	
	@Override
	public boolean canUse() {
		return this.zombifiedPiglin.getTarget() instanceof Player && this.zombifiedPiglin.isAngry();
	}

	@Override
	public void tick() {
		if (this.zombifiedPiglin.getTarget() instanceof Player player) {
			if (player.isCreative() || player.isSpectator()) {
				this.zombifiedPiglin.stopBeingAngry();
			}
		}
	}
	
}
