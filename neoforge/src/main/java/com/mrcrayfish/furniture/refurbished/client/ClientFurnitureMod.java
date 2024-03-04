package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.ClientBootstrap;
import com.mrcrayfish.furniture.refurbished.client.ExtraModels;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import org.apache.commons.lang3.function.TriFunction;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientFurnitureMod
{
    @SubscribeEvent
    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
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

    @SubscribeEvent
    private static void onRegisterAdditional(ModelEvent.RegisterAdditional event)
    {
        ExtraModels.register(event::register);
    }

    @SubscribeEvent
    private static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
    {
        ClientBootstrap.registerParticleProviders(new ParticleProviderRegister()
        {
            @Override
            public <T extends ParticleOptions> void apply(ParticleType<T> type, SpriteProvider<T> provider)
            {
                event.registerSpriteSet(type, provider::apply);
            }
        });
    }

    @SubscribeEvent
    private static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event)
    {
        ClientBootstrap.registerBlockColors(event::register);
    }

    @SubscribeEvent
    private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event)
    {
        ClientBootstrap.registerItemColors(event::register);
    }

    @SubscribeEvent
    private static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event)
    {
        ClientBootstrap.registerHudOverlays((id, overlay) -> {
            event.registerAboveAll(id, (gui, graphics, partialTick, screenWidth, screenHeight) -> {
                overlay.draw(graphics, partialTick);
            });
        });
    }

    @SubscribeEvent
    private static void onRegisterRecipeCategories(RegisterRecipeBookCategoriesEvent event)
    {
        ClientBootstrap.registerRecipeBookCategories((type, function) -> {
            event.registerRecipeCategoryFinder(type, holder -> function.apply(holder.value()));
        });
    }
}
