package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.LinkHandler;
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
                if(LinkHandler.get().onWrenchLeftClick(mc.level))
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

        // TODO 1.21.10 restore
        /*PoseStack stack = event.getPoseStack();
        stack.pushPose();
        Vec3 view = event.getCamera().getPosition();
        stack.translate(-view.x(), -view.y(), -view.z());
        float deltaTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        ToolAnimationRenderer.get().render(mc.level, stack, mc.renderBuffers().bufferSource(), deltaTick);
        stack.popPose();*/

        // End render types
        // TODO move
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
        ElectricityRenderer.get().extract();
    }

    @SubscribeEvent
    private static void onSetupFrameGraph(FrameGraphSetupEvent event)
    {
        ElectricityRenderer.get().setupFramePass(event.getFrameGrapBuilder(), event.getCamera().getPosition());
    }

    @SubscribeEvent
    private static void afterEntities(RenderLevelStageEvent.AfterEntities event)
    {
        ElectricityRenderer.get().renderPowerableArea(event.getLevelRenderState().cameraRenderState.pos);
    }

    @SubscribeEvent
    private static void afterRenderLevel(RenderLevelStageEvent.AfterLevel event)
    {
        ElectricityRenderer.get().blitToScreen();
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
