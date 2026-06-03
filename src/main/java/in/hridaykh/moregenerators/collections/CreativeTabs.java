package in.hridaykh.moregenerators.collections;

import in.hridaykh.moregenerators.MoreGenerators;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CreativeTabs {

	public static final CreativeTabName[] CREATIVE_TABS = {new CreativeTabName("more_generators", ModBlocks.LIGHT_RESISTOR)};

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreGenerators.MOD_ID);
	private static final Map<CreativeTabName, List<ItemLike>> ITEMS = new HashMap<>();

	public static void addItemToTab(ItemLike item, CreativeTabName tab) {
		ITEMS.computeIfAbsent(tab, k -> new ArrayList<>()).add(item);
	}

	public static void register(IEventBus eventBus) {
		for (Map.Entry<CreativeTabName, List<ItemLike>> entry : ITEMS.entrySet()) {
			CreativeTabName tabName = entry.getKey();
			List<ItemLike> items = entry.getValue();

			Supplier<ItemStack> icon = () -> new ItemStack(tabName.icon());

			CreativeModeTab.Builder tabBuilder = CreativeModeTab.builder().icon(icon).title(Component.translatable("creativetab." + MoreGenerators.MOD_ID + "." + tabName.name())).displayItems((itemDisplayParams, output) -> items.forEach(output::accept));

			CREATIVE_MODE_TABS.register(tabName.name(), () -> tabBuilder.build());
		}
		CREATIVE_MODE_TABS.register(eventBus);
	}

	public record CreativeTabName(String name, ItemLike icon) {
	}
}
