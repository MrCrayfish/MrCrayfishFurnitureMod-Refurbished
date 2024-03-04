package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class NeoForgeStoveBlockEntity extends StoveBlockEntity
{
    public NeoForgeStoveBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public void onNeighbourChanged()
    {
        super.onNeighbourChanged();
        this.invalidateCapabilities();
    }

    @Override
    public void setBlockState(BlockState state)
    {
        super.setBlockState(state);
        this.invalidateCapabilities();
    }
}
