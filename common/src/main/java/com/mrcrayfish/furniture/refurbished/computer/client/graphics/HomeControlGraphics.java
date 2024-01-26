package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mrcrayfish.furniture.refurbished.blockentity.IHomeControlDevice;
import com.mrcrayfish.furniture.refurbished.computer.app.HomeControl;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerSelectionList;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageHomeControl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class HomeControlGraphics extends DisplayableProgram<HomeControl>
{
    public HomeControlGraphics(HomeControl program)
    {
        super(program, 200, 100);
        this.setScene(new Home(this));
    }

    public static class Home extends Scene
    {
        private final HomeControlGraphics graphics;
        private final ComputerSelectionList<DeviceItem> devices;

        public Home(HomeControlGraphics graphics)
        {
            this.graphics = graphics;
            this.devices = this.addWidget(new ComputerSelectionList<>(graphics.getWidth() - 10, graphics.getHeight() - 30, 25, 100, 20));
            this.devices.setRenderSelection(false);
            graphics.getProgram().findDevices().forEach(device -> {
                this.devices.children().add(new DeviceItem(device));
            });
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.devices.setPosition(contentStart + 5, contentTop + 25);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            graphics.fill(0, 0, this.graphics.getWidth(), 20, 0xFF262626);
        }
    }

    public static class AddDevice extends Scene
    {
        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {

        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }

    private static class DeviceItem extends ObjectSelectionList.Entry<DeviceItem>
    {
        private final IHomeControlDevice device;
        private final ComputerButton button;

        public DeviceItem(IHomeControlDevice device)
        {
            this.device = device;
            this.button = new ComputerButton(50, 16, Component.literal("Toggle"), btn -> {
                Network.getPlay().sendToServer(new MessageHomeControl.Toggle(this.device.getDevicePos()));
            });
            this.button.setBackgroundHighlightColour(0xFF332E2D);
            this.updateButtonLabel();
        }

        private void updateButtonLabel()
        {
            this.button.setMessage(this.device.isDevicePowered() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
            this.button.setTextColour(this.device.isDevicePowered() ? 0xFF376337 : 0xFF653938);
            this.button.setTextHighlightColour(this.device.isDevicePowered() ? 0xFF376337 : 0xFF653938);
        }

        @Override
        public void renderBack(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9)
        {
            super.renderBack($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
        }

        @Override
        public void render(GuiGraphics graphics, int index, int top, int left, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float partialTick)
        {
            this.updateButtonLabel();
            graphics.fill(left, top, left + rowWidth, top + rowHeight, 0xFF47403E);
            graphics.drawString(Minecraft.getInstance().font, this.device.getDeviceName(), left + 5, top + 6, 0xFF222225, false);
            this.button.setPosition(left + rowWidth - this.button.getWidth() - 2, top + 2);
            this.button.render(graphics, mouseX, mouseY, partialTick);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button)
        {
            this.button.mouseClicked(mouseX, mouseY, button);
            return true;
        }

        @Override
        public Component getNarration()
        {
            return this.device.getDeviceName();
        }
    }
}
