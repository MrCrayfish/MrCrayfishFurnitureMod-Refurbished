package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityGeneratorBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public TelevisionBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(2, 0, 2, 14, 12, 14);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> baseShape)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new TelevisionBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, ModBlockEntities.TELEVISION.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends TelevisionBlockEntity> generator)
    {
        if(level.isClientSide())
        {
            return createTickerHelper(type, generator, TelevisionBlockEntity::clientTick);
        }
        return null;
    }

    public static int light(BlockState state)
    {
        return 8;
    }
}
