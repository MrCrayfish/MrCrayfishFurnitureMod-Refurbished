package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.RangeHoodBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class RangeHoodBlockEntity extends ElectricityModuleBlockEntity
{
    public RangeHoodBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.RANGE_HOOD.get(), pos, state);
    }

    public RangeHoodBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(RangeHoodBlock.POWERED) && state.getValue(RangeHoodBlock.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(RangeHoodBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(RangeHoodBlock.POWERED, powered), Block.UPDATE_ALL);
        }
    }
}
