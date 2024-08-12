package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.app.Marketplace;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerSelectionList;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class MarketplaceGraphics extends DisplayableProgram<Marketplace>
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/program/marketplace.png");

    public MarketplaceGraphics(Marketplace program)
    {
        super(program, MAX_CONTENT_WIDTH / 2, MAX_CONTENT_HEIGHT / 2);
        this.setScene(new ComingSoon(this));
    }

    public static class ComingSoon extends Scene
    {
        private final MarketplaceGraphics program;

        public ComingSoon(MarketplaceGraphics program)
        {
            this.program = program;
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop) {}

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
        {
            Icon icon = Display.get().getIcon(this.program.getProgram().getId());
            if(icon != null)
            {
                RenderSystem.setShaderTexture(0, icon.texture());
                GuiComponent.blit(poseStack, (this.program.getWidth() - 16) / 2, 10, icon.u(), icon.v(), 16, 16, 128, 128);
            }
            GuiComponent.drawCenteredString(poseStack, Minecraft.getInstance().font, "Coming Soon!", MAX_CONTENT_WIDTH / 4, 35, 0xFFFFFFFF);
        }
    }

    public static class CatalogueScene extends Scene
    {
        private final MarketplaceGraphics program;
        private final ComputerSelectionList<Item> itemList;

        private CatalogueScene(MarketplaceGraphics program)
        {
            this.program = program;
            this.itemList = this.addWidget(new ComputerSelectionList<>(program.getWidth() - 10, program.getHeight() - 26, 0, 100, 30));
            this.itemList.children().add(new Item());
            this.itemList.children().add(new Item());
            this.itemList.children().add(new Item());
            this.itemList.children().add(new Item());
            this.itemList.children().add(new Item());
            this.itemList.children().add(new Item());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.itemList.setPosition(contentStart + 5, contentTop + 21);
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
        {
            GuiComponent.fill(poseStack, 0, 0, this.program.getWidth(), 16, 0xFF653938);
            RenderSystem.setShaderTexture(0, TEXTURE);
            GuiComponent.blit(poseStack, 5, 2, 0, 0, 12, 12, 256, 256);
            ScreenHelper.drawString(poseStack, Integer.toString(this.getEmeraldCount()), 50, 5, 0xFFFFFFFF, true);
        }

        private int getEmeraldCount()
        {
            return Optional.ofNullable(this.program.getProgram().getComputer().getMenu()).map(menu -> (int) menu.getProgramData()).orElse(0);
        }

        private static class Item extends ObjectSelectionList.Entry<Item>
        {
            private final ComputerButton buyButton;

            public Item()
            {
                this.buyButton = new ComputerButton(50, 16, Component.literal("Buy"), btn -> {
                    System.out.println("Hello");
                });
            }

            @Override
            public Component getNarration()
            {
                return CommonComponents.EMPTY;
            }

            @Override
            public void render(PoseStack poseStack, int index, int top, int left, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float partialTick)
            {
                //graphics.drawString(Minecraft.getInstance().font, "Hello", 0, top, 0xFFFFFFFF);
                GuiComponent.fill(poseStack, left, top, left + rowWidth, top + rowHeight, 0xFFFFFFFF);
                this.buyButton.x = left + rowWidth - this.buyButton.getWidth();
                this.buyButton.y = top;
                this.buyButton.render(poseStack, mouseX, mouseY, partialTick);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button)
            {
                this.buyButton.mouseClicked(mouseX, mouseY, button);
                return true;
            }
        }
    }
}
