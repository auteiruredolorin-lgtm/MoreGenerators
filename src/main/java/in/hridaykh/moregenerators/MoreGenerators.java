package in.hridaykh.moregenerators;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;

import in.hridaykh.moregenerators.collections.CreativeTabs;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import in.hridaykh.moregenerators.collections.ModBlocks;
import in.hridaykh.moregenerators.collections.ModItems;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
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
		LOGGER.info("HELLO from MoreGenerators");
	}

	@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemDescription.Modifier solarPanelModifier = new ItemDescription.Modifier(ModBlocks.SOLAR_PANEL.asItem(), Palette.STANDARD_CREATE);
				TooltipModifier.REGISTRY.register(ModBlocks.SOLAR_PANEL.get().asItem(), solarPanelModifier);
			});
		}

		@SubscribeEvent
		public static void onItemTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (!(stack.getItem() instanceof BlockItem blockItem))
				return;
			Block block = blockItem.getBlock();
			if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MOD_ID))
				return;
			if (!(block instanceof IHaveElectricProperties tooltipBlock))
				return;
			tooltipBlock.appendProperties(stack, event.getEntity(), event.getToolTip());
		}
	}

}
