package in.hridaykh.moregenerators;

import com.simibubi.create.foundation.block.IBE;
import in.hridaykh.moregenerators.collections.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.patryk3211.powergrid.electricity.info.IHaveElectricProperties;
import org.patryk3211.powergrid.electricity.resistor.AbstractResistorBlock;
import org.patryk3211.powergrid.utility.Lang;
import org.patryk3211.powergrid.utility.Unit;

import java.util.List;

public class Resistor extends AbstractResistorBlock implements IBE<ResistorBE>, IHaveElectricProperties {
	public Resistor(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public Class<ResistorBE> getBlockEntityClass() {
		return ResistorBE.class;
	}

	@Override
	public BlockEntityType<? extends ResistorBE> getBlockEntityType() {
		return ModBlockEntities.RESISTOR_BE.get();
	}

	@Override
	public void appendProperties(ItemStack stack, Player player, List<Component> tooltip) {
		Item var4 = stack.getItem();
		if (var4 instanceof BlockItem blockItem) {
			float power = 100f;
			Lang.translate("tooltip.power.max").style(ChatFormatting.GRAY).addTo(tooltip);
			Lang.builder().add(Component.nullToEmpty(" ")).add(Lang.number((double) power)).add(Component.nullToEmpty(" ")).add(Unit.POWER.get()).style(ChatFormatting.RED).addTo(tooltip);
		}
	}

}
