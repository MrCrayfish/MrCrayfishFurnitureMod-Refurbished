package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.client.RecipeBookCategoryHolder;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ModRecipeBookCategories
{
    private static final List<RecipeBookCategoryHolder> ALL_CATEGORIES = new ArrayList<>();
    public static final RecipeBookCategoryHolder FREEZER_SEARCH = createHolder("REFURBISHED_FURNITURE_FREEZER_SEARCH", () -> List.of(new ItemStack(Items.COMPASS)));
    public static final RecipeBookCategoryHolder FREEZER_BLOCKS = createHolder("REFURBISHED_FURNITURE_FREEZER_BLOCKS", () -> List.of(new ItemStack(Items.ICE)));
    public static final RecipeBookCategoryHolder FREEZER_ITEMS = createHolder("REFURBISHED_FURNITURE_FREEZER_ITEMS", () -> List.of(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.HONEYCOMB)));
    public static final RecipeBookCategoryHolder FREEZER_FOOD = createHolder("REFURBISHED_FURNITURE_FREEZER_FOOD", () -> List.of(new ItemStack(Items.PORKCHOP)));
    public static final RecipeBookCategoryHolder FREEZER_MISC = createHolder("REFURBISHED_FURNITURE_FREEZER_MISC", () -> List.of(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.FERN)));
    public static final RecipeBookCategoryHolder MICROWAVE_SEARCH = createHolder("REFURBISHED_FURNITURE_MICROWAVE_SEARCH", () -> List.of(new ItemStack(Items.COMPASS)));
    public static final RecipeBookCategoryHolder MICROWAVE_BLOCKS = createHolder("REFURBISHED_FURNITURE_MICROWAVE_BLOCKS", () -> List.of(new ItemStack(Blocks.GLOW_LICHEN)));
    public static final RecipeBookCategoryHolder MICROWAVE_ITEMS = createHolder("REFURBISHED_FURNITURE_MICROWAVE_ITEMS", () -> List.of(new ItemStack(Items.AMETHYST_SHARD)));
    public static final RecipeBookCategoryHolder MICROWAVE_FOOD = createHolder("REFURBISHED_FURNITURE_MICROWAVE_FOOD", () -> List.of(new ItemStack(Items.COOKIE)));
    public static final RecipeBookCategoryHolder MICROWAVE_MISC = createHolder("REFURBISHED_FURNITURE_MICROWAVE_MISC", () -> List.of(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.FERN)));
    public static final RecipeBookCategoryHolder OVEN_SEARCH = createHolder("REFURBISHED_FURNITURE_OVEN_SEARCH", () -> List.of(new ItemStack(Items.COMPASS)));
    public static final RecipeBookCategoryHolder OVEN_BLOCKS = createHolder("REFURBISHED_FURNITURE_OVEN_BLOCKS", () -> List.of(new ItemStack(Blocks.DRIED_KELP_BLOCK)));
    public static final RecipeBookCategoryHolder OVEN_ITEMS = createHolder("REFURBISHED_FURNITURE_OVEN_ITEMS", () -> List.of(new ItemStack(Items.CHARCOAL), new ItemStack(Items.WHEAT)));
    public static final RecipeBookCategoryHolder OVEN_FOOD = createHolder("REFURBISHED_FURNITURE_OVEN_FOOD", () -> List.of(new ItemStack(Items.PORKCHOP)));
    public static final RecipeBookCategoryHolder OVEN_MISC = createHolder("REFURBISHED_FURNITURE_OVEN_MISC", () -> List.of(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.STICK)));

    private static RecipeBookCategoryHolder createHolder(String constantName, Supplier<List<ItemStack>> icons)
    {
        RecipeBookCategoryHolder holder = new RecipeBookCategoryHolder(constantName, icons);
        ALL_CATEGORIES.add(holder);
        return holder;
    }

    public static List<RecipeBookCategoryHolder> getAllCategories()
    {
        return Collections.unmodifiableList(ALL_CATEGORIES);
    }
}
