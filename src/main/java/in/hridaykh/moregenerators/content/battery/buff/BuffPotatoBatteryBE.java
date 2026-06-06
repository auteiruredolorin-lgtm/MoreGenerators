package in.hridaykh.moregenerators.content.battery.buff;

import org.jetbrains.annotations.Nullable;
import org.patryk3211.powergrid.electricity.base.ThermalBehaviour;
import org.patryk3211.powergrid.electricity.battery.BatteryBlockEntity;

import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BuffPotatoBatteryBE extends BatteryBlockEntity {
	public BuffPotatoBatteryBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BUFF_POTATO_BATTERY_BE.get(), pos, state);
	}

	@Override
	public @Nullable ThermalBehaviour specifyThermalBehaviour() {
		var b = ThermalBehaviour.forMaxPower(this, 0.2f, 3f);
		if (b != null)
			b.behaviourFlags(ThermalBehaviour.OVERHEAT_PARTICLES);
		return b;
	}

	@Override
	public float calculatePower() {
		// No recharging
		return Math.max(super.calculatePower(), 0);
	}

	@Override
	public void electricalTick() {
		super.electricalTick();
		// if (thermalBehaviour != null && thermalBehaviour.isOverheated() && !level.isClientSide) {
		// 	level.setBlockAndUpdate(worldPosition, getBlockState().setValue(PotatoBatteryBlock.BAKED, true));
		// 	notifyUpdate();
		// }
	}
}
