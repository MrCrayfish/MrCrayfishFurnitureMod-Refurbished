package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.StoveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * Author: MrCrayfish
 */
public class StoveBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IHeatingSource, IPowerSwitch
{
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;

    protected boolean enabled;
    protected boolean processing;
    protected int totalProcessingTime;
    protected int processingTime;
    protected WeakReference<ICookingBlock> cookingBlockRef;
    protected boolean sync;
    protected @Nullable StoveContainer container;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> isPowered() ? 1 : 0, value -> {});
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
    });

    public StoveBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.STOVE.get(), pos, state);
    }

    public StoveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 1);
    }

    @Override
    public int getEnergy()
    {
        return 0;
    }

    @Override
    public void addEnergy(int energy) {}

    @Override
    public boolean requiresEnergy()
    {
        return false;
    }

    @Override
    public int retrieveEnergy(boolean simulate)
    {
        return 0;
    }

    @Override
    public int updateAndGetTotalProcessingTime()
    {
        int time = 0;
        ICookingBlock block = this.getCookingBlock();
        if(block != null)
        {
            time = block.getTimeToCook();
        }
        if(this.totalProcessingTime != time)
        {
            this.totalProcessingTime = time;
        }
        return this.totalProcessingTime;
    }

    @Override
    public int getTotalProcessingTime()
    {
        return this.totalProcessingTime;
    }

    @Override
    public int getProcessingTime()
    {
        return this.processingTime;
    }

    @Override
    public void setProcessingTime(int time)
    {
        boolean changed = false;
        if(this.processingTime == 0 && time > this.processingTime)
        {
            ICookingBlock block = this.getCookingBlock();
            if(block != null)
            {
                block.onStartCooking();
            }
            this.processing = true;
            this.sync();
            changed = true;
        }
        else if(time == 0 && time < this.processingTime)
        {
            ICookingBlock block = this.getCookingBlock();
            if(block != null)
            {
                block.onStopCooking();
            }
            this.processing = false;
            this.sync();
            changed = true;
        }
        if(this.processingTime != time)
        {
            this.processingTime = time;
            changed = true;
        }
        if(changed)
        {
            this.setChanged();
        }
    }

    @Override
    public void onCompleteProcess()
    {
        ICookingBlock block = this.getCookingBlock();
        if(block != null)
        {
            block.onCompleteCooking();
            this.processing = false;
            this.setChanged();
            this.sync();
        }
    }

    @Override
    public boolean canProcess()
    {
        if(!this.isPowered() || !this.enabled) return false;
        ICookingBlock block = this.getCookingBlock();
        return block != null && block.canCook();
    }

    @Nullable
    public ICookingBlock getCookingBlock()
    {
        if(this.cookingBlockRef != null)
        {
            ICookingBlock block = this.cookingBlockRef.get();
            if(block != null && !block.getBlockEntity().isRemoved())
            {
                return block;
            }
            this.cookingBlockRef = null;
        }

        if(this.level instanceof ServerLevel serverLevel)
        {
            BlockEntity entity = serverLevel.getBlockEntity(this.worldPosition.above());
            if(entity instanceof ICookingBlock cookingBlock)
            {
                this.cookingBlockRef = new WeakReference<>(cookingBlock);
                return cookingBlock;
            }
        }
        return null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StoveBlockEntity stove)
    {
        ElectricityModuleLootBlockEntity.serverTick(level, pos, state, stove);
        stove.processTick();
        if(stove.sync)
        {
            BlockEntityHelper.sendCustomUpdate(stove, stove.getUpdateTag());
            stove.sync = false;
        }
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "stove");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new StoveMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof StoveMenu stove && stove.getContainer() == this;
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        // TODO sounds
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.setDoorState(state, false);
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(StoveBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean isProcessing()
    {
        return this.processing;
    }

    @Override
    public boolean isHeating()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(StoveBlock.LIT) && state.getValue(StoveBlock.LIT);
    }

    /**
     * Callback when the stove is removed from the level and updates the above cooking block, if
     * any, that cooking has stopped.
     *
     * @param pos the block position of the stove
     */
    public void onDestroyed(BlockPos pos)
    {
        if(this.isProcessing())
        {
            Level level = this.getLevel();
            if(level != null && level.getBlockEntity(pos.above()) instanceof ICookingBlock cooking)
            {
                cooking.onStopCooking();
            }
        }
    }

    /**
     * Called when a neighbour block is changed.
     */
    public void onNeighbourChanged()
    {
        this.container = null;
    }

    @Override
    public void setBlockState(BlockState state)
    {
        super.setBlockState(state);
        this.container = null;
    }

    @Nullable
    public WorldlyContainer getContainer()
    {
        if(this.container == null)
        {
            ICookingBlock cookingBlock = this.getCookingBlock();
            if(cookingBlock != null && cookingBlock.getBlockEntity() instanceof Container cookingContainer)
            {
                this.container = new StoveContainer(this, cookingBlock, cookingContainer);
            }
        }
        else if(!this.container.isValid())
        {
            this.container = null;
        }
        return this.container != null ? this.container : this;
    }

    /**
     * Marks the frying pan as needing to sync data to tracking clients
     */
    protected void sync()
    {
        this.sync = true;
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
        return this.saveWithoutMetadata();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Processing", Tag.TAG_BYTE))
        {
            this.processing = tag.getBoolean("Processing");
        }
        if(tag.contains("TotalProcessingTime", Tag.TAG_INT))
        {
            this.totalProcessingTime = tag.getInt("TotalProcessingTime");
        }
        if(tag.contains("ProcessingTime", Tag.TAG_INT))
        {
            this.processingTime = tag.getInt("ProcessingTime");
        }
        if(tag.contains("Enabled", Tag.TAG_BYTE))
        {
            this.enabled = tag.getBoolean("Enabled");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Processing", this.processing);
        tag.putInt("TotalProcessingTime", this.totalProcessingTime);
        tag.putInt("ProcessingTime", this.processingTime);
        tag.putBoolean("Enabled", this.enabled);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(StoveBlock.POWERED) && state.getValue(StoveBlock.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(StoveBlock.POWERED))
        {
            state = state.setValue(StoveBlock.POWERED, powered);
        }
        if(state.hasProperty(StoveBlock.LIT))
        {
            state = state.setValue(StoveBlock.LIT, powered && this.enabled);
        }
        this.level.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
    }

    @Override
    public void toggle()
    {
        this.enabled = !this.enabled;
        this.level.setBlock(this.worldPosition, this.getBlockState().setValue(StoveBlock.LIT, this.isPowered() && this.enabled), Block.UPDATE_ALL);
        this.setChanged();
        this.sync();
    }
}
