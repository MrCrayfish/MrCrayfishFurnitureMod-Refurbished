package com.mrcrayfish.furniture.refurbished.client;

import net.fabricmc.api.ClientModInitializer;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientBootstrap.init();
    }
}
