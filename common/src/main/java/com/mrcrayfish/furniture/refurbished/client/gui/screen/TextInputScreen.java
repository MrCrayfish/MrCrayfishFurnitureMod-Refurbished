package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.google.common.base.MoreObjects;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class TextInputScreen extends Screen
{
    private static final Identifier WINDOW_SPRITE = Utils.id("window");
    public static final int WINDOW_WIDTH = 160;
    public static final int WINDOW_HEIGHT = 72;

    protected final Component hint;
    protected final Function<String, Boolean> callback;
    protected Function<String, Boolean> validator = s -> true;
    protected EditBox editBox;
    protected Button closeButton;
    protected Button acceptButton;
    protected Component acceptLabel;
    protected String input = "";

    public TextInputScreen(Component title, Component hint, Function<String, Boolean> callback)
    {
        super(title);
        this.hint = hint;
        this.callback = callback;
    }

    public void setValidator(Function<String, Boolean> validator)
    {
        this.validator = validator;
    }

    public void setAcceptLabel(Component acceptLabel)
    {
        this.acceptLabel = acceptLabel;
    }

    @Override
    protected void init()
    {
        int startX = (this.width - WINDOW_WIDTH) / 2;
        int startY = (this.height - WINDOW_HEIGHT) / 2;
        this.addRenderableWidget(this.editBox = new EditBox(this.minecraft.font, startX + 6, startY + 20, WINDOW_WIDTH - 12, 20, this.hint));
        this.editBox.setResponder(this::updateAcceptButton);
        if(!this.input.isBlank())
        {
            this.editBox.setValue(this.input);
        }
        this.addRenderableWidget(this.closeButton = Button.builder(Component.literal("Close"), btn -> {
            this.minecraft.gui.setScreen(null);
        }).pos(startX + 6, startY + 45).size((WINDOW_WIDTH - 12) / 2 - 2, 20).build());
        this.addRenderableWidget(this.acceptButton = Button.builder(MoreObjects.firstNonNull(this.acceptLabel, Component.literal("Accept")), btn -> {
            if(this.callback.apply(this.input)) {
                this.minecraft.gui.setScreen(null);
            }
        }).pos(startX + (WINDOW_WIDTH - 12) / 2 + 2 + 6, startY + 45).size((WINDOW_WIDTH - 12) / 2 - 2, 20).build());
        this.updateAcceptButton(this.input);
    }

    private void updateAcceptButton(String input)
    {
        boolean valid = this.validator.apply(input);
        this.editBox.setTextColor(valid ? 0xFFFFFFFF : 0xFFFF0000);
        this.acceptButton.active = valid;
        this.input = input;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        super.extractBackground(extractor, mouseX, mouseY, partialTick);
        int startX = (this.width - WINDOW_WIDTH) / 2;
        int startY = (this.height - WINDOW_HEIGHT) / 2;
        extractor.blitSprite(RenderPipelines.GUI_TEXTURED, WINDOW_SPRITE, startX, startY, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        super.extractRenderState(extractor, mouseX, mouseY, partialTick);
        int startX = (this.width - WINDOW_WIDTH) / 2;
        int startY = (this.height - WINDOW_HEIGHT) / 2;
        extractor.text(this.minecraft.font, this.title, startX + 6, startY + 7, 0xFF404040, false);
    }
}
