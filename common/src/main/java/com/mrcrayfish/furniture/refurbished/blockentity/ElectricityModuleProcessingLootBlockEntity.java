package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mojang.serialization.Codec;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

    @Override
    public void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        this.readNodeNbt(input);
        input.read("Powered", Codec.BOOL).ifPresent(value -> this.powered = value);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        this.writeNodeNbt(output);
        output.store("Powered", Codec.BOOL, this.powered);
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
        // TODO create a util for this
        try(ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(this.problemPath(), Constants.LOG))
        {
            TagValueOutput output = TagValueOutput.createWithContext(collector, provider);
            this.writeNodeNbt(output);
            output.store("Powered", Codec.BOOL, this.powered);
            return output.buildResult();
        }
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
    public void removeComponentsFromTag(ValueOutput output)
    {
        output.discard("Connections"); // Don't include connections as this breaks node limits
        output.discard("NodePos"); // Don't include fix for connections since none are present anyway
        output.discard("Powered"); // Remove the powered property
        output.discard("Overloaded"); // Remove the overloaded property
    }
}
