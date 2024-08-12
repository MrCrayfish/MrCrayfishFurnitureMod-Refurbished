package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
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
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.withdrawButton.active = this.getExperiencePoints() >= 1;
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, RECYCLING_BIN_TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getProcessTime() >= 0)
        {
            int maxProcessTime = Config.SERVER.recycleBin.processingTime.get();
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) maxProcessTime));
            this.blit(poseStack, this.leftPos + 85, this.topPos + 28, 176, 0, width, 17);
        }
        int maxLevel = Config.SERVER.recycleBin.maximumExperienceLevels.get();
        double currentLevel = Mth.clamp(this.getExperienceLevel(), 0, maxLevel);
        Component levelLabel = Utils.translation("gui", "experience_level", FORMAT.format(currentLevel), maxLevel);
        int labelWidth = this.minecraft.font.width(levelLabel) / 2;
        Matrix4f matrix = poseStack.last().pose();
        this.minecraft.font.drawInBatch8xOutline(levelLabel.getVisualOrderText(), this.leftPos + 68 - labelWidth, this.topPos + 60, 0xFFC8FF8F, 0xFF2D2102, matrix, this.minecraft.renderBuffers().bufferSource(), 0xF000F0);
        this.drawExperienceFluid(poseStack, (float) (currentLevel / maxLevel));

        if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 118, this.topPos + 22, 32, 48))
        {
            this.renderTooltip(poseStack, Utils.translation("gui", "experience_points", (int) this.getExperiencePoints()), mouseX, mouseY);
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

    private void drawExperienceFluid(PoseStack poseStack, float amount)
    {
        int yOffset = 48 - (int) (48 * amount);
        int height = (int) (48 * amount);
        float animation = (Mth.sin(Util.getMillis() / 500F) + 1) / 2F;
        this.drawBlitWithAlpha(poseStack, this.leftPos + 118, this.topPos + 22 + yOffset, 176, 17, 32, height, animation);
        this.drawBlitWithAlpha(poseStack, this.leftPos + 118, this.topPos + 22 + yOffset, 208, 17, 32, height, 1.0F - animation);
    }

    private void drawBlitWithAlpha(PoseStack poseStack, int x, int y, int u, int v, int width, int height, float alpha)
    {
        float scale = (float) 1 / 256;
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, RECYCLING_BIN_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        builder.vertex(matrix, x, y, 0).color(1.0F, 1.0F, 1.0F, alpha).uv(u * scale, v * scale).endVertex();
        builder.vertex(matrix, x, y + height, 0).color(1.0F, 1.0F, 1.0F, alpha).uv(u * scale, (v + height) * scale).endVertex();
        builder.vertex(matrix, x + width, y + height, 0).color(1.0F, 1.0F, 1.0F, alpha).uv((u + width) * scale, (v + height) * scale).endVertex();
        builder.vertex(matrix, x + width, y, 0).color(1.0F, 1.0F, 1.0F, alpha).uv((u + width) * scale, v * scale).endVertex();
        BufferUploader.drawWithShader(builder.end());
        RenderSystem.disableBlend();
    }
}
