package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.actions;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
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
public class ActionAddItemsToRecycleBinOutput implements IAction, IRuntimeAction
{
    private final IRecipeManager<RecycleBinRecyclingRecipe> manager;
    private final ResourceLocation id;
    private final List<IItemStack> add;

    public ActionAddItemsToRecycleBinOutput(IRecipeManager<RecycleBinRecyclingRecipe> manager, ResourceLocation id, List<IItemStack> add)
    {
        this.manager = manager;
        this.id = id;
        this.add = add;
    }

    @Override
    public void apply()
    {
        RecipeList<RecycleBinRecyclingRecipe> list = this.manager.getRecipeList();
        RecycleBinRecyclingRecipe recipe = list.get(this.id);
        NonNullList<ItemStack> newOutput = NonNullList.create();
        newOutput.addAll(recipe.getScraps());
        this.add.forEach(iStack -> newOutput.add(iStack.getInternal()));
        RecycleBinRecyclingRecipe newRecipe = new RecycleBinRecyclingRecipe(this.id, recipe.getRecyclable(), newOutput);
        list.remove(this.id);
        list.add(this.id, newRecipe);
    }

    @Override
    public String describe()
    {
        String items = "[" + StringUtils.join(this.add.stream().map(IItemStack::getCommandString).collect(Collectors.toList()), ", ") + "]";
        return "Adding the items '%s' to the scraps of the recycling bin '%s'".formatted(items, this.id);
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

        RecycleBinRecyclingRecipe recipe = this.manager.getRecipeList().get(this.id);
        List<ItemStack> outputs = recipe.getScraps();
        for(IItemStack stack : this.add)
        {
            if(outputs.stream().anyMatch(stack1 -> ItemStack.isSameItemSameTags(stack1, stack.getInternal())))
            {
                logger.error("Cannot add the item '{}' to the scraps as the item already exists in the output", stack.getCommandString());
                return false;
            }
        }

        if(outputs.size() + this.add.size() > RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT)
        {
            String items = "[" + StringUtils.join(this.add.stream().map(IItemStack::getCommandString).collect(Collectors.toList()), ", ") + "]";
            logger.error("Cannot add the items '{}' as it will exceed the maximum number of allow scraps items (max: {})", items, RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT);
            return false;
        }

        return true;
    }
}
