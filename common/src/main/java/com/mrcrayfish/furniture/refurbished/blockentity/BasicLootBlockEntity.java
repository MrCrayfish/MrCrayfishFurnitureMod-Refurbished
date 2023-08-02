package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class BasicLootBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer
{
    protected final int[] slots;
    protected final int containerSize;
    protected NonNullList<ItemStack> items;
    protected final BasicContainerCounter tracker = new BasicContainerCounter(this);

    public BasicLootBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize)
    {
        super(type, pos, state);
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
        this.slots = IntStream.range(0, containerSize).toArray();
        this.containerSize = containerSize;
        this.initItemHandler();
    }

    public abstract boolean isMatchingContainerMenu(AbstractContainerMenu menu);

    @Override
    public int getContainerSize()
    {
        return this.containerSize;
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items)
    {
        this.items = items;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        if(!this.trySaveLootTable(tag))
        {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if(!this.tryLoadLootTable(compound))
        {
            ContainerHelper.loadAllItems(compound, this.items);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return this.slots;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return this.isSlotInsertable(slotIndex); // Additional check for container max stack size
    }

    @Override
    public boolean canPlaceItemThroughFace(int slotIndex, ItemStack stack, @Nullable Direction direction)
    {
        return this.canPlaceItem(slotIndex, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction direction)
    {
        return this.canTakeItem(this, slotIndex, stack);
    }

    /**
     * A utility method to check if the slot at the given index is empty or is less than it's max
     * stack size with additional respect to the max stack size of the container; something vanilla
     * unfortunately doesn't do.
     *
     * @param slotIndex the index of the slot to check
     * @return True if the slot is available
     */
    protected boolean isSlotInsertable(int slotIndex)
    {
        ItemStack target = this.getItem(slotIndex);
        return target.isEmpty() || target.getCount() < target.getMaxStackSize() && target.getCount() < this.getMaxStackSize();
    }

    @Override
    public void startOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.tracker.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player)
    {
        if(!this.remove && !player.isSpectator())
        {
            this.tracker.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void updateOpenerCount()
    {
        if(!this.remove)
        {
            this.tracker.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void onOpen(Level level, BlockPos pos, BlockState state) {}

    public void onClose(Level level, BlockPos pos, BlockState state) {}

    // Hack for Forge capabilities
    public void initItemHandler()
    {
        if(Services.PLATFORM.getPlatform().isForge())
        {
            Services.BLOCK_ENTITY.createForgeSidedWrapper(this, Direction.UP);
        }
    }

    // Hack for Forge capabilities
    // @Override
    public void reviveCaps()
    {
        if(Services.PLATFORM.getPlatform().isForge())
        {
            Services.BLOCK_ENTITY.reviveForgeCapabilities(this);
            Services.BLOCK_ENTITY.createForgeSidedWrapper(this, Direction.UP);
        }
    }
}
