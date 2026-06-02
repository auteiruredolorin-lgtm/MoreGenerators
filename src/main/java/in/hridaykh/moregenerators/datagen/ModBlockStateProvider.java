package in.hridaykh.moregenerators.datagen;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MoreGenerators.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		blockWithItem(ModBlocks.BISMUTH_BLOCK);
		blockWithItem(ModBlocks.BISMUTH_DEEPSLATE_ORE);
		blockWithItem(ModBlocks.BISMUTH_ORE);

	}

	private void blockWithItem(DeferredBlock<?> block) {
		simpleBlockWithItem(block.get(), cubeAll(block.get()));
	}

}
