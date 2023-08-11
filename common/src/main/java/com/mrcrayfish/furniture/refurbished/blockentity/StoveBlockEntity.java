package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.DrawerBlock;
import com.mrcrayfish.furniture.refurbished.block.StoveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class StoveBlockEntity extends BasicLootBlockEntity implements IProcessingBlock
{
    public static final int DATA_ENERGY = 0;
    public static final int DATA_TOTAL_ENERGY = 1;

    // MOVE TO FRYING PAN
    private RecipeType<? extends AbstractCookingRecipe> recipeType;
    private RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> recipeCache;
    protected int totalProcessingTime;
    protected int processingTime;
    protected int totalEnergy;
    protected int energy;
    // TODO
    // protected WeakReference<FryingPanBlockEntity> fryingPanRef = new WeakReference<FryingPanBlockEntity>();

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENERGY, () -> energy, value -> energy = value);
        builder.add(DATA_TOTAL_ENERGY, () -> totalEnergy, value -> totalEnergy = value);
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
    public EnergyMode getEnergyMode()
    {
        return EnergyMode.ALWAYS_CONSUME;
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

    @Override
    public boolean requiresEnergy()
    {
        return true;
    }

    @Override
    public int retrieveEnergy(boolean consume)
    {
        ItemStack stack = this.getItem(0);
        if(!stack.isEmpty())
        {
            int energy = Services.ITEM.getBurnTime(stack, this.recipeType);
            if(energy > 0)
            {
                if(consume)
                {
                    stack.shrink(1);
                    this.totalEnergy = energy;
                }
                return energy;
            }
        }
        return 0;
    }

    @Override
    public int updateAndGetTotalProcessingTime()
    {
        int time = 0;
        ItemStack stack = ItemStack.EMPTY;
        // TODO get stack from frying pan
        if(!stack.isEmpty())
        {
            Optional<? extends AbstractCookingRecipe> optional = this.getRecipe(this.recipeCache, stack);
            if(optional.isPresent())
            {
                time = Math.max(time, optional.get().getCookingTime());
            }
        }
        if(this.totalProcessingTime != time)
        {
            this.totalProcessingTime = time;
        }
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

    @Override
    public void onCompleteProcess()
    {
        ItemStack stack = ItemStack.EMPTY;
        // TODO get stack from frying pan
        if(!stack.isEmpty())
        {
            Item remainingItem = stack.getItem().getCraftingRemainingItem();
            Optional<? extends AbstractCookingRecipe> optional = this.getRecipe(this.recipeCache, stack);
            ItemStack result = optional.map(recipe -> recipe.getResultItem(this.level.registryAccess())).orElse(ItemStack.EMPTY);
            stack.shrink(1);
            if(!result.isEmpty())
            {
                ItemStack copy = result.copy();
                // TODO set item in frying pan
                if(remainingItem != null)
                {
                    // TODO spawn into the level?
                    //this.setItem(slot, new ItemStack(remainingItem));
                }
            }
        }
    }

    @Override
    public boolean canProcess()
    {
        ItemStack stack = ItemStack.EMPTY;
        // TODO get stack from frying pan
        if(!stack.isEmpty())
        {
            Optional<? extends AbstractCookingRecipe> optional = this.getRecipe(this.recipeCache, stack);
            return optional.isPresent();
        }
        return false;
    }

    /**
     * A utility to get a recipe for the given cache and ItemStack.
     * @param cache the cache to check
     * @param stack the itemstack of the recipe
     * @return An optional recipe
     */
    private Optional<? extends AbstractCookingRecipe> getRecipe(RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> cache, ItemStack stack)
    {
        return cache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
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
}
