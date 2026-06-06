package in.hridaykh.moregenerators.datagen.extra;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import in.hridaykh.moregenerators.MoreGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {

	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {

		super(output, lookupProvider, blockTags, MoreGenerators.MOD_ID, existingFileHelper);

	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
	}
}
