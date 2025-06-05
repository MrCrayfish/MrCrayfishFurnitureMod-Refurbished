package com.mrcrayfish.furniture.refurbished.compat.jei;

import net.minecraft.world.item.crafting.RecipeMap;

public class SyncedRecipes
{
    private static RecipeMap map = RecipeMap.EMPTY;

    public static RecipeMap getMap()
    {
        return map;
    }

    public static void setMap(RecipeMap map)
    {
        SyncedRecipes.map = map;
    }

    public static void reset()
    {
        SyncedRecipes.map = RecipeMap.EMPTY;
    }
}
