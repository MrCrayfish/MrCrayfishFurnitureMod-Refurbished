package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipePropertySet;

/**
 * Author: MrCrayfish
 */
public class ModRecipePropertySets
{
    public static final ResourceKey<RecipePropertySet> FREEZER_INPUT = register("freezer_input");
    public static final ResourceKey<RecipePropertySet> OVEN_INPUT = register("oven_input");
    public static final ResourceKey<RecipePropertySet> MICROWAVE_INPUT = register("microwave_input");
    public static final ResourceKey<RecipePropertySet> CUTTING_BOARD_INPUT = register("cutting_board_input");

    private static ResourceKey<RecipePropertySet> register(String name)
    {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
