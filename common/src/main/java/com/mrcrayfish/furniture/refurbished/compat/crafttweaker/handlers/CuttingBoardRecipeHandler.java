package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(CuttingBoardSlicingRecipe.class)
public class CuttingBoardRecipeHandler implements IRecipeHandler<CuttingBoardSlicingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, CuttingBoardSlicingRecipe recipe)
    {
        return String.format("%s.addRecipe(%s, %s, %s);",
                manager.getCommandString(),
                StringUtil.quoteAndEscape(recipe.getId()),
                IIngredient.fromIngredient(recipe.getInput()).getCommandString(),
                IItemStack.ofMutable(recipe.getOutput()).getCommandString());
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, CuttingBoardSlicingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof CuttingBoardSlicingRecipe slicingRecipe))
            return true;
        // Cutting board recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getInput(), slicingRecipe.getInput());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, CuttingBoardSlicingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, IIngredient.fromIngredient(recipe.getInput()))
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getOutput()))
                .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
                .build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<CuttingBoardSlicingRecipe> recompose(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, ResourceLocation id, IDecomposedRecipe recipe)
    {
        Ingredient input = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
        ItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        return Optional.of(new CuttingBoardSlicingRecipe(id, group, input, output));
    }
}
