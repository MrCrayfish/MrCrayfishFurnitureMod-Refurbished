package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.DoorMatCopyRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ToasterHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.crafting.BannerDuplicateRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModRecipeSerializers
{
    public static final RegistryEntry<SimpleCookingSerializer<GrillCookingRecipe>> GRILL_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("grill_cooking"), () -> Services.RECIPE.createSimpleCookingSerializer(GrillCookingRecipe::new, 200));
    public static final RegistryEntry<SimpleCookingSerializer<FreezerSolidifyingRecipe>> FREEZER_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("freezer_solidifying"), () -> Services.RECIPE.createSimpleCookingSerializer(FreezerSolidifyingRecipe::new, 200));
    public static final RegistryEntry<SimpleCookingSerializer<ToasterHeatingRecipe>> TOASTER_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("toaster_heating"), () -> Services.RECIPE.createSimpleCookingSerializer(ToasterHeatingRecipe::new, 300));
    public static final RegistryEntry<SingleItemRecipe.Serializer<CuttingBoardRecipe>> CUTTING_BOARD_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("cutting_board_slicing"), () -> Services.RECIPE.createSingleItemSerializer(CuttingBoardRecipe::new));
    public static final RegistryEntry<SimpleCookingSerializer<MicrowaveHeatingRecipe>> MICROWAVE_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("microwave_heating"), () -> Services.RECIPE.createSimpleCookingSerializer(MicrowaveHeatingRecipe::new, 200));
    public static final RegistryEntry<SimpleCookingSerializer<FryingPanCookingRecipe>> FRYING_PAN_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("frying_pan_cooking"), () -> Services.RECIPE.createSimpleCookingSerializer(FryingPanCookingRecipe::new, 200));
    public static final RegistryEntry<RecycleBinRecyclingRecipe.Serializer> RECYCLE_BIN_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("recycle_bin_recycling"), RecycleBinRecyclingRecipe.Serializer::new);
    public static final RegistryEntry<RecipeSerializer<DoorMatCopyRecipe>> DOOR_MAT_COPY_RECIPE = RegistryEntry.recipeSerializer(Utils.resource("crafting_special_door_mat_copy"), () -> new SimpleCraftingRecipeSerializer<>(DoorMatCopyRecipe::new));

}
