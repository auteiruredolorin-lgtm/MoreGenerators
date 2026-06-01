package in.hridaykh.moregenerators.datagen;

import java.util.concurrent.CompletableFuture;

import in.hridaykh.moregenerators.collections.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.NotNull;

public class ModDataMapProvider extends DataMapProvider {

	protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.@NotNull Provider provider) {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS).add(ModItems.STARLIGHT_ASHES.getId(), new FurnaceFuel(120000), false)
				.add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(1200), false);
	}

}
