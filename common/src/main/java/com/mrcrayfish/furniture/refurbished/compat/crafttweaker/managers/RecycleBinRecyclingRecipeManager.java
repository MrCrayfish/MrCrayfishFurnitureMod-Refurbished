package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.Plugin;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.actions.ActionAddItemsToRecycleBinOutput;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.actions.ActionRemoveItemsFromRecycleBinOutput;
import com.mrcrayfish.furniture.refurbished.compat.crafttweaker.actions.ActionReplaceItemInRecycleBinOutput;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
@ZenRegister
@Document("mods/RefurbishedFurniture/RecycleBin/Recycling")
@ZenCodeType.Name("mods.refurbished_furniture.RecycleBinRecycling")
public class RecycleBinRecyclingRecipeManager implements IRecipeManager<RecycleBinRecyclingRecipe>
{
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack recyclable, List<IItemStack> scraps)
    {
        if(!this.validate(scraps))
            return;
        NonNullList<ItemStack> outputs = NonNullList.create();
        scraps.stream().map(IItemStack::getInternal).forEach(outputs::add);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new RecycleBinRecyclingRecipe(CraftTweakerConstants.rl(name), recyclable.getInternal(), outputs)));
    }

    @ZenCodeType.Method
    public void addOutput(String id, IItemStack output)
    {
        CraftTweakerAPI.apply(new ActionAddItemsToRecycleBinOutput(this, new ResourceLocation(id), List.of(output)));
    }

    @ZenCodeType.Method
    public void addOutput(String id, List<IItemStack> output)
    {
        CraftTweakerAPI.apply(new ActionAddItemsToRecycleBinOutput(this, new ResourceLocation(id), output));
    }

    @ZenCodeType.Method
    public void removeOutput(String id, IItemStack removal)
    {
        CraftTweakerAPI.apply(new ActionRemoveItemsFromRecycleBinOutput(this, new ResourceLocation(id), List.of(removal)));
    }

    @ZenCodeType.Method
    public void removeOutput(String id, List<IItemStack> removal)
    {
        CraftTweakerAPI.apply(new ActionRemoveItemsFromRecycleBinOutput(this, new ResourceLocation(id), removal));
    }

    @ZenCodeType.Method
    public void replaceOutput(String id, IItemStack from, IItemStack to)
    {
        CraftTweakerAPI.apply(new ActionReplaceItemInRecycleBinOutput(this, new ResourceLocation(id), from, to));
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




