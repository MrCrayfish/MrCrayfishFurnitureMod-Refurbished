package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class PlateBlockEntity extends BasicLootBlockEntity
{
    protected int rotation;

    public PlateBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.PLATE.get(), pos, state);
    }

    public PlateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 1);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "plate");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return null;
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex == 0 && this.isSlotInsertable(slotIndex);
    }

    /**
     * Attempts to place the given item onto the plate. If the plate already has an item placed on
     * it, this method will simply fail and return false.
     *
     * @param player the player placing the item
     * @param stack  the item to place
     * @return True if the item was placed onto the plate
     */
    public boolean placeItem(Player player, ItemStack stack)
    {
        if(this.canPlaceItem(0, stack))
        {
            this.rotation = player.getDirection().get2DDataValue();
            ItemStack copy = stack.copy();
            copy.setCount(1);
            this.setItem(0, copy);
            return true;
        }
        return false;
    }

    /**
     * Pops the item placed on the plate and spawns it into the level as an entity
     *
     * @return True if the placed item was removed
     */
    public boolean popItem()
    {
        if(!this.getItem(0).isEmpty())
        {
            ItemStack stack = this.getItem(0);
            BlockPos pos = this.worldPosition;
            ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack.copy());
            this.setItem(0, ItemStack.EMPTY);
            this.level.addFreshEntity(entity);
            return true;
        }
        return false;
    }

    /**
     * Attempts to eat the item that is placed on the plate. This will only work if the item is
     * edible and if the player eating the item is hungry.
     *
     * @param player the player that is going to eat the item
     * @return True if the player ate the placed item
     */
    public boolean eat(Player player)
    {
        ItemStack stack = this.getItem(0);
        if(stack.has(DataComponents.FOOD) && player.canEat(false))
        {
            Services.ENTITY.spawnFoodParticles(player, stack);
            player.eat(player.level(), stack);
            this.setChanged();
            return true;
        }
        return false;
    }

    /**
     * @return The direction of the placed item
     */
    public Direction getPlacedDirection()
    {
        return Direction.from2DDataValue(this.rotation);
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

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        if(tag.contains("Rotation", Tag.TAG_INT))
        {
            this.rotation = tag.getInt("Rotation");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        tag.putInt("Rotation", this.rotation);
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
