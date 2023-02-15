package net.luis.xsurvive.wiki.files;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.MinMax;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.world.entity.npc.XSVillagerProfessions;
import net.minecraft.Util;
import net.minecraft.locale.Language;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author Luis-st
 *
 */

public class VillagerTradeWikiFile {
	
	private static final List<Supplier<VillagerProfession>> PROFESSIONS = Util.make(Lists.newArrayList(), (list) -> {
		list.add(() -> VillagerProfession.ARMORER);
		list.add(() -> VillagerProfession.LIBRARIAN);
		list.add(() -> VillagerProfession.WEAPONSMITH);
		list.add(() -> VillagerProfession.TOOLSMITH);
		list.add(XSVillagerProfessions.BEEKEEPER);
		list.add(XSVillagerProfessions.ENCHANTER);
		list.add(XSVillagerProfessions.END_TRADER);
		list.add(XSVillagerProfessions.LUMBERJACK);
		list.add(XSVillagerProfessions.MINER);
		list.add(XSVillagerProfessions.MOB_HUNTER);
		list.add(XSVillagerProfessions.NETHER_TRADER);
	});
	
	public static WikiFileBuilder create() {
		WikiFileBuilder wikiBuilder = new WikiFileBuilder("VillagerTradeWiki");
		wikiBuilder.header1("Villager Trades");
		for (Supplier<VillagerProfession> profession : PROFESSIONS) {
			addTradesForProfession(wikiBuilder, profession.get());
		}
		return wikiBuilder;
	}
	
	private static void addTradesForProfession(WikiFileBuilder wikiBuilder, VillagerProfession profession) {
		wikiBuilder.header2(StringUtils.capitalize(Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession)).getPath()));
		for (Map.Entry<Integer, VillagerTrades.ItemListing[]> trades : Maps.newHashMap(VillagerTrades.TRADES.get(profession)).entrySet()) {
			wikiBuilder.header3(StringUtils.capitalize(Language.getInstance().getOrDefault("merchant.level." + trades.getKey())) + " trades");
			wikiBuilder.table((builder) -> {
				builder.append("Cost item").append("Cost count").append("Cost item").append("Cost count").append("Max uses").append("Price multiplier").append("Xp").append("Result item").append("Result count").endLine();
				for (VillagerTrades.ItemListing trade : trades.getValue()) {
					TradeData tradeData = getTradeData(trade);
					builder.append(tradeData.costItemA).append(tradeData.costCountA).append(tradeData.costItemB).append(tradeData.costCountB).append(tradeData.maxUses).append(tradeData.priceMultiplier)
							.append(tradeData.xp).append(tradeData.resultItem).append(tradeData.resultCount.toString()).endLine();
				}
			});
		}
	}
	
	private static TradeData getTradeData(VillagerTrades.ItemListing trade) {
		Villager villager = new Villager(EntityType.VILLAGER, ServerLifecycleHooks.getCurrentServer().overworld());
		Item costItemA = null;
		MinMax costCountA = new MinMax();
		Item costItemB = null;
		MinMax costCountB = new MinMax();
		MinMax maxUses = new MinMax();
		float priceMultiplier = 0;
		MinMax xp = new MinMax();
		Item resultItem = null;
		MinMax resultCount = new MinMax();
		for (int i = 0; i < 128; i++) {
			MerchantOffer offer = Objects.requireNonNull(trade.getOffer(villager, RandomSource.create()));
			if (costItemA == null) {
				costItemA = offer.getCostA().getItem();
			}
			costCountA.update(offer.getCostA().getCount());
			if (costItemB == null) {
				costItemB = offer.getCostB().getItem();
			}
			costCountB.update(offer.getCostB().getCount());
			maxUses.update(offer.getMaxUses());
			priceMultiplier = offer.getPriceMultiplier();
			xp.update(offer.getXp());
			if (resultItem == null) {
				resultItem = offer.getResult().getItem();
			} else if (resultItem != offer.getResult().getItem()) {
				XSurvive.LOGGER.error("Result item is not the same for all trades -> {} != {}", resultItem, offer.getResult().getItem());
			}
			resultCount.update(offer.getResult().getCount());
		}
		String costItemAName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(costItemA)).getPath().replace("_", " ");
		String costItemBName = costItemB == Items.AIR ? "-" : Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(costItemB)).getPath().replace("_", " ");
		String resultItemName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(resultItem)).getPath().replace("_", " ");
		return new TradeData(StringUtils.capitalize(costItemAName), costCountA, StringUtils.capitalize(costItemBName), costCountB, maxUses, priceMultiplier, xp, StringUtils.capitalize(resultItemName), resultCount);
	}
	
	
	private record TradeData(String costItemA, MinMax costCountA, String costItemB, MinMax costCountB, MinMax maxUses, float priceMultiplier, MinMax xp, String resultItem, MinMax resultCount) {
	
	}
	
}
