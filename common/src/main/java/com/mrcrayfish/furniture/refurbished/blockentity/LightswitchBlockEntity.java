package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlockEntity extends ElectricityModuleBlockEntity implements IHomeControlDevice, Nameable
{
    @Nullable
    private Component name;

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
    public boolean isDeviceEnabled()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.ENABLED) && state.getValue(LightswitchBlock.ENABLED);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public void toggleDeviceState()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(LightswitchBlock.ENABLED))
        {
            boolean enabled = !state.getValue(LightswitchBlock.ENABLED);
            this.level.setBlock(this.worldPosition, state.setValue(LightswitchBlock.ENABLED, enabled), Block.UPDATE_ALL);
        }
    }

    @Override
    public void setDeviceState(boolean enabled)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(LightswitchBlock.ENABLED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(LightswitchBlock.ENABLED, enabled), Block.UPDATE_ALL);
        }
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return this.getName();
    }

    @Override
    public Component getName()
    {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    public Component getDisplayName()
    {
        return this.name != null ? this.name : this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName()
    {
        return this.name;
    }

    public void setCustomName(@Nullable Component name)
    {
        this.name = name;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("CustomName", Tag.TAG_STRING))
        {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        if(this.name != null)
        {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }
}
