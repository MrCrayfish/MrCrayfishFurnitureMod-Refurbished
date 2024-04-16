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
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/CuttingBoard/Slicing")
@ZenCodeType.Name("mods.refurbished_furniture.CuttingBoardSlicing")
public class CuttingBoardSlicingRecipeManager implements IRecipeManager<CuttingBoardSlicingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient ingredient, IItemStack result)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new CuttingBoardSlicingRecipe(CraftTweakerConstants.rl(name), "", ingredient.asVanillaIngredient(), result.getInternal())));
    }

    @Override
    public RecipeType<CuttingBoardSlicingRecipe> getRecipeType()
    {
        return ModRecipeTypes.CUTTING_BOARD_SLICING.get();
    }
}




