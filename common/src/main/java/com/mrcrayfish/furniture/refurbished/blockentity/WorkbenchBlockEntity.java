package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class WorkbenchBlockEntity extends ElectricityModuleLootBlockEntity
{
    public WorkbenchBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.WORKBENCH.get(), pos, state);
    }

    public WorkbenchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 9);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof WorkbenchMenu;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "workbench");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new WorkbenchMenu(windowId, playerInventory, this);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public void setPowered(boolean powered)
    {

    }
}
