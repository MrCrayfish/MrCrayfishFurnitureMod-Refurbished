package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.block.TelevisionBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class TelevisionRemoteItem extends Item
{
    public TelevisionRemoteItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        // Perform a raycast for blocks
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(16));
        BlockHitResult result = level.clip(new ClipContext(start, end, ClipContext.Block.VISUAL, ClipContext.Fluid.ANY, player));
        if(result.getType() != HitResult.Type.BLOCK)
            return InteractionResultHolder.fail(stack);

        // Check if the hit block is a television
        BlockPos pos = result.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if(!state.is(ModBlocks.TELEVISION.get()))
            return InteractionResultHolder.fail(stack);

        // Check if the hit face is the front of the television
        Direction direction = state.getValue(TelevisionBlock.DIRECTION);
        if(direction.getOpposite() != result.getDirection())
            return InteractionResultHolder.fail(stack);

        // Interact with the tv if server side
        if(!level.isClientSide())
        {
            if(level.getBlockEntity(pos) instanceof TelevisionBlockEntity television)
            {
                television.interact();
            }
        }
        return InteractionResultHolder.consume(stack);
    }
}
