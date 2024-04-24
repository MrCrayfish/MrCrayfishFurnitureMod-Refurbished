package com.mrcrayfish.furniture.refurbished.client;

import net.minecraft.world.inventory.RecipeBookType;

/**
 * Author: MrCrayfish
 */
public class RecipeBookTypeHolder
{
    private final String constantName;
    private RecipeBookType type;

    public RecipeBookTypeHolder(String constantName)
    {
        this.constantName = constantName;
    }

    public String getConstantName()
    {
        return this.constantName;
    }

    /**
     * @return The custom enum constant. Don't call too early!
     */
    public RecipeBookType get()
    {
        if(this.type == null)
        {
            this.type = RecipeBookType.valueOf(this.constantName);
        }
        return this.type;
    }
}
