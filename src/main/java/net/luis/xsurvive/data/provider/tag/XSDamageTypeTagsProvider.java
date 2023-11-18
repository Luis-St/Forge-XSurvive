package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSDamageTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.damagesource.DamageTypes.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeTagsProvider extends DamageTypeTagsProvider {
	
	public XSDamageTypeTagsProvider(@NotNull DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
		this.tag(XSDamageTypeTags.DAMAGE_FROM_ABOVE).add(LIGHTNING_BOLT).addTag(DamageTypeTags.DAMAGES_HELMET);
		this.tag(XSDamageTypeTags.DAMAGE_FROM_FRONT).add(STING).addTag(DamageTypeTags.IS_PROJECTILE);
		this.tag(XSDamageTypeTags.DAMAGE_FROM_BELOW).add(IN_FIRE, ON_FIRE, LAVA, SWEET_BERRY_BUSH);
		this.tag(XSDamageTypeTags.FULL_BODY_DAMAGE).add(IN_WALL, CRAMMING, DROWN, STARVE, CACTUS, FELL_OUT_OF_WORLD, GENERIC, MAGIC, WITHER, DRY_OUT, FREEZE, MOB_ATTACK, MOB_ATTACK_NO_AGGRO, PLAYER_ATTACK, INDIRECT_MAGIC, THORNS, EXPLOSION)
			.add(PLAYER_EXPLOSION, SONIC_BOOM, BAD_RESPAWN_POINT, OUTSIDE_BORDER, GENERIC_KILL);
		this.tag(XSDamageTypeTags.HEAD_ONLY_DAMAGE).add(FLY_INTO_WALL);
		this.tag(XSDamageTypeTags.FEET_ONLY_DAMAGE).add(HOT_FLOOR, FALL, DRAGON_BREATH, STALAGMITE);
		this.tag(DamageTypeTags.BYPASSES_ARMOR).replace().add(ON_FIRE, IN_WALL, CRAMMING, DROWN, GENERIC, WITHER, DRAGON_BREATH, STARVE, FREEZE, MAGIC, INDIRECT_MAGIC, FELL_OUT_OF_WORLD, GENERIC_KILL, SONIC_BOOM, OUTSIDE_BORDER)
			.add(/*FLY_INTO_WALL, FALL, STALAGMITE*/);
	}
}
