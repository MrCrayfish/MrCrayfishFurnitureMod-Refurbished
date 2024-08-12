package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.AABB;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class is mostly a hack to make the electricity rendering compatible with shader mods. It
 * doesn't work with every shader. Everything is drawn onto the entity render target as this is
 * drawn after the world is drawn.
 * <p>
 * Author: MrCrayfish
 */
public class DeferredElectricRenderer
{
    private static DeferredElectricRenderer instance;

    public static DeferredElectricRenderer get()
    {
        if(instance == null)
        {
            instance = new DeferredElectricRenderer();
        }
        return instance;
    }

    private final ResourceLocation nodeTexture = new ResourceLocation(Constants.MOD_ID, "textures/misc/electricity_nodes.png");
    private final List<BiConsumer<PoseStack, VertexConsumer>> builders = new LinkedList<>();

    private DeferredElectricRenderer() {}

    public void draw(PoseStack pose)
    {
        Minecraft mc = Minecraft.getInstance();
        RenderTarget target = mc.levelRenderer.entityTarget();
        if(target == null || this.builders.isEmpty())
            return;

        // During testing, if the render target is not cleared before drawing, some shader packs
        // will not draw electricity components correctly. This issue only happens if an entity with
        // an outline is in view. This is a hacky fix at the expense of breaking entity outline glow.
        if(Config.CLIENT.experimental.electricityShadersFix.get())
        {
            target.clear(Minecraft.ON_OSX);
        }

        // Draw to the entity outline layer
        target.bindWrite(false);

        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthMask(true);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.nodeTexture);

        pose.pushPose();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        for(BiConsumer<PoseStack, VertexConsumer> consumer : this.builders)
            consumer.accept(pose, builder);
        BufferUploader.drawWithShader(builder.end());
        pose.popPose();

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);

        // Restore main render target
        mc.getMainRenderTarget().bindWrite(false);

        this.builders.clear();
    }

    public void deferDraw(BiConsumer<PoseStack, VertexConsumer> consumer)
    {
        this.builders.add(consumer);
    }

    /**
     * Draws a coloured box with the given colour. This method is designed for the deferred renderer.
     *
     * @param matrix   the current posestack
     * @param consumer a multi buffer source
     * @param box      the aabb box to draw
     * @param colour   the colour of the box in decimal
     * @param alpha    the alpha value from 0 to 1
     */
    public void drawColouredBox(Matrix4f matrix, VertexConsumer consumer, AABB box, int colour, float alpha)
    {
        float red = FastColor.ARGB32.red(colour) / 255F;
        float green = FastColor.ARGB32.green(colour) / 255F;
        float blue = FastColor.ARGB32.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // South
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // West
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // East
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // Up
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxV, minU).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(minV, minU).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minV, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(maxV, maxV).color(red, green, blue, alpha).endVertex();
        // Down
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
    }

    /**
     * Draws a coloured box with the given colour, except the box will be inverted. This method is
     * designed for the deferred renderer.
     *
     * @param matrix   the current posestack
     * @param consumer a multi buffer source
     * @param box      the aabb box to draw
     * @param colour   the colour of the box in decimal
     * @param alpha    the alpha value from 0 to 1
     */
    public void drawInvertedColouredBox(Matrix4f matrix, VertexConsumer consumer, AABB box, int colour, float alpha)
    {
        float red = FastColor.ARGB32.red(colour) / 255F;
        float green = FastColor.ARGB32.green(colour) / 255F;
        float blue = FastColor.ARGB32.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // South
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // West
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // East
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // Up
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
        // Down
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(minU, minV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minU, maxV).color(red, green, blue, alpha).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(maxU, maxV).color(red, green, blue, alpha).endVertex();
    }

    /**
     *
     * @param matrix
     * @param consumer
     * @param box
     * @param minU
     * @param minV
     * @param maxU
     * @param maxV
     */
    public void drawTexturedBox(Matrix4f matrix, VertexConsumer consumer, AABB box, float minU, float minV, float maxU, float maxV)
    {
        // North
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(minU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        // South
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(maxU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(minU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        // West
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(maxU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(minU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        // East
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(maxU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        // Up
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(maxV, minU).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(minV, minU).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(minV, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(maxV, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        // Down
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(maxU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(minU, minV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(minU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(maxU, maxV).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
    }
}
