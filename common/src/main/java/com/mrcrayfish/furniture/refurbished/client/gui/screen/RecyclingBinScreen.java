package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.RecyclingBinMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageRecycleItems;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public class RecyclingBinScreen extends AbstractContainerScreen<RecyclingBinMenu>
{
    private static final ResourceLocation RECYCLING_BIN_TEXTURE = Utils.resource("textures/gui/container/recycling_bin.png");

    //private OnOffSlider slider;
    private Button recycleButton;

    public RecyclingBinScreen(RecyclingBinMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageHeight = 172;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init()
    {
        super.init();
        /*this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Utils.translation("gui", "generator_toggle"), btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));*/
        this.addRenderableWidget(this.recycleButton = new IconButton(this.leftPos + this.imageWidth / 2 - 9, this.topPos + 34, 20, 10, 18, 18, CommonComponents.EMPTY, btn -> {
            Network.getPlay().sendToServer(new MessageRecycleItems());
        }));
        this.recycleButton.setTooltip(Tooltip.create(Utils.translation("gui", "recycle")));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.recycleButton.active = IntStream.range(0, 9)
                .mapToObj(value -> this.menu.getContainer().getItem(value))
                .anyMatch(stack -> !stack.isEmpty());
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        graphics.blit(RECYCLING_BIN_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth + 25, this.imageHeight);
    }
}
