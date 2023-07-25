package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IPlatformHelper;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;

import java.util.ServiceLoader;

public class Services
{
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IBlockEntityHelper BLOCK_ENTITY = load(IBlockEntityHelper.class);
    public static final IItemHelper ITEM = load(IItemHelper.class);
    public static final IRecipeHelper RECIPE = load(IRecipeHelper.class);

    public static <T> T load(Class<T> clazz)
    {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}