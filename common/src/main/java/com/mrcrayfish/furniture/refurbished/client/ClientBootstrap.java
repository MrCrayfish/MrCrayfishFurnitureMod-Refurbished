package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.FreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.registration.BlockEntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.EntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.CuttingBoardBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.GrillBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.KitchenSinkBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ToasterBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.entity.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
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

    public static void registerBlockEntityRenderers(BlockEntityRendererRegister register)
    {
        register.apply(ModBlockEntities.KITCHEN_SINK.get(), KitchenSinkBlockEntityRenderer::new);
        register.apply(ModBlockEntities.GRILL.get(), GrillBlockEntityRenderer::new);
        register.apply(ModBlockEntities.TOASTER.get(), ToasterBlockEntityRenderer::new);
        register.apply(ModBlockEntities.CUTTING_BOARD.get(), CuttingBoardBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRendererRegister register)
    {
        register.apply(ModEntities.SEAT.get(), SeatRenderer::new);
    }
}
