package in.hridaykh.moregenerators.content.resistor;

import com.simibubi.create.foundation.block.IBE;

import in.hridaykh.moregenerators.ModLang;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.electricity.resistor.AbstractResistorBlock;
import org.patryk3211.powergrid.utility.*;

import java.util.List;

public class Resistor extends AbstractResistorBlock implements IBE<ResistorBE>, IHaveElectricProperties {

	public static final IntegerProperty LIGHT_LEVEL = BlockStateProperties.LEVEL;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public Resistor(BlockBehaviour.Properties settings) {
		super(settings.lightLevel(state -> state.getValue(LIT) ? state.getValue(LIGHT_LEVEL) : 0));

		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT_LEVEL, 0).setValue(LIT, false));
	}

	@Override
	public Class<ResistorBE> getBlockEntityClass() {
		return ResistorBE.class;
	}

	@Override
	public BlockEntityType<? extends ResistorBE> getBlockEntityType() {
		return ModBlockEntities.RESISTOR_BE.get();
	}

	@Override
	public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
		ModLang.builder().add(Lang.number(15d * ResistorBE.powerMultiplier)).add(Component.nullToEmpty(" ")).add(Unit.POWER.get()).addTo(tooltip);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIGHT_LEVEL, LIT);
	}

}
