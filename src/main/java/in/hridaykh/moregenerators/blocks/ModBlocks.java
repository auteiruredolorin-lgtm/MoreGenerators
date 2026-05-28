package in.hridaykh.moregenerators.blocks;

import java.util.function.Supplier;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.items.ModItems;
import in.hridaykh.moregenerators.tabs.CreativeTabs;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreGenerators.MOD_ID);

	public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlockWithItem("bismuth_block",
			() -> new BismuthMagicBlock(Block.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final DeferredBlock<Block> BISMUTH_ORE = registerBlockWithItem("bismuth_ore", () -> new DropExperienceBlock(UniformInt.of(40, 80),
			Block.Properties.of().strength(3f, 3).requiresCorrectToolForDrops().sound(SoundType.STONE)));

	public static final DeferredBlock<Block> BISMUTH_DEEPSLATE_ORE = registerBlockWithItem("bismuth_deepslate_ore",
			() -> new DropExperienceBlock(UniformInt.of(40, 80),
					Block.Properties.of().strength(4.5f, 4).requiresCorrectToolForDrops().sound(SoundType.STONE)));

	public static void register(IEventBus eventBus) {
		CreativeTabs.addItemToTab(BISMUTH_BLOCK, CreativeTabs.CREATIVE_TABS[1]);
		CreativeTabs.addItemToTab(BISMUTH_ORE, CreativeTabs.CREATIVE_TABS[1]);
		CreativeTabs.addItemToTab(BISMUTH_DEEPSLATE_ORE, CreativeTabs.CREATIVE_TABS[1]);

		BLOCKS.register(eventBus);
	}

	public static void registerCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(ModBlocks.BISMUTH_BLOCK);
			event.accept(ModBlocks.BISMUTH_ORE);
			event.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ModBlocks.BISMUTH_ORE);
			event.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
		}
	}

	private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> blockSupplier) {
		DeferredBlock<T> deferredBlock = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(deferredBlock.get(), new Item.Properties()));
		return deferredBlock;
	}
}
