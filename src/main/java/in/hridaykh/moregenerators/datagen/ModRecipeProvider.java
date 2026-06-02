package in.hridaykh.moregenerators.datagen;

import com.simibubi.create.AllItems;
import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
import in.hridaykh.moregenerators.collections.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
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

		// TODO: bonemeal -> phosphor, quartz -> silicon
		shapelessRecipe2Ins(ro, Items.BONE_MEAL, Items.QUARTZ, ModItems.LED_FILAMENT, 2);
		var lb = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LED_BULB).unlockedBy("has_led_filament", has(ModItems.LED_FILAMENT));
		lb.define('G', Items.GLASS_PANE).define('F', ModItems.LED_FILAMENT).define('C', AllItems.COPPER_SHEET);
		lb.pattern(" G ").pattern("GFG").pattern(" C ");
		lb.save(ro);

		// Smelting
		List<ItemLike> bismuthSmeltables = List.of(ModBlocks.BISMUTH_ORE, ModBlocks.BISMUTH_DEEPSLATE_ORE, ModItems.RAW_BISMUTH);
		oreSmelting(ro, bismuthSmeltables, RecipeCategory.MISC, bismuthItem, 5f, 100, "bismuth");
		oreBlasting(ro, bismuthSmeltables, RecipeCategory.MISC, bismuthItem, 5f, 100, "bismuth");

	}

	private void shapelessRecipe(RecipeOutput ro, ItemLike in, ItemLike out, int q) {
		String inName = in.asItem().getDescriptionId();
		String outName = out.asItem().getDescriptionId();
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, out, q).requires(in).unlockedBy("has_" + inName, has(in)).save(ro, outName + "_from_" + inName);
	}

	private void shapelessRecipe2Ins(RecipeOutput ro, ItemLike in1, ItemLike in2, ItemLike out, int q) {
		String in1Name = in1.asItem().getDescriptionId();
		String in2Name = in2.asItem().getDescriptionId();
		String outName = out.asItem().getDescriptionId();
		var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, out, q).requires(in1).requires(in2);
		builder.unlockedBy("has_" + in1Name, has(in1)).unlockedBy("has_" + in2Name, has(in2));
		builder.save(ro, outName + "_from_" + in1Name + "_and_" + in2Name);
	}

	private void shapedRecipe(RecipeOutput ro, ItemLike in, String pattern, ItemLike out, int q) {
		// Pad to 9 chars, replace non-spaces with 'I', and split every 3 characters
		String[] p = String.format("%-9s", pattern.replaceAll("_", "")).replaceAll("[^ ]", "I").split("(?<=\\G...)");
		String inName = in.asItem().getDescriptionId();
		String outName = out.asItem().getDescriptionId();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, out, q).pattern(p[0]).pattern(p[1]).pattern(p[2]).define('I', in).unlockedBy("has_" + inName, has(in)).save(ro, outName + "_from_" + inName);

	}


}
