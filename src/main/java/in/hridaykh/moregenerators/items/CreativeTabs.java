package in.hridaykh.moregenerators.items;

import java.util.function.Supplier;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreGenerators.MOD_ID);

	public static final Supplier<CreativeModeTab> MORE_GENERATORS_TAB = CREATIVE_MODE_TABS.register("more_generators_tab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BISMUTH.get()))
					.title(Component.translatable("creativetab.moregenerators.more_generators_tab"))
					.displayItems((itemDisplayParams, output) -> {
						output.accept(ModItems.BISMUTH);
						output.accept(ModItems.RAW_BISMUTH);
					}).build());

	public static final Supplier<CreativeModeTab> MORE_GENERATORS_BLOCKS_TAB = CREATIVE_MODE_TABS.register("more_generators_blocks_tab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK))
					.withTabsBefore(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "more_generators_tab"))
					.title(Component.translatable("creativetab.moregenerators.more_generators_blocks_tab"))
					.displayItems((itemDisplayParams, output) -> {
						output.accept(ModBlocks.BISMUTH_BLOCK);
						output.accept(ModBlocks.BISMUTH_ORE);
					}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
