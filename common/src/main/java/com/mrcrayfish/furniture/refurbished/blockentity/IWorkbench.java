package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;

/**
 * Author: MrCrayfish
 */
public interface IWorkbench
{
    void performCraft(WorkbenchContructingRecipe recipe);

    boolean canCraft(WorkbenchContructingRecipe recipe);

    DataSlot selectedRecipeDataSlot();

    DataSlot searchNeighboursDataSlot();

    Container getWorkbenchContainer();

    ContainerLevelAccess createLevelAccess();
}
