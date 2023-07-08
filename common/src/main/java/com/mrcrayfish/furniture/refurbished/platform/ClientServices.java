package com.mrcrayfish.furniture.refurbished.platform;

import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;

/**
 * Author: MrCrayfish
 */
public class ClientServices
{
    public static final IClientHelper PLATFORM = Services.load(IClientHelper.class);
}
