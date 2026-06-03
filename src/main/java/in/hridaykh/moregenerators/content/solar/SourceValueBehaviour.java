package in.hridaykh.moregenerators.content.solar;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.patryk3211.powergrid.utility.Lang;

public class SourceValueBehaviour extends ScrollValueBehaviour {
	private final float multiplier;

	public SourceValueBehaviour(Component label, SmartBlockEntity be, float multiplier, ValueBoxTransform slot) {
		super(label, be, slot);
		this.multiplier = multiplier;
		this.between(-250, 250);
		this.withFormatter((i) -> String.format("%.1f", (float)Math.abs(i) * multiplier));
	}

	public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
		ImmutableList<Component> rows = ImmutableList.of(Component.literal("+"), Component.literal("-"));
		ValueSettingsFormatter formatter = new ValueSettingsFormatter(this::formatSettings);
		return new ValueSettingsBoard(this.label, 250, 20, rows, formatter);
	}

	public void setValueSettings(Player player, ValueSettingsBehaviour.ValueSettings valueSetting, boolean ctrlHeld) {
		int value = Math.max(0, valueSetting.value());
		if (!valueSetting.equals(this.getValueSettings())) {
			this.playFeedbackSound(this);
		}

		this.setValue(valueSetting.row() == 0 ? value : -value);
	}

	public ValueSettingsBehaviour.ValueSettings getValueSettings() {
		return new ValueSettingsBehaviour.ValueSettings(this.value < 0 ? 1 : 0, Math.abs(this.value));
	}

	public MutableComponent formatSettings(ValueSettingsBehaviour.ValueSettings settings) {
		return Lang.number(Math.max(0.0F, Math.abs((float)settings.value() * this.multiplier))).component();
	}
}
