package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class StorageJarBlockEntity extends BasicLootBlockEntity
{
    private int headIndex = -1;

    public StorageJarBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.STORAGE_JAR.get(), pos, state);
    }

    public StorageJarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 18);
    }

    private int getHeadIndex()
    {
        if(this.headIndex == -1 || this.headIndex >= 0 && this.headIndex < this.getContainerSize() && !this.getItem(this.headIndex).isEmpty())
        {
            this.recalculateHeadIndex();
        }
        return this.headIndex;
    }

    private void recalculateHeadIndex()
    {
        for(int i = 0; i < this.getContainerSize(); i++)
        {
            if(this.getItem(i).isEmpty())
            {
                this.headIndex = i;
                return;
            }
        }
        this.headIndex = this.getContainerSize();
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    public boolean addItem(ItemStack stack)
    {
        if(this.level == null)
            return false;

        ItemStack filter = this.getItem(0);
        if(!filter.isEmpty() && !ItemStack.isSameItem(filter, stack))
            return false;

        int head = this.getHeadIndex();
        if(head < this.getContainerSize())
        {
            ItemStack copy = stack.copy();
            copy.setCount(1);
            stack.shrink(1);
            this.setItem(head, copy);
            float percent = (float) this.headIndex / this.getContainerSize();
            float pitch = 0.9F + 0.3F * percent;
            this.level.playSound(null, this.worldPosition, ModSounds.BLOCK_STORAGE_JAR_INSERT_ITEM.get(), SoundSource.BLOCKS, 1.0F, pitch);
            return true;
        }
        return false;
    }

    public void popItem(Direction face)
    {
        int index = this.getHeadIndex() - 1;
        if(index >= 0 && index < this.getContainerSize())
        {
            ItemStack stack = this.getItem(index);
            if(!stack.isEmpty())
            {
                BlockPos pos = this.worldPosition;
                double x = pos.getX() + 0.5 + face.getStepX() * 0.35;
                double y = pos.getY() + 0.15;
                double z = pos.getZ() + 0.5 + face.getStepZ() * 0.35;
                ItemEntity entity = new ItemEntity(this.level, x, y, z, stack.copy());
                this.level.addFreshEntity(entity);

                float percent = (float) this.headIndex / this.getContainerSize();
                float pitch = 0.9F + 0.3F * percent;
                this.level.playSound(null, pos, ModSounds.BLOCK_STORAGE_JAR_INSERT_ITEM.get(), SoundSource.BLOCKS, 1.0F, pitch);

                stack.setCount(0);
                this.headIndex = index;
                this.setChanged();
            }
        }
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(this.getHeadIndex() > 0 && this.getItem(this.getHeadIndex() - 1).isEmpty())
        {
            this.recalculateHeadIndex();
        }
        return slotIndex == this.getHeadIndex() - 1;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        ItemStack filter = this.getItem(0);
        if(slotIndex > 0 && !filter.isEmpty() && !ItemStack.isSameItem(filter, stack))
            return false;

        if(slotIndex < this.getHeadIndex() && this.getItem(slotIndex).isEmpty())
            this.recalculateHeadIndex();

        if(slotIndex > this.getHeadIndex())
            return false;

        if(this.getHeadIndex() >= this.getContainerSize())
            return false;

        return slotIndex <= this.getHeadIndex();
    }

    @Override
    public void setItem(int slotIndex, ItemStack stack)
    {
        int headIndex = this.getHeadIndex();
        if(slotIndex <= headIndex)
        {
            super.setItem(slotIndex, stack);
            if(!stack.isEmpty())
            {
                if(slotIndex == headIndex)
                {
                    this.headIndex++;
                }
            }
            else if(slotIndex == headIndex - 1)
            {
                this.headIndex--;
            }
        }
    }

    @Override
    public ItemStack removeItem(int slotIndex, int count)
    {
        if(slotIndex == this.getHeadIndex() - 1)
        {
            ItemStack result = super.removeItem(slotIndex, count);
            if(this.getItem(slotIndex).isEmpty())
            {
                this.headIndex--;
            }
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "storage_jar");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        // No screen
        return null;
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        if(!this.level.isClientSide())
        {
            BlockEntityHelper.sendCustomUpdate(this, BlockEntity::getUpdateTag);
        }
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
        return this.saveWithoutMetadata(provider);
    }
}
