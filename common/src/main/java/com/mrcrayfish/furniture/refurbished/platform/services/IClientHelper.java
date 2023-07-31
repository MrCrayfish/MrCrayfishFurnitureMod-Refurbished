package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.IScreenBuilder;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public interface IClientHelper
{
    int getGuiLeft(AbstractContainerScreen<?> screen);

    int getGuiTop(AbstractContainerScreen<?> screen);

    CreativeModeTab getSelectedCreativeModeTab();

    void setTooltipCache(Tooltip tooltip, List<FormattedCharSequence> lines);

    TextureAtlasSprite[] getFluidSprites(Fluid fluid, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, FluidState state);

    <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void registerScreen(MenuType<? extends T> type, IScreenBuilder<T, U> builder);
}
