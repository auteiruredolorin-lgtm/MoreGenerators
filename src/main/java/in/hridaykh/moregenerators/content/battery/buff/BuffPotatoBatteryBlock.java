package in.hridaykh.moregenerators.content.battery.buff;

import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.patryk3211.powergrid.electricity.base.HorizontalElectricBlock;
import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.battery.AbstractBatteryBlock;
import org.patryk3211.powergrid.electricity.battery.BatterySpec;
import org.patryk3211.powergrid.electricity.battery.SimpleBatterySpec;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.utility.Lang;
import org.patryk3211.powergrid.utility.Unit;

import in.hridaykh.moregenerators.ModLang;
import in.hridaykh.moregenerators.collections.ModBlockEntities;

public class BuffPotatoBatteryBlock extends AbstractBatteryBlock<BuffPotatoBatteryBE> implements IHaveElectricProperties {
	// 30 joules, can power a 3w bulb for 10s
	public static final BatterySpec BATTERY_SPEC = new SimpleBatterySpec(180f, 180f, e -> 12f, e -> 0f);

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final TerminalBoundingBox[] TERMINALS_NORTH = new TerminalBoundingBox[] {
			new TerminalBoundingBox(IDecoratedTerminal.POSITIVE, 7, 3, 4.5, 9, 5.5, 6).withColor(IDecoratedTerminal.RED),
			new TerminalBoundingBox(IDecoratedTerminal.NEGATIVE, 7, 3, 10, 9, 5.5, 11.5).withColor(IDecoratedTerminal.BLUE) };

	private static final VoxelShape SHAPE_NORTH = box(6, 0, 5, 10, 3, 11);

	public BuffPotatoBatteryBlock(Properties settings) {
		super(settings);
		setTerminalCollection(HorizontalElectricBlock.horizontalNorthTerminals(this, TERMINALS_NORTH, SHAPE_NORTH));
	}

	@Override
	public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
		ModLang.builder().add(Lang.number(3)).add(Component.nullToEmpty(" ")).add(Unit.POWER.get()).addTo(tooltip);
		ModLang.builder().add(Lang.number(12)).add(Component.nullToEmpty(" ")).add(Unit.VOLTAGE.get()).addTo(tooltip);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
		@SuppressWarnings("null")
		var player = ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown() ? ctx.getHorizontalDirection()
				: ctx.getHorizontalDirection().getOpposite();
		return defaultBlockState().setValue(HORIZONTAL_FACING, player.getClockWise());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSupportCenter(world, pos.below(), Direction.UP);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos,
			BlockPos neighborPos) {
		return direction == Direction.DOWN && !canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public BatterySpec getSpec() {
		return BATTERY_SPEC;
	}

	@Override
	public Class<BuffPotatoBatteryBE> getBlockEntityClass() {
		return BuffPotatoBatteryBE.class;
	}

	@Override
	public BlockEntityType<? extends BuffPotatoBatteryBE> getBlockEntityType() {
		return ModBlockEntities.BUFF_POTATO_BATTERY_BE.get();
	}
}