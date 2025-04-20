package com.mrcrayfish.furniture.refurbished.platform;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mrcrayfish.furniture.refurbished.client.FabricRenderType;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricFreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricMicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.client.screen.FabricStoveScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FabricFreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricMicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.FabricStoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.locale.Language;
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
    public TextureAtlasSprite[] getFluidSprites(Fluid fluid, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, FluidState state)
    {
        return FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(getter, pos, state);
    }

    @Override
    public RenderType getTelevisionScreenRenderType(ResourceLocation id)
    {
        return FabricRenderType.televisionScreen(id);
    }

    @Override
    public void renderTooltip(GuiGraphics graphics, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner position)
    {
        graphics.renderTooltipInternal(font, components, mouseX, mouseY, position, null);
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

    @Override
    public RenderPipeline.Snippet getMatricesColorSnippet()
    {
        return RenderPipelines.MATRICES_COLOR_SNIPPET;
    }
}
