package in.hridaykh.moregenerators.collections;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.content.resistor.ResistorBE;
import in.hridaykh.moregenerators.content.solar.SourceBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MoreGenerators.MOD_ID);
	public static final Supplier<BlockEntityType<ResistorBE>> RESISTOR_BE = BLOCK_ENTITIES.register("resistor", () -> BlockEntityType.Builder.of(ResistorBE::new, ModBlocks.LIGHT_RESISTOR.get()).build(null));
	public static final Supplier<BlockEntityType<SourceBE>> SOURCE_BE = BLOCK_ENTITIES.register("source", () -> BlockEntityType.Builder.of(SourceBE::new, ModBlocks.SOURCE_BLOCK.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}




}