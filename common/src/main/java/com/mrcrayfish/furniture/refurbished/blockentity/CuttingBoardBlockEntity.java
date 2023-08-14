package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntity extends BasicLootBlockEntity
{
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> inputRecipeCache;
    private final RecipeManager.CachedCheck<Container, ? extends SingleItemRecipe> outputCache;
    protected boolean sync;

    public CuttingBoardBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.CUTTING_BOARD.get(), pos, state, 1, ModRecipeTypes.CUTTING_BOARD_SLICING.get());
    }

    public CuttingBoardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends SingleItemRecipe> recipeType)
    {
        super(type, pos, state, containerSize);
        this.inputRecipeCache = RecipeManager.createCheck(recipeType);
        this.outputCache = RecipeManager.createCheck(recipeType);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "cutting_board");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        // Cutting board has no gui
        return null;
    }

    /**
     *
     * @param heldItem
     * @return
     */
    public boolean placeItem(ItemStack heldItem)
    {
        if(this.canPlaceItem(0, heldItem))
        {
            ItemStack copy = heldItem.copy();
            copy.setCount(1);
            heldItem.shrink(1);
            this.setItem(0, copy);
            this.sync();
            return true;
        }
        else if(!this.getItem(0).isEmpty())
        {
            ItemStack stack = this.getItem(0);
            BlockPos pos = this.worldPosition;
            ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack.copy());
            this.setItem(0, ItemStack.EMPTY);
            this.level.addFreshEntity(entity);
            this.sync();
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean sliceItem(boolean drop)
    {
        ItemStack input = this.getItem(0);
        Optional<? extends SingleItemRecipe> recipe = this.getRecipe(input);
        if(recipe.isPresent())
        {
            // TODO play sound
            ItemStack result = recipe.get().getResultItem(this.level.registryAccess());
            BlockPos pos = this.worldPosition;
            if(drop)
            {
                ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, result.copy());
                entity.setDefaultPickUpDelay();
                entity.setDeltaMovement(new Vec3(0, 0.15, 0));
                this.level.addFreshEntity(entity);
                result = ItemStack.EMPTY;
            }
            this.setItem(0, result.copy());
            this.sync();
            return true;
        }
        return false;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex == 0 && this.isSlotInsertable(slotIndex) && this.isRecipe(stack);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        if(slotIndex == 0)
        {
            return this.outputCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level)).isEmpty();
        }
        return false;
    }

    @Override
    protected boolean isSlotInsertable(int slotIndex)
    {
        ItemStack target = this.getItem(slotIndex);
        return target.isEmpty() || target.getCount() < target.getMaxStackSize() && target.getCount() < 1;
    }

    /**
     * A utility method to checks if the given ItemStack is a valid recipe for this processing block.
     * @param stack the item stack to check
     * @return True if it matches a recipe
     */
    public boolean isRecipe(ItemStack stack)
    {
        return this.getRecipe(stack).isPresent();
    }

    /**
     *
     * @param stack
     * @return
     */
    public Optional<? extends SingleItemRecipe> getRecipe(ItemStack stack)
    {
        return this.inputRecipeCache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        if(!this.level.isClientSide())
        {
            this.sync();
        }
    }

    /**
     * Marks the toaster as needing to sync data to tracking clients
     */
    protected void sync()
    {
        this.sync = true;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CuttingBoardBlockEntity entity)
    {
        if(entity.sync)
        {
            BlockEntityHelper.sendCustomUpdate(entity, entity.getUpdateTag());
            entity.sync = false;
        }
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
}
