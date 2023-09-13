package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class FabricItemHelper implements IItemHelper
{
    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type)
    {
        return Optional.ofNullable(FuelRegistry.INSTANCE.get(stack.getItem())).orElse(0);
    }
}
