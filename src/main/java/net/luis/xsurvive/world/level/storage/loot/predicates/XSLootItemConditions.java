package net.luis.xsurvive.world.level.storage.loot.predicates;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class XSLootItemConditions {
	
	public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, XSurvive.MOD_ID);
	
	public static final RegistryObject<LootItemConditionType> LOOT_TABLE_IDS = LOOT_ITEM_CONDITIONS.register("loot_table_ids", () -> {
		return new LootItemConditionType(new LootTableIdsCondition.Serializer());
	});
}
