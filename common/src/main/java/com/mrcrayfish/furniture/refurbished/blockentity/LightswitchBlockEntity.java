package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

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
    public boolean canPowerTraverseNode()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.ENABLED) && state.getValue(LightswitchBlock.ENABLED);
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(LightswitchBlock.POWERED) && state.getValue(LightswitchBlock.POWERED);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public void setNodePowered(boolean powered)
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

    private Component getDefaultName()
    {
        return Utils.translation("container", "lightswitch");
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return this.getDefaultName();
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

    @Override
    protected void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        BlockEntityHelper.readCustomName(input).ifPresent(name -> this.name = name);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        BlockEntityHelper.saveCustomName(output, this.name);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return super.getUpdateTag(provider);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter getter)
    {
        super.applyImplicitComponents(getter);
        this.name = getter.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder)
    {
        super.collectImplicitComponents(builder);
        if(this.name != null)
        {
            builder.set(DataComponents.CUSTOM_NAME, this.name);
        }
    }
}
