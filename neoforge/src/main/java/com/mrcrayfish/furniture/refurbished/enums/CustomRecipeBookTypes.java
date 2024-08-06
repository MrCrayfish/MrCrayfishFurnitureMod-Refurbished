package com.mrcrayfish.furniture.refurbished.enums;

import net.minecraft.world.inventory.RecipeBookType;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("unused")
public class CustomRecipeBookTypes
{
    public static final EnumProxy<RecipeBookType> FREEZER_PROXY = new EnumProxy<>(RecipeBookType.class);
    public static final EnumProxy<RecipeBookType> MICROWAVE_PROXY = new EnumProxy<>(RecipeBookType.class);
    public static final EnumProxy<RecipeBookType> OVEN_PROXY = new EnumProxy<>(RecipeBookType.class);
}
