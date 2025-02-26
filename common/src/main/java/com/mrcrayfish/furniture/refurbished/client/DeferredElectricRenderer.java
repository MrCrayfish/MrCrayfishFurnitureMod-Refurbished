package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Handles drawing electricity nodes and connections. They are drawn on a separate texture so it's can overlay the world.
 *
 * <p>
 * Author: MrCrayfish
 */
public class DeferredElectricRenderer implements ResourceManagerReloadListener
{
    public static final String PASS_NAME = "refurbished_furniture_electricity";
    public static final ResourceLocation ID = Utils.resource("deferred_electric_renderer");
    private static DeferredElectricRenderer instance;

    public static DeferredElectricRenderer get()
    {
        if(instance == null)
        {
            instance = new DeferredElectricRenderer();
        }
        return instance;
    }

    private final ResourceLocation nodeTexture = Utils.resource("textures/misc/electricity_nodes.png");
    private final List<BiConsumer<PoseStack, VertexConsumer>> deferredDrawCalls = new LinkedList<>();
    private TextureTarget electricityTarget;
    private ResourceHandle<TextureTarget> handle;

    private DeferredElectricRenderer() {}

    /**
     * Called when the resource managed is reloaded. Sets up a custom texture target specifically for electricity nodes
     * and links. This texture is drawn in front of the world, which effectively allows nodes and electricity links to
     * be seen through walls and such.
     *
     * @param manager a resource manager instance
     */
    @Override
    public void onResourceManagerReload(ResourceManager manager)
    {
        if(this.electricityTarget != null)
            this.electricityTarget.destroyBuffers();
        Window window = Minecraft.getInstance().getWindow();
        this.electricityTarget = new TextureTarget(window.getWidth(), window.getHeight(), true);
        this.electricityTarget.setClearColor(0, 0, 0, 0);
        this.electricityTarget.clear();
    }

    /**
     * Simply resizes the electricity texture to the new window size
     *
     * @param width the new window width
     * @param height the new window height
     */
    public void resize(int width, int height)
    {
        if(this.electricityTarget != null)
        {
            this.electricityTarget.resize(width, height);
        }
    }

    /**
     * Sets up the frame pass required to draw the electricity texture target
     *
     * @param builder the FrameGraphBuilder of the current frame
     * @param camera the current camera instance
     */
    public void setupFramePass(FrameGraphBuilder builder, Camera camera)
    {
        if(this.electricityTarget != null && LinkHandler.isHoldingWrench())
        {
            ResourceHandle<TextureTarget> handle = builder.importExternal(PASS_NAME, this.electricityTarget);
            FramePass pass = builder.addPass(PASS_NAME);
            this.handle = pass.readsAndWrites(handle);
            pass.executes(() -> this.drawToTexture(camera, this.electricityTarget));
        }
        else
        {
            this.deferredDrawCalls.clear();
        }
    }

    public void deferDraw(BiConsumer<PoseStack, VertexConsumer> consumer)
    {
        this.deferredDrawCalls.add(consumer);
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
        float red = ARGB.red(colour) / 255F;
        float green = ARGB.green(colour) / 255F;
        float blue = ARGB.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // South
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // West
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // East
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Up
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Down
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
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
        float red = ARGB.red(colour) / 255F;
        float green = ARGB.green(colour) / 255F;
        float blue = ARGB.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // South
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // West
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // East
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Up
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Down
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
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
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // South
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // West
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // East
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Up
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxV, minU).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minV, minU).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minV, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxV, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Down
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Prepares and draws the electricity nodes and link to a separate texture so it can overlay the world, similar to
     * entity outlines. The texture is then later blit to screen in {@link #blitToScreen()}.
     *
     * @param camera the current camera
     * @param texture the texture target to draw to
     */
    private void drawToTexture(Camera camera, TextureTarget texture)
    {
        // Clear and bind the electricity texture
        texture.setClearColor(0, 0, 0, 0);
        texture.clear();
        texture.bindWrite(false);

        // Draw the nodes and connections
        PoseStack stack = new PoseStack();
        stack.pushPose();
        Vec3 view = camera.getPosition();
        stack.translate(-view.x(), -view.y(), -view.z());
        this.draw(stack);
        stack.popPose();

        // Restore main target
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    }

    private void draw(PoseStack pose)
    {
        if(this.deferredDrawCalls.isEmpty())
            return;

        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthMask(true);
        RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.nodeTexture);

        pose.pushPose();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        for(BiConsumer<PoseStack, VertexConsumer> consumer : this.deferredDrawCalls)
            consumer.accept(pose, builder);
        MeshData data = builder.build();
        if(data != null)
            BufferUploader.drawWithShader(data);
        pose.popPose();

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);

        this.deferredDrawCalls.clear();
    }

    /**
     * Draws the electricity texture onto the screen. This is drawn after the world, but before the player hand and HUD.
     * This will result in the electricity nodes and links drawn in front of everything, effectively removing the depth.
     */
    public void blitToScreen()
    {
        if(this.handle != null)
        {
            Window window = Minecraft.getInstance().getWindow();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            this.handle.get().blitAndBlendToScreen(window.getWidth(), window.getHeight());
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }

        // Once drawn, remove handle
        this.handle = null;
    }
}
