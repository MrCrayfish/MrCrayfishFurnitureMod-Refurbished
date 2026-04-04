package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.WrenchHandler;
import com.mrcrayfish.furniture.refurbished.compat.jei.SyncedRecipes;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

/**
 * Author: MrCrayfish
 */
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEvents
{
    @SubscribeEvent
    private static void onKeyTriggered(InputEvent.InteractionKeyMappingTriggered event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(event.getKeyMapping() == mc.options.keyAttack && mc.player != null && mc.level != null)
        {
            if(mc.player.getMainHandItem().is(ModItems.WRENCH.get()))
            {
                if(WrenchHandler.get().onPerformAttack(mc.level))
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    private static void onRenderLevelStage(RenderLevelStageEvent.AfterLevel event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.level == null)
            return;

        mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getTelevisionScreenRenderType(CustomSheets.TV_CHANNELS_SHEET));
    }

    @SubscribeEvent
    private static void onDrawHighlight(ExtractBlockOutlineRenderStateEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null)
        {
            ItemStack stack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stack.is(ModItems.WRENCH.get()))
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    private static void onExtractLevelRenderState(ExtractLevelRenderStateEvent event)
    {
        ElectricityRenderer.get().extract(event.getCamera());
    }

    @SubscribeEvent
    private static void onSetupFrameGraph(FrameGraphSetupEvent event)
    {
        ElectricityRenderer.get().setupFramePass(event.getFrameGrapBuilder(), event.getCameraState().pos);
    }

    @SubscribeEvent
    private static void afterEntities(RenderLevelStageEvent.AfterWeather event) // TODO 26.1.1 test after weather
    {
        ElectricityRenderer.get().renderPowerableArea(event.getLevelRenderState().cameraRenderState.pos);
    }

    @SubscribeEvent
    private static void afterRenderLevel(RenderLevelStageEvent.AfterLevel event)
    {
        ElectricityRenderer.get().blitToScreen();
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getTelevisionScreenRenderType(CustomSheets.TV_CHANNELS_SHEET));
    }

    @SubscribeEvent
    public static void onRecipesSynced(RecipesReceivedEvent event)
    {
        if(Services.PLATFORM.isModLoaded("jei")) // Only store if JEI is loaded
        {
            SyncedRecipes.setMap(event.getRecipeMap());
        }
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event)
    {
        SyncedRecipes.reset();
    }
}
