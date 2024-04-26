package com.mrcrayfish.furniture.refurbished.compat.crafttweaker;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.google.gson.reflect.TypeToken;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;

/**
 * Author: MrCrayfish
 */
public class CustomRecipeComponents
{
    public static class Metadata
    {
        public static final IRecipeComponent<ProcessingRecipe.Category> PROCESSING_CATEGORY = IRecipeComponent.simple(
            Utils.resource("metadata/processing_category"),
            new TypeToken<>() {},
            Object::equals
        );
    }
}
