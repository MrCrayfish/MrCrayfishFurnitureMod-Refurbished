package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.client.RecipeBookCategoryHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

/**
 * Author: MrCrayfish
 */
public class ModRecipeBookCategories
{
    public static final RecipeBookCategoryHolder FREEZER_SEARCH = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_FREEZER_SEARCH", () -> new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategoryHolder FREEZER_BLOCKS = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_FREEZER_BLOCKS", () -> new ItemStack[]{new ItemStack(Items.ICE)});
    public static final RecipeBookCategoryHolder FREEZER_ITEMS = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_FREEZER_ITEMS", () -> new ItemStack[]{new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.HONEYCOMB)});
    public static final RecipeBookCategoryHolder FREEZER_FOOD = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_FREEZER_FOOD", () -> new ItemStack[]{new ItemStack(Items.PORKCHOP)});
    public static final RecipeBookCategoryHolder FREEZER_MISC = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_FREEZER_MISC", () -> new ItemStack[]{new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.FERN)});
    public static final RecipeBookCategoryHolder MICROWAVE_SEARCH = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_MICROWAVE_SEARCH", () -> new ItemStack[]{new ItemStack(Items.COMPASS)});
    public static final RecipeBookCategoryHolder MICROWAVE_BLOCKS = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_MICROWAVE_BLOCKS", () -> new ItemStack[]{new ItemStack(Blocks.GLOW_LICHEN)});
    public static final RecipeBookCategoryHolder MICROWAVE_ITEMS = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_MICROWAVE_ITEMS", () -> new ItemStack[]{new ItemStack(Items.AMETHYST_SHARD)});
    public static final RecipeBookCategoryHolder MICROWAVE_FOOD = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_MICROWAVE_FOOD", () -> new ItemStack[]{new ItemStack(Items.COOKIE)});
    public static final RecipeBookCategoryHolder MICROWAVE_MISC = new RecipeBookCategoryHolder("REFURBISHED_FURNITURE_MICROWAVE_MISC", () -> new ItemStack[]{new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.FERN)});
}
