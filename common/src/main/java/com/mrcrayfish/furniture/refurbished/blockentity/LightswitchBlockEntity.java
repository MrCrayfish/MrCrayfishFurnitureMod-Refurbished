package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.ElectricityNetwork;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlockEntity extends ElectricSourceBlockEntity
{
    public LightswitchBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.LIGHTSWITCH.get(), pos, state);
    }

    public void onPowered(boolean powered)
    {
        this.updatePowerInNetwork(powered);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.POWERED) && state.getValue(LightswitchBlock.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(LightswitchBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(LightswitchBlock.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public int hashCode()
    {
        return this.worldPosition.hashCode();
    }
}
