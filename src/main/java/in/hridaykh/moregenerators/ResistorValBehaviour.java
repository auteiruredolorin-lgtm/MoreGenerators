package in.hridaykh.moregenerators;

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

import java.util.function.Consumer;

public class ResistorValBehaviour extends ScrollValueBehaviour {

	public ResistorValBehaviour(Component label, SmartBlockEntity be, ValueBoxTransform slot, int max) {
		super(label, be, slot);
		this.between(1, max);
		this.withFormatter(String::valueOf);
	}

	@Override
	public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
		ImmutableList<Component> rows = ImmutableList.of(Lang.translateDirect("generic.unit.ohm"));
		ValueSettingsFormatter formatter = new ValueSettingsFormatter(this::formatSettings);
		return new ValueSettingsBoard(this.label, this.max, 10, rows, formatter);
	}

	@Override
	public void setValueSettings(Player player, ValueSettingsBehaviour.ValueSettings valueSetting, boolean ctrlHeld) {
		int value = Math.max(1, valueSetting.value());
		if (!valueSetting.equals(this.getValueSettings()))
			this.playFeedbackSound(this);
		this.setValue(value);
	}

	public void withResistanceCallback(Consumer<Float> resistanceCallback) {
		super.withCallback(i -> resistanceCallback.accept((float) i));
	}

	public MutableComponent formatSettings(ValueSettingsBehaviour.ValueSettings settings) {
		return Component.literal(String.valueOf(settings.value()));
	}

	public double getResistance() {
		return this.value;
	}
}

