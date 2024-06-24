package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.CustomRecipeComponents;
import com.mrcrayfish.furniture.refurbished.crafting.FryingPanCookingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(FryingPanCookingRecipe.class)
public class FryingPanCookingRecipeHandler implements IRecipeHandler<FryingPanCookingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super FryingPanCookingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<FryingPanCookingRecipe> holder)
    {
        return "%s.addRecipe(%s, %s, %s, %s);".formatted(
            manager.getCommandString(),
            StringUtil.quoteAndEscape(holder.id()),
            IIngredient.fromIngredient(holder.value().getIngredient()).getCommandString(),
            IItemStack.ofMutable(holder.value().getResult()).getCommandString(),
            holder.value().getTime()
        );
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super FryingPanCookingRecipe> manager, FryingPanCookingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof FryingPanCookingRecipe toasterRecipe))
            return true;
        // Toaster recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getIngredient(), toasterRecipe.getIngredient());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super FryingPanCookingRecipe> manager, RegistryAccess registryAccess, FryingPanCookingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
            .with(CustomRecipeComponents.Metadata.PROCESSING_CATEGORY, recipe.getCategory())
            .with(BuiltinRecipeComponents.Input.INGREDIENTS, IIngredient.fromIngredient(recipe.getIngredient()))
            .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getResult()))
            .with(BuiltinRecipeComponents.Processing.TIME, recipe.getTime())
            .build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<FryingPanCookingRecipe> recompose(IRecipeManager<? super FryingPanCookingRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe)
    {
        ProcessingRecipe.Category category = recipe.getOrThrowSingle(CustomRecipeComponents.Metadata.PROCESSING_CATEGORY);
        Ingredient ingredient = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        ItemStack result = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        int time = recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME);
        return Optional.of(new FryingPanCookingRecipe(category, ingredient, result, time));
    }
}
