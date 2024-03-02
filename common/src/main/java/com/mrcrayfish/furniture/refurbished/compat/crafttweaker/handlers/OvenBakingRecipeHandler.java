package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(OvenBakingRecipe.class)
public class OvenBakingRecipeHandler implements IRecipeHandler<OvenBakingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super OvenBakingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<OvenBakingRecipe> holder)
    {
        return "%s.addRecipe(%s, %s, %s, %s);".formatted(
            manager.getCommandString(),
            StringUtil.quoteAndEscape(holder.id()),
            IIngredient.fromIngredient(holder.value().getInput()).getCommandString(),
            IItemStack.ofMutable(holder.value().getOutput()).getCommandString(),
            holder.value().getProcessTime()
        );
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super OvenBakingRecipe> manager, OvenBakingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof OvenBakingRecipe bakingRecipe))
            return true;
        // Baking recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getInput(), bakingRecipe.getInput());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super OvenBakingRecipe> manager, RegistryAccess registryAccess, OvenBakingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
            .with(BuiltinRecipeComponents.Input.INGREDIENTS, IIngredient.fromIngredient(recipe.getInput()))
            .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getOutput()))
            .with(BuiltinRecipeComponents.Processing.TIME, recipe.getProcessTime())
            .build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<OvenBakingRecipe> recompose(IRecipeManager<? super OvenBakingRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe)
    {
        Ingredient input = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        ItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        int cookingTime = recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME);
        return Optional.of(new OvenBakingRecipe(input, output, cookingTime));
    }
}
