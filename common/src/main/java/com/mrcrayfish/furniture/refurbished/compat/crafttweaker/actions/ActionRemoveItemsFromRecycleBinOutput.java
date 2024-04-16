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
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class ActionRemoveItemsFromRecycleBinOutput implements IAction, IRuntimeAction
{
    private final IRecipeManager<RecycleBinRecyclingRecipe> manager;
    private final ResourceLocation id;
    private final List<IItemStack> removal;

    public ActionRemoveItemsFromRecycleBinOutput(IRecipeManager<RecycleBinRecyclingRecipe> manager, ResourceLocation id, List<IItemStack> removal)
    {
        this.manager = manager;
        this.id = id;
        this.removal = removal;
    }

    @Override
    public void apply()
    {
        RecipeList<RecycleBinRecyclingRecipe> list = this.manager.getRecipeList();
        RecycleBinRecyclingRecipe recipe = list.get(this.id);
        NonNullList<ItemStack> newOutput = NonNullList.create();
        newOutput.addAll(recipe.getScraps());
        newOutput.removeIf(stack -> this.removal.stream().anyMatch(iStack -> ItemStack.isSameItemSameTags(stack, iStack.getInternal())));
        RecycleBinRecyclingRecipe newRecipe = new RecycleBinRecyclingRecipe(this.id, recipe.getRecyclable(), newOutput);
        list.remove(this.id);
        list.add(this.id, newRecipe);
    }

    @Override
    public String describe()
    {
        String items = "[" + StringUtils.join(this.removal.stream().map(IItemStack::getCommandString).collect(Collectors.toList()), ", ") + "]";
        return "Removing the items '%s' from the scraps of the recycling bin recipe '%s'".formatted(items, this.id);
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
