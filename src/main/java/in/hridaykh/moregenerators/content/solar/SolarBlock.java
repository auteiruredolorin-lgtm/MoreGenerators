package in.hridaykh.moregenerators.content.solar;

import com.simibubi.create.foundation.block.IBE;

import in.hridaykh.moregenerators.ModLang;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

import org.patryk3211.powergrid.electricity.base.HorizontalAxisElectricBlock;
import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.utility.Lang;
import org.patryk3211.powergrid.utility.Unit;

@MethodsReturnNonnullByDefault
public class SolarBlock extends HorizontalAxisElectricBlock implements IBE<SolarBE>, IHaveElectricProperties {
	public static final Property<Direction.Axis> HORIZONTAL_AXIS;
	private static final VoxelShape SHAPE;
	private static final TerminalBoundingBox[] TERMINALS;

	static {
		HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

		// Centered on the 16x16 grid (0.0 to 16.0)
		SHAPE = Shapes.or(box(0.0F, 0.0F, 0.0F, 16.0F, 1.0F, 16.0F), box(1.0F, 1.0F, 1.0F, 15.0F, 2.0F, 15.0F));

		// Terminals placed on the Z-axis faces (North/South) relative to the 16x16 grid
		var t1 = new TerminalBoundingBox(IDecoratedTerminal.POSITIVE, 7.0F, 1.0F, 0.0F, 9.0F, 2.0F, 1.0F).withColor(16726843);
		var t2 = new TerminalBoundingBox(IDecoratedTerminal.NEGATIVE, 7.0F, 1.0F, 15.0F, 9.0F, 2.0F, 16.0F).withColor(3899647);

		TERMINALS = new TerminalBoundingBox[] { t1, t2 };
	}

	public SolarBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.setTerminalCollection(horizontalZTerminals(this, TERMINALS, SHAPE));
	}

	public Class<SolarBE> getBlockEntityClass() {
		return SolarBE.class;
	}

	public BlockEntityType<? extends SolarBE> getBlockEntityType() {
		return ModBlockEntities.SOLAR_PANEL_BE.get();
	}

	@Override
	public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
		ModLang.builder().add(Component.nullToEmpty("Peak Power: ")).add(Lang.number(SolarBE.PEAK_POWER)).add(Unit.POWER.get()).addTo(tooltip);
		ModLang.builder().add(Component.nullToEmpty("Internal Resistance: ")).add(Lang.number(SolarBE.INTERNAL_RESISTANCE)).add(Unit.RESISTANCE.get())
				.addTo(tooltip);
	}
}	
