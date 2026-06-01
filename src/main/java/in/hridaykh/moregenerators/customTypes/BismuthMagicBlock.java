package in.hridaykh.moregenerators.customTypes;

import java.util.List;

import in.hridaykh.moregenerators.MoreGenerators;
import in.hridaykh.moregenerators.collections.ModItems;
import in.hridaykh.moregenerators.collections.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BismuthMagicBlock extends Block {
	public BismuthMagicBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		level.playSound(player, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (!(entity instanceof ItemEntity itemEntity))
			return;

		if (!isValidItem(itemEntity.getItem()))
			return;
		MoreGenerators.LOGGER.debug("Bismuth Magic Block activated by item {}", itemEntity.getItem());
		itemEntity.setItem(new ItemStack(ModItems.BISMUTH.get(), itemEntity.getItem().getCount()));
		level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS);
	}

	private boolean isValidItem(ItemStack itemStack) {
		MoreGenerators.LOGGER.debug("Checking if item {} is valid for Bismuth Magic Block", itemStack);
		return itemStack.is(ModTags.Items.RAW_BISMUTH);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable("tooltip.moregenerators.bismuth_magic_block"));
	}
}
