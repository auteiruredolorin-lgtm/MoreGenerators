package in.hridaykh.moregenerators.content.solar;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import in.hridaykh.moregenerators.ModLang;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.node.FloatingNode;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;
import org.patryk3211.powergrid.utility.Unit;

import java.util.List;

public class SolarBE extends ElectricBlockEntity implements IHaveGoggleInformation {
	public static final float PEAK_POWER = 200.0f;
	public static final float INTERNAL_RESISTANCE = 1f;
	public static final float PEAK_VOLTAGE = Mth.sqrt(PEAK_POWER * INTERNAL_RESISTANCE);

	private VoltageSourceCoupling voltageSourceCoupling;
	private boolean overwrite = false;

	public SolarBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SOLAR_PANEL_BE.get(), pos, state);
	}

	public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
		builder.setTerminalCount(2);
		FloatingNode positive = builder.terminalNode(0);
		FloatingNode negative = builder.terminalNode(1);

		this.voltageSourceCoupling = builder.addInternalNode(VoltageSourceCoupling.class, positive, negative, INTERNAL_RESISTANCE);
	}

	@SuppressWarnings("null")
	@Override
	public void electricalTick() {
		super.electricalTick();
		if (this.level == null || this.level.isClientSide) {
			this.voltageSourceCoupling.setVoltage(0);
			return;
		}
		int baseLight = this.level.getBrightness(LightLayer.SKY, this.worldPosition) - this.level.getSkyDarken();
		float sunIntensity = Math.max(0.0F, baseLight * Mth.cos(this.level.getSunAngle(1.0F)) / 15);
		this.voltageSourceCoupling.setVoltage(PEAK_VOLTAGE * sunIntensity);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		var terminalVolt = this.voltageSourceCoupling.getVoltage() - this.voltageSourceCoupling.getCurrent() * INTERNAL_RESISTANCE;
		ModLang.builder().text(String.format("%.2f", terminalVolt)).add(Component.nullToEmpty(" ")).add(Unit.VOLTAGE.get()).forGoggles(tooltip, 1);
		ModLang.builder().text(String.format("%.2f", -this.voltageSourceCoupling.getCurrent())).add(Component.nullToEmpty(" ")).add(Unit.CURRENT.get())
				.forGoggles(tooltip, 1);
		return true;
	}

	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		if (tag.contains("Overwrite"))
			this.overwrite = tag.getBoolean("Overwrite");
		this.voltageSourceCoupling.setVoltage(tag.getFloat("NodeValue"));
	}

	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		if (this.overwrite)
			tag.putBoolean("Overwrite", true);
		tag.putFloat("NodeValue", (float) this.voltageSourceCoupling.getVoltage());
	}

	public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
		super.writeSafe(tag, registries);
		if (this.overwrite)
			tag.putBoolean("Overwrite", true);
		tag.putFloat("NodeValue", (float) this.voltageSourceCoupling.getVoltage());
	}

}
