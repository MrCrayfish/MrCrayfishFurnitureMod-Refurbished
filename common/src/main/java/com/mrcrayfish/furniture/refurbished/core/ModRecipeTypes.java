package com.mrcrayfish.furniture.refurbished.core;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.*;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeTypes
{
    public static final RegistryEntry<RecipeType<WorkbenchContructingRecipe>> WORKBENCH_CONSTRUCTING = RegistryEntry.recipeType(Utils.resource("workbench_constructing"));
    public static final RegistryEntry<RecipeType<GrillCookingRecipe>> GRILL_COOKING = RegistryEntry.recipeType(Utils.resource("grill_cooking"));
    public static final RegistryEntry<RecipeType<FreezerSolidifyingRecipe>> FREEZER_SOLIDIFYING = RegistryEntry.recipeType(Utils.resource("freezer_solidifying"));
    public static final RegistryEntry<RecipeType<ToasterHeatingRecipe>> TOASTER_HEATING = RegistryEntry.recipeType(Utils.resource("toaster_heating"));
    public static final RegistryEntry<RecipeType<CuttingBoardSlicingRecipe>> CUTTING_BOARD_SLICING = RegistryEntry.recipeType(Utils.resource("cutting_board_slicing"));
    public static final RegistryEntry<RecipeType<CuttingBoardCombiningRecipe>> CUTTING_BOARD_COMBINING = RegistryEntry.recipeType(Utils.resource("cutting_board_combining"));
    public static final RegistryEntry<RecipeType<MicrowaveHeatingRecipe>> MICROWAVE_HEATING = RegistryEntry.recipeType(Utils.resource("microwave_heating"));
    public static final RegistryEntry<RecipeType<ProcessingRecipe.Item>> FRYING_PAN_COOKING = RegistryEntry.recipeType(Utils.resource("frying_pan_cooking"));
    public static final RegistryEntry<RecipeType<OvenBakingRecipe>> OVEN_BAKING = RegistryEntry.recipeType(Utils.resource("oven_baking"));

    // A list of recipe types that should be ignored when ClientRecipeBook#getCategory is called
    // This just prevents warning of unknown recipe since they don't use the recipe book system
    public static final Supplier<List<RecipeType<?>>> IGNORED_RECIPE_TYPES = Suppliers.memoize(() -> ImmutableList.of(
        WORKBENCH_CONSTRUCTING.get(),
        GRILL_COOKING.get(),
        TOASTER_HEATING.get(),
        CUTTING_BOARD_SLICING.get(),
        CUTTING_BOARD_COMBINING.get(),
        FRYING_PAN_COOKING.get()
    ));
}
