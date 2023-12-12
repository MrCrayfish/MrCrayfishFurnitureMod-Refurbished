package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/RecycleBin")
@ZenCodeType.Name("mods.refurbished_furniture.RecycleBin")
public class RecycleBinRecyclingRecipeManager implements IRecipeManager<RecycleBinRecyclingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack input, List<IItemStack> output)
    {
        if(!this.validate(output))
            return;
        ItemStack[] outputs = output.stream().map(IItemStack::getInternal).toArray(ItemStack[]::new);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new RecycleBinRecyclingRecipe(CraftTweakerConstants.rl(name), input.getInternal(), outputs)));
    }

    private boolean validate(List<IItemStack> output)
    {
        if(output.isEmpty())
        {
            Plugin.LOGGER.error("Recycling bin output items cannot be empty");
            return false;
        }
        if(output.size() > RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT)
        {
            Plugin.LOGGER.error("Recycling bin output can only have between 1 and " + RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT + " items");
            return false;
        }
        for(IItemStack iStack : output)
        {
            ItemStack stack = iStack.getInternal();
            if(RecycleBinBlockEntity.isInvalidItem(stack))
            {
                String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                Plugin.LOGGER.error("{} is not an acceptable item for a recycling bin recipe output", itemName);
                return false;
            }
        }
        return true;
    }

    @Override
    public RecipeType<RecycleBinRecyclingRecipe> getRecipeType()
    {
        return ModRecipeTypes.RECYCLE_BIN_RECYCLING.get();
    }
}




