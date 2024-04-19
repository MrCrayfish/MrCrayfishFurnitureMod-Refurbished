package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IWorkbench;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.crafting.RecipeHolder;

/**
 * Author: MrCrayfish
 */
public class ClientWorkbench implements IWorkbench
{
    private final DataSlot selectedRecipe = DataSlot.standalone();
    private final DataSlot searchNeighbours = DataSlot.standalone();
    private final Container container;

    public ClientWorkbench(Container container)
    {
        this.container = container;
    }

    @Override
    public void performCraft(RecipeHolder<WorkbenchContructingRecipe> recipe) {}

    @Override
    public boolean canCraft(RecipeHolder<WorkbenchContructingRecipe> recipe)
    {
        return true;
    }

    @Override
    public DataSlot selectedRecipeDataSlot()
    {
        return this.selectedRecipe;
    }

    @Override
    public DataSlot searchNeighboursDataSlot()
    {
        return this.searchNeighbours;
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
