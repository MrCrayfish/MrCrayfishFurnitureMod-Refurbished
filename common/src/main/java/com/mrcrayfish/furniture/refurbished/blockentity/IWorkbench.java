package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import net.minecraft.world.Container;

/**
 * Author: MrCrayfish
 */
public interface IWorkbench
{
    void performCraft(WorkbenchCraftingRecipe recipe);

    boolean canCraft(WorkbenchCraftingRecipe recipe);

    Container getWorkbenchContainer();
}
