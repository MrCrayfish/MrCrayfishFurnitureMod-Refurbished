package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.display.FreezerRecipeDisplay;
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
public class FreezerSolidifyingRecipe extends ProcessingRecipe.Item
{
    public FreezerSolidifyingRecipe(Category category, Ingredient ingredient, ItemStackTemplate result, int time)
    {
        super(ModRecipeTypes.FREEZER_SOLIDIFYING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<FreezerSolidifyingRecipe> getSerializer()
    {
        return ModRecipeSerializers.FREEZER_RECIPE.get();
    }

    @Override
    public List<RecipeDisplay> display()
    {
        return List.of(new FreezerRecipeDisplay(
            this.ingredient.display(),
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(ModBlocks.FRIDGE_LIGHT.get().asItem()),
            this.time
        ));
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return switch(this.category) {
            case BLOCKS -> ModRecipeBookCategories.FREEZER_BLOCKS.get();
            case ITEMS -> ModRecipeBookCategories.FREEZER_ITEMS.get();
            case FOOD -> ModRecipeBookCategories.FREEZER_FOOD.get();
            case MISC -> ModRecipeBookCategories.FREEZER_MISC.get();
        };
    }
}
