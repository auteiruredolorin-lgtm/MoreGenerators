package in.hridaykh.moregenerators.collections;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import in.hridaykh.moregenerators.MoreGenerators;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = MoreGenerators.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModPartialModels {

	public static final PartialModel LV_MODEL_OFF = partial("block/lamps/lv_light_bulb");
	public static final PartialModel LV_MODEL_ON = partial("block/lamps/lv_light_bulb_on");
	public static final PartialModel LV_MODEL_BROKEN = partial("block/lamps/lv_light_bulb_broken");
	public static final PartialModel LV_MODEL_LIGHT = partial("block/lamps/lv_light_bulb_light");

	public static final PartialModel LV_DYED_MODEL_OFF = partial("block/lamps/lv_dyed_light_bulb");
	public static final PartialModel LV_DYED_MODEL_ON = partial("block/lamps/lv_dyed_light_bulb_on");
	public static final PartialModel LV_DYED_MODEL_BROKEN = partial("block/lamps/lv_dyed_light_bulb_broken");
	public static final PartialModel LV_DYED_MODEL_LIGHT = partial("block/lamps/lv_dyed_light_bulb_light");
	public static final PartialModel LV_DYED_MODEL_BULB = partial("block/lamps/lv_dyed_light_bulb_bulb");

	public static final PartialModel MODEL_OFF = partial("block/lamps/light_bulb");
	public static final PartialModel MODEL_ON = partial("block/lamps/light_bulb_on");
	public static final PartialModel MODEL_BROKEN = partial("block/lamps/light_bulb_broken");
	public static final PartialModel MODEL_LIGHT = partial("block/lamps/light_bulb_light");

	public static final PartialModel DYED_MODEL_OFF = partial("block/lamps/dyed_light_bulb");
	public static final PartialModel DYED_MODEL_ON = partial("block/lamps/dyed_light_bulb_on");
	public static final PartialModel DYED_MODEL_BROKEN = partial("block/lamps/dyed_light_bulb_broken");
	public static final PartialModel DYED_MODEL_LIGHT = partial("block/lamps/dyed_light_bulb_light");
	public static final PartialModel DYED_MODEL_BULB = partial("block/lamps/dyed_light_bulb_bulb");

	private static PartialModel partial(String path) {
		return PartialModel.of(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, path));
	}

	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional event) {
		// Register LV Bulb Models
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_light_bulb")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_light_bulb_on")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_light_bulb_broken")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_light_bulb_light")));

		// Register LV Dyed Models
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_dyed_light_bulb")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_dyed_light_bulb_on")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_dyed_light_bulb_broken")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_dyed_light_bulb_light")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/lv_dyed_light_bulb_bulb")));

		// Register Standard Bulb Models
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/light_bulb")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/light_bulb_on")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/light_bulb_broken")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/light_bulb_light")));

		// Register Standard Dyed Models
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/dyed_light_bulb")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/dyed_light_bulb_on")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/dyed_light_bulb_broken")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/dyed_light_bulb_light")));
		event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, "block/lamps/dyed_light_bulb_bulb")));
	}
}