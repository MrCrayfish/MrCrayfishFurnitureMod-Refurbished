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
import net.minecraft.world.item.crafting.RecipeHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class ActionAddItemsToRecycleBinScraps implements IAction, IRuntimeAction
{
    private final IRecipeManager<RecycleBinRecyclingRecipe> manager;
    private final ResourceLocation id;
    private final List<IItemStack> add;

    public ActionAddItemsToRecycleBinScraps(IRecipeManager<RecycleBinRecyclingRecipe> manager, ResourceLocation id, List<IItemStack> add)
    {
        this.manager = manager;
        this.id = id;
        this.add = add;
    }

    @Override
    public void apply()
    {
        RecipeList<RecycleBinRecyclingRecipe> list = this.manager.getRecipeList();
        RecipeHolder<RecycleBinRecyclingRecipe> holder = list.get(this.id);
        NonNullList<ItemStack> newOutput = NonNullList.create();
        newOutput.addAll(holder.value().getScraps());
        this.add.forEach(iStack -> newOutput.add(iStack.getInternal()));
        RecycleBinRecyclingRecipe newRecipe = new RecycleBinRecyclingRecipe(holder.value().getRecyclable(), newOutput);
        list.remove(this.id);
        list.add(this.id, new RecipeHolder<>(this.id, newRecipe));
    }

    @Override
    public String describe()
    {
        String items = "[" + StringUtils.join(this.add.stream().map(IItemStack::getCommandString).collect(Collectors.toList()), ", ") + "]";
        return "Adding the items '%s' to the scraps of the recycling bin recipe '%s'".formatted(items, this.id);
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

        RecipeHolder<RecycleBinRecyclingRecipe> recipe = this.manager.getRecipeList().get(this.id);
        List<ItemStack> outputs = recipe.value().getScraps();
        for(IItemStack stack : this.add)
        {
            if(outputs.stream().anyMatch(stack1 -> ItemStack.isSameItemSameTags(stack1, stack.getInternal())))
            {
                logger.error("Cannot add the item '{}' to the scraps as the item already exists in the scraps", stack.getCommandString());
                return false;
            }

            if(RecycleBinBlockEntity.isInvalidItem(stack.getInternal()))
            {
                logger.error("'{}' is not an acceptable item for a recycling bin recipe scraps", stack.getCommandString());
                return false;
            }
        }

        if(outputs.size() + this.add.size() > RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT)
        {
            String items = "[" + StringUtils.join(this.add.stream().map(IItemStack::getCommandString).collect(Collectors.toList()), ", ") + "]";
            logger.error("Cannot add the items '{}' as it will exceed the maximum number of allow scrap items (max: {})", items, RecycleBinRecyclingRecipe.MAX_OUTPUT_COUNT);
            return false;
        }

        return true;
    }
}
