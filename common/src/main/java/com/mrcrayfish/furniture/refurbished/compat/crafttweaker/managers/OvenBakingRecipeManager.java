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
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/Oven/Baking")
@ZenCodeType.Name("mods.refurbished_furniture.OvenBaking")
public class OvenBakingRecipeManager implements IRecipeManager<OvenBakingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient input, IItemStack output, @ZenCodeType.OptionalInt(300) int processTime)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new OvenBakingRecipe(CraftTweakerConstants.rl(name), input.asVanillaIngredient(), output.getInternal(), processTime)));
    }

    @Override
    public RecipeType<OvenBakingRecipe> getRecipeType()
    {
        return ModRecipeTypes.OVEN_BAKING.get();
    }
}




