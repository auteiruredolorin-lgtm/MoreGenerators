package in.hridaykh.moregenerators.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.blocks.ModBlocks;
import in.hridaykh.moregenerators.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {

		List<ItemLike> bismuthSmeltables = List.of(ModBlocks.BISMUTH_ORE, ModBlocks.BISMUTH_DEEPSLATE_ORE, ModItems.RAW_BISMUTH);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BISMUTH_BLOCK.get()).pattern("BBB").pattern("BBB").pattern("BBB")
				.define('B', ModItems.BISMUTH.get()).unlockedBy("has_bismuth", has(ModItems.BISMUTH.get())).save(recipeOutput);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 9).requires(ModBlocks.BISMUTH_BLOCK.get())
				.unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK.get())).save(recipeOutput);

		oreSmelting(recipeOutput, bismuthSmeltables, RecipeCategory.MISC, ModItems.BISMUTH, 5f, 100, "bismuth");
		oreBlasting(recipeOutput, bismuthSmeltables, RecipeCategory.MISC, ModItems.BISMUTH, 5f, 100, "bismuth");
	}

	protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience,
			int cookingTime, String group) {
		oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group,
				"_from_smelting");
	}

	protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience,
			int cookingTime, String group) {
		oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group,
				"_from_blasting");
	}

	protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> serializer,
			AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience,
			int cookingTime, String group, String suffix) {
		for (ItemLike itemlike : ingredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, recipeFactory)
					.group(group).unlockedBy(getHasName(itemlike), has(itemlike))
					.save(recipeOutput, MoreGenerators.MOD_ID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
		}
	}

}
