package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.vertex.*;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.RecycleBinMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.network.message.MessageWithdrawExperience;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Matrix4f;

import java.text.DecimalFormat;

/**
 * Author: MrCrayfish
 */
public class RecyclingBinScreen extends ElectricityContainerScreen<RecycleBinMenu>
{
    private static final DecimalFormat FORMAT = new DecimalFormat("0.###");
    private static final ResourceLocation RECYCLING_BIN_TEXTURE = Utils.resource("textures/gui/container/recycle_bin.png");

    private OnOffSlider slider;
    private Button withdrawButton;

    public RecyclingBinScreen(RecycleBinMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageHeight = 193;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init()
    {
        super.init();
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
        this.withdrawButton = this.addRenderableWidget(new IconButton(this.leftPos + 7, this.topPos + 75, 20, 10, 162, Components.GUI_WITHDRAW_EXPERIENCE, btn -> {
            Network.getPlay().sendToServer(new MessageWithdrawExperience());
        }));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.withdrawButton.active = this.getExperiencePoints() >= 1;
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.blit(RenderPipelines.GUI_TEXTURED, RECYCLING_BIN_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        if(this.menu.getProcessTime() >= 0)
        {
            int maxProcessTime = Config.SERVER.recycleBin.processingTime.get();
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) maxProcessTime));
            graphics.blit(RenderPipelines.GUI_TEXTURED, RECYCLING_BIN_TEXTURE, this.leftPos + 85, this.topPos + 28, 176, 0, width, 17, 256, 256);
        }
        int maxLevel = Config.SERVER.recycleBin.maximumExperienceLevels.get();
        double currentLevel = Mth.clamp(this.getExperienceLevel(), 0, maxLevel);
        Component levelLabel = Utils.translation("gui", "experience_level", FORMAT.format(currentLevel), maxLevel);
        int labelWidth = this.minecraft.font.width(levelLabel) / 2;

        // No longer easily possible to render the outlined text
        /*Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.mul(graphics.pose());
        MultiBufferSource.BufferSource source = this.minecraft.renderBuffers().bufferSource();
        this.minecraft.font.drawInBatch8xOutline(levelLabel.getVisualOrderText(), this.leftPos + 68 - labelWidth, this.topPos + 60, 0xFFC8FF8F, 0xFF2D2102, matrix, source, 0xF000F0);*/
        
        graphics.drawString(this.font, levelLabel, this.leftPos + 68 - labelWidth, this.topPos + 59, 0xFFC8FF8F, true);

        this.drawExperienceFluid(graphics, (float) (currentLevel / maxLevel));

        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 118, this.topPos + 22, 32, 48))
        {
            graphics.setTooltipForNextFrame(Utils.translation("gui", "experience_points", (int) this.getExperiencePoints()), mouseX, mouseY);
        }
    }

    private double getExperiencePoints()
    {
        return this.menu.getRecycledCount() * Config.SERVER.recycleBin.experiencePerItem.get();
    }

    /*
     * Credit to https://minecraft.wiki/w/Experience for the equations
     */
    private double getExperienceLevel()
    {
        double points = this.getExperiencePoints();
        if(points <= 352)
        {
            return Math.sqrt(points + 9) - 3;
        }
        else if(points <= 1507)
        {
            return ((double) 81 / 10) + Math.sqrt(((double) 2 / 5) * (points - ((double) 7839 / 40)));
        }
        return ((double) 325 / 18) + Math.sqrt(((double) 2 / 9) * (points - ((double) 54215 / 72)));
    }

    private void drawExperienceFluid(GuiGraphics graphics, float amount)
    {
        int yOffset = 48 - (int) (48 * amount);
        int height = (int) (48 * amount);
        float animation = (Mth.sin(Util.getMillis() / 500F) + 1) / 2F;
        this.drawBlitWithAlpha(graphics, this.leftPos + 118, this.topPos + 22 + yOffset, 176, 17, 32, height, animation);
        this.drawBlitWithAlpha(graphics, this.leftPos + 118, this.topPos + 22 + yOffset, 208, 17, 32, height, 1.0F - animation);
    }

    private void drawBlitWithAlpha(GuiGraphics graphics, int x, int y, int u, int v, int width, int height, float alpha)
    {
        int color = ARGB.colorFromFloat(Mth.clamp(alpha + 0.2F, 0, 1), 1.0F, 1.0F, 1.0F);
        graphics.blit(RenderPipelines.GUI_TEXTURED, RECYCLING_BIN_TEXTURE, x, y, u, v, width, height, 256, 256, color);
    }
}
