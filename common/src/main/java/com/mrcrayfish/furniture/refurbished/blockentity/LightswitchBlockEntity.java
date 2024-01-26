package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlockEntity extends ElectricityModuleBlockEntity implements IHomeControlDevice
{
    public LightswitchBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.LIGHTSWITCH.get(), pos, state);
    }

    @Override
    public boolean canPowerTraverse()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.ENABLED) && state.getValue(LightswitchBlock.ENABLED);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.POWERED) && state.getValue(LightswitchBlock.POWERED);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(LightswitchBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(LightswitchBlock.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public BlockPos getDevicePos()
    {
        return this.worldPosition;
    }

    @Override
    public boolean isDevicePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.ENABLED) && state.getValue(LightswitchBlock.ENABLED);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public void toggleDevicePower()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(LightswitchBlock.ENABLED))
        {
            boolean enabled = !state.getValue(LightswitchBlock.ENABLED);
            this.level.setBlock(this.worldPosition, state.setValue(LightswitchBlock.ENABLED, enabled), Block.UPDATE_ALL);
        }
    }

    @Override
    public Component getDeviceName()
    {
        // TODO custom names
        return this.getBlockState().getBlock().getName();
    }
}
