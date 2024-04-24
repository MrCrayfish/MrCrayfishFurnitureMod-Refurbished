package com.mrcrayfish.furniture.refurbished.client;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public final class RecipeBookCategoryHolder
{
    private final String constantName;
    private final Supplier<ItemStack[]> icons;
    private RecipeBookCategories category;

    public RecipeBookCategoryHolder(String constantName, Supplier<ItemStack[]> icons)
    {
        this.constantName = constantName;
        this.icons = icons;
    }

    public String getConstantName()
    {
        return this.constantName;
    }

    public Supplier<ItemStack[]> getIcons()
    {
        return this.icons;
    }

    /**
     * @return The custom enum constant. Don't call too early!
     */
    public RecipeBookCategories get()
    {
        if(this.category == null)
        {
            this.category = RecipeBookCategories.valueOf(this.constantName);
        }
        return this.category;
    }
}
