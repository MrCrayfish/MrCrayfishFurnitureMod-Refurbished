package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mrcrayfish.furniture.refurbished.client.FluidSprites;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
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
    FluidSprites getFluidSprites(Fluid fluid, BlockAndTintGetter getter, BlockPos pos, FluidState state);

    RenderType getTelevisionScreenRenderType(ResourceLocation id);

    RenderType createPaletteImageRenderType(ResourceLocation id);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createFreezerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createMicrowaveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    @SuppressWarnings("rawtypes")
    AbstractContainerScreen createStoveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title);

    RenderPipeline.Snippet getMatricesProjectionSnippet();
}
