package com.mrcrayfish.furniture.refurbished.client.renderer;

import com.mrcrayfish.furniture.refurbished.entity.Seat;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class SeatRenderer extends EntityRenderer<Seat>
{
    public SeatRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Seat seat)
    {
        return null;
    }
}
