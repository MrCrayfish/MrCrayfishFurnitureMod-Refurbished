package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.GrillBlock;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.GrillBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.KitchenSinkBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ToasterBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.entity.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Author: MrCrayfish
 */
public class ForgeClientEvents
{
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.SEAT.get(), SeatRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.KITCHEN_SINK.get(), KitchenSinkBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GRILL.get(), GrillBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TOASTER.get(), ToasterBlockEntityRenderer::new);

        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            Block block = (Block) entry.get();
            if(block instanceof GrillBlock grill) {
                ItemBlockRenderTypes.setRenderLayer(grill, RenderType.cutout());
            }
        });
    }
}
