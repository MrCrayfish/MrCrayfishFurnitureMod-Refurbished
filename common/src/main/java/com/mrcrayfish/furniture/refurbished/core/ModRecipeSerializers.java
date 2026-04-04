package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.*;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeSerializers
{
    public static final RegistryEntry<RecipeSerializer<WorkbenchContructingRecipe>> WORKBENCH_RECIPE = RegistryEntry.recipeSerializer(Utils.id("workbench_constructing"), () -> {
        return new RecipeSerializer<>(WorkbenchContructingRecipe.CODEC, WorkbenchContructingRecipe.STREAM_CODEC);
    });
    public static final RegistryEntry<RecipeSerializer<GrillCookingRecipe>> GRILL_RECIPE = RegistryEntry.recipeSerializer(Utils.id("grill_cooking"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(GrillCookingRecipe::new, 200), ProcessingRecipe.Item.createStreamCodec(GrillCookingRecipe::new, 200));
    });
    public static final RegistryEntry<RecipeSerializer<FreezerSolidifyingRecipe>> FREEZER_RECIPE = RegistryEntry.recipeSerializer(Utils.id("freezer_solidifying"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(FreezerSolidifyingRecipe::new, 200), ProcessingRecipe.Item.createStreamCodec(FreezerSolidifyingRecipe::new, 200));
    });
    public static final RegistryEntry<RecipeSerializer<ToasterHeatingRecipe>> TOASTER_RECIPE = RegistryEntry.recipeSerializer(Utils.id("toaster_heating"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(ToasterHeatingRecipe::new, 300), ProcessingRecipe.Item.createStreamCodec(ToasterHeatingRecipe::new, 300));
    });
    public static final RegistryEntry<RecipeSerializer<CuttingBoardSlicingRecipe>> CUTTING_BOARD_SLICING_RECIPE = RegistryEntry.recipeSerializer(Utils.id("cutting_board_slicing"), () -> {
        return Services.RECIPE.createSingleItemSerializer(CuttingBoardSlicingRecipe::new);
    });
    public static final RegistryEntry<RecipeSerializer<CuttingBoardCombiningRecipe>> CUTTING_BOARD_COMBINING_RECIPE = RegistryEntry.recipeSerializer(Utils.id("cutting_board_combining"), () -> {
        return new RecipeSerializer<>(CuttingBoardCombiningRecipe.CODEC, CuttingBoardCombiningRecipe.STREAM_CODEC);
    });
    public static final RegistryEntry<RecipeSerializer<MicrowaveHeatingRecipe>> MICROWAVE_RECIPE = RegistryEntry.recipeSerializer(Utils.id("microwave_heating"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(MicrowaveHeatingRecipe::new, 200), ProcessingRecipe.Item.createStreamCodec(MicrowaveHeatingRecipe::new, 200));
    });
    public static final RegistryEntry<RecipeSerializer<FryingPanCookingRecipe>> FRYING_PAN_RECIPE = RegistryEntry.recipeSerializer(Utils.id("frying_pan_cooking"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(FryingPanCookingRecipe::new, 200), ProcessingRecipe.Item.createStreamCodec(FryingPanCookingRecipe::new, 200));
    });
    public static final RegistryEntry<RecipeSerializer<DoorMatCloneRecipe>> DOOR_MAT_COPY_RECIPE = RegistryEntry.recipeSerializer(Utils.id("crafting_special_door_mat_copy"), () -> {
        return new RecipeSerializer<>(DoorMatCloneRecipe.CODEC, DoorMatCloneRecipe.STREAM_CODEC);
    });
    public static final RegistryEntry<RecipeSerializer<OvenBakingRecipe>> OVEN_BAKING = RegistryEntry.recipeSerializer(Utils.id("oven_baking"), () -> {
        return new RecipeSerializer<>(ProcessingRecipe.Item.createCodec(OvenBakingRecipe::new, 300), ProcessingRecipe.Item.createStreamCodec(OvenBakingRecipe::new, 300));
    });
}
