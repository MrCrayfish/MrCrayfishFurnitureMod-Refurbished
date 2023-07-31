package com.mrcrayfish.furniture.refurbished.platform;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.IScreenBuilder;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
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
    }

    @Override
    public TextureAtlasSprite[] getFluidSprites(Fluid fluid, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, FluidState state)
    {
        return FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(getter, pos, state);
    }

    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void registerScreen(MenuType<? extends T> type, IScreenBuilder<T, U> builder)
    {
        MenuScreens.register(type, builder::create);
    }
}
