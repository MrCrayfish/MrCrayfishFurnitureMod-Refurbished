package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.CeilingLightBlock;
import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class CeilingLightBlockEntity extends ElectricModuleBlockEntity
{
    public CeilingLightBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CEILING_LIGHT.get(), pos, state);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(CeilingLightBlock.POWERED) && state.getValue(CeilingLightBlock.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = ModBlocks.CEILING_LIGHT.get().defaultBlockState();
        this.level.setBlock(this.worldPosition, state.setValue(CeilingLightBlock.POWERED, powered), Block.UPDATE_ALL);
    }
}
