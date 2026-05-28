package in.hridaykh.moregenerators.items;

import java.util.List;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.items.custom.ChieslItem;
import in.hridaykh.moregenerators.items.custom.FuelItem;
import in.hridaykh.moregenerators.tabs.CreativeTabs;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreGenerators.MOD_ID);

	// bismuth
	public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> CHISEL_ITEM = ITEMS.register("chiesl", () -> new ChieslItem(new Item.Properties().durability(18)));

	// food
	public static final DeferredItem<Item> RADISH_ITEM = ITEMS.register("radish",
			() -> new Item(new Item.Properties().stacksTo(67).food(ModFoodProps.RADISH)) {
				@Override
				public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents,
						TooltipFlag tooltipFlag) {
					tooltipComponents.add(Component.translatable("tooltip.moregenerators.radish"));

				}
			});

	// fuel
	public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.register("frostfire_ice", () -> new FuelItem(new Item.Properties().stacksTo(67), 2000000));
	public static final DeferredItem<Item> STARLIGHT_ASHES = ITEMS.register("starlight_ashes", () -> new Item(new Item.Properties().stacksTo(67)));

	public static void register(IEventBus eventBus) {
		CreativeTabs.addItemToTab(BISMUTH, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(RAW_BISMUTH, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(CHISEL_ITEM, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(RADISH_ITEM, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(FROSTFIRE_ICE, CreativeTabs.CREATIVE_TABS[0]);
		CreativeTabs.addItemToTab(STARLIGHT_ASHES, CreativeTabs.CREATIVE_TABS[0]);

		ITEMS.register(eventBus);
	}

	public static void registerCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ModItems.BISMUTH);
			event.accept(ModItems.RAW_BISMUTH);
		}
	}
}
