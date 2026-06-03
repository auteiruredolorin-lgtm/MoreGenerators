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
public class SourceBlock extends HorizontalAxisElectricBlock implements IBE<SourceBE> {
	public static final Property<Direction.Axis> HORIZONTAL_AXIS;
	private static final VoxelShape SHAPE;
	private static final TerminalBoundingBox[] TERMINALS;

	static {
		HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
		SHAPE = Shapes.or(box(0.0F, 0.0F, 0.0F, 16.0F, 2.0F, 16.0F), box(1.0F, 2.0F, 1.0F, 15.0F, 13.0F, 15.0F));
		TERMINALS = new TerminalBoundingBox[]{(new TerminalBoundingBox(IDecoratedTerminal.POSITIVE, 6.0F, 13.0F, 2.0F, 10.0F, 16.0F, 6.0F)).withColor(16726843), (new TerminalBoundingBox(IDecoratedTerminal.NEGATIVE, 6.0F, 13.0F, 10.0F, 10.0F, 16.0F, 14.0F)).withColor(3899647)};
	}

	public SourceBlock(BlockBehaviour.Properties settings) {
		super(settings);
		this.setTerminalCollection(horizontalZTerminals(this, TERMINALS, SHAPE));
	}

	public Class<SourceBE> getBlockEntityClass() {
		return SourceBE.class;
	}

	public BlockEntityType<? extends SourceBE> getBlockEntityType() {
		return ModBlockEntities.SOURCE_BE.get();
	}
}
