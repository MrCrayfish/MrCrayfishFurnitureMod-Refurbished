package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ActionReplaceItemInRecycleBinOutput implements IAction, IRuntimeAction
{
    private final IRecipeManager<RecycleBinRecyclingRecipe> manager;
    private final ResourceLocation id;
    private final IItemStack from;
    private final IItemStack to;

    public ActionReplaceItemInRecycleBinOutput(IRecipeManager<RecycleBinRecyclingRecipe> manager, ResourceLocation id, IItemStack from, IItemStack to)
    {
        this.manager = manager;
        this.id = id;
        this.from = from;
        this.to = to;
    }

    @Override
    public void apply()
    {
        RecipeList<RecycleBinRecyclingRecipe> list = this.manager.getRecipeList();
        RecycleBinRecyclingRecipe recipe = list.get(this.id);
        NonNullList<ItemStack> newOutput = NonNullList.create();
        newOutput.addAll(recipe.getScraps());
        for(int i = 0; i < newOutput.size(); i++)
        {
            ItemStack stack = newOutput.get(i);
            if(ItemStack.isSameItemSameTags(stack, this.from.getInternal()))
            {
                newOutput.set(i, this.to.getInternal());
                break;
            }
        }
        list.remove(this.id);
        list.add(this.id, new RecycleBinRecyclingRecipe(this.id, recipe.getRecyclable(), newOutput));
    }

    @Override
    public String describe()
    {
        return "Replacing the item '%s' with '%s' from the scraps of the recycling bin recipe '%s'".formatted(this.from.getCommandString(), this.to.getCommandString(), this.id);
    }

    @Override
    public String systemName()
    {
        return Constants.MOD_NAME;
    }

    @Override
    public boolean validate(Logger logger)
    {
        if(!this.manager.getRecipeList().has(this.id))
        {
            logger.error("No recycling bin recipe exists for the id '{}'", this.id);
            return false;
        }
        return true;
    }
}
