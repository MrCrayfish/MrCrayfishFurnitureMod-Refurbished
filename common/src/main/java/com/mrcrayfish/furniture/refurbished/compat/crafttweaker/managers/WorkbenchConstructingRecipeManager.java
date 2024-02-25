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
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Arrays;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/Workbench/Constructing")
@ZenCodeType.Name("mods.refurbished_furniture.WorkbenchConstructing")
public class WorkbenchConstructingRecipeManager implements IRecipeManager<WorkbenchContructingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack result, IIngredient[] materials, @ZenCodeType.OptionalBoolean boolean notification)
    {
        NonNullList<StackedIngredient> ingredients = Arrays.stream(materials)
                .map(IIngredient::asIIngredientWithAmount)
                .map(v -> StackedIngredient.of(v.getIngredient().asVanillaIngredient(), v.getAmount()))
                .collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new WorkbenchContructingRecipe(CraftTweakerConstants.rl(name), ingredients, result.getInternal(), notification)));
    }

    @Override
    public RecipeType<WorkbenchContructingRecipe> getRecipeType()
    {
        return ModRecipeTypes.WORKBENCH_CONSTRUCTING.get();
    }
}
