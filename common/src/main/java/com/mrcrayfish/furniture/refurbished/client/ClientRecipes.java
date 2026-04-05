package com.mrcrayfish.furniture.refurbished.client;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class ClientRecipes
{
    private List<RecipeHolder<WorkbenchContructingRecipe>> workbenchRecipes = List.of();

    public void accept(List<RecipeHolder<WorkbenchContructingRecipe>> recipes)
    {
        this.workbenchRecipes = ImmutableList.copyOf(recipes);
    }

    public List<RecipeHolder<WorkbenchContructingRecipe>> workbenchRecipes()
    {
        return this.workbenchRecipes;
    }

    public interface Access
    {
        ClientRecipes refurbished_furniture$clientRecipes();
    }

    public static Optional<ClientRecipes> get()
    {
        return Optional.ofNullable(Minecraft.getInstance().getConnection()).map(listener -> {
            return ((Access) listener).refurbished_furniture$clientRecipes();
        });
    }
}
