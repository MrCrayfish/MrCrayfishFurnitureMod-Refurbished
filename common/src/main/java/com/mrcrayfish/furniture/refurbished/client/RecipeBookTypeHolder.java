package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.world.inventory.RecipeBookType;

/**
 * Author: MrCrayfish
 */
public record RecipeBookTypeHolder(String constantName)
{
    /**
     * @return The custom enum constant. Don't call too early!
     */
    public RecipeBookType get()
    {
        if(Services.PLATFORM.getPlatform().isFabric())
        {
            throw new UnsupportedOperationException("Cannot get custom RecipeBookCategories on Fabric");
        }
        return RecipeBookType.valueOf(this.constantName);
    }
}
