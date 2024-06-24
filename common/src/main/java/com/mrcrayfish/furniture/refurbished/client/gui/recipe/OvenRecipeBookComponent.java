package com.mrcrayfish.furniture.refurbished.client.gui.recipe;

import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class OvenRecipeBookComponent extends RecipeBookComponent
{
    @Override
    public void setupGhostRecipe(RecipeHolder<?> holder, List<Slot> slots)
    {
        if(holder.value() instanceof OvenBakingRecipe bakingRecipe)
        {
            ItemStack stack = bakingRecipe.getResultItem(this.minecraft.level.registryAccess());
            this.ghostRecipe.setRecipe(holder);
            this.ghostRecipe.addIngredient(Ingredient.of(stack), slots.get(3).x, slots.get(3).y);
            this.ghostRecipe.addIngredient(bakingRecipe.getIngredient(), slots.get(0).x, slots.get(0).y);
        }
    }
}
