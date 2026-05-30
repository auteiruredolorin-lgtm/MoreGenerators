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
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreGenerators.MOD_ID);

	public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlockWithItem("bismuth_block",
			() -> new BismuthMagicBlock(Block.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	// ORES
	public static final DeferredBlock<Block> BISMUTH_ORE = registerBlockWithItem("bismuth_ore", () -> new DropExperienceBlock(UniformInt.of(40, 80),
			Block.Properties.of().strength(3f, 3).requiresCorrectToolForDrops().sound(SoundType.STONE)));

	public static final DeferredBlock<Block> BISMUTH_DEEPSLATE_ORE = registerBlockWithItem("bismuth_deepslate_ore",
			() -> new DropExperienceBlock(UniformInt.of(40, 80),
					Block.Properties.of().strength(4.5f, 4).requiresCorrectToolForDrops().sound(SoundType.STONE)));

	// BUILDING BLOCKS
	public static final DeferredBlock<StairBlock> BISMUTH_STAIRS = registerBlockWithItem("bismuth_stairs",
			() -> new StairBlock(ModBlocks.BISMUTH_BLOCK.get().defaultBlockState(),
					BlockBehaviour.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final DeferredBlock<SlabBlock> BISMUTH_SLAB = registerBlockWithItem("bismuth_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final DeferredBlock<FenceBlock> BISMUTH_FENCE = registerBlockWithItem("bismuth_fence",
			() -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<FenceGateBlock> BISMUTH_FENCE_GATE = registerBlockWithItem("bismuth_fence_gate",
			() -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

	public static final DeferredBlock<WallBlock> BISMUTH_WALL = registerBlockWithItem("bismuth_wall",
			() -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

	// REDSTONE
	public static final DeferredBlock<PressurePlateBlock> BISMUTH_PRESSURE_PLATE = registerBlockWithItem("bismuth_pressure_plate",
			() -> new PressurePlateBlock(BlockSetType.IRON,
					BlockBehaviour.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	public static final DeferredBlock<ButtonBlock> BISMUTH_BUTTON = registerBlockWithItem("bismuth_button", () -> new ButtonBlock(BlockSetType.IRON, 1,
			BlockBehaviour.Properties.of().strength(5f, 6).requiresCorrectToolForDrops().sound(SoundType.METAL).noCollission()));

	public static final DeferredBlock<DoorBlock> BISMUTH_DOOR = registerBlockWithItem("bismuth_door",
			() -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));

	public static final DeferredBlock<TrapDoorBlock> BISMUTH_TRAPDOOR = registerBlockWithItem("bismuth_trapdoor",
			() -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().noOcclusion()));

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
