package in.hridaykh.moregenerators.content.solar;

import org.patryk3211.powergrid.electricity.battery.MultiBlockBatteryEntity;

import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomMultiBlockEntity implements IMultiBlockEntityContainer {

	public CustomMultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
	}

	@Override
	public BlockPos getController() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getController'");
	}

	@Override
	public <T extends BlockEntity & IMultiBlockEntityContainer> T getControllerBE() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getControllerBE'");
	}

	@Override
	public boolean isController() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isController'");
	}

	@Override
	public void setController(BlockPos pos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setController'");
	}

	@Override
	public void removeController(boolean keepContents) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeController'");
	}

	@Override
	public BlockPos getLastKnownPos() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getLastKnownPos'");
	}

	@Override
	public void preventConnectivityUpdate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'preventConnectivityUpdate'");
	}

	@Override
	public void notifyMultiUpdated() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'notifyMultiUpdated'");
	}

	@Override
	public Axis getMainConnectionAxis() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMainConnectionAxis'");
	}

	@Override
	public int getMaxLength(Axis longAxis, int width) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMaxLength'");
	}

	@Override
	public int getMaxWidth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMaxWidth'");
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getHeight'");
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setHeight'");
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getWidth'");
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setWidth'");
	}

	// This method defines what happens when a block is added or removed from the cluster
	// @Override
	// protected void updateConnectivity() {
	// if (level.isClientSide)
	// return;
	// // Create provides built-in search logic.
	// // You can use Create's ConnectivityHandler helper to discover neighboring blocks of the same type.
	// }

	// // Define what data or capabilities are bound to the Controller (Master) block
	// @Override
	// public boolean isController() {
	// return getController() == this.getBlockPos();
	// }
}