package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricModuleProcessingContainerBlockEntity extends ProcessingContainerBlockEntity implements IElectricNode
{
    protected final Set<Connection> connections = new HashSet<>();
    protected boolean powered;
    protected boolean receivingPower;

    protected ElectricModuleProcessingContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends AbstractCookingRecipe> recipeType)
    {
        super(type, pos, state, containerSize, recipeType);
    }

    @Override
    public boolean canProcess()
    {
        return this.powered && super.canProcess();
    }

    @Override
    public BlockPos getPosition()
    {
        return this.worldPosition;
    }

    @Override
    public BlockEntity getBlockEntity()
    {
        return this;
    }

    @Override
    public boolean isSource()
    {
        return false;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public void setPowered(boolean powered)
    {
        this.powered = powered;
        this.setChanged();
        if(this.level instanceof ServerLevel)
        {
            this.syncNodeData();
        }
    }

    @Override
    public Set<Connection> getConnections()
    {
        return this.connections;
    }

    @Override
    public void setReceivingPower(boolean power)
    {
        this.receivingPower = power;
    }

    @Override
    public boolean isReceivingPower()
    {
        return this.receivingPower;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ElectricModuleProcessingContainerBlockEntity module)
    {
        module.updatePoweredState();
        module.setReceivingPower(false);
        ProcessingContainerBlockEntity.serverTick(level, pos, state, module);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.readNodeNbt(tag);
        if(tag.contains("Powered", Tag.TAG_BYTE))
        {
            this.powered = tag.getBoolean("Powered");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", this.powered);
    }

    @Override
    public void syncNodeData()
    {
        this.updateConnections();
        BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", powered);
        return tag;
    }

    @Override
    public int hashCode()
    {
        return this.worldPosition.hashCode();
    }
}
