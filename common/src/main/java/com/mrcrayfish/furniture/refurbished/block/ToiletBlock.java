package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class ToiletBlock extends FurnitureHorizontalBlock
{
    public ToiletBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape seatShape = Block.box(0, 4, 2, 16, 10, 14);
        VoxelShape tankShape = Block.box(11, 10, 2, 16, 17, 14);
        VoxelShape lidShape = Block.box(10, 17, 2, 16, 19, 14);
        VoxelShape supportShape = Block.box(3, 0, 4, 16, 4, 12);
        VoxelShape combinedShape = VoxelShapeHelper.combine(List.of(seatShape, tankShape, lidShape, supportShape));
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(combinedShape, state.getValue(DIRECTION));
        })));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(Seat.sit(player, pos, 0.35, state.getValue(DIRECTION).getOpposite()))
        {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
