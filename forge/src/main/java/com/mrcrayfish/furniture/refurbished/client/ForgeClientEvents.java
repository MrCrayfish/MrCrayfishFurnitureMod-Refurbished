package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Author: MrCrayfish
 */
public class ForgeClientEvents
{
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.SEAT.get(), SeatRenderer::new);
    }
}
