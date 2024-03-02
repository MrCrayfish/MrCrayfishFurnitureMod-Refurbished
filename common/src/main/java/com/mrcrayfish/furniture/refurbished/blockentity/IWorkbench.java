package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.crafting.RecipeHolder;

/**
 * Author: MrCrayfish
 */
public interface IWorkbench
{
    void performCraft(RecipeHolder<WorkbenchContructingRecipe> recipe);

    boolean canCraft(RecipeHolder<WorkbenchContructingRecipe> recipe);

    DataSlot getSelectedRecipeData();

    Container getWorkbenchContainer();

    ContainerLevelAccess createLevelAccess();
}
