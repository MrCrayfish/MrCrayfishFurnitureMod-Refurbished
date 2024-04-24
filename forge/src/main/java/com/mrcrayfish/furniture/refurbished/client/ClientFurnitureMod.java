package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import net.minecraft.client.RecipeBookCategories;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod
{
    public static void init()
    {
        ModRecipeBookCategories.getAllCategories().forEach(holder -> {
            RecipeBookCategories.create(holder.getConstantName(), holder.getIcons().get());
        });
    }
}
