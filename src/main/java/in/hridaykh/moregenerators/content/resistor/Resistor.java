package in.hridaykh.moregenerators.content.resistor;

import com.simibubi.create.foundation.block.IBE;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.electricity.resistor.AbstractResistorBlock;
import org.patryk3211.powergrid.utility.Lang;
import org.patryk3211.powergrid.utility.Unit;

import java.util.List;

public class Resistor extends AbstractResistorBlock implements IBE<ResistorBE>, IHaveElectricProperties {

	public static final IntegerProperty LIGHT_LEVEL = BlockStateProperties.LEVEL;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public Resistor(BlockBehaviour.Properties settings) {
		super(settings.lightLevel(state -> state.getValue(LIT) ? state.getValue(LIGHT_LEVEL) : 0));

		this.registerDefaultState(this.stateDefinition.any()
			.setValue(LIGHT_LEVEL, 0)
			.setValue(LIT, false)
		);
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
		Item var4 = stack.getItem();
		if (var4 instanceof BlockItem blockItem) {
			Lang.translate("tooltip.power.max").style(ChatFormatting.GRAY).addTo(tooltip);
			Lang.builder().add(Component.nullToEmpty(" ")).add(Lang.number(15d * ResistorBE.powerMultiplier)).add(Component.nullToEmpty(" ")).add(Unit.POWER.get()).style(ChatFormatting.RED).addTo(tooltip);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIGHT_LEVEL, LIT);
	}

}
