package in.hridaykh.moregenerators.blocks;

import java.util.function.Supplier;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.items.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreGenerators.MOD_ID);

	public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlockWithItem("bismuth_block",
			() -> new Block(Block.Properties.of().strength(8f, 600).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final DeferredBlock<Block> BISMUTH_ORE = registerBlockWithItem("bismuth_ore", () -> new DropExperienceBlock(UniformInt.of(40, 80),
			Block.Properties.of().strength(1f, 20).requiresCorrectToolForDrops().sound(SoundType.STONE)));

	private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> blockSupplier) {
		DeferredBlock<T> deferredBlock = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(deferredBlock.get(), new Item.Properties()));

		return deferredBlock;
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

	public static void registerCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(ModBlocks.BISMUTH_BLOCK);
			event.accept(ModBlocks.BISMUTH_ORE);
		}
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ModBlocks.BISMUTH_ORE);
		}
	}

}
