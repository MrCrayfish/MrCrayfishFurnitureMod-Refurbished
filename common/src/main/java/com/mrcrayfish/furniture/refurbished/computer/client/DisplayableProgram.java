package com.mrcrayfish.furniture.refurbished.computer.client;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public abstract class DisplayableProgram<T extends Program>
{
    protected final T program;
    protected final int width;
    protected final int height;
    protected int windowOutlineColour = 0xFF47403E;
    protected int windowTitleBarColour = 0xFF5B5450;
    protected int windowTitleLabelColour = 0xFF222225;
    protected int windowBackgroundColour = 0xFF222225;
    protected @Nullable Scene scene;
    private @Nullable Listener listener;
    private int contentStart;
    private int contentTop;

    public DisplayableProgram(T program, int width, int height)
    {
        Preconditions.checkArgument(width >= 16 && width <= 224, "Width must be between 16 and 224 (inclusive)");
        Preconditions.checkArgument(height >= 16 && height <= 110, "Height must be between 16 and 110 (inclusive)");
        this.program = program;
        this.width = width;
        this.height = height;
    }

    public void update(int contentStart, int contentTop)
    {
        this.contentStart = contentStart;
        this.contentTop = contentTop;
        if(this.scene != null)
        {
            this.scene.updateWidgets(contentStart, contentTop);
        }
    }

    public void tick()
    {
        if(this.scene != null)
        {
            this.scene.tick();
        }
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        if(this.scene != null)
        {
            // Render scene
            graphics.pose().pushPose();
            graphics.pose().translate(this.contentStart, this.contentTop, 0);
            this.scene.render(graphics, mouseX, mouseY, partialTick);
            graphics.pose().popPose();

            // Render widgets
            this.scene.getWidgets().forEach(widget -> widget.render(graphics, mouseX, mouseY, partialTick));
        }
    }

    public final T getProgram()
    {
        return this.program;
    }

    public final int getWidth()
    {
        return this.width;
    }

    public final int getHeight()
    {
        return this.height;
    }

    protected void setWindowOutlineColour(int colour)
    {
        this.windowOutlineColour = colour;
    }

    public int getWindowOutlineColour()
    {
        return this.windowOutlineColour;
    }

    protected void setWindowTitleBarColour(int colour)
    {
        this.windowTitleBarColour = colour;
    }

    public int getWindowTitleBarColour()
    {
        return this.windowTitleBarColour;
    }

    protected void setWindowTitleLabelColour(int colour)
    {
        this.windowTitleLabelColour = colour;
    }

    public int getWindowTitleLabelColour()
    {
        return this.windowTitleLabelColour;
    }

    protected void setWindowBackgroundColour(int colour)
    {
        this.windowBackgroundColour = colour;
    }

    public int getWindowBackgroundColour()
    {
        return windowBackgroundColour;
    }

    protected void setScene(@Nullable Scene newScene)
    {
        Scene oldScene = this.scene;
        this.scene = newScene;
        if(this.scene != null)
        {
            this.scene.updateWidgets(this.contentStart, this.contentTop);
        }
        if(this.listener != null)
        {
            this.listener.onChangeScene(oldScene, newScene);
        }
    }

    @Nullable
    public final Scene getScene()
    {
        return this.scene;
    }

    public final Component translation(String key)
    {
        ResourceLocation id = this.getProgram().getId();
        return Component.translatable(String.format("computer_program.%s.%s.%s", id.getNamespace(), id.getPath(), key));
    }

    public final void setListener(Listener listener)
    {
        Preconditions.checkState(this.listener == null, "Listener can only be assigned once");
        this.listener = listener;

        // Trigger scene change if scene already set
        if(this.scene != null)
        {
            listener.onChangeScene(null, this.scene);
        }
    }

    public static class Listener
    {
        private final ComputerScreen screen;

        public Listener(ComputerScreen screen)
        {
            this.screen = screen;
        }

        public void onChangeScene(@Nullable Scene oldScene, @Nullable Scene newScene)
        {
            if(oldScene != null)
            {
                this.screen.removeWidgets(oldScene);
            }
            if(newScene != null)
            {
                this.screen.addWidgets(newScene);
            }
        }
    }
}
