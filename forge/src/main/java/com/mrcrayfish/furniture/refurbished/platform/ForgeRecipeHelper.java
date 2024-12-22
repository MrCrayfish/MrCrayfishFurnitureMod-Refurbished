package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
public class ForgeRecipeHelper implements IRecipeHelper
{
    @Override
    public <T extends SingleItemRecipe> SingleItemRecipe.Serializer<T> createSingleItemSerializer(ISingleBuilder<T> builder)
    {
        return new SingleItemRecipe.Serializer<>(builder::create){};
    }
}
