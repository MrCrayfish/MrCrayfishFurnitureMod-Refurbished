package com.mrcrayfish.furniture.refurbished.enums;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import net.minecraft.client.RecipeBookCategories;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("unused")
public class CustomRecipeBookCategories
{
    public static final EnumProxy<RecipeBookCategories> FREEZER_SEARCH_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.FREEZER_SEARCH.icons()
    );
    public static final EnumProxy<RecipeBookCategories> FREEZER_BLOCKS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.FREEZER_BLOCKS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> FREEZER_ITEMS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.FREEZER_ITEMS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> FREEZER_FOOD_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.FREEZER_FOOD.icons()
    );
    public static final EnumProxy<RecipeBookCategories> FREEZER_MISC_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.FREEZER_MISC.icons()
    );
    public static final EnumProxy<RecipeBookCategories> MICROWAVE_SEARCH_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.MICROWAVE_SEARCH.icons()
    );
    public static final EnumProxy<RecipeBookCategories> MICROWAVE_BLOCKS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.MICROWAVE_BLOCKS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> MICROWAVE_ITEMS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.MICROWAVE_ITEMS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> MICROWAVE_FOOD_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.MICROWAVE_FOOD.icons()
    );
    public static final EnumProxy<RecipeBookCategories> MICROWAVE_MISC_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.MICROWAVE_MISC.icons()
    );
    public static final EnumProxy<RecipeBookCategories> OVEN_SEARCH_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.OVEN_SEARCH.icons()
    );
    public static final EnumProxy<RecipeBookCategories> OVEN_BLOCKS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.OVEN_BLOCKS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> OVEN_ITEMS_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.OVEN_ITEMS.icons()
    );
    public static final EnumProxy<RecipeBookCategories> OVEN_FOOD_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.OVEN_FOOD.icons()
    );
    public static final EnumProxy<RecipeBookCategories> OVEN_MISC_PROXY = new EnumProxy<>(
        RecipeBookCategories.class, ModRecipeBookCategories.OVEN_MISC.icons()
    );
}
