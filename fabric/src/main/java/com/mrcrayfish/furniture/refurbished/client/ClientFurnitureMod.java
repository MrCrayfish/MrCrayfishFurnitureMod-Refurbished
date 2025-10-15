package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.FurnitureMod;
import com.mrcrayfish.furniture.refurbished.client.electricity.WrenchHandler;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.apache.commons.lang3.function.TriFunction;

/**
 * Author: MrCrayfish
 */
public class ClientFurnitureMod implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientBootstrap.init();
        ClientBootstrap.registerScreens(new ScreenRegister() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<T> type, TriFunction<T, Inventory, Component, U> factory) {
                MenuScreens.register(type, factory::apply);
            }
        });
        ClientBootstrap.registerBlockEntityRenderers(BlockEntityRenderers::register);
        ClientBootstrap.registerEntityRenderers(EntityRendererRegistry::register);
        ClientBootstrap.registerRenderTypes(BlockRenderLayerMap::putBlock);
        ClientBootstrap.registerBlockColors(ColorProviderRegistry.BLOCK::register);
        ClientBootstrap.registerHudOverlays((id, overlay) -> HudRenderCallback.EVENT.register(overlay::draw));
        ClientBootstrap.registerParticleProviders(new ParticleProviderRegister() {
            @Override
            public <T extends ParticleOptions> void apply(ParticleType<T> type, SpriteProvider<T> provider) {
                ParticleFactoryRegistry.getInstance().register(type, provider::apply);
            }
        });

        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null && mc.level != null) {
                if(mc.player.getMainHandItem().is(ModItems.WRENCH.get())) {
                    return WrenchHandler.get().onPerformAttack(mc.level);
                }
            }
            return false;
        });

        BlockRenderLayerMap.putFluid(FurnitureMod.MILK, ChunkSectionLayer.SOLID);
        FluidRenderHandlerRegistry.INSTANCE.register(FurnitureMod.MILK, new SimpleFluidRenderHandler(Utils.resource("block/milk_still"), Utils.resource("block/milk_still")));
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(ElectricityRenderer.ID, ElectricityRenderer.get());
    }
}
