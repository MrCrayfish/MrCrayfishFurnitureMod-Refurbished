package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.computer.Computer;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ProgramShortcutButton;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageComputerOpenProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

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
                    if(btn.isFocused()) {
                        btn.changeFocus(true);
                    }
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
            shortcut.x = shortcutX;
            shortcut.y = shortcutY;
        });
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        GuiComponent.fill(poseStack, this.displayStart, this.displayTop, this.displayStart + this.displayWidth, this.displayTop + this.displayHeight, 0xFF262626);
        this.shortcuts.forEach(shortcut -> shortcut.render(poseStack, mouseX, mouseY, partialTick));

        // Draw fake taskbar
        GuiComponent.fill(poseStack, this.displayStart, this.displayTop + this.displayHeight - 16, this.displayStart + this.displayWidth, this.displayTop + this.displayHeight, 0xFF5A534F);
        GuiComponent.fill(poseStack, this.displayStart, this.displayTop + this.displayHeight - 16 + 2, this.displayStart + this.displayWidth, this.displayTop + this.displayHeight, 0xFF332E2D);

        // Draw time
        Font font = Minecraft.getInstance().font;
        String timeLabel = this.getDayTimeLabel();
        int width = font.width(timeLabel);
        ScreenHelper.drawString(poseStack, timeLabel, this.displayStart + this.displayWidth - width - 5, this.displayTop + this.displayHeight - 11, 0xFFFFFFFF, false);

        // Draw logo
        RenderSystem.setShaderTexture(0, ComputerScreen.TEXTURE);
        GuiComponent.blit(poseStack, this.displayStart, this.displayTop + this.displayHeight - 24, 32, 36, 0, 150, 16, 18, 256, 256);
    }

    private Component getProgramName(ResourceLocation id)
    {
        return Component.translatable(String.format("computer_program.%s.%s", id.getNamespace(), id.getPath()));
    }

    public List<ProgramShortcutButton> getShortcuts()
    {
        return this.shortcuts;
    }

    private String getDayTimeLabel()
    {
        Level level = Minecraft.getInstance().level;
        if(level != null)
        {
            // 6000 to offset midnight to be exactly when the moon is directly up
            long time = (level.getDayTime() + 6000L) % 24000L;
            long hours = time / 1000L;
            long minutes = 60 * (time % 1000L) / 1000L;
            return "%d:%02d".formatted(hours, minutes);
        }
        return "";
    }
}
