package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientBootstrap.init();
        EntityRendererRegistry.register(ModEntities.SEAT.get(), SeatRenderer::new);
    }
}
