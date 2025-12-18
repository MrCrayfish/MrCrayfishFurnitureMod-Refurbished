package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.display.OvenRecipeDisplay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class OvenBakingRecipe extends ProcessingRecipe.ItemWithCount
{
    public OvenBakingRecipe(Category category, Ingredient ingredient, ItemStack result, int time)
    {
        super(ModRecipeTypes.OVEN_BAKING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<OvenBakingRecipe> getSerializer()
    {
        return ModRecipeSerializers.OVEN_BAKING.get();
    }

    @Override
    public List<RecipeDisplay> display()
    {
        return List.of(new OvenRecipeDisplay(
            this.ingredient.display(),
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(ModBlocks.STOVE_LIGHT.get().asItem()),
            this.time
        ));
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return switch(this.category) {
            case BLOCKS -> ModRecipeBookCategories.OVEN_BLOCKS.get();
            case ITEMS -> ModRecipeBookCategories.OVEN_ITEMS.get();
            case FOOD -> ModRecipeBookCategories.OVEN_FOOD.get();
            case MISC -> ModRecipeBookCategories.OVEN_MISC.get();
        };
    }
}
