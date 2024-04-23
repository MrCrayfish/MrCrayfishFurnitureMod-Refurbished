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
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(OvenBakingRecipe.class)
public class OvenBakingRecipeHandler implements IRecipeHandler<OvenBakingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super OvenBakingRecipe> manager, OvenBakingRecipe recipe)
    {
        return "%s.addRecipe(%s, %s, %s, %s);".formatted(
            manager.getCommandString(),
            StringUtil.quoteAndEscape(recipe.getId()),
            IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(),
            IItemStack.ofMutable(recipe.getResult()).getCommandString(),
            recipe.getTime()
        );
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super OvenBakingRecipe> manager, OvenBakingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof OvenBakingRecipe bakingRecipe))
            return true;
        // Baking recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getIngredient(), bakingRecipe.getIngredient());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super OvenBakingRecipe> manager, OvenBakingRecipe recipe)
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
    public Optional<OvenBakingRecipe> recompose(IRecipeManager<? super OvenBakingRecipe> manager, ResourceLocation id, IDecomposedRecipe recipe)
    {
        ProcessingRecipe.Category category = recipe.getOrThrowSingle(CustomRecipeComponents.Metadata.PROCESSING_CATEGORY);
        Ingredient ingredient = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        ItemStack result = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        int time = recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME);
        return Optional.of(new OvenBakingRecipe(id, category, ingredient, result, time));
    }
}
