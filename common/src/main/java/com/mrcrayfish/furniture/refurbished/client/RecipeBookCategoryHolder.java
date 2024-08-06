package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public record RecipeBookCategoryHolder(String constantName, Supplier<List<ItemStack>> icons)
{
    public ItemStack[] getIconsArray()
    {
        return this.icons.get().toArray(ItemStack[]::new);
    }

    /**
     * @return The custom enum constant. Don't call too early!
     */
    public RecipeBookCategories get()
    {
        if(Services.PLATFORM.getPlatform().isFabric())
        {
            throw new UnsupportedOperationException("Cannot get custom RecipeBookCategories on Fabric");
        }
        return RecipeBookCategories.valueOf(this.constantName);
    }
}
