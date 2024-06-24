package com.mrcrayfish.furniture.refurbished.client.gui.recipe;

import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class MicrowaveRecipeBookComponent extends RecipeBookComponent
{
    @Override
    public void setupGhostRecipe(RecipeHolder<?> holder, List<Slot> slots)
    {
        if(holder.value() instanceof MicrowaveHeatingRecipe heatingRecipe)
        {
            Slot inputSlot = slots.get(0);
            Slot resultSlot = slots.get(this.menu.getResultSlotIndex());
            ItemStack stack = heatingRecipe.getResultItem(this.minecraft.level.registryAccess());
            this.ghostRecipe.setRecipe(holder);
            this.ghostRecipe.addIngredient(Ingredient.of(stack), resultSlot.x, resultSlot.y);
            this.ghostRecipe.addIngredient(heatingRecipe.getIngredient(), inputSlot.x, inputSlot.y);
        }
    }
}