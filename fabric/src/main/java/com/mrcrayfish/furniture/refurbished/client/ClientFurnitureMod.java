package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.FurnitureMod;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.client.electricity.WrenchHandler;
import com.mrcrayfish.furniture.refurbished.client.registration.ParticleProviderRegister;
import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
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
        ClientBootstrap.registerEntityRenderers(EntityRenderers::register);
        ClientBootstrap.registerBlockTintSources(BlockColorRegistry::register);
        ClientBootstrap.registerHudOverlays((id, overlay) -> HudElementRegistry.addLast(id, overlay::draw));
        ClientBootstrap.registerParticleProviders(new ParticleProviderRegister() {
            @Override
            public <T extends ParticleOptions> void apply(ParticleType<T> type, SpriteProvider<T> provider) {
                ParticleProviderRegistry.getInstance().register(type, provider::apply);
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

        FluidRenderingRegistry.register(FurnitureMod.MILK, new FluidModel.Unbaked(new Material(Utils.id("block/milk_still")), new Material(Utils.id("block/milk_still")), null, null));
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(ElectricityRenderer.ID, ElectricityRenderer.get());
    }
}
