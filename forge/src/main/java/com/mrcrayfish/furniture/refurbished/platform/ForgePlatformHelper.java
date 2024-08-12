package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.FurnitureMod;
import com.mrcrayfish.furniture.refurbished.platform.services.IPlatformHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper
{
    @Override
    public Platform getPlatform()
    {
        return Platform.FORGE;
    }

    @Override
    public boolean isModLoaded(String modId)
    {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment()
    {
        return !FMLLoader.isProduction();
    }

    @Override
    public CreativeModeTab getCreativeModeTab()
    {
        return FurnitureMod.CREATIVE_TAB;
    }
}