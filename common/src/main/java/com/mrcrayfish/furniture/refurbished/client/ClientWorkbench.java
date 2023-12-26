package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbench implements IWorkbench
{
    private final Container container;

    public ClientWorkbench(Container container)
    {
        this.container = container;
    }

    @Override
    public void performCraft(WorkbenchCraftingRecipe recipe) {}

    @Override
    public boolean canCraft(WorkbenchCraftingRecipe recipe)
    {
        return true;
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
