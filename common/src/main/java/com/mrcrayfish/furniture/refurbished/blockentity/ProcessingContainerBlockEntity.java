package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public abstract class ProcessingContainerBlockEntity extends BasicLootBlockEntity implements IProcessingBlock
{
    protected static final int[] NO_SLOTS = new int[]{};

    private final RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> inputRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe>[] processRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> outputRecipeCache;
    protected int totalProcessingTime;
    protected int processingTime;
    protected int energy;

    public ProcessingContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends ProcessingRecipe> recipeType)
    {
        super(type, pos, state, containerSize);
        this.inputRecipeCache = RecipeManager.createCheck(recipeType);
        this.processRecipeCache = this.createCacheArray(this.getInputSlots().length, () -> RecipeManager.createCheck(recipeType));
        this.outputRecipeCache = RecipeManager.createCheck(recipeType);
    }

    /**
     * @return An array of slots in the inventory used for input
     */
    protected abstract int[] getInputSlots();

    /**
     * @return An array of slots in the inventory used for output
     */
    protected abstract int[] getOutputSlots();

    /**
     * @return An array of slots in the inventory used for energy
     */
    protected abstract int[] getEnergySlots();

    /**
     * @return True if all input should be processed at the same time instead of one by one
     */
    protected boolean shouldProcessAll()
    {
        return false;
    }

    /**
     * Gets the amount of energy that the given ItemStack provides for this processing block
     *
     * @param stack the item stack to check
     * @return The amount of energy for this stack or zero if no energy
     */
    protected int getEnergyFor(ItemStack stack)
    {
        return 0;
    }

    /**
     * Callback method when an item from an energy slot is consumed
     * @param stack the item stack that was consumed
     */
    protected void onConsumeEnergy(ItemStack stack) {}

    /**
     * Handles the processed item. If this method returns true, it will cancel the default behaviour
     * of pushing the item to the output slots. This can be used to define custom behaviour. See
     * the {@link ToasterBlockEntity} for an example.
     *
     * @param stack the item stack that was processed
     * @return True to cancel the default behaviour
     */
    protected boolean handleProcessed(ItemStack stack)
    {
        return false;
    }

    @Override
    public int getEnergy()
    {
        return this.energy;
    }

    @Override
    public void addEnergy(int energy)
    {
        this.energy += energy;
    }

    /**
     * Determines if this processing block requires energy. By default, a processing block
     * assumes that if there are any energy slots, it requires energy. You will need to implement
     * {@link #getEnergyFor(ItemStack)} if processing block requires energy.
     * @return True if requires energy
     */
    @Override
    public boolean requiresEnergy()
    {
        return this.getEnergySlots().length > 0;
    }

    /**
     * Attempts to consume an item from an energy slot. If an item in the energy slot does not
     * provide any energy, it will be ignored. This method also has the option to simulate, which
     * means that it can be used to check if an item in the energy slots provides energy without
     * shrinking it.
     *
     * @param consume mark as false to check if energy can be provided without consuming (simulate)
     * @return the amount of energy returned from consuming an energy item
     */
    @Override
    public int retrieveEnergy(boolean consume)
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
                    if(consume)
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

    /**
     * Gets the time required to process the input. If {@link #shouldProcessAll()} is marked as
     * true, then the processing time is the maximum time of all the inputs.
     *
     * @return the time to process the inputs
     */
    @Override
    public int updateAndGetTotalProcessingTime()
    {
        int time = 0;
        int[] slots = this.getInputSlots();
        for(int i = 0; i < slots.length; i++)
        {
            int slot = slots[i];
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                Optional<? extends ProcessingRecipe> optional = this.getRecipe(this.processRecipeCache[i], stack);
                if(optional.isPresent())
                {
                    time = Math.max(time, optional.get().getTime());
                    if(!this.shouldProcessAll())
                    {
                        break;
                    }
                }
            }
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
        this.processingTime = time;
    }

    /**
     * A modifiable method that determines if this processing block can process. By default,
     * processing can happen if the input can be processed. See {@link ToasterBlockEntity} for an
     * example override.
     *
     * @return True if processing can happen
     */
    @Override
    public boolean canProcess()
    {
        return this.canProcessInput();
    }

    /**
     * @return True if first found item in the input slot can be processed. If {@link #shouldProcessAll()}
     * is enabled, method will return true if all items in the input slots can bne processed.
     */
    protected final boolean canProcessInput()
    {
        int[] slots = this.getInputSlots();
        for(int i = 0; i < slots.length; i++)
        {
            int slot = slots[i];
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                Optional<? extends ProcessingRecipe> optional = this.getRecipe(this.processRecipeCache[i], stack);
                if(optional.isEmpty())
                {
                    return false;
                }

                ItemStack result = optional.get().getResultItem(this.level.registryAccess());
                if(!this.canOutput(stack, result))
                {
                    return false;
                }

                if(!this.shouldProcessAll())
                {
                    return true;
                }
            }
        }
        return !this.isInputEmpty();
    }

    /**
     * Processes the input
     */
    @Override
    public void onCompleteProcess()
    {
        int[] slots = this.getInputSlots();
        for(int i = 0; i < slots.length; i++)
        {
            int slot = slots[i];
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                Item remainingItem = stack.getItem().getCraftingRemainingItem();
                Optional<? extends ProcessingRecipe> optional = this.getRecipe(this.processRecipeCache[i], stack);
                ItemStack result = optional.map(recipe -> recipe.getResultItem(this.level.registryAccess())).orElse(ItemStack.EMPTY);
                stack.shrink(1);
                if(!result.isEmpty())
                {
                    ItemStack copy = result.copy();
                    if(!this.handleProcessed(copy))
                    {
                        this.pushOutput(copy);
                    }
                    if(remainingItem != null)
                    {
                        this.setItem(slot, new ItemStack(remainingItem));
                    }
                }
                if(!this.shouldProcessAll())
                {
                    return;
                }
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ProcessingContainerBlockEntity processor)
    {
        processor.processTick();
    }

    /**
     *
     * @param input
     * @param result
     * @return
     */
    protected boolean canOutput(ItemStack input, ItemStack result)
    {
        if(result.isEmpty())
            return false;

        int count = 0;
        int[] slots = this.getOutputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(stack.isEmpty())
                return true;

            // Special case where input can output to itself, instead of a different slot
            if(this.isOutputInput(slot) && input.getItem().getCraftingRemainingItem() == null)
                return true;

            if(ItemStack.isSameItemSameTags(result, stack))
                count += (stack.getMaxStackSize() - stack.getCount());
        }
        return count >= result.getCount();
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
     * Attempts to put an item into an output slot. This method will fail if all output slots are
     * full, either because it is not empty or the given stack cannot be merged into one. Although
     * it can fail, processing can only happen if it knows output is possible.
     *
     * @param result the ItemStack to put into the output
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
            else if(ItemStack.isSameItemSameTags(result, stack) && stack.getCount() < stack.getMaxStackSize())
            {
                // Find the highest count of the result that can be pushed into this stack
                int count = Math.min(result.getCount(), stack.getMaxStackSize() - stack.getCount());
                stack.grow(count);
                result.shrink(count);

                // Break if empty
                if(result.isEmpty())
                    return;
            }
        }
    }

    /**
     * @return True if the all input slots are empty
     */
    protected boolean isInputEmpty()
    {
        int[] slots = this.getInputSlots();
        for(int slot : slots)
        {
            ItemStack stack = this.getItem(slot);
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return slots.length > 0;
    }

    /**
     * A utility to get a recipe for the given cache and ItemStack.
     * @param cache the cache to check
     * @param stack the itemstack of the recipe
     * @return An optional recipe
     */
    private Optional<? extends ProcessingRecipe> getRecipe(RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> cache, ItemStack stack)
    {
        return cache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
    }

    /**
     * A utility method to checks if the given ItemStack is a valid recipe for this processing block.
     * @param stack the item stack to check
     * @return True if it matches a recipe
     */
    public boolean isRecipe(ItemStack stack)
    {
        return this.inputRecipeCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level)).isPresent();
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        // By default, items can be placed through any face
        if(this.slotsContains(this.getInputSlots(), slotIndex))
        {
            return this.isSlotInsertable(slotIndex);
        }
        else if(this.slotsContains(this.getEnergySlots(), slotIndex))
        {
            return this.getEnergyFor(stack) > 0 && this.isSlotInsertable(slotIndex);
        }
        return false;
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
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

    /**
     * Creates an array of recipe caches for the given size and fill each index with a
     * unique cache check. This is used for given a cache check for each input slot.
     *
     * @param size the size of the array
     * @param fill a supplier that returns a new cache check
     * @return an array of cache checks
     */
    @SuppressWarnings("unchecked")
    protected RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe>[] createCacheArray(int size, Supplier<RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe>> fill)
    {
        RecipeManager.CachedCheck<?, ?>[] array = new RecipeManager.CachedCheck<?, ?>[size];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = fill.get();
        }
        return (RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe>[]) array;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("MaxProcessTime", Tag.TAG_INT))
        {
            this.totalProcessingTime = tag.getInt("MaxProcessTime");
        }
        if(tag.contains("ProcessTime", Tag.TAG_INT))
        {
            this.processingTime = tag.getInt("ProcessTime");
        }
        if(tag.contains("Energy", Tag.TAG_INT))
        {
            this.energy = tag.getInt("Energy");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putInt("MaxProcessTime", this.totalProcessingTime);
        tag.putInt("ProcessTime", this.processingTime);
        tag.putInt("Energy", this.energy);
    }
}
