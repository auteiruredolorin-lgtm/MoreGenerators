package in.hridaykh.moregenerators.datagen;

import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

	protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);

	}

	@Override
	protected void generate() {
		dropSelf(ModBlocks.BISMUTH_BLOCK.get());

		add(ModBlocks.BISMUTH_ORE.get(), b -> oreLootTable(ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get(), 3, 7));
		add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), b -> oreLootTable(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(), 5, 10));

	}

	protected LootTable.Builder oreLootTable(Block block, Item item, int min, int max) {
		var itemCountFunc = SetItemCountFunction.setCount(UniformGenerator.between(min, max));

		var registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		var fortuneBonusFunc = ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE));

		var lootTable = LootItem.lootTableItem(item).apply(itemCountFunc).apply(fortuneBonusFunc);
		return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, lootTable));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(deferredHolder -> (Block) deferredHolder.get()).toList();
	}

}
