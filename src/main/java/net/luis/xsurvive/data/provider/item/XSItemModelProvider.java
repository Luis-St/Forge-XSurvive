package net.luis.xsurvive.data.provider.item;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class XSItemModelProvider extends ItemModelProvider {
	
	public XSItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		for (Item item : XSItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			if (item instanceof TieredItem tieredItem) {
				this.handheldItem(tieredItem);
			} else {
				this.generatedItem(item);
			}
		}
	}
	
	private void generatedItem(Item item) {
		ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
		ModelFile model = new ExistingModelFile(new ResourceLocation("item/generated"), this.existingFileHelper);
		this.getBuilder(location.getPath()).parent(model).texture("layer0", new ResourceLocation(XSurvive.MOD_ID, "item/" + location.getPath()));
	}
	
	private void handheldItem(TieredItem tool) {
		ResourceLocation location = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(tool));
		ModelFile model = new ExistingModelFile(new ResourceLocation("item/handheld"), this.existingFileHelper);
		this.getBuilder(location.getPath()).parent(model).texture("layer0", new ResourceLocation(XSurvive.MOD_ID, "item/" + location.getPath()));
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Item Models";
	}
}
