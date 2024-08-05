package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.RecipeCategoryRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
//import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class ForgeClientEvents
{
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        ClientBootstrap.registerScreens(new ScreenRegister() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<T> type, TriFunction<T, Inventory, Component, U> factory) {
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

    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
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

    public static void onRenderLevelStage(RenderLevelStageEvent event)
    {
        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.level == null)
            return;

        // Draw active link
        PoseStack stack = new PoseStack();
        stack.setIdentity();
        stack.mulPose(event.getPoseStack());
        stack.pushPose();
        Vec3 view = event.getCamera().getPosition();
        stack.translate(-view.x(), -view.y(), -view.z());
        LinkHandler.get().render(mc.player, stack, mc.renderBuffers().bufferSource(), event.getPartialTick());
        ToolAnimationRenderer.get().render(mc.level, stack, mc.renderBuffers().bufferSource(), event.getPartialTick());
        DeferredElectricRenderer.get().draw(stack);
        stack.popPose();

        // End render types
        mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getTelevisionScreenRenderType(CustomSheets.TV_CHANNELS_SHEET));
    }

    public static void onKeyTriggered(InputEvent.InteractionKeyMappingTriggered event)
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

    public static void onDrawHighlight(RenderHighlightEvent.Block event)
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

    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event)
    {
        ClientBootstrap.registerBlockColors(event::register);
    }

    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event)
    {
        ClientBootstrap.registerItemColors(event::register);
    }

    // TODO why they do this?
    /*public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event)
    {
        ClientBootstrap.registerHudOverlays((id, overlay) -> {
            event.registerAboveAll(id.getPath(), (gui, graphics, partialTick, screenWidth, screenHeight) -> {
                overlay.draw(graphics, partialTick);
            });
        });
    }*/

    public static void onRegisterRecipeCategories(RegisterRecipeBookCategoriesEvent event)
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
                event.registerRecipeCategoryFinder(type, function);
            }
        });
    }
}
