package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbench implements IWorkbench
{
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final Container container;

    public ClientWorkbench(Container container)
    {
        this.container = container;
    }

    @Override
    public void performCraft(WorkbenchContructingRecipe recipe) {}

    @Override
    public boolean canCraft(WorkbenchContructingRecipe recipe)
    {
        return true;
    }

    @Override
    public DataSlot getSelectedRecipeData()
    {
        return this.selectedRecipe;
    }

    @Override
    public Container getWorkbenchContainer()
    {
        return this.container;
    }

    @Override
    public ContainerLevelAccess createLevelAccess()
    {
        return ContainerLevelAccess.NULL;
    }
}
