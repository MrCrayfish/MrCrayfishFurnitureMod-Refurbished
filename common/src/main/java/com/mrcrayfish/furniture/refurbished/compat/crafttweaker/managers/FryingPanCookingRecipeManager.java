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
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/FryingPan/Cooking")
@ZenCodeType.Name("mods.refurbished_furniture.FryingPanCooking")
public class FryingPanCookingRecipeManager implements IRecipeManager<AbstractCookingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient input, IItemStack output, @ZenCodeType.OptionalFloat float experience, @ZenCodeType.OptionalInt(200) int cookingTime)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new RecipeHolder<>(CraftTweakerConstants.rl(name), new FryingPanCookingRecipe("", CookingBookCategory.MISC, input.asVanillaIngredient(), output.getInternal(), experience, cookingTime))));
    }

    @Override
    public RecipeType<AbstractCookingRecipe> getRecipeType()
    {
        return ModRecipeTypes.FRYING_PAN_COOKING.get();
    }
}




