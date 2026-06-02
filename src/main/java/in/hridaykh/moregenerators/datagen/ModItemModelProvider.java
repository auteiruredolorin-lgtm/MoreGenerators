package in.hridaykh.moregenerators.datagen;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
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
		basicItem(ModItems.RAW_BISMUTH.get());

		basicItem(ModItems.LED_FILAMENT.get());

		basicItem(ModItems.PHOSPHORUS.get());
		basicItem(ModItems.SILICON.get());

		this.withExistingParent(ModItems.LED_BULB.getId().getPath(), modLoc("block/lamps/light_bulb"));
		this.withExistingParent(ModBlocks.RESISTOR.getId().getPath(), modLoc("block/resistor_v"));
	}

}
