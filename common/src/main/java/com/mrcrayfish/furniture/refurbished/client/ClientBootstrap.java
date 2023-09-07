package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.FreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.MicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.PostBoxScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.StoveScreen;
import com.mrcrayfish.furniture.refurbished.client.registration.BlockEntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.EntityRendererRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.RenderTypeRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.CuttingBoardBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ElectricBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.FryingPanBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.GrillBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.KitchenSinkBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ToasterBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.client.renderer.entity.SeatRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import net.minecraft.client.renderer.RenderType;

/**
 * Author: MrCrayfish
 */
public class ClientBootstrap
{
    public static void init()
    {
        CreativeFilters.init();
        TickEvents.START_RENDER.register(partialTick -> {
            LinkHandler.get().beforeRender(partialTick);
            ElectricBlockEntityRenderer.clearDrawn();
        });
    }

    public static void registerScreens(ScreenRegister register)
    {
        register.apply(ModMenuTypes.FREEZER.get(), FreezerScreen::new);
        register.apply(ModMenuTypes.MICROWAVE.get(), MicrowaveScreen::new);
        register.apply(ModMenuTypes.STOVE.get(), StoveScreen::new);
        register.apply(ModMenuTypes.POST_BOX.get(), PostBoxScreen::new);
    }

    public static void registerBlockEntityRenderers(BlockEntityRendererRegister register)
    {
        register.apply(ModBlockEntities.KITCHEN_SINK.get(), KitchenSinkBlockEntityRenderer::new);
        register.apply(ModBlockEntities.GRILL.get(), GrillBlockEntityRenderer::new);
        register.apply(ModBlockEntities.TOASTER.get(), ToasterBlockEntityRenderer::new);
        register.apply(ModBlockEntities.CUTTING_BOARD.get(), CuttingBoardBlockEntityRenderer::new);
        register.apply(ModBlockEntities.FRYING_PAN.get(), FryingPanBlockEntityRenderer::new);
        register.apply(ModBlockEntities.LIGHTSWITCH.get(), ElectricBlockEntityRenderer::new);
        register.apply(ModBlockEntities.CEILING_LIGHT.get(), ElectricBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRendererRegister register)
    {
        register.apply(ModEntities.SEAT.get(), SeatRenderer::new);
    }
    
    public static void registerRenderTypes(RenderTypeRegister register)
    {
        register.apply(ModBlocks.GRILL_WHITE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_ORANGE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_MAGENTA.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIGHT_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_YELLOW.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIME.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_PINK.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_LIGHT_GRAY.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_CYAN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_PURPLE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BLUE.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BROWN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_GREEN.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_RED.get(), RenderType.cutout());
        register.apply(ModBlocks.GRILL_BLACK.get(), RenderType.cutout());
        register.apply(ModBlocks.MICROWAVE_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.MICROWAVE_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.STOVE_LIGHT.get(), RenderType.cutout());
        register.apply(ModBlocks.STOVE_DARK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_SPRUCE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_BIRCH.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_JUNGLE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_ACACIA.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_DARK_OAK.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_MANGROVE.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_CHERRY.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_CRIMSON.get(), RenderType.cutout());
        register.apply(ModBlocks.MAIL_BOX_WARPED.get(), RenderType.cutout());
        register.apply(ModBlocks.CEILING_LIGHT_LIGHT.get(), RenderType.translucent());
        register.apply(ModBlocks.CEILING_LIGHT_DARK.get(), RenderType.translucent());
    }
}
