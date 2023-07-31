package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.FreezerScreen;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;

/**
 * Author: MrCrayfish
 */
public class ClientBootstrap
{
    public static void init()
    {
        CreativeFilters.init();
        ClientServices.PLATFORM.registerScreen(ModMenuTypes.FREEZER.get(), FreezerScreen::new);
    }
}
