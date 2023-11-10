package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.blockentity.ComputerBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ComputerScreen extends AbstractContainerScreen<ComputerMenu> implements ContainerListener
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/computer.png");

    public ComputerScreen(ComputerMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 150;
        menu.setChangeListener(this);
    }

    // Stop default labels from rendering
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Draw the current program
        DisplayableProgram<?> displayable = ((ClientComputer) this.menu.getComputer()).getDisplayable();
        if(displayable != null)
        {
            graphics.enableScissor(this.leftPos + 15, this.topPos + 15, this.leftPos + 241, this.topPos + 135);
            graphics.pose().translate(this.leftPos + 15, this.topPos + 15, 0);
            displayable.render(graphics, mouseX, mouseY, partialTick);
            graphics.disableScissor();
        }
    }

    @Override
    public void dataChanged(AbstractContainerMenu menu, int dataIndex, int dataValue)
    {

    }

    @Override
    public void slotChanged(AbstractContainerMenu menu, int slotIndex, ItemStack stack) {}
}
