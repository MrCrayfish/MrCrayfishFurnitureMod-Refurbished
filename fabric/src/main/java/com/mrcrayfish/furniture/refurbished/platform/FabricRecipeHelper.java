package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.crafting.ICookingBuilder;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

/**
 * Author: MrCrayfish
 */
public class FabricRecipeHelper implements IRecipeHelper
{
    @Override
    public <T extends AbstractCookingRecipe> SimpleCookingSerializer<T> createSimpleCookingSerializer(ICookingBuilder<T> builder, int defaultCookingTime)
    {
        return new SimpleCookingSerializer<>(builder::create, defaultCookingTime);
    }
}
