package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.GrillBlock;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.GrillBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.KitchenSinkBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.entity.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;

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
        BlockEntityRenderers.register(ModBlockEntities.KITCHEN_SINK.get(), KitchenSinkBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.GRILL.get(), GrillBlockEntityRenderer::new);

        Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
            if(entry.get() instanceof GrillBlock block) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
            }
        });
    }
}
