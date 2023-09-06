package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.apache.commons.lang3.function.TriFunction;

/**
 * Author: MrCrayfish
 */
public class ForgeClientEvents
{
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        ClientBootstrap.registerScreens(new ScreenRegister() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<? extends T> type, TriFunction<T, Inventory, Component, U> factory) {
                MenuScreens.register(type, factory::apply);
            }
        });
        ClientBootstrap.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        ClientBootstrap.registerEntityRenderers(event::registerEntityRenderer);
        ClientBootstrap.registerRenderTypes(ItemBlockRenderTypes::setRenderLayer);
    }

    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event)
    {
        ExtraModels.register(event::register);
    }

    // TODO fabric
    public static void onRenderLevelStage(RenderLevelStageEvent event)
    {
        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
            return;

        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        {
            Vec3 view = event.getCamera().getPosition();
            stack.translate(-view.x(), -view.y(), -view.z());
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null)
            {
                LinkRenderer.get().drawLink(mc.player, stack, mc.renderBuffers().bufferSource(), event.getPartialTick());
            }
        }
        stack.popPose();
    }
}
