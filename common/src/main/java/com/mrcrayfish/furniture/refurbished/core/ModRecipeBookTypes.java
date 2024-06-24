package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.client.RecipeBookTypeHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ModRecipeBookTypes
{
    private static final List<RecipeBookTypeHolder> ALL_TYPES = new ArrayList<>();
    public static final RecipeBookTypeHolder FREEZER = createHolder("REFURBISHED_FURNITURE_FREEZER");
    public static final RecipeBookTypeHolder MICROWAVE = createHolder("REFURBISHED_FURNITURE_MICROWAVE");
    public static final RecipeBookTypeHolder OVEN = createHolder("REFURBISHED_FURNITURE_OVEN");

    private static RecipeBookTypeHolder createHolder(String constantName)
    {
        RecipeBookTypeHolder holder = new RecipeBookTypeHolder(constantName);
        ALL_TYPES.add(holder);
        return holder;
    }

    public static List<RecipeBookTypeHolder> getAllTypes()
    {
        return Collections.unmodifiableList(ALL_TYPES);
    }
}
