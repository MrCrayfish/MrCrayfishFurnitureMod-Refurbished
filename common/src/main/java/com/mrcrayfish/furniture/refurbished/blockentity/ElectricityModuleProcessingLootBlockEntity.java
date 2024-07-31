package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public abstract class ElectricityModuleProcessingLootBlockEntity extends ProcessingContainerBlockEntity implements IModuleNode
{
    protected final Set<Connection> connections = new HashSet<>();
    protected final Set<BlockPos> powerSources = new HashSet<>();
    protected boolean powered;
    protected boolean receivingPower;

    protected ElectricityModuleProcessingLootBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends ProcessingRecipe> recipeType)
    {
        super(type, pos, state, containerSize, recipeType);
    }

    @Override
    public boolean canProcess()
    {
        return this.powered && super.canProcess();
    }

    @Override
    public Level getNodeLevel()
    {
        return this.level;
    }

    @Override
    public BlockPos getNodePosition()
    {
        return this.worldPosition;
    }

    @Override
    public BlockEntity getNodeOwner()
    {
        return this;
    }

    @Override
    public boolean isSourceNode()
    {
        return false;
    }

    @Override
    public boolean isNodePowered()
    {
        return this.powered;
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        this.powered = powered;
        this.setChanged();
        if(this.level instanceof ServerLevel)
        {
            this.syncDataToTrackingClients();
        }
    }

    @Override
    public Set<Connection> getNodeConnections()
    {
        return this.connections;
    }

    @Override
    public void setNodeReceivingPower(boolean state)
    {
        this.receivingPower = state;
    }

    @Override
    public boolean isNodeReceivingPower()
    {
        return this.receivingPower;
    }

    @Override
    public Set<BlockPos> getPowerSources()
    {
        return this.powerSources;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ElectricityModuleProcessingLootBlockEntity module)
    {
        module.updateNodePoweredState();
        module.setNodeReceivingPower(false);
        ProcessingContainerBlockEntity.serverTick(level, pos, state, module);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        this.readNodeNbt(tag);
        if(tag.contains("Powered", Tag.TAG_BYTE))
        {
            this.powered = tag.getBoolean("Powered");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", this.powered);
    }

    @Override
    public void syncDataToTrackingClients()
    {
        this.updateNodeConnections();
        BlockEntityHelper.sendCustomUpdate(this, BlockEntity::getUpdateTag);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        CompoundTag tag = new CompoundTag();
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", powered);
        return tag;
    }

    // @Override From IForgeBlockEntity
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox()
    {
        return new AABB(this.worldPosition).inflate(Config.CLIENT.electricityViewDistance.get());
    }

    @Override
    public int hashCode()
    {
        return this.worldPosition.hashCode();
    }

    @Override
    public void saveToItem(ItemStack stack, HolderLookup.Provider provider)
    {
        this.saveNodeNbtToItem(stack, provider);
    }
}
