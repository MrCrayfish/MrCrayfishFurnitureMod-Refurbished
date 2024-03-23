package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.OvenBakingRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.SinkFluidTransmutingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/Sink/FluidTransmuting")
@ZenCodeType.Name("mods.refurbished_furniture.SinkFluidTransmuting")
public class SinkFluidTransmutingRecipeManager implements IRecipeManager<SinkFluidTransmutingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IFluidStack fluid, IIngredient catalyst, IItemStack output)
    {
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new RecipeHolder<>(CraftTweakerConstants.rl(name), new SinkFluidTransmutingRecipe(fluid.getFluid(), catalyst.asVanillaIngredient(), output.getInternal()))));
    }

    @Override
    public RecipeType<SinkFluidTransmutingRecipe> getRecipeType()
    {
        return ModRecipeTypes.SINK_FLUID_TRANSMUTING.get();
    }
}




