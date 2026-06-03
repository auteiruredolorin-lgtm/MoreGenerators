package in.hridaykh.moregenerators.content.solar;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.patryk3211.powergrid.collections.ModdedBlocks;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.node.CurrentSourceNode;
import org.patryk3211.powergrid.electricity.sim.node.FloatingNode;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;
import org.patryk3211.powergrid.utility.Lang;
import org.patryk3211.powergrid.utility.Unit;

import java.util.List;

public class SourceBE extends ElectricBlockEntity implements IHaveGoggleInformation {
	private CurrentSourceNode currentSourceNode;
	private VoltageSourceCoupling voltageSourceNode;
	private boolean overwrite = false;
	private boolean voltageSource;

	public SourceBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SOURCE_BE.get(), pos, state);
	}

	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		super.addBehaviours(behaviours);
		Component label = null;
		float multiplier;
		if (this.getBlockState().is(ModdedBlocks.CREATIVE_VOLTAGE_SOURCE.get())) {
			label = Lang.translateDirect("devices.creative.voltage");
			multiplier = 1.0F;
		} else if (this.getBlockState().is(ModdedBlocks.CREATIVE_CURRENT_SOURCE.get())) {
			label = Lang.translateDirect("devices.creative.current");
			multiplier = 0.1F;
		} else {
			multiplier = 0.0F;
		}

		var value = new SourceValueBehaviour(label, this, multiplier, new SourceBoxTransform());
		value.withCallback((i) -> {
			if (!this.overwrite) {
				this.setValue((float) i * multiplier);
			}

		});
		behaviours.add(value);
	}

	public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
		builder.setTerminalCount(2);
		FloatingNode positive = builder.terminalNode(0);
		FloatingNode negative = builder.terminalNode(1);
		if (this.getBlockState().is(ModdedBlocks.CREATIVE_VOLTAGE_SOURCE.get())) {
			this.voltageSource = true;
			this.voltageSourceNode = builder.addInternalNode(VoltageSourceCoupling.class, positive, negative, 1.0E-4F);
		} else {
			if (!this.getBlockState().is(ModdedBlocks.CREATIVE_CURRENT_SOURCE.get())) {
				throw new IllegalArgumentException();
			}

			this.voltageSource = false;
			this.currentSourceNode = builder.addInternalNode(CurrentSourceNode.class);
			builder.couple(1.0F, 1.0E-4F, this.currentSourceNode, positive, negative);
		}

	}

	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		if (tag.contains("Overwrite")) {
			this.overwrite = tag.getBoolean("Overwrite");
		}

		this.setValue(tag.getFloat("NodeValue"));
	}

	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		if (this.overwrite) {
			tag.putBoolean("Overwrite", true);
		}

		tag.putFloat("NodeValue", this.getValue());
	}

	public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
		super.writeSafe(tag, registries);
		if (this.overwrite) {
			tag.putBoolean("Overwrite", true);
		}

		tag.putFloat("NodeValue", this.getValue());
	}

	public float getValue() {
		return this.voltageSource ? (float) this.voltageSourceNode.getVoltage() : (float) this.currentSourceNode.getCurrent();
	}

	public void setValue(float value) {
		if (this.voltageSource) {
			this.voltageSourceNode.setVoltage(value);
		} else {
			this.currentSourceNode.setCurrent(value);
		}

	}

	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		Lang.translate("gui.creative_source.info_header").forGoggles(tooltip);
		Lang.builder().translate("gui.creative_source.voltage").style(ChatFormatting.GRAY).forGoggles(tooltip);
		double voltage = this.voltageSource ? this.voltageSourceNode.getVoltage() : this.currentSourceNode.getVoltage();
		String voltageText = String.format("%.2f", voltage);
		Lang.builder().text(voltageText).add(Component.nullToEmpty(" ")).add(Unit.VOLTAGE.get()).style(ChatFormatting.BLUE).forGoggles(tooltip, 1);
		Lang.builder().translate("gui.creative_source.current").style(ChatFormatting.GRAY).forGoggles(tooltip);
		double current = this.voltageSource ? -this.voltageSourceNode.getCurrent() : this.currentSourceNode.getCurrent();
		String currentText = String.format("%.2f", current);
		Lang.builder().text(currentText).add(Component.nullToEmpty(" ")).add(Unit.CURRENT.get()).style(ChatFormatting.GREEN).forGoggles(tooltip, 1);
		return true;
	}


	public static class SourceBoxTransform extends CenteredSideValueBoxTransform {
		public SourceBoxTransform() {
			super((state, dir) -> dir.getAxis() != Direction.Axis.Y);
		}

		protected Vec3 getSouthLocation() {
			return VecHelper.voxelSpace(8.0F, 8.0F, 14.5F);
		}
	}

}
