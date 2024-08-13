package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IBlockHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IEntityHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IFluidHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IItemHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IMenuHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IPlatformHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.IRecipeHelper;
import com.mrcrayfish.furniture.refurbished.platform.services.ITagHelper;

import java.util.ServiceLoader;

public class Services
{
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IBlockHelper BLOCK = load(IBlockHelper.class);
    public static final IBlockEntityHelper BLOCK_ENTITY = load(IBlockEntityHelper.class);
    public static final IItemHelper ITEM = load(IItemHelper.class);
    public static final IEntityHelper ENTITY = load(IEntityHelper.class);
    public static final IRecipeHelper RECIPE = load(IRecipeHelper.class);
    public static final IFluidHelper FLUID = load(IFluidHelper.class);
    public static final ITagHelper TAG = load(ITagHelper.class);
    public static final IMenuHelper MENU = load(IMenuHelper.class);

    public static <T> T load(Class<T> clazz)
    {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}