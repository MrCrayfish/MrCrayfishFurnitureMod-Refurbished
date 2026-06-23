package com.mrcrayfish.furniture.refurbished.platform;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mrcrayfish.furniture.refurbished.client.FabricRenderType;
import com.mrcrayfish.furniture.refurbished.client.FluidSprites;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricFreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricMicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricStoveScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FabricFreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricMicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricStoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.FluidState;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FabricClientHelper implements IClientHelper
{
    @Override
    public int getGuiLeft(AbstractContainerScreen<?> screen)
    {
        return screen.leftPos;
    }

    @Override
    public int getGuiTop(AbstractContainerScreen<?> screen)
    {
        return screen.topPos;
    }

    @Override
    public CreativeModeTab getSelectedCreativeModeTab()
    {
        return CreativeModeInventoryScreen.selectedTab;
    }

    @Override
    public void setTooltipCache(Tooltip tooltip, List<FormattedCharSequence> lines)
    {
        tooltip.cachedTooltip = ImmutableList.copyOf(lines);
        tooltip.splitWithLanguage = Language.getInstance();
    }

    @Override
    public FluidSprites getFluidSprites(FluidState state)
    {
        FluidModel model = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(state);
        return new FluidSprites(model.stillMaterial().sprite(), model.flowingMaterial().sprite());
    }

    @Override
    public RenderType getElectricityRenderType()
    {
        return FabricRenderType.ELECTRICITY;
    }

    @Override
    public RenderType getTelevisionScreenRenderType(Identifier id)
    {
        return FabricRenderType.televisionScreen(id);
    }

    @Override
    public RenderType createPaletteImageRenderType(Identifier id)
    {
        return FabricRenderType.createPaletteImage(id);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createFreezerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new FabricFreezerScreen((FabricFreezerMenu) menu, playerInventory, title);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createMicrowaveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new FabricMicrowaveScreen((FabricMicrowaveMenu) menu, playerInventory, title);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createStoveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new FabricStoveScreen((FabricStoveMenu) menu, playerInventory, title);
    }

}
