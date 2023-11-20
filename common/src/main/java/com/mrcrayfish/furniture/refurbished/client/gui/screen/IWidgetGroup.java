package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface IWidgetGroup
{
    List<GuiEventListener> getWidgets();
}
