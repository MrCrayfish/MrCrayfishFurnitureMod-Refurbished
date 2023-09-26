package com.mrcrayfish.furniture.refurbished.platform.services;

import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public interface IItemHelper
{
    int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type);

    ItemStack deserializeItemStack(JsonObject object);
}
