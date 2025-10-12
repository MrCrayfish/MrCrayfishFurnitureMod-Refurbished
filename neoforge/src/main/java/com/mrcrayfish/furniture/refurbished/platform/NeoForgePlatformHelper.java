package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IPlatformHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.FuelValues;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.Nullable;

public class NeoForgePlatformHelper implements IPlatformHelper
{
    @Override
    public Platform getPlatform()
    {
        return Platform.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment()
    {
        return !FMLEnvironment.isProduction();
    }

    @Override
    public int getBurnTime(@Nullable RecipeType<?> type, FuelValues values, ItemStack stack)
    {
        return stack.getBurnTime(type, values);
    }
}