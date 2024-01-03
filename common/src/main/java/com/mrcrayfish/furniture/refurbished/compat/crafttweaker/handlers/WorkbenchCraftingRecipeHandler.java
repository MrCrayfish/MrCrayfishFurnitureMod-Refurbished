package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.ToasterHeatingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
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
@IRecipeHandler.For(WorkbenchCraftingRecipe.class)
public class WorkbenchCraftingRecipeHandler implements IRecipeHandler<WorkbenchCraftingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super WorkbenchCraftingRecipe> manager, WorkbenchCraftingRecipe recipe)
    {
        return String.format("%s.addRecipe(%s, %s, %s);",
                manager.getCommandString(),
                StringUtil.quoteAndEscape(recipe.getId()),
                IItemStack.ofMutable(Plugin.getResult(recipe)).getCommandString(),
                "[" + String.join(", ", recipe.getMaterials().stream().map(v -> IIngredient.fromIngredient(v.ingredient()).mul(v.count()).getCommandString()).toArray(String[]::new)) + "]");
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super WorkbenchCraftingRecipe> manager, WorkbenchCraftingRecipe firstRecipe, U secondRecipe)
    {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super WorkbenchCraftingRecipe> manager, WorkbenchCraftingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getMaterials().stream()
                        .map(stack -> IIngredient.fromIngredient(stack.ingredient()).mul(stack.count()))
                        .map(v -> (IIngredient) v)
                        .collect(Collectors.toList()))
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(Plugin.getResult(recipe))).build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<WorkbenchCraftingRecipe> recompose(IRecipeManager<? super WorkbenchCraftingRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe)
    {
        NonNullList<StackedIngredient> materials = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS).stream()
                .map(IIngredient::asIIngredientWithAmount)
                .map(v -> StackedIngredient.of(v.getIngredient().asVanillaIngredient(), v.getAmount()))
                .collect(NonNullList::create, AbstractList::add, AbstractCollection::addAll);
        ItemStack result = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        return Optional.of(new WorkbenchCraftingRecipe(name, materials, result, false));
    }
}
