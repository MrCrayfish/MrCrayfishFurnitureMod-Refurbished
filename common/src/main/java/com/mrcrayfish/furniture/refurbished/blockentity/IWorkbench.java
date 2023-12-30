package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;

/**
 * Author: MrCrayfish
 */
public interface IWorkbench
{
    void performCraft(WorkbenchCraftingRecipe recipe);

    boolean canCraft(WorkbenchCraftingRecipe recipe);

    DataSlot getSelectedRecipeData();

    Container getWorkbenchContainer();

    ContainerLevelAccess createLevelAccess();
}
