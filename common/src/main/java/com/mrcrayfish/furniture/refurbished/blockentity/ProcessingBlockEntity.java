package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingBlockEntity extends BasicLootBlockEntity
{
    protected static final int[] NO_SLOTS = new int[]{};

    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> processRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> outputRecipeCache;

    // Properties
    protected EnergyMode energyMode = EnergyMode.ONLY_WHEN_PROCESSING;
    protected int maxProcessTime;
    protected int processTime;
    protected int energy;

    public ProcessingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends AbstractCookingRecipe> recipeType)
    {
        super(type, pos, state, containerSize);
        this.processRecipeCache = RecipeManager.createCheck(recipeType);
        this.outputRecipeCache = RecipeManager.createCheck(recipeType);
    }

    public void setEnergyMode(EnergyMode energyMode)
    {
        this.energyMode = energyMode;
    }

    public abstract int[] getInputSlots();

    public abstract int[] getOutputSlots();

    public abstract int[] getEnergySlots();

    protected boolean requiresEnergy()
    {
        return this.getEnergySlots().length > 0;
    }

    protected int consumeEnergy(boolean simulate)
    {
        int[] slots = this.getEnergySlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                int energy = this.getEnergyFor(stack);
                if(energy > 0)
                {
                    if(!simulate)
                    {
                        this.onConsumeEnergy(stack);
                        stack.shrink(1);
                    }
                    return energy;
                }
            }
        }
        return 0;
    }

    protected void onConsumeEnergy(ItemStack stack) {}

    protected int getEnergyFor(ItemStack stack)
    {
        return 200;
    }

    protected void process()
    {
        int[] slots = this.getInputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                Item remainingItem = stack.getItem().getCraftingRemainingItem();
                Optional<? extends AbstractCookingRecipe> optional = this.getRecipe(this.processRecipeCache, stack);
                ItemStack result = optional.map(recipe -> recipe.getResultItem(this.level.registryAccess())).orElse(ItemStack.EMPTY);
                stack.shrink(1);
                if(!result.isEmpty())
                {
                    ItemStack copy = result.copy();
                    this.pushOutput(copy);
                    this.onProcessed(copy);
                    if(remainingItem != null)
                    {
                        this.setItem(slot, new ItemStack(remainingItem));
                    }
                }
                return;
            }
        }
    }

    protected void onProcessed(ItemStack stack) {}

    @Nullable
    protected AbstractCookingRecipe getProcessableRecipe()
    {
        int[] slots = this.getInputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                Optional<? extends AbstractCookingRecipe> optional = this.getRecipe(this.processRecipeCache, stack);
                if(optional.isPresent())
                {
                    ItemStack result = optional.get().getResultItem(this.level.registryAccess());
                    if(this.canOutput(stack, result))
                    {
                        return optional.get();
                    }
                }
            }
        }
        return null;
    }

    protected void processTick()
    {
        boolean processing = false;
        AbstractCookingRecipe recipe = this.getProcessableRecipe();
        if(recipe != null)
        {
            // If energy is required, and no energy is left, attempt to add more energy
            if(this.requiresEnergy() && this.energy <= 0)
            {
                if(this.consumeEnergy(true) > 0)
                {
                    this.energy += this.consumeEnergy(false);
                }
            }

            if(!this.requiresEnergy() || this.energy > 0)
            {
                processing = true;

                // Update the max process time if different
                if(this.maxProcessTime != recipe.getCookingTime())
                {
                    this.maxProcessTime = recipe.getCookingTime();
                }

                // Increase the process time if not yet reach the final process time and consume energy
                if(this.processTime < this.maxProcessTime)
                {
                    this.processTime++;
                    if(this.requiresEnergy() && this.energyMode == EnergyMode.ONLY_WHEN_PROCESSING)
                    {
                        this.energy--;
                    }
                }

                // Finally check if the process time is finished and output the result
                if(this.processTime >= this.maxProcessTime)
                {
                    this.process();
                    this.processTime = 0;
                    this.maxProcessTime = 0;
                }
            }
        }

        // Consume energy if always consuming
        if(this.requiresEnergy() && this.energyMode == EnergyMode.ALWAYS_CONSUME && this.energy > 0)
        {
            this.energy--;
        }

        if(!processing)
        {
            this.processTime = 0;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ProcessingBlockEntity entity)
    {
        entity.processTick();
    }

    protected boolean canOutput(ItemStack input, ItemStack result)
    {
        if(result.isEmpty())
        {
            return false;
        }
        int[] slots = this.getOutputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(stack.isEmpty() || ItemStack.matches(result, stack) && stack.getCount() < stack.getMaxStackSize())
            {
                return true;
            }

            // Special case where input can output to itself, instead of a different slot
            if(this.isOutputInput(slot) && input.getItem().getCraftingRemainingItem() == null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if an output slot is also an input slot. An output slot can only be an input slot
     * if the max stack size is restricted to one, since the slot would need to be empty after it
     * is processed; a stack size greater than one wouldn't work in that case.
     *
     * @param outputSlotIndex the index of an output slot
     * @return True if output slot is an input slot and max stack size is one
     */
    protected boolean isOutputInput(int outputSlotIndex)
    {
        return this.getMaxStackSize() == 1 && this.slotsContains(this.getInputSlots(), outputSlotIndex);
    }

    /**
     *
     * @param result
     */
    protected void pushOutput(ItemStack result)
    {
        int[] slots = this.getOutputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(stack.isEmpty())
            {
                this.setItem(slot, result);
                return;
            }
            else if(ItemStack.matches(result, stack) && stack.getCount() < stack.getMaxStackSize())
            {
                stack.grow(1);
                return;
            }
        }
    }

    private Optional<? extends AbstractCookingRecipe> getRecipe(RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> cache, ItemStack stack)
    {
        return cache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
    }

    @Override
    public boolean canPlaceItemThroughFace(int slotIndex, ItemStack stack, @Nullable Direction direction)
    {
        // By default, items can be placed through any face
        if(this.slotsContains(this.getInputSlots(), slotIndex))
        {
            return true;
        }
        else if(this.slotsContains(this.getEnergySlots(), slotIndex))
        {
            return this.getEnergyFor(stack) > 0;
        }
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction direction)
    {
        // By default, items can be taken from any face
        if(this.slotsContains(this.getOutputSlots(), slotIndex))
        {
            // Special case where output slot is also an input slot. Item must not be a recipe to take.
            if(this.isOutputInput(slotIndex))
            {
                return this.getRecipe(this.outputRecipeCache, stack).isEmpty();
            }
            return true;
        }
        return false;
    }

    /**
     * A simple utility method to determine if the given slot index
     * exists in an array of slots.
     *
     * @param slots an array of slots indexes
     * @param slotIndex the slot index to test
     * @return True if slotIndex exists in slots
     */
    protected boolean slotsContains(int[] slots, int slotIndex)
    {
        for(int slot : slots)
        {
            if(slot == slotIndex)
            {
                return true;
            }
        }
        return false;
    }

    public enum EnergyMode
    {
        ALWAYS_CONSUME,
        ONLY_WHEN_PROCESSING
    }
}
