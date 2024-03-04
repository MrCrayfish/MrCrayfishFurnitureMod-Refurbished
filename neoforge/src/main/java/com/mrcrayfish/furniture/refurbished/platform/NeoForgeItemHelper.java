package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class NeoForgeItemHelper implements IItemHelper
{
    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type)
    {
        return stack.getBurnTime(type);
    }

}
