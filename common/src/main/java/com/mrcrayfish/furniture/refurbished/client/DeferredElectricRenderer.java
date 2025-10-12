package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Method;
import java.util.*;
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
    private @Nullable Class<?> irisClass;
    private Method shaderPack;
    private Boolean shaderEnabled;
    private TextureTarget electricityTarget;
    private ResourceHandle<TextureTarget> handle;

    private DeferredElectricRenderer()
    {
        this.setupIrisSupport();
    }

    /**
     * Try and detect if Iris is loaded at runtime, and gather the required method
     * to determine if a shader pack is enabled.
     */
    private void setupIrisSupport()
    {
        try
        {
            this.irisClass = Class.forName("net.irisshaders.iris.Iris");
            this.shaderPack = this.irisClass.getDeclaredMethod("getCurrentPack");
            this.shaderPack.setAccessible(true);
            Constants.LOG.info("Iris detected! Will use modified rendering for electricity when shaders are enabled");
        }
        catch(NoSuchMethodException e)
        {
            // If Iris is loaded but the method is missing, we have a problem
            throw new RuntimeException("Failed to locate Iris shader pack getter", e);
        }
        catch(ClassNotFoundException ignored) {}
    }

    /**
     * @return True if Iris is installed and a shader pack is currently enabled
     */
    public boolean isIrisShadersEnabled()
    {
        if(this.irisClass != null && this.shaderPack != null && this.shaderEnabled == null)
        {
            try
            {
                Optional<?> optional = (Optional<?>) this.shaderPack.invoke(null);
                this.shaderEnabled = optional.isPresent();
                return this.shaderEnabled;
            }
            catch(Exception e)
            {
                throw new RuntimeException("Failed to invoke shader pack getter", e);
            }
        }
        return this.shaderEnabled != null && this.shaderEnabled;
    }

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
        this.electricityTarget = new TextureTarget("Refurbished Furniture Electricity Overlay", window.getWidth(), window.getHeight(), true);
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

    public TextureTarget getElectricityTarget()
    {
        return this.electricityTarget;
    }

    /**
     * Pushes a draw call to be handled at a later point
     *
     * @param consumer a consumer to contain the draw calls
     */
    public void deferDraw(BiConsumer<PoseStack, VertexConsumer> consumer)
    {
        this.deferredDrawCalls.add(consumer);
    }

    /**
     * Sets up the frame pass required to draw the electricity texture target
     *
     * @param builder the FrameGraphBuilder of the current frame
     * @param camera the current camera instance
     */
    @SuppressWarnings("DataFlowIssue")
    public void setupFramePass(FrameGraphBuilder builder, Camera camera)
    {
        // Reset shader enabled cache for this frame
        this.shaderEnabled = null;

        if(this.electricityTarget != null && LinkHandler.isHoldingWrench())
        {
            ResourceHandle<TextureTarget> handle = builder.importExternal(PASS_NAME, this.electricityTarget);
            FramePass pass = builder.addPass(PASS_NAME);
            this.handle = pass.readsAndWrites(handle);
            pass.executes(() -> {
                // Clear and bind the electricity texture
                GpuTexture colorTexture = this.electricityTarget.getColorTexture();
                GpuTexture depthTexture = this.electricityTarget.getDepthTexture();
                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colorTexture, 0, depthTexture, 1);

                // Draw all the deferred render calls
                if(!this.isIrisShadersEnabled())
                {
                    PoseStack stack = new PoseStack();
                    Vec3 view = camera.getPosition();
                    stack.translate(-view.x(), -view.y(), -view.z());
                    this.drawDeferredCalls(stack);
                }
            });
        }
        else
        {
            this.deferredDrawCalls.clear();
        }
    }

    private void drawDeferredCalls(PoseStack pose)
    {
        if(this.deferredDrawCalls.isEmpty())
            return;

        RenderPipeline pipeline = ModRenderPipelines.ELECTRICITY;
        try(ByteBufferBuilder quadBuilder = new ByteBufferBuilder(pipeline.getVertexFormat().getVertexSize() * 4))
        {
            BufferBuilder vertexBuilder = new BufferBuilder(quadBuilder, pipeline.getVertexFormatMode(), pipeline.getVertexFormat());
            this.deferredDrawCalls.forEach(consumer -> consumer.accept(pose, vertexBuilder));
            try(MeshData data = vertexBuilder.build())
            {
                this.uploadMeshToElectricityTexture(data);
            }
        }

        this.deferredDrawCalls.clear();
    }

    /**
     * Uploads the provided MeshData to the electricity texture target.
     *
     * @param data the mesh data. null is allowed if mesh data was empty
     */
    @SuppressWarnings("DataFlowIssue")
    private void uploadMeshToElectricityTexture(@Nullable MeshData data)
    {
        if(data == null)
            return;

        RenderTarget target = this.electricityTarget;
        RenderPipeline pipeline = ModRenderPipelines.ELECTRICITY;
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(this.nodeTexture);

        GpuBufferSlice slice = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), new Vector3f(), new Matrix4f(), 0.0F);
        RenderSystem.AutoStorageIndexBuffer autoIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode());
        GpuBuffer indexBuffer = autoIndexBuffer.getBuffer(data.drawState().indexCount());

        GpuBuffer vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Deferred Electricity Renderer", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_COPY_DST, data.vertexBuffer().remaining());
        RenderSystem.getDevice().createCommandEncoder().writeToBuffer(vertexBuffer.slice(), data.vertexBuffer());

        try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Deferred Electricity Renderer", target.getColorTextureView(), OptionalInt.empty(), target.getDepthTextureView(), OptionalDouble.empty()))
        {
            pass.setPipeline(pipeline);
            RenderSystem.bindDefaultUniforms(pass);
            pass.setVertexBuffer(0, vertexBuffer);
            pass.setUniform("DynamicTransforms", slice);
            pass.setIndexBuffer(indexBuffer, autoIndexBuffer.type());
            pass.bindSampler("Sampler0", texture.getTextureView());
            pass.drawIndexed(0, 0, data.drawState().indexCount(), 1);
        }
    }

    /**
     * Draws the electricity texture onto the screen. This is drawn after the world, but before the player hand and HUD.
     * This will result in the electricity nodes and links drawn in front of everything, effectively removing the depth.
     */
    public void blitToScreen(Matrix4f projMatrix, Camera camera)
    {
        // When Iris is enabled, we have to do a late call to draw
        if(this.isIrisShadersEnabled())
        {
            PoseStack stack = new PoseStack();
            Vec3 view = camera.getPosition();
            stack.mulPose(projMatrix);
            stack.translate(-view.x(), -view.y(), -view.z());
            this.drawDeferredCalls(stack);
        }

        GpuTextureView mainColor = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
        GpuTextureView electricityColorView = this.electricityTarget.getColorTextureView();
        if(this.handle != null && electricityColorView != null && mainColor != null)
        {
            // TODO 1.21.10 test
            try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Blit", mainColor, OptionalInt.empty()))
            {
                pass.setPipeline(ModRenderPipelines.ELECTRICITY_BLIT);
                RenderSystem.bindDefaultUniforms(pass);
                pass.bindSampler("InSampler", electricityColorView);
                pass.draw(0, 3);
            }
        }

        // Once drawn, remove handle
        this.handle = null;
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
}
