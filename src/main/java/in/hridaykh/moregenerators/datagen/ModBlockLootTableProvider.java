package in.hridaykh.moregenerators.datagen;

import java.util.Set;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.blocks.ModBlocks;
import in.hridaykh.moregenerators.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

	protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);

	}

	@Override
	protected void generate() {
		MoreGenerators.LOGGER.info("Generating block loot tables...");
		dropSelf(ModBlocks.BISMUTH_BLOCK.get());
		add(ModBlocks.BISMUTH_ORE.get(), b -> createMultipleOre(ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get(), 3, 7));
		add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), b -> createMultipleOre(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(), 5, 10));
	}

	protected LootTable.Builder createMultipleOre(Block block, Item item, int min, int max) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createSilkTouchDispatchTable(block,
				(LootPoolEntryContainer.Builder<?>) this.applyExplosionDecay(block,
						LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
								.apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(deferredHolder -> (Block) deferredHolder.get()).toList();
	}

}
