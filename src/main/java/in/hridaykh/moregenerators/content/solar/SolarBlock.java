package in.hridaykh.moregenerators.content.solar;

import com.simibubi.create.foundation.block.IBE;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.patryk3211.powergrid.electricity.base.HorizontalAxisElectricBlock;
import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;

@MethodsReturnNonnullByDefault
public class SolarBlock extends HorizontalAxisElectricBlock implements IBE<SolarBE> {
	public static final Property<Direction.Axis> HORIZONTAL_AXIS;
	private static final VoxelShape SHAPE;
	private static final TerminalBoundingBox[] TERMINALS;

	static {
		HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
		SHAPE = Shapes.or(box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F));
		var t1 = new TerminalBoundingBox(IDecoratedTerminal.POSITIVE, 6.0F, 13.0F, 2.0F, 10.0F, 16.0F, 6.0F).withColor(16726843);
		var t2 = new TerminalBoundingBox(IDecoratedTerminal.NEGATIVE, 6.0F, 13.0F, 10.0F, 10.0F, 16.0F, 14.0F).withColor(3899647);
		TERMINALS = new TerminalBoundingBox[]{t1, t2};
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
}
