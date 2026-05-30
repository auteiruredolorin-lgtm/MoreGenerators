package in.hridaykh.moregenerators.datagen;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MoreGenerators.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(ModItems.BISMUTH.get());
		basicItem(ModItems.CHISEL_ITEM.get());
		basicItem(ModItems.FROSTFIRE_ICE.get());
		basicItem(ModItems.RADISH_ITEM.get());
		basicItem(ModItems.RAW_BISMUTH.get());
		basicItem(ModItems.STARLIGHT_ASHES.get());
	}

}
