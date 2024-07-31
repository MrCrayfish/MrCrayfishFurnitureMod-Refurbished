package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class StoveContainer extends CompoundContainer implements WorldlyContainer
{
    private final StoveBlockEntity stove;
    private final ICookingBlock cookingBlock;
    private final Container cookingContainer;
    private final int[][] slots;

    public StoveContainer(StoveBlockEntity stove, ICookingBlock cookingBlock, Container cookingContainer)
    {
        super(stove, cookingContainer);
        this.stove = stove;
        this.cookingBlock = cookingBlock;
        this.cookingContainer = cookingContainer;
        this.slots = this.generateSlots();
    }

    private int[][] generateSlots()
    {
        int[][] slots = new int[Direction.values().length][1];
        for(Direction direction : Direction.values())
        {
            int[] stoveSlots = this.stove.getSlotsForFace(direction);
            if(this.cookingContainer instanceof WorldlyContainer worldlyContainer)
            {
                int[] cookingSlots = worldlyContainer.getSlotsForFace(direction);
                slots[direction.get3DDataValue()] = this.combineSlots(stoveSlots, cookingSlots, this.stove.getContainerSize());
                continue;
            }
            slots[direction.get3DDataValue()] = stoveSlots;
        }
        return slots;
    }

    private int[] combineSlots(int[] a, int[] b, int indexOffset)
    {
        int[] copy = new int[b.length];
        System.arraycopy(b, 0, copy, 0, b.length);
        for(int i = 0; i < copy.length; i++)
        {
            copy[i] = copy[i] + indexOffset;
        }
        int[] c = new int[a.length + copy.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(copy, 0, c, a.length, copy.length);
        return c;
    }

    public boolean isValid()
    {
        return !this.stove.isRemoved() && !this.cookingBlock.getBlockEntity().isRemoved();
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return this.slots[direction.get3DDataValue()];
    }

    @Override
    public boolean canPlaceItemThroughFace(int slotIndex, ItemStack stack, @Nullable Direction direction)
    {
        if(slotIndex >= this.stove.getContainerSize())
        {
            if(this.cookingContainer instanceof WorldlyContainer worldlyContainer)
            {
                return worldlyContainer.canPlaceItemThroughFace(slotIndex - this.stove.getContainerSize(), stack, direction);
            }
            return this.cookingContainer.canPlaceItem(slotIndex - this.stove.getContainerSize(), stack);
        }
        return this.stove.canPlaceItemThroughFace(slotIndex, stack, direction);
    }

    @Override
    public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction direction)
    {
        if(slotIndex >= this.stove.getContainerSize())
        {
            if(this.cookingContainer instanceof WorldlyContainer worldlyContainer)
            {
                return worldlyContainer.canTakeItemThroughFace(slotIndex - this.stove.getContainerSize(), stack, direction);
            }
            return this.cookingContainer.canTakeItem(this.cookingContainer, slotIndex - this.stove.getContainerSize(), stack);
        }
        return this.stove.canTakeItemThroughFace(slotIndex, stack, direction);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(slotIndex >= this.stove.getContainerSize())
        {
            return this.cookingContainer.canTakeItem(this.cookingContainer, slotIndex - this.stove.getContainerSize(), stack);
        }
        return this.stove.canTakeItem(this.stove, slotIndex, stack);
    }
}
