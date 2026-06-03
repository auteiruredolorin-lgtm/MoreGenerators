package in.hridaykh.moregenerators.collections;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.content.LedLightBulb;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreGenerators.MOD_ID);

	// led
	public static final DeferredItem<Item> LED_FILAMENT = ITEMS.register("led_filament", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LED_BULB = ITEMS.register("led_bulb", () -> new LedLightBulb(new Item.Properties()));

	// elements
	public static final DeferredItem<Item> SILICON = ITEMS.register("silicon", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> PHOSPHORUS = ITEMS.register("phosphorus", () -> new Item(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		CreativeTabs.addItemToTab(LED_BULB, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(LED_FILAMENT, CreativeTabs.CREATIVE_TABS[0]);

		CreativeTabs.addItemToTab(SILICON, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(PHOSPHORUS, CreativeTabs.CREATIVE_TABS[0]);

		ITEMS.register(eventBus);
	}

}
