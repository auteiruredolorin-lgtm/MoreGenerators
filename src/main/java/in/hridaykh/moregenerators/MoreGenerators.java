package in.hridaykh.moregenerators;

import com.mojang.logging.LogUtils;
import in.hridaykh.moregenerators.collections.CreativeTabs;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(MoreGenerators.MOD_ID)
public class MoreGenerators {
	public static final String MOD_ID = "moregenerators";
	public static final Logger LOGGER = LogUtils.getLogger();

	public MoreGenerators(IEventBus modEventBus, ModContainer modContainer) {
		NeoForge.EVENT_BUS.register(this);

		// ALWAYS REGISTER CREATIVE MODE TABS LAST
		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModBlockEntities.register(modEventBus);
		CreativeTabs.register(modEventBus);

		if (org.patryk3211.powergrid.PowerGrid.REGISTRATE != null)
			org.patryk3211.powergrid.PowerGrid.REGISTRATE.registerEventListeners(modEventBus);

	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		LOGGER.info("HELLO from server starting");
	}

	@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLCommonSetupEvent event) {
		}

		@SubscribeEvent
		public static void onItemTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.is(ModBlocks.RESISTOR.asItem()))
				ModBlocks.RESISTOR.get().appendProperties(stack, event.getEntity(), event.getToolTip());
		}
	}


}
