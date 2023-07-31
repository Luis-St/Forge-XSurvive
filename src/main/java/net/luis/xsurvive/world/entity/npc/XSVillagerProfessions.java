package net.luis.xsurvive.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 *
 * @author Luis-St
 *
 */

public class XSVillagerProfessions {
	
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, XSurvive.MOD_ID);
	
	public static final RegistryObject<VillagerProfession> BEEKEEPER = VILLAGER_PROFESSIONS.register("beekeeper", () -> {
		return register("beekeeper", SoundEvents.BEEHIVE_SHEAR);
	});
	public static final RegistryObject<VillagerProfession> ENCHANTER = VILLAGER_PROFESSIONS.register("enchanter", () -> {
		return register("enchanter", SoundEvents.ENCHANTMENT_TABLE_USE);
	});
	public static final RegistryObject<VillagerProfession> END_TRADER = VILLAGER_PROFESSIONS.register("end_trader", () -> {
		return register("end_trader", SoundEvents.ENDER_CHEST_OPEN);
	});
	public static final RegistryObject<VillagerProfession> LUMBERJACK = VILLAGER_PROFESSIONS.register("lumberjack", () -> {
		return register("lumberjack", SoundEvents.AXE_STRIP);
	});
	public static final RegistryObject<VillagerProfession> MINER = VILLAGER_PROFESSIONS.register("miner", () -> {
		return register("miner", SoundEvents.STONE_BREAK);
	});
	public static final RegistryObject<VillagerProfession> MOB_HUNTER = VILLAGER_PROFESSIONS.register("mob_hunter", () -> {
		return register("mob_hunter", SoundEvents.ANVIL_USE);
	});
	public static final RegistryObject<VillagerProfession> NETHER_TRADER = VILLAGER_PROFESSIONS.register("nether_trader", () -> {
		return register("nether_trader", null);
	});
	
	private static @NotNull VillagerProfession register(@NotNull String name, @Nullable SoundEvent workSound) {
		ResourceLocation location = new ResourceLocation(XSurvive.MOD_ID, name);
		return new VillagerProfession(location.toString(), (holder) -> holder.is(location), (holder) -> holder.is(location), ImmutableSet.of(), ImmutableSet.of(), workSound);
	}
}
