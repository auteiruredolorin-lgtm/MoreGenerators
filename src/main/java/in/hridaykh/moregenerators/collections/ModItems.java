package in.hridaykh.moregenerators.collections;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.customTypes.LedLightBulb;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreGenerators.MOD_ID);

	// bismuth
	public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth", () -> new Item(new Item.Properties()));

	// led
	public static final DeferredItem<Item> LED_FILAMENT = ITEMS.register("led_filament", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LED_BULB = ITEMS.register("led_bulb", () -> new LedLightBulb(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		CreativeTabs.addItemToTab(BISMUTH, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(RAW_BISMUTH, CreativeTabs.CREATIVE_TABS[0]);

		CreativeTabs.addItemToTab(LED_BULB, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(LED_FILAMENT, CreativeTabs.CREATIVE_TABS[0]);

		ITEMS.register(eventBus);
	}

}
