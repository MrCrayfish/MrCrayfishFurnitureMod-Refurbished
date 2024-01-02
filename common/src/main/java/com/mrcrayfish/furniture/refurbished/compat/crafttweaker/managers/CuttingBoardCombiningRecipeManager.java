package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/CuttingBoard/Combining")
@ZenCodeType.Name("mods.refurbished_furniture.CuttingBoardCombining")
public class CuttingBoardCombiningRecipeManager implements IRecipeManager<CuttingBoardCombiningRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient[] input)
    {
        if(!this.validate(input))
            return;
        Ingredient[] ingredients = Arrays.stream(input).map(IIngredient::asVanillaIngredient).toArray(Ingredient[]::new);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new CuttingBoardCombiningRecipe(CraftTweakerConstants.rl(name), ingredients, output.getInternal())));
    }

    @Override
    public RecipeType<CuttingBoardCombiningRecipe> getRecipeType()
    {
        return ModRecipeTypes.CUTTING_BOARD_COMBINING.get();
    }

    private boolean validate(IIngredient[] inputs)
    {
        if(inputs.length == 0)
        {
            Plugin.LOGGER.error("Cutting Board combining inputs cannot be empty");
            return false;
        }
        if(inputs.length > CuttingBoardCombiningRecipe.MAX_INGREDIENTS)
        {
            Plugin.LOGGER.error("Cutting Board combining inputs can only have between 2 and " + CuttingBoardCombiningRecipe.MAX_INGREDIENTS + " ingredients");
            return false;
        }
        return true;
    }
}




