package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.ToasterBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class ToasterBlockEntity extends ElectricityModuleProcessingContainerBlockEntity
{
    public static final int[] INPUT_SLOTS = new int[]{0, 1};
    public static final int[] OUTPUT_SLOTS = new int[]{0, 1};

    protected boolean heating;
    protected boolean sync;

    public ToasterBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.TOASTER.get(), pos, state, 2, ModRecipeTypes.TOASTER_HEATING.get());
    }

    public ToasterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int containerSize, RecipeType<? extends AbstractCookingRecipe> recipeType)
    {
        super(type, pos, state, containerSize, recipeType);
    }

    @Override
    public void setPowered(boolean powered)
    {
        super.setPowered(powered);
        if(!powered && this.isHeating())
        {
            this.setHeating(false);
        }
    }

    /**
     * A hook for redstone to trigger to put the toaster into a heating state
     */
    public void startHeating()
    {
        if(!this.heating && this.canProcessInput())
        {
            this.setHeating(true);
        }
    }

    /**
     * Attempts to toggle the heating state of the toaster
     * @return True if the state changed
     */
    public boolean toggleHeating()
    {
        if(this.heating || this.canProcessInput())
        {
            this.setHeating(!this.heating);
            return true;
        }
        return false;
    }

    /**
     * Sets the heating state of the toaster. This will also play a sound and update the blockstate
     * with the powered version of the toaster.
     *
     * @param heating the new heating state of the toaster
     */
    private void setHeating(boolean heating)
    {
        this.heating = heating;
        // TODO change sound to custom one
        this.level.playSound(null, this.worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS);
        this.level.setBlock(this.worldPosition, this.getBlockState().setValue(ToasterBlock.POWERED, this.heating), Block.UPDATE_ALL);
        this.sync();
    }

    /**
     * @return True if the toaster is heating
     */
    public boolean isHeating()
    {
        return this.heating;
    }

    @Override
    public int[] getInputSlots()
    {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots()
    {
        return OUTPUT_SLOTS;
    }

    @Override
    public int[] getEnergySlots()
    {
        return NO_SLOTS;
    }

    @Override
    protected boolean shouldProcessAll()
    {
        return true;
    }

    @Override
    public boolean canProcess()
    {
        return this.heating && super.canProcessInput();
    }

    @Override
    public void onCompleteProcess()
    {
        super.onCompleteProcess();
        this.setHeating(false);
        this.sync();
    }

    @Override
    protected boolean handleProcessed(ItemStack stack)
    {
        BlockPos pos = this.worldPosition;
        ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack.copy());
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(new Vec3(0, 0.35, 0));
        this.level.addFreshEntity(entity);
        return true;
    }

    @Override
    public void processTick()
    {
        super.processTick();
        if(this.sync)
        {
            BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
            this.sync = false;
        }
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "toaster");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        // Toaster has no gui
        return null;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        // Prevents placing items when heating
        return !this.isHeating() && super.canPlaceItem(slotIndex, stack);
    }

    @Override
    public boolean canTakeItem(Container container, int slotIndex, ItemStack stack)
    {
        // Prevents taking items when heating
        return !this.isHeating() && super.canTakeItem(container, slotIndex, stack);
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        // Only sync from server side
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

    /**
     * Attempts to insert an item into the toaster. An item can only be inserted if the item matches
     * a recipe for the toaster, the toaster is not full, and the toaster is not currently heating.
     * If the given heldItem is empty or the toaster is full, one item will be removed from the
     * toaster.
     *
     * @param heldItem the held item to insert or empty to remove items
     * @return True if an action occurred, does not mean an item was inserted.
     */
    public boolean insertItem(ItemStack heldItem)
    {
        if(this.isHeating())
        {
            return false;
        }
        if(heldItem.isEmpty() || this.isFull())
        {
            return this.extractItem();
        }
        if(this.isRecipe(heldItem))
        {
            for(int i = 0; i < this.getContainerSize(); i++)
            {
                if(this.getItem(i).isEmpty())
                {
                    ItemStack stack = heldItem.copy();
                    stack.setCount(1);
                    heldItem.shrink(1);
                    this.setItem(i, stack);
                    this.sync();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Extracts an item from the toaster and spawns it into the level.
     * @return True if an item was extreacted
     */
    public boolean extractItem()
    {
        for(int i = 0; i < this.getContainerSize(); i++)
        {
            ItemStack stack = this.getItem(i);
            if(!stack.isEmpty())
            {
                BlockPos pos = this.worldPosition;
                ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack.copy());
                this.setItem(i, ItemStack.EMPTY);
                this.level.addFreshEntity(entity);
                this.sync();
                return true;
            }
        }
        return false;
    }

    /**
     * @return True if toaster inventory is full
     */
    private boolean isFull()
    {
        for(int i = 0; i < this.getContainerSize(); i++)
        {
            if(this.getItem(i).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ToasterBlockEntity entity)
    {
        if(state.getValue(ToasterBlock.POWERED))
        {
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0, 0.1 * level.random.nextDouble(), 0.0);
        }
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Heating", Tag.TAG_BYTE))
        {
            this.heating = tag.getBoolean("Heating");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Heating", this.heating);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }
}
