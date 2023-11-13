package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.IWidgetGroup;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class Scene implements IWidgetGroup
{
    private final List<AbstractWidget> widgets = new ArrayList<>();

    public abstract void updateWidgets(int contentStart, int contentTop);

    public abstract void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);

    public void tick() {}

    protected final <T extends AbstractWidget> T addWidget(T widget)
    {
        this.widgets.add(widget);
        return widget;
    }

    @Override
    public List<AbstractWidget> getWidgets()
    {
        return Collections.unmodifiableList(this.widgets);
    }
}
