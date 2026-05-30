package in.hridaykh.moregenerators.datagen;

import java.util.concurrent.CompletableFuture;

import in.hridaykh.moregenerators.items.ModItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class ModDataMapProvider extends DataMapProvider {

	protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather() {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS).add(ModItems.STARLIGHT_ASHES.getId(), new FurnaceFuel(120000), false)
				.add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(1200), false);
	}

}
