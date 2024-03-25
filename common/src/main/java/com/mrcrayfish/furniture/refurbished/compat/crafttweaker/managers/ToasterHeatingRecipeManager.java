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
import com.mrcrayfish.furniture.refurbished.crafting.ToasterHeatingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/Toaster/Heating")
@ZenCodeType.Name("mods.refurbished_furniture.ToasterHeating")
public class ToasterHeatingRecipeManager implements IRecipeManager<ToasterHeatingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient ingredient, IItemStack result, @ZenCodeType.OptionalInt(200) int time)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new RecipeHolder<>(CraftTweakerConstants.rl(name), new ToasterHeatingRecipe(ingredient.asVanillaIngredient(), result.getInternal(), time))));
    }

    @Override
    public RecipeType<ToasterHeatingRecipe> getRecipeType()
    {
        return ModRecipeTypes.TOASTER_HEATING.get();
    }
}




