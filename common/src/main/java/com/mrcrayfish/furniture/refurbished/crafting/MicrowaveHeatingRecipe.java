package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.display.MicrowaveRecipeDisplay;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class MicrowaveHeatingRecipe extends ProcessingRecipe.Item
{
    public MicrowaveHeatingRecipe(Category category, Ingredient ingredient, ItemStackTemplate result, int time)
    {
        super(ModRecipeTypes.MICROWAVE_HEATING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<MicrowaveHeatingRecipe> getSerializer()
    {
        return ModRecipeSerializers.MICROWAVE_RECIPE.get();
    }

    @Override
    public List<RecipeDisplay> display()
    {
        return List.of(new MicrowaveRecipeDisplay(
            this.ingredient.display(),
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(ModBlocks.MICROWAVE_LIGHT.get().asItem()),
            this.time
        ));
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return switch(this.category) {
            case BLOCKS -> ModRecipeBookCategories.MICROWAVE_BLOCKS.get();
            case ITEMS -> ModRecipeBookCategories.MICROWAVE_ITEMS.get();
            case FOOD -> ModRecipeBookCategories.MICROWAVE_FOOD.get();
            case MISC -> ModRecipeBookCategories.MICROWAVE_MISC.get();
        };
    }
}
