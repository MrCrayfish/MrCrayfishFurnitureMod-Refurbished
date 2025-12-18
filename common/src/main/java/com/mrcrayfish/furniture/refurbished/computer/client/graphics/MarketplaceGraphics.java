package com.mrcrayfish.furniture.refurbished.computer.client.graphics;

import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.app.Marketplace;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.computer.client.Icon;
import com.mrcrayfish.furniture.refurbished.computer.client.Scene;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerButton;
import com.mrcrayfish.furniture.refurbished.computer.client.widget.ComputerSelectionList;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class MarketplaceGraphics extends DisplayableProgram<Marketplace>
{
    private static final Identifier TEXTURE = Utils.resource("textures/gui/program/marketplace.png");

    private final Scene catalogueScene;
    private final Scene shoppingCartScene;
    private final Scene mailboxScene;

    public MarketplaceGraphics(Marketplace program)
    {
        super(program, MAX_CONTENT_WIDTH / 2, MAX_CONTENT_HEIGHT / 2);
        this.catalogueScene = new CatalogueScene(this);
        this.shoppingCartScene = new ShoppingCartScene(this);
        this.mailboxScene = new MailboxScene(this);
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
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            Icon icon = Display.get().getIcon(this.program.getProgram().getId());
            if(icon != null)
            {
                graphics.blit(RenderPipelines.GUI_TEXTURED, icon.texture(), (this.program.getWidth() - 16) / 2, 10, icon.u(), icon.v(), 16, 16, 128, 128);
            }
            graphics.drawCenteredString(Minecraft.getInstance().font, "Coming Soon!", MAX_CONTENT_WIDTH / 4, 35, 0xFFFFFFFF);
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
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {
            graphics.fill(0, 0, this.program.getWidth(), 16, 0xFF653938);
            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 5, 2, 0, 0, 12, 12, 12, 12);
            graphics.drawString(Minecraft.getInstance().font, Integer.toString(this.getEmeraldCount()), 50, 5, 0xFFFFFFFF);
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
            public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float partialTick)
            {
                graphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0xFFFFFFFF);
                this.buyButton.setPosition(this.getX() + this.getWidth() - this.buyButton.getWidth(), this.getY());
                this.buyButton.render(graphics, mouseX, mouseY, partialTick);
            }

            @Override
            public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
            {
                this.buyButton.mouseClicked(event, doubleClick);
                return true;
            }
        }
    }

    public static class ShoppingCartScene extends Scene
    {
        private ShoppingCartScene(MarketplaceGraphics program)
        {

        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {

        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }

    public static class MailboxScene extends Scene
    {
        private MailboxScene(MarketplaceGraphics program)
        {

        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {

        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }
}
