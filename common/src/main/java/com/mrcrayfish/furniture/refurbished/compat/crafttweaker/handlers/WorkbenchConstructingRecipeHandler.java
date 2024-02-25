package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(WorkbenchContructingRecipe.class)
public class WorkbenchConstructingRecipeHandler implements IRecipeHandler<WorkbenchContructingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super WorkbenchContructingRecipe> manager, WorkbenchContructingRecipe recipe)
    {
        return String.format("%s.addRecipe(%s, %s, %s);",
                manager.getCommandString(),
                StringUtil.quoteAndEscape(recipe.getId()),
                IItemStack.ofMutable(recipe.getResult()).getCommandString(),
                "[" + String.join(", ", recipe.getMaterials().stream().map(v -> IIngredient.fromIngredient(v.ingredient()).mul(v.count()).getCommandString()).toArray(String[]::new)) + "]");
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super WorkbenchContructingRecipe> manager, WorkbenchContructingRecipe firstRecipe, U secondRecipe)
    {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super WorkbenchContructingRecipe> manager, WorkbenchContructingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getMaterials().stream()
                        .map(stack -> IIngredient.fromIngredient(stack.ingredient()).mul(stack.count()))
                        .map(v -> (IIngredient) v)
                        .collect(Collectors.toList()))
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getResult())).build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<WorkbenchContructingRecipe> recompose(IRecipeManager<? super WorkbenchContructingRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe)
    {
        NonNullList<StackedIngredient> materials = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS).stream()
                .map(IIngredient::asIIngredientWithAmount)
                .map(v -> StackedIngredient.of(v.getIngredient().asVanillaIngredient(), v.getAmount()))
                .collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
        ItemStack result = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        return Optional.of(new WorkbenchContructingRecipe(name, materials, result, false));
    }
}
