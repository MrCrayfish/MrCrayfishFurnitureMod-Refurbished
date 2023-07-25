package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class FabricItemHelper implements IItemHelper
{
    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type)
    {
        return FurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
    }
}
