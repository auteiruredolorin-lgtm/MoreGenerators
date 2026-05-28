package in.hridaykh.moregenerators;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import in.hridaykh.moregenerators.blocks.ModBlocks;
import in.hridaykh.moregenerators.items.ModItems;
import in.hridaykh.moregenerators.tabs.CreativeTabName;
import in.hridaykh.moregenerators.tabs.CreativeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(MoreGenerators.MOD_ID)
public class MoreGenerators {
	public static final String MOD_ID = "moregenerators";
	public static final Logger LOGGER = LogUtils.getLogger();

	public MoreGenerators(IEventBus modEventBus, ModContainer modContainer) {
		NeoForge.EVENT_BUS.register(this);

		// ALWAYS REGISTER CREATIVE MODE TABS LAST
		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		CreativeTabs.register(modEventBus);

		modEventBus.addListener(ModItems::registerCreativeModeTabContents);
		modEventBus.addListener(ModBlocks::registerCreativeModeTabContents);
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
	}
}
