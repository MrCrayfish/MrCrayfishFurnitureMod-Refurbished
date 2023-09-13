package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.client.registration.ScreenRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
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
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void apply(MenuType<? extends T> type, TriFunction<T, Inventory, Component, U> factory) {
                MenuScreens.register(type, factory::apply);
            }
        });
        ClientBootstrap.registerBlockEntityRenderers(BlockEntityRenderers::register);
        ClientBootstrap.registerEntityRenderers(EntityRendererRegistry::register);
        ClientBootstrap.registerRenderTypes(BlockRenderLayerMap.INSTANCE::putBlock);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> ExtraModels.register(out));
    }
}
