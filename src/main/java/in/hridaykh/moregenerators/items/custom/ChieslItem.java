package in.hridaykh.moregenerators.items.custom;

import java.util.List;
import java.util.Map;

import in.hridaykh.moregenerators.blocks.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("null")
public class ChieslItem extends Item {

	private static final Map<Block, Block> CHISELABLE_BLOCKS = Map.ofEntries(Map.entry(Blocks.STONE, Blocks.STONE_BRICKS),
			Map.entry(Blocks.END_STONE, Blocks.END_STONE_BRICKS), Map.entry(Blocks.DIRT, Blocks.ROOTED_DIRT),
			Map.entry(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS), Map.entry(Blocks.GRASS_BLOCK, Blocks.DIRT),
			Map.entry(ModBlocks.BISMUTH_ORE.get(), ModBlocks.BISMUTH_BLOCK.get()),
			Map.entry(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModBlocks.BISMUTH_BLOCK.get()));

	public ChieslItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide())
			return InteractionResult.PASS;
		Block block = level.getBlockState(context.getClickedPos()).getBlock();
		if (!CHISELABLE_BLOCKS.containsKey(block))
			return InteractionResult.PASS;

		level.setBlockAndUpdate(context.getClickedPos(), CHISELABLE_BLOCKS.get(block).defaultBlockState());
		context.getItemInHand().hurtAndBreak(1, (ServerLevel) level, context.getPlayer(),
				item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
		level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		if (Screen.hasShiftDown()) {
			tooltipComponents.add(Component.translatable("tooltip.moregenerators.chiesl.shift_down"));
		} else {
			tooltipComponents.add(Component.translatable("tooltip.moregenerators.chiesl"));
		}
	}

}