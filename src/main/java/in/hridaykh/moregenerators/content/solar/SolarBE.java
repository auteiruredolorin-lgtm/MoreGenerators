package in.hridaykh.moregenerators.content.solar;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import in.hridaykh.moregenerators.ModLang;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.node.FloatingNode;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;
import org.patryk3211.powergrid.utility.Unit;

import java.util.List;

public class SolarBE extends ElectricBlockEntity implements IHaveGoggleInformation {
	private static final double PEAK_VOLTAGE = 25.0;
	private static final float INTERNAL_RESISTANCE = 2.5f;

	private VoltageSourceCoupling voltageSourceCoupling;
	private boolean overwrite = false;

	public SolarBE(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SOLAR_PANEL_BE.get(), pos, state);
	}

	private static int sunIntensity(long time) {
		long dayTime = time % 24000;

		// 2. Calculate the sun's angle in radians.
		// Minecraft's day begins at tick 0, but the sun rises at tick 22000.
		// Subtracting 6000 offsets the calculation so peak noon is at tick 6000.
		double angle = Math.toRadians((dayTime - 6000) * 360.0 / 24000.0);

		// 3. Use a cosine wave to calculate the height of the sun.
		// Scale it up by 15 (max power) and add a small offset (+0.5) to mimic
		// the game's rounding transitions seen in your stair-step graph.
		int power = (int) Math.round(15.0 * Math.cos(angle) + 0.5);

		// 4. Clamp the values between 0 and 15 so it never goes out of bounds
		return Math.clamp(power, 0, 15);
	}

	public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
		builder.setTerminalCount(2);
		FloatingNode positive = builder.terminalNode(0);
		FloatingNode negative = builder.terminalNode(1);

		this.voltageSourceCoupling = builder.addInternalNode(VoltageSourceCoupling.class, positive, negative, INTERNAL_RESISTANCE);
	}

	@Override
	public void electricalTick() {
		super.electricalTick();
		if (this.level == null || this.level.isClientSide) return;

		long timeOfDay = this.level.dayTime() % 24000L;

		double targetVoltage = PEAK_VOLTAGE * ((double) sunIntensity(timeOfDay) / 15);

		this.voltageSourceCoupling.setVoltage(targetVoltage);
	}

	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		ModLang.builder().translate("gui.solar_panel.emf").style(ChatFormatting.GRAY).forGoggles(tooltip);
		double emf = this.voltageSourceCoupling.getVoltage() - Math.abs(this.voltageSourceCoupling.getCurrent()) * INTERNAL_RESISTANCE;
		ModLang.builder().text(String.format("%.2f", emf)).add(Component.nullToEmpty(" ")).add(Unit.VOLTAGE.get()).style(ChatFormatting.BLUE).forGoggles(tooltip, 1);

		ModLang.builder().translate("gui.solar_panel.voltage").style(ChatFormatting.GRAY).forGoggles(tooltip);
		ModLang.builder().text(String.format("%.2f", this.voltageSourceCoupling.getVoltage())).add(Component.nullToEmpty(" ")).add(Unit.VOLTAGE.get()).style(ChatFormatting.BLUE).forGoggles(tooltip, 1);

		ModLang.builder().translate("gui.solar_panel.current").style(ChatFormatting.GRAY).forGoggles(tooltip);
		ModLang.builder().text(String.format("%.2f", -this.voltageSourceCoupling.getCurrent())).add(Component.nullToEmpty(" ")).add(Unit.CURRENT.get()).style(ChatFormatting.GREEN).forGoggles(tooltip, 1);

		return true;
	}

	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		if (tag.contains("Overwrite")) this.overwrite = tag.getBoolean("Overwrite");
		this.voltageSourceCoupling.setVoltage(tag.getFloat("NodeValue"));
	}

	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		if (this.overwrite) tag.putBoolean("Overwrite", true);
		tag.putFloat("NodeValue", (float) this.voltageSourceCoupling.getVoltage());
	}

	public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
		super.writeSafe(tag, registries);
		if (this.overwrite) tag.putBoolean("Overwrite", true);
		tag.putFloat("NodeValue", (float) this.voltageSourceCoupling.getVoltage());
	}

}
