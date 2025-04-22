package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.framework.api.client.FrameworkClientAPI;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.registration.ItemTintRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiConsumer;

import static net.minecraft.client.renderer.RenderPipelines.MATRICES_COLOR_SNIPPET;

/**
 * Author: MrCrayfish
 */
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientFurnitureMod
{
    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientBootstrap::init);
    }

    @SubscribeEvent
    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        ClientBootstrap.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        ClientBootstrap.registerEntityRenderers(event::registerEntityRenderer);
        ClientBootstrap.registerRenderTypes(ItemBlockRenderTypes::setRenderLayer);
    }

    @SubscribeEvent
    private static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
    {
        ClientBootstrap.registerScreens(new ScreenRegister() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<T> type, TriFunction<T, Inventory, Component, U> factory) {
                event.register(type, factory::apply);
            }
        });
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
    private static void onRegisterGuiOverlays(RegisterGuiLayersEvent event)
    {
        ClientBootstrap.registerHudOverlays((id, overlay) -> {
            event.registerAboveAll(id, overlay::draw);
        });
    }

    @SubscribeEvent
    private static void onRegisterListeners(AddClientReloadListenersEvent event)
    {
        event.addListener(DeferredElectricRenderer.ID, DeferredElectricRenderer.get());
    }

    /*@SubscribeEvent
    private static void onRegisterRecipeCategories(RegisterRecipeBookCategoriesEvent event)
    {
        ClientBootstrap.registerRecipeBookCategories(new RecipeCategoryRegister()
        {
            @Override
            public void applyCategory(RecipeBookType recipeBookType, RecipeBookCategories ... categories)
            {
                event.registerBookCategories(recipeBookType, List.of(categories));
            }

            @Override
            public void applyAggregate(RecipeBookCategories category, RecipeBookCategories ... categories)
            {
                event.registerAggregateCategory(category, List.of(categories));
            }

            @Override
            public void applyFinder(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategories> function)
            {
                event.registerRecipeCategoryFinder(type, holder -> function.apply(holder.value()));
            }
        });
    }*/
}
