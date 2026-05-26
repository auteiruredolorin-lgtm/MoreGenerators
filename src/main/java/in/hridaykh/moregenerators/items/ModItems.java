package in.hridaykh.moregenerators.items;

import in.hridaykh.moregenerators.MoreGenerators;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreGenerators.MOD_ID);

	public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth",
			() -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
			() -> new Item(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	public static void registerCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ModItems.BISMUTH);
			event.accept(ModItems.RAW_BISMUTH);
		}
	}
}
