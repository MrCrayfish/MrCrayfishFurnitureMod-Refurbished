package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.*;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.RecipeType;

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
    public static final RegistryEntry<RecipeType<FryingPanCookingRecipe>> FRYING_PAN_COOKING = RegistryEntry.recipeType(Utils.resource("frying_pan_cooking"));
    public static final RegistryEntry<RecipeType<OvenBakingRecipe>> OVEN_BAKING = RegistryEntry.recipeType(Utils.resource("oven_baking"));
}
