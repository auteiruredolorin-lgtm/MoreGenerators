package in.hridaykh.moregenerators;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.resistor.ResistorBoxTransform;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;
import org.patryk3211.powergrid.utility.Lang;

import java.util.List;

public class ResistorBE extends ElectricBlockEntity {
	protected ResistorValBehaviour value;
	protected ElectricWire wire;

	private double loadedResistance = 1.0;

	public ResistorBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.RESISTOR_BE.get(), pos, state);
	}

	protected ResistorValBehaviour makeScroll() {
		return new ResistorValBehaviour(Lang.translateDirect("devices.resistor.resistance"), this, new ResistorBoxTransform(), 45);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		this.value = this.makeScroll();

		this.value.withResistanceCallback(R -> {
			double target = (double) R;
			if (target <= 0.0001 || !Double.isFinite(target))
				target = 1.0;

			if (this.wire != null)
				this.wire.setResistance(target);

			this.notifyUpdate();
		});

		behaviours.add(this.value);
	}

	@Override
	public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
		builder.setTerminalCount(2);
		this.wire = builder.connect(0.1F, builder.terminalNode(0), builder.terminalNode(1));

		double targetResistance = this.loadedResistance;
		if (this.value != null)
			targetResistance = this.value.getResistance();

		if (targetResistance <= 0.0)
			targetResistance = 1.0;

		this.wire.setResistance(targetResistance);
	}

	@Override
	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		if (tag.contains("ScrollValue"))
			this.loadedResistance = Math.max(tag.getDouble("ScrollValue"), 0.0001f);
		else
			this.loadedResistance = 0.0001f;
	}

	@Override
	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		double currentRes = this.value != null ? (double) this.value.getResistance() : this.loadedResistance;
		tag.putDouble("ScrollValue", currentRes);
	}

	public void electricalTick() {
		if (this.wire != null) this.applyPower(this.wire);
	}
}