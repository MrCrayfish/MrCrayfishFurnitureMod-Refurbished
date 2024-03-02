package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.SinkFluidTransmutingRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(SinkFluidTransmutingRecipe.class)
public class SinkFluidTransmutingRecipeHandler implements IRecipeHandler<SinkFluidTransmutingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super SinkFluidTransmutingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<SinkFluidTransmutingRecipe> holder)
    {
        return "%s.addRecipe(%s, %s, %s, %s);".formatted(
            manager.getCommandString(),
            StringUtil.quoteAndEscape(holder.id()),
            IFluidStack.of(holder.value().getFluid(), 1000).getCommandString(),
            IIngredient.fromIngredient(holder.value().getCatalyst()).getCommandString(),
            IItemStack.ofMutable(holder.value().getResult()).getCommandString()
        );
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super SinkFluidTransmutingRecipe> manager, SinkFluidTransmutingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof SinkFluidTransmutingRecipe transmutingRecipe))
            return true;
        // Baking recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getCatalyst(), transmutingRecipe.getCatalyst());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super SinkFluidTransmutingRecipe> manager, RegistryAccess registryAccess, SinkFluidTransmutingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
            .with(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS, IFluidStack.of(recipe.getFluid(), 1000).asFluidIngredient())
            .with(BuiltinRecipeComponents.Input.INGREDIENTS, IIngredient.fromIngredient(recipe.getCatalyst()))
            .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getResult()))
            .build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<SinkFluidTransmutingRecipe> recompose(IRecipeManager<? super SinkFluidTransmutingRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe)
    {
        Fluid fluid = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS).getMatchingStacks().get(0).getFluid();
        Ingredient catalyst = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        ItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        return Optional.of(new SinkFluidTransmutingRecipe(fluid, catalyst, output));
    }
}
