package in.hridaykh.moregenerators.datagen;

import com.simibubi.create.AllItems;
import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.patryk3211.powergrid.collections.ModdedItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
	}

	protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
	}

	protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String suffix) {
		for (ItemLike itemlike : ingredients) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, recipeFactory).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(recipeOutput, MoreGenerators.MOD_ID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
		}
	}

	@Override
	protected void buildRecipes(RecipeOutput ro) {
		ItemLike bismuthItem = ModItems.BISMUTH.get();

		// Basic Bismuth
		shapelessRecipe(ro, AllItems.CRUSHED_TIN, bismuthItem, 9);
		shapelessRecipe(ro, ModBlocks.BISMUTH_BLOCK.get(), bismuthItem, 9);
		shapedRecipe(ro, bismuthItem, "bbb_bbb_bbb", ModBlocks.BISMUTH_BLOCK.get(), 1);

		// wire
		shapedRecipe(ro, AllItems.COPPER_NUGGET, "##", ModdedItems.WIRE, 3);

		// Smelting
		List<ItemLike> bismuthSmeltables = List.of(ModBlocks.BISMUTH_ORE, ModBlocks.BISMUTH_DEEPSLATE_ORE, ModItems.RAW_BISMUTH);
		oreSmelting(ro, bismuthSmeltables, RecipeCategory.MISC, bismuthItem, 5f, 100, "bismuth");
		oreBlasting(ro, bismuthSmeltables, RecipeCategory.MISC, bismuthItem, 5f, 100, "bismuth");

		// Non-Block Blocks
		shapedRecipe(ro, bismuthItem, "1  22", ModBlocks.BISMUTH_STAIRS.get(), 4);
		slab(ro, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_SLAB.get(), bismuthItem);
		wall(ro, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_WALL.get(), bismuthItem);
		pressurePlate(ro, ModBlocks.BISMUTH_PRESSURE_PLATE.get(), bismuthItem);
		// non-block blocks' builders
		Ingredient ing = Ingredient.of(bismuthItem);
		var builders = List.of(buttonBuilder(ModBlocks.BISMUTH_BUTTON.get(), ing), fenceBuilder(ModBlocks.BISMUTH_FENCE.get(), ing), fenceGateBuilder(ModBlocks.BISMUTH_FENCE_GATE.get(), ing), doorBuilder(ModBlocks.BISMUTH_DOOR.get(), ing), trapdoorBuilder(ModBlocks.BISMUTH_TRAPDOOR.get(), ing));
		for (RecipeBuilder builder : builders)
			builder.group("bismuth").unlockedBy("has_bismuth", has(bismuthItem)).save(ro);

	}

	private void shapelessRecipe(RecipeOutput ro, ItemLike in, ItemLike out, int q) {
		String inName = in.asItem().getDescriptionId();
		String outName = out.asItem().getDescriptionId();
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, out, q).requires(in).unlockedBy("has_" + inName, has(in)).save(ro, outName + "_from_" + inName);
	}

	private void shapedRecipe(RecipeOutput ro, ItemLike in, String pattern, ItemLike out, int q) {
		// Pad to 9 chars, replace non-spaces with 'I', and split every 3 characters
		String[] p = String.format("%-9s", pattern.replaceAll("_", "")).replaceAll("[^ ]", "I").split("(?<=\\G...)");
		String inName = in.asItem().getDescriptionId();
		String outName = out.asItem().getDescriptionId();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, out, q).pattern(p[0]).pattern(p[1]).pattern(p[2]).define('I', in).unlockedBy("has_" + inName, has(in)).save(ro, outName + "_from_" + inName);

	}


}
