package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.IWidgetGroup;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class Scene implements IWidgetGroup
{
    private final List<GuiEventListener> widgets = new ArrayList<>();
    private final List<Renderable> renderables = new ArrayList<>();

    public abstract void updateWidgets(int contentStart, int contentTop);

    public abstract void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);

    public void tick() {}

    protected final <T extends GuiEventListener> T addWidget(T widget)
    {
        this.widgets.add(widget);
        if(widget instanceof Renderable renderable)
        {
            this.renderables.add(renderable);
        }
        return widget;
    }

    @Override
    public List<GuiEventListener> getWidgets()
    {
        return Collections.unmodifiableList(this.widgets);
    }

    public List<Renderable> getRenderables()
    {
         return this.renderables;
    }
}
