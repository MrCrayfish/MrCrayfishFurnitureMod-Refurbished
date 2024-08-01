package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.RecipeCategoryRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeBookCategories;
import net.minecraft.client.RecipeBookCategories;
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
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.function.Function;

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
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<? extends T> type, TriFunction<T, Inventory, Component, U> factory) {
                event.register(type, factory::apply);
            }
        });
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
    private static void onRegisterGuiOverlays(RegisterGuiLayersEvent event)
    {
        ClientBootstrap.registerHudOverlays((id, overlay) -> {
            event.registerAboveAll(id, overlay::draw);
        });
    }

    @SubscribeEvent
    private static void onRegisterRecipeCategories(RegisterRecipeBookCategoriesEvent event)
    {
        // Register all categories
        ModRecipeBookCategories.getAllCategories().forEach(holder -> {
            RecipeBookCategories.create(holder.getConstantName(), holder.getIcons().get());
        });

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
    }

    private static void registerRecipeBookCategory(RecipeBookCategoryHolder holder)
    {
        RecipeBookCategories.create(holder.getConstantName(), holder.getIcons().get());
    }

}
