package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mrcrayfish.furniture.refurbished.client.FluidSprites;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface IClientHelper
{
    int getGuiLeft(AbstractContainerScreen<?> screen);

    int getGuiTop(AbstractContainerScreen<?> screen);

    CreativeModeTab getSelectedCreativeModeTab();

    void setTooltipCache(Tooltip tooltip, List<FormattedCharSequence> lines);

    @Nullable
    FluidSprites getFluidSprites(FluidState state);

    RenderType getElectricityRenderType();

    RenderType getTelevisionScreenRenderType(Identifier id);

    RenderType createPaletteImageRenderType(Identifier id);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createFreezerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createMicrowaveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createStoveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    RenderPipeline.Snippet getMatricesProjectionSnippet();
}
