package in.hridaykh.moregenerators.collections;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.content.resistor.Resistor;
import in.hridaykh.moregenerators.content.solar.SourceBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreGenerators.MOD_ID);

	public static final DeferredBlock<Resistor> LIGHT_RESISTOR = registerBlockWithItem("resistor", () -> new Resistor(BlockBehaviour.Properties.of().instabreak()));

	public static final DeferredBlock<SourceBlock> SOURCE_BLOCK = registerBlockWithItem("source_block", () -> new SourceBlock(BlockBehaviour.Properties.of().instabreak()));

	public static void register(IEventBus eventBus) {
		CreativeTabs.addItemToTab(LIGHT_RESISTOR, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(SOURCE_BLOCK, CreativeTabs.CREATIVE_TABS[0]);

		BLOCKS.register(eventBus);
	}

	private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> blockSupplier) {
		DeferredBlock<T> deferredBlock = BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name, () -> new BlockItem(deferredBlock.get(), new Item.Properties()));
		return deferredBlock;
	}
}
