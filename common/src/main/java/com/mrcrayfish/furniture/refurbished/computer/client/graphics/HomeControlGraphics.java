package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.blockentity.IHomeControlDevice;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.computer.app.HomeControl;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerSelectionList;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageHomeControl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
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
        private final ComputerButton turnOnAllButton;
        private final ComputerButton turnOffAllButton;
        private final ComputerButton infoButton;

        public Home(HomeControlGraphics graphics)
        {
            this.graphics = graphics;
            this.devices = this.addWidget(new ComputerSelectionList<>(graphics.getWidth() - 10, graphics.getHeight() - 30, 25, 100, 16));
            this.devices.setRenderSelection(false);
            graphics.getProgram().findDevices().forEach(device -> {
                this.devices.children().add(new DeviceItem(device));
            });
            this.turnOnAllButton = this.addWidget(new ComputerButton(60, 14, graphics.translation("turn_on_all"), btn -> {
                Network.getPlay().sendToServer(new MessageHomeControl.UpdateAll(true));
            }));
            this.turnOnAllButton.setTextColour(0xFF376337);
            this.turnOnAllButton.setTextHighlightColour(0xFF376337);
            this.turnOnAllButton.setOutlineColour(0xFF222225);
            this.turnOnAllButton.setBackgroundHighlightColour(0xFF332E2D);
            this.turnOffAllButton = this.addWidget(new ComputerButton(60, 14, graphics.translation("turn_off_all"), btn -> {
                Network.getPlay().sendToServer(new MessageHomeControl.UpdateAll(false));
            }));
            this.turnOffAllButton.setTextColour(0xFF653938);
            this.turnOffAllButton.setTextHighlightColour(0xFF653938);
            this.turnOffAllButton.setOutlineColour(0xFF222225);
            this.turnOffAllButton.setBackgroundHighlightColour(0xFF332E2D);
            this.infoButton = this.addWidget(new ComputerButton(13, 14, Component.literal("i"), btn -> {
                graphics.setScene(new Info(graphics));
            }));
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.devices.setPosition(contentStart + 5, contentTop + 25);
            this.turnOnAllButton.setPosition(contentStart + 5, contentTop + 3);
            this.turnOffAllButton.setPosition(contentStart + 5 + this.turnOnAllButton.getWidth() + 2, contentTop + 3);
            this.infoButton.setPosition(contentStart + this.graphics.getWidth() - 5 - this.infoButton.getWidth(), contentTop + 3);
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
        {
            GuiComponent.fill(poseStack, 0, 0, this.graphics.getWidth(), 20, 0xFF262626);
        }
    }

    public static class Info extends Scene
    {
        private final HomeControlGraphics graphics;
        private final ComputerButton backButton;
        private final Component infoText;

        public Info(HomeControlGraphics graphics)
        {
            this.graphics = graphics;
            this.backButton = this.addWidget(new ComputerButton(14, 14, Component.literal("<"), btn -> {
                graphics.setScene(new Home(graphics));
            }));
            this.infoText = graphics.translation("info");
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.backButton.setPosition(contentStart + 5, contentTop + 3);
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
        {
            GuiComponent.fill(poseStack, 0, 0, this.graphics.getWidth(), 20, 0xFF262626);
            Font font = Minecraft.getInstance().font;
            font.drawWordWrap(poseStack, this.infoText, 5, 25, this.graphics.getWidth() - 10, 0xFF47403E);
        }
    }

    private static class DeviceItem extends ObjectSelectionList.Entry<DeviceItem>
    {
        private final IHomeControlDevice device;
        private final ComputerButton button;

        public DeviceItem(IHomeControlDevice device)
        {
            this.device = device;
            this.button = new ComputerButton(50, 14, Component.literal("Toggle"), btn -> {
                Network.getPlay().sendToServer(new MessageHomeControl.Toggle(this.device.getDevicePos()));
            });
            this.button.setBackgroundHighlightColour(0xFF332E2D);
            this.updateButtonLabel();
        }

        private void updateButtonLabel()
        {
            this.button.setMessage(this.device.isDeviceEnabled() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
            this.button.setTextColour(this.device.isDeviceEnabled() ? 0xFF376337 : 0xFF653938);
            this.button.setTextHighlightColour(this.device.isDeviceEnabled() ? 0xFF376337 : 0xFF653938);
        }

        @Override
        public void render(PoseStack poseStack, int index, int top, int left, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float partialTick)
        {
            this.updateButtonLabel();
            GuiComponent.fill(poseStack, left, top, left + rowWidth, top + rowHeight, 0xFF47403E);
            ScreenHelper.drawString(poseStack, this.device.getDeviceName(), left + 5, top + 4, 0xFF222225, false);
            this.button.setPosition(left + rowWidth - this.button.getWidth() - 1, top + 1);
            this.button.render(poseStack, mouseX, mouseY, partialTick);
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
