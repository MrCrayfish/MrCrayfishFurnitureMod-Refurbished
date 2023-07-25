package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class CommonRecipeProvider
{
    private final Consumer<FinishedRecipe> consumer;
    private final Function<ItemLike, CriterionTriggerInstance> has;

    public CommonRecipeProvider(Consumer<FinishedRecipe> consumer, Function<ItemLike, CriterionTriggerInstance> has)
    {
        this.consumer = consumer;
        this.has = has;
    }

    public void run()
    {
        this.grillCooking(Items.BEEF, Items.COOKED_BEEF, 600);
    }

    private void grillCooking(ItemLike rawItem, ItemLike cookedItem, int cookingTime)
    {
        String rawName = rawItem.asItem().toString();
        String cookedName = cookedItem.asItem().toString();
        SimpleCookingRecipeBuilder
                .generic(Ingredient.of(rawItem), RecipeCategory.FOOD, cookedItem, 0.35F, cookingTime, ModRecipeSerializers.GRILL_RECIPE.get())
                .unlockedBy("has_" + rawName, this.has.apply(rawItem))
                .save(this.consumer, cookedName + "_from_grill_cooking");
    }
}
