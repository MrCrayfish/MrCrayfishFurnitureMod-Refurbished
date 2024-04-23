package com.mrcrayfish.furniture.refurbished.client;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.FurnitureMod;
import com.mrcrayfish.furniture.refurbished.asm.Loader;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.RecipeCategoryRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import io.github.fabricators_of_create.porting_lib.recipe_book_categories.RecipeBookRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod implements ClientModInitializer
{
    public static final Supplier<RecipeBookCategories> CATEGORY_SEARCH = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, Loader.RECIPE_BOOK_CATEGORY_SEARCH));
    public static final Supplier<RecipeBookCategories> CATEGORY_BLOCKS = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, Loader.RECIPE_BOOK_CATEGORY_BLOCKS));
    public static final Supplier<RecipeBookCategories> CATEGORY_ITEMS = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, Loader.RECIPE_BOOK_CATEGORY_ITEMS));
    public static final Supplier<RecipeBookCategories> CATEGORY_FOOD = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, Loader.RECIPE_BOOK_CATEGORY_FOOD));
    public static final Supplier<RecipeBookCategories> CATEGORY_MISC = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, Loader.RECIPE_BOOK_CATEGORY_MISC));

    @Override
    public void onInitializeClient()
    {
        ClientBootstrap.init();
        ClientBootstrap.registerScreens(new ScreenRegister() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<? extends T> type, TriFunction<T, Inventory, Component, U> factory) {
                MenuScreens.register(type, factory::apply);
            }
        });
        ClientBootstrap.registerBlockEntityRenderers(BlockEntityRenderers::register);
        ClientBootstrap.registerEntityRenderers(EntityRendererRegistry::register);
        ClientBootstrap.registerRenderTypes(BlockRenderLayerMap.INSTANCE::putBlock);
        ClientBootstrap.registerBlockColors(ColorProviderRegistry.BLOCK::register);
        ClientBootstrap.registerItemColors(ColorProviderRegistry.ITEM::register);
        ClientBootstrap.registerHudOverlays((id, overlay) -> HudRenderCallback.EVENT.register(overlay::draw));
        ClientBootstrap.registerParticleProviders(new ParticleProviderRegister() {
            @Override
            public <T extends ParticleOptions> void apply(ParticleType<T> type, SpriteProvider<T> provider) {
                ParticleFactoryRegistry.getInstance().register(type, provider::apply);
            }
        });
        ClientBootstrap.registerRecipeBookCategories(new RecipeCategoryRegister() {
            @Override
            public void applyCategory(RecipeBookType recipeBookType, List<RecipeBookCategories> categories) {
                RecipeBookRegistry.registerBookCategories(recipeBookType, categories);
            }

            @Override
            public void applyAggregate(RecipeBookCategories category, List<RecipeBookCategories> categories) {
                RecipeBookRegistry.registerAggregateCategory(category, categories);
            }

            @Override
            public void applyFinder(RecipeType<?> type, Function<Recipe<?>, RecipeBookCategories> function) {
                RecipeBookRegistry.registerRecipeCategoryFinder(type, function);
            }
        });

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> ExtraModels.register(out));

        WorldRenderEvents.LAST.register(context -> {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player == null || mc.level == null)
                return;

            // Draw active link
            PoseStack stack = context.matrixStack();
            stack.pushPose();
            Vec3 view = context.camera().getPosition();
            stack.translate(-view.x(), -view.y(), -view.z());
            LinkHandler.get().render(mc.player, stack, mc.renderBuffers().bufferSource(), context.tickDelta());
            ToolAnimationRenderer.get().render(mc.level, stack, mc.renderBuffers().bufferSource(), context.tickDelta());
            stack.popPose();

            // End render types
            mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getElectrictyNodeRenderType());
            mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getElectricityConnectionRenderType());
            mc.renderBuffers().bufferSource().endBatch(ClientServices.PLATFORM.getTelevisionScreenRenderType(CustomSheets.TV_CHANNELS_SHEET));
        });

        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null && mc.level != null) {
                if(mc.player.getMainHandItem().is(ModItems.WRENCH.get())) {
                    return LinkHandler.get().onWrenchLeftClick(mc.level);
                }
            }
            return false;
        });

        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, hitResult) -> {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null) {
                ItemStack stack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
                return !stack.is(ModItems.WRENCH.get());
            }
            return true;
        });
    }
}
