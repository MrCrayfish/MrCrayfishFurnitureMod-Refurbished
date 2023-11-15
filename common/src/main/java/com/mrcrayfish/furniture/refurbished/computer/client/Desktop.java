package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.computer.Computer;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ProgramShortcutButton;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageComputerOpenProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class Desktop
{
    private static final int SHORTCUT_AREA_WIDTH = 220;
    private static final int SHORTCUT_COLUMNS = 7;

    private final ComputerScreen screen;
    private final List<ProgramShortcutButton> shortcuts;
    private int displayStart, displayTop, displayWidth, displayHeight;

    public Desktop(ComputerScreen screen)
    {
        this.screen = screen;
        this.shortcuts = this.createProgramShortcuts();
    }

    private List<ProgramShortcutButton> createProgramShortcuts()
    {
        int index = 0;
        List<ProgramShortcutButton> shortcuts = new ArrayList<>();
        for(ResourceLocation id : Computer.get().getPrograms())
        {
            Icon icon = Display.get().getIcon(id);
            if(icon != null)
            {
                shortcuts.add(new ProgramShortcutButton(this.screen, index++, 42, 32, this.getProgramName(id), icon, btn -> {
                    Network.getPlay().sendToServer(new MessageComputerOpenProgram(id));
                    btn.setFocused(false);
                }));
            }
        }
        return shortcuts;
    }

    public void update(int displayStart, int displayTop, int displayWidth, int displayHeight)
    {
        this.displayStart = displayStart;
        this.displayTop = displayTop;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        int startX = displayStart + (displayWidth - SHORTCUT_AREA_WIDTH) / 2;
        int startY = displayTop + 4;
        this.shortcuts.forEach(shortcut -> {
            int shortcutX = startX + (shortcut.getIndex() % SHORTCUT_COLUMNS) * 42 + (shortcut.getIndex() % SHORTCUT_COLUMNS) * 4;
            int shortcutY = startY + (shortcut.getIndex() / SHORTCUT_COLUMNS) * 32 + (shortcut.getIndex() / SHORTCUT_COLUMNS) * 4;
            shortcut.setPosition(shortcutX, shortcutY);
        });
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        graphics.fill(this.displayStart, this.displayTop, this.displayStart + this.displayWidth, this.displayTop + this.displayHeight, 0xFF262626);
        this.shortcuts.forEach(shortcut -> shortcut.render(graphics, mouseX, mouseY, partialTick));
    }

    private Component getProgramName(ResourceLocation id)
    {
        return Component.translatable(String.format("computer_program.%s.%s", id.getNamespace(), id.getPath()));
    }

    public List<ProgramShortcutButton> getShortcuts()
    {
        return this.shortcuts;
    }
}
