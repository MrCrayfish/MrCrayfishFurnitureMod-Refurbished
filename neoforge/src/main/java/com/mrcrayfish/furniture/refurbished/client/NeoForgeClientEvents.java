package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.client.ToolAnimationRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
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
    private static void onRenderLevelStage(RenderLevelStageEvent event)
    {
        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.level == null)
            return;

        // Draw active link
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        Vec3 view = event.getCamera().getPosition();
        stack.translate(-view.x(), -view.y(), -view.z());
        LinkHandler.get().render(mc.player, stack, mc.renderBuffers().bufferSource(), event.getPartialTick());
        ToolAnimationRenderer.get().render(mc.level, stack, mc.renderBuffers().bufferSource(), event.getPartialTick());
        stack.popPose();

        // End render types
        mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getElectrictyNodeRenderType());
        mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getElectricityConnectionRenderType());
        mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getTelevisionScreenRenderType(CustomSheets.TV_CHANNELS_SHEET));
    }

    @SubscribeEvent
    private static void onDrawHighlight(RenderHighlightEvent.Block event)
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
}
