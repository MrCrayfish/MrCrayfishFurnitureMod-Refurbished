package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class FurnitureRecipeProvider extends FabricRecipeProvider
{
    public FurnitureRecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        new CommonRecipeProvider(consumer, RecipeProvider::has).run();
        SpecialRecipeBuilder.special(ModRecipeSerializers.DOOR_MAT_COPY_RECIPE.get()).save(consumer, "door_mat_copy");
    }
}
