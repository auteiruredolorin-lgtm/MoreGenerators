package in.hridaykh.moregenerators.datagen.createRecipes;

import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CrushingRecipes extends CrushingRecipeGen {
	GeneratedRecipe PHOSPHORUS = create(() -> Items.BONE_BLOCK, b -> b.duration(100).output(ModItems.PHOSPHORUS.get(), 18));

	public CrushingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, MoreGenerators.MOD_ID);
	}
}
