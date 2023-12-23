package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.CeilingFanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.WorkbenchBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class WorkbenchBlock extends FurnitureBlock implements EntityBlock
{
    public WorkbenchBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> Shapes.block())));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && level.getBlockEntity(pos) instanceof WorkbenchBlockEntity workbench)
        {
            if(!workbench.isOccupied())
            {
                player.openMenu(workbench);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new WorkbenchBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, ModBlockEntities.WORKBENCH.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends WorkbenchBlockEntity> workbench)
    {
        if(!level.isClientSide())
        {
            return createTickerHelper(type, workbench, WorkbenchBlockEntity::serverTick);
        }
        return null;
    }
}
