package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.ISingleBuilder;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleItemRecipe;

import java.util.Collection;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FabricRecipeHelper implements IRecipeHelper
{
    @Override
    public <T extends SingleItemRecipe> SingleItemRecipe.Serializer<T> createSingleItemSerializer(ISingleBuilder<T> builder)
    {
        return new SingleItemRecipe.Serializer<>(builder::create){};
    }

    @Override
    public Collection<RecipeHolder<WorkbenchContructingRecipe>> getWorkbenchRecipes(ServerLevel level)
    {
        return level.getServer().getRecipeManager().recipes.byType(ModRecipeTypes.WORKBENCH_CONSTRUCTING.get());
    }
}
