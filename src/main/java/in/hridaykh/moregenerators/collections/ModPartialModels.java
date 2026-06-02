package in.hridaykh.moregenerators.collections;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import in.hridaykh.moregenerators.MoreGenerators;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = MoreGenerators.MOD_ID, value = Dist.CLIENT)
public class ModPartialModels {

	private static final List<String> REGISTERED_PARTIALS_PATHS = new ArrayList<>();

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
		REGISTERED_PARTIALS_PATHS.add(path);
		return PartialModel.of(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, path));
	}

	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional event) {
		for (String partialPath : REGISTERED_PARTIALS_PATHS)
			event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MoreGenerators.MOD_ID, partialPath)));
	}

}