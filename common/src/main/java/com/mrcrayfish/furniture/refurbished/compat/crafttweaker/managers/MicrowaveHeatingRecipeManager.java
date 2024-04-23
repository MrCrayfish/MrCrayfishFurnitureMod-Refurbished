package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.MicrowaveHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/Microwave/Heating")
@ZenCodeType.Name("mods.refurbished_furniture.MicrowaveHeating")
public class MicrowaveHeatingRecipeManager implements IRecipeManager<MicrowaveHeatingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, String category, IIngredient ingredient, IItemStack result, @ZenCodeType.OptionalInt(200) int time)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new MicrowaveHeatingRecipe(CraftTweakerConstants.rl(name), ProcessingRecipe.Category.byName(category), ingredient.asVanillaIngredient(), result.getInternal(), time)));
    }

    @Override
    public RecipeType<MicrowaveHeatingRecipe> getRecipeType()
    {
        return ModRecipeTypes.MICROWAVE_HEATING.get();
    }
}




