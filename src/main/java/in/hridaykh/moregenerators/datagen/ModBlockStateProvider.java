package in.hridaykh.moregenerators.datagen;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MoreGenerators.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		solarPanelBlock(ModBlocks.SOLAR_PANEL);
	}

	private void solarPanelBlock(DeferredBlock<?> block) {
		ModelFile.ExistingModelFile solarPanelModel = models().getExistingFile(modLoc("block/" + block.getId().getPath()));
		simpleBlock(block.get(), solarPanelModel);
	}
}