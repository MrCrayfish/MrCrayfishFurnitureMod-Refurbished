package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ToasterHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeTypes
{
    public static final RegistryEntry<RecipeType<WorkbenchCraftingRecipe>> WORKBENCH_CRAFTING = create("workbench_crafting");
    public static final RegistryEntry<RecipeType<GrillCookingRecipe>> GRILL_COOKING = create("grill_cooking");
    public static final RegistryEntry<RecipeType<FreezerSolidifyingRecipe>> FREEZER_SOLIDIFYING = create("freezer_solidifying");
    public static final RegistryEntry<RecipeType<ToasterHeatingRecipe>> TOASTER_HEATING = create("toaster_heating");
    public static final RegistryEntry<RecipeType<CuttingBoardSlicingRecipe>> CUTTING_BOARD_SLICING = create("cutting_board_slicing");
    public static final RegistryEntry<RecipeType<CuttingBoardCombiningRecipe>> CUTTING_BOARD_COMBINING = create("cutting_board_combining");
    public static final RegistryEntry<RecipeType<MicrowaveHeatingRecipe>> MICROWAVE_HEATING = create("microwave_heating");
    public static final RegistryEntry<RecipeType<FryingPanCookingRecipe>> FRYING_PAN_COOKING = create("frying_pan_cooking");
    public static final RegistryEntry<RecipeType<RecycleBinRecyclingRecipe>> RECYCLE_BIN_RECYCLING = create("recycle_bin_recycling");

    // This should just be in Framework
    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> create(String name)
    {
        ResourceLocation id = Utils.resource(name);
        return RegistryEntry.recipeType(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return id.getPath();
            }
        });
    }
}
