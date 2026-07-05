package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.PrimitiveTopology;
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
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.electricity.state.*;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;

/**
 * Handles drawing electricity nodes and connections. This needs special handling as electricity
 * nodes and connections are drawn in front the game world. This is performed by drawing to a
 * separate texture, then applying that texture to the main texture after the world has been drawn
 * but before the GUI is drawn.
 * <p>
 * The separate texture also allows depth information to be retained, unlike if you turned depth
 * test off and draw straight to the main texture. (which would produce a similar result but then
 * nodes and connections wouldn't look great).
 * <p>
 * Author: MrCrayfish
 */
public final class ElectricityRenderer
{
    public static final String PASS_NAME = "refurbished_furniture_electricity";
    private static final Identifier POWERABLE_AREA = Utils.id("textures/misc/powerable_area.png");
    private static final Identifier UNPOWERABLE_AREA = Utils.id("textures/misc/unpowerable_area.png");
    private static final Vector4fc CLEAR_COLOR = new Vector4f(0);

    private static ElectricityRenderer instance;

    public static ElectricityRenderer get()
    {
        if(instance == null)
        {
            instance = new ElectricityRenderer();
        }
        return instance;
    }

    private final PoseStack poseStack = new PoseStack();
    private final ElectricityRenderState electricityRenderState = new ElectricityRenderState();
    private final PowerableAreaRenderState powerableAreaRenderState = new PowerableAreaRenderState();
    private @Nullable Class<?> irisClass;
    private Method shaderPack;
    private Boolean shaderEnabled;
    private final TextureTarget electricityTarget;
    private ResourceHandle<@NotNull TextureTarget> handle;
    private boolean takenScreenshot;

    private ElectricityRenderer()
    {
        this.setupIrisSupport();
        Window window = Minecraft.getInstance().getWindow();
        this.electricityTarget = new TextureTarget("Refurbished Furniture Electricity Overlay", window.getWidth(), window.getHeight(), true, GpuFormat.RGBA8_UNORM);
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

    public void close()
    {
        this.electricityTarget.destroyBuffers();
    }

    public TextureTarget getTextureTarget()
    {
        return this.electricityTarget;
    }

    /**
     * Gets the current electricity render state. Do not modify this.
     *
     * @return The current electricity render state.
     */
    public ElectricityRenderState getElectricityRenderState()
    {
        return this.electricityRenderState;
    }

    public void extract(Camera camera)
    {
        this.electricityRenderState.reset();

        if(!WrenchHandler.isHoldingWrench())
            return;

        WrenchHandler handler = WrenchHandler.get();
        handler.extractPowerableArea(this.powerableAreaRenderState, camera.position());
        handler.extractLinkingConnection(this.electricityRenderState);

        this.forEachVisibleElectricityNode(camera, node -> {
            // Collect data about the node
            boolean nodeCanBeSelected = handler.isTargetNode(node) && !handler.isCreatingLink() && !node.isNodeConnectionLimitReached();
            boolean nodeIsBeingLinked = handler.isSelectedNode(node);
            boolean nodeIsJoinable = handler.canLinkToNode(node) && handler.isTargetNode(node);
            boolean nodeHighlighted = nodeCanBeSelected || nodeIsBeingLinked || nodeIsJoinable;

            // Create the node render state
            NodeRenderState nodeState = new NodeRenderState();
            nodeState.box = node.getNodeInteractBox().move(node.getNodePosition());
            nodeState.highlighted = nodeHighlighted;
            nodeState.highlightColour = handler.getLinkColour();
            this.electricityRenderState.nodeRenderStates.add(nodeState);

            // Collect connection states
            for(Connection connection : node.getNodeConnections())
            {
                BlockPos start = connection.getPosA();
                BlockPos end = connection.getPosB();
                boolean hovered = !handler.isCreatingLink() && connection.equals(handler.getTargetConnection());
                int colour = connection.getColour(node.getNodeLevel());
                this.electricityRenderState.connectionRenderStates.add(new ConnectionRenderState(start, end, hovered, colour));
            }
        });
    }

    public void submit(LevelRenderState levelRenderState, SubmitNodeCollector collector)
    {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();

        Vec3 camera = levelRenderState.cameraRenderState.pos;
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        ElectricitySubmitNodeCollector electricityCollector = (ElectricitySubmitNodeCollector) collector;
        RenderType renderType = ClientServices.PLATFORM.getElectricityRenderType();
        if(this.electricityRenderState.linkingConnectionRenderState != null)
        {
            electricityCollector.refurbished_furniture$submitElectricity(poseStack, renderType, (pose, vertexConsumer) -> {
                this.renderLinkingConnection(pose, vertexConsumer, this.electricityRenderState.linkingConnectionRenderState);
            });
        }

        this.electricityRenderState.nodeRenderStates.forEach(state -> {
            electricityCollector.refurbished_furniture$submitElectricity(poseStack, renderType, (pose, vertexConsumer) -> {
                drawTexturedBox(pose, vertexConsumer, state.box, 0, 0, 0.25F, 0.25F);
                if(state.highlighted) {
                    drawInvertedColouredBox(pose, vertexConsumer, state.box.inflate(0.03125), state.highlightColour, 0.7F);
                }
            });
        });

        this.electricityRenderState.connectionRenderStates.forEach(state -> {
            electricityCollector.refurbished_furniture$submitElectricity(poseStack, renderType, (pose, vertexConsumer) -> {
                this.renderConnection(pose, vertexConsumer, state);
            });
        });

        poseStack.popPose();
    }

    /**
     * Sets up the frame pass required to draw the electricity texture target
     *
     * @param builder the FrameGraphBuilder of the current frame
     */
    public void addFramePass(FrameGraphBuilder builder, FeatureRenderDispatcher.PreparedFrame preparedFrame)
    {
        // Reset shader enabled cache for this frame
        this.shaderEnabled = null;

        // Don't set up pass if nothing is submitted for electricity
        if(!((ElectricityPhaseExecutor) preparedFrame).refurbished_furniture$hasElectricity())
            return;

        // Create the frame pass
        ResourceHandle<@NotNull TextureTarget> handle = builder.importExternal(PASS_NAME, this.electricityTarget);
        FramePass pass = builder.addPass(PASS_NAME);
        this.handle = pass.readsAndWrites(handle);
        pass.executes(() -> {
            GpuTexture colorTexture = this.electricityTarget.getColorTexture();
            GpuTexture depthTexture = this.electricityTarget.getDepthTexture();
            if(colorTexture == null || depthTexture == null)
                return;
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colorTexture, CLEAR_COLOR, depthTexture, 0);
            ((ElectricityPhaseExecutor) preparedFrame).refurbished_furniture$executeElectricity();
        });
    }

    /**
     * Draws the electricity texture onto the screen. This is drawn after the world, but before the player hand and HUD.
     * This will result in the electricity nodes and links drawn in front of everything, effectively removing the depth.
     */
    public void blitToScreen()
    {
        this.tryAndTakeDebugScreenshot();

        // Only blit to the main texture if render pass handle was created
        if(this.handle != null)
        {
            GpuTextureView mainTexture = Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView();
            GpuTextureView electricityTexture = this.electricityTarget.getColorTextureView();
            if(electricityTexture != null && mainTexture != null)
            {
                try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Blit", mainTexture, Optional.empty()))
                {
                    pass.setPipeline(ModRenderPipelines.ELECTRICITY_BLIT);
                    RenderSystem.bindDefaultUniforms(pass);
                    pass.bindTexture("InSampler", electricityTexture, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
                    pass.draw(3, 1, 0, 0); // TODO 26.2 test
                }
            }
        }

        // Once drawn, remove handle
        this.handle = null;
    }

    /**
     *
     * @param pose
     * @param vertexConsumer
     * @param state
     */
    private void renderConnection(PoseStack.Pose pose, VertexConsumer vertexConsumer, ConnectionRenderState state)
    {
        this.poseStack.pushPose();
        this.poseStack.last().set(pose);

        BlockPos start = state.a();
        this.poseStack.translate(start.getX(), start.getY(), start.getZ());
        this.poseStack.translate(0.5, 0.5, 0.5);

        Vec3 delta = Vec3.atLowerCornerOf(state.b().subtract(state.a()));
        double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
        double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
        this.poseStack.mulPose(Axis.YP.rotation((float) yaw));
        this.poseStack.mulPose(Axis.ZP.rotation((float) pitch));

        float offset = (float) (Math.sin(Util.getMillis() / 500.0) + 1.0F) / 2.0F * 0.2F;
        AABB box = new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125);
        drawColouredBox(this.poseStack.last(), vertexConsumer, box, state.colour(), 0.7F + offset);
        drawColouredBox(this.poseStack.last(), vertexConsumer, box.inflate(0.03125), state.colour(), 0.5F + offset);
        if(state.hovered())
        {
            drawColouredBox(this.poseStack.last(), vertexConsumer, box.inflate(0.03125), 0xFFFFFFFF, 0.8F);
        }

        this.poseStack.popPose();
    }

    /**
     *
     * @param pose
     * @param vertexConsumer
     * @param state
     */
    private void renderLinkingConnection(PoseStack.Pose pose, VertexConsumer vertexConsumer, LinkingConnectionRenderState state)
    {
        this.poseStack.pushPose();
        this.poseStack.last().set(pose);

        Vec3 delta = state.end.subtract(state.start);
        double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
        double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
        this.poseStack.translate(state.start.x, state.start.y, state.start.z);
        this.poseStack.mulPose(Axis.YP.rotation((float) yaw));
        this.poseStack.mulPose(Axis.ZP.rotation((float) pitch));
        drawColouredBox(this.poseStack.last(), vertexConsumer, new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125), state.colour, 0.8F);
        drawColouredBox(this.poseStack.last(), vertexConsumer, new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125).inflate(0.03125), state.colour, 0.6F);

        this.poseStack.popPose();
    }

    // TODO spilt into separate class
    public void renderPowerableArea(Vec3 camera)
    {
        if(!WrenchHandler.isHoldingWrench())
            return;

        // If there is no shape, do not render
        if(this.powerableAreaRenderState.shape == null)
            return;

        // No point drawing if the alpha is zero
        if(this.powerableAreaRenderState.alpha <= 0)
            return;

        PoseStack stack = new PoseStack();
        stack.translate(-camera.x, -camera.y, -camera.z);

        // The render pipeline depends on if shaders are in use. Using the world border pipeline works
        // better as it is a vanilla pipeline that has been considered when designing the shaders, while
        // powerable area pipeline is custom and usually not compatible.
        boolean shaders = ElectricityRenderer.get().isIrisShadersEnabled();
        RenderPipeline pipeline = shaders ? RenderPipelines.WORLD_BORDER : ModRenderPipelines.POWERABLE_AREA;

        // Push the shape into the vertex buffer
        int indexCount;
        IndexType indexType;
        GpuBuffer powerableIndexBuffer;
        GpuBuffer powerableAreaBuffer;

        List<AABB> boxes = this.powerableAreaRenderState.shape.toAabbs();
        try(ByteBufferBuilder quadBuilder = new ByteBufferBuilder(boxes.size() * 4 * DefaultVertexFormat.POSITION_TEX.getVertexSize()))
        {
            BufferBuilder vertexBuilder = new BufferBuilder(quadBuilder, PrimitiveTopology.QUADS, DefaultVertexFormat.POSITION_TEX);
            boxes.forEach(box -> drawPowerableAreaBox(stack.last(), vertexBuilder, box));
            try(MeshData vertexData = vertexBuilder.buildOrThrow())
            {
                var autoIndexBuffer = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
                indexCount = vertexData.drawState().indexCount();
                indexType = autoIndexBuffer.type();
                powerableIndexBuffer = autoIndexBuffer.getBuffer(indexCount);
                powerableAreaBuffer = RenderSystem.getDevice().createBuffer(() -> "Powerable Area Buffer", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_COPY_DST, vertexData.vertexBuffer().remaining());
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(powerableAreaBuffer.slice(), vertexData.vertexBuffer());
            }
        }

        RenderTarget mainTarget = Minecraft.getInstance().gameRenderer.mainRenderTarget();
        RenderTarget weatherTarget = Minecraft.getInstance().levelRenderer.weatherTarget();
        GpuTextureView mainColor = mainTarget.getColorTextureView();
        GpuTextureView mainDepth = mainTarget.getDepthTextureView();
        if(weatherTarget != null)
        {
            mainColor = weatherTarget.getColorTextureView();
            mainDepth = weatherTarget.getDepthTextureView();
        }

        if(mainColor != null && mainDepth != null)
        {
            GpuBufferSlice slice = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrixCopy(), new Vector4f(1.0F, 1.0F, 1.0F, 0.6F * this.powerableAreaRenderState.alpha), new Vector3f(), new Matrix4f());
            AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(this.powerableAreaRenderState.invalid ? UNPOWERABLE_AREA : POWERABLE_AREA);
            try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Powerable Area", mainColor, Optional.empty(), mainDepth, OptionalDouble.empty()))
            {
                pass.setPipeline(pipeline);
                RenderSystem.bindDefaultUniforms(pass);
                pass.setUniform("DynamicTransforms", slice);
                pass.setIndexBuffer(powerableIndexBuffer, indexType);
                pass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
                pass.setVertexBuffer(0, powerableAreaBuffer.slice());
                pass.drawIndexed(indexCount, 1, 0, 0, 0);
            }
        }

        powerableAreaBuffer.close();
    }

    /**
     * For development purposes. Takes a screenshot of the electricity texture.
     */
    private void tryAndTakeDebugScreenshot()
    {
        if(Services.PLATFORM.isDevelopmentEnvironment())
        {
            Minecraft mc = Minecraft.getInstance();
            if(mc.hasShiftDown() && mc.hasAltDown() && mc.hasControlDown())
            {
                if(!this.takenScreenshot)
                {
                    Screenshot.grab(mc.gameDirectory, null, this.electricityTarget, 1, component -> {});
                    this.takenScreenshot = true;
                }
            }
            else
            {
                this.takenScreenshot = false;
            }
        }
    }

    /**
     * A helper method to iterate over visible electricity nodes in the level. Visible nodes are
     * ones that are within or equal to the electricity view distance, which is controlled in the
     * config. Only visible electricity nodes will be accepted into the consumer.
     *
     * @param consumer a consumer that will accept an electricity node
     */
    private void forEachVisibleElectricityNode(Camera camera, Consumer<IElectricityNode> consumer)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null)
            return;
        ((CachedElectricityNodes) mc.level).refurbished_furniture$ElectricityNodes().forEach(node -> {
            double maxDistance = Config.CLIENT.electricityViewDistance.get();
            double distance = node.getNodePosition().distToCenterSqr(camera.position());
            if(distance <= maxDistance * maxDistance) {
                consumer.accept(node);
            }
        });
    }

    /**
     * Draws a coloured box with the given colour. This method is designed for the deferred renderer.
     *
     * @param pose     the current posestack pose
     * @param consumer a multi buffer source
     * @param box      the aabb box to draw
     * @param colour   the colour of the box in decimal
     * @param alpha    the alpha value from 0 to 1
     */
    @SuppressWarnings("DuplicatedCode")
    public static void drawColouredBox(PoseStack.Pose pose, VertexConsumer consumer, AABB box, int colour, float alpha)
    {
        float red = ARGB.red(colour) / 255F;
        float green = ARGB.green(colour) / 255F;
        float blue = ARGB.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // South
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // West
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // East
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Up
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Down
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
    }

    /**
     * Draws a coloured box with the given colour, except the box will be inverted. This method is
     * designed for the deferred renderer.
     *
     * @param pose     the current posestack pose
     * @param consumer a multi buffer source
     * @param box      the aabb box to draw
     * @param colour   the colour of the box in decimal
     * @param alpha    the alpha value from 0 to 1
     */
    @SuppressWarnings("DuplicatedCode")
    public static void drawInvertedColouredBox(PoseStack.Pose pose, VertexConsumer consumer, AABB box, int colour, float alpha)
    {
        float red = ARGB.red(colour) / 255F;
        float green = ARGB.green(colour) / 255F;
        float blue = ARGB.blue(colour) / 255F;
        float minU = 0.0F;
        float minV = 0.25F;
        float maxU = minU + 0.25F;
        float maxV = minV + 0.25F;
        // North
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // South
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // West
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // East
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Up
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
        // Down
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, maxV).setColor(red, green, blue, alpha);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, maxV).setColor(red, green, blue, alpha);
    }

    /**
     *
     * @param pose
     * @param consumer
     * @param box
     * @param minU
     * @param minV
     * @param maxU
     * @param maxV
     */
    @SuppressWarnings("DuplicatedCode")
    public static void drawTexturedBox(PoseStack.Pose pose, VertexConsumer consumer, AABB box, float minU, float minV, float maxU, float maxV)
    {
        // North
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // South
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // West
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // East
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Up
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(maxV, minU).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(minV, minU).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(minV, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(maxV, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Down
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(maxU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(minU, minV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(minU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
        consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(maxU, maxV).setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Draws a texture box from an AABB. The position of the box is determined by the AABB.
     *
     * @param pose      the current pose stack pose
     * @param consumer  the vertex consumer to accept the data. Must be VERTEX and UV only
     * @param box       the AABB box to draw
     */
    private static void drawPowerableAreaBox(PoseStack.Pose pose, VertexConsumer consumer, AABB box)
    {
        float offset = Util.getMillis() * 0.001F;
        float width = (float) (box.maxX - box.minX);
        float height = (float) (box.maxY - box.minY);
        if(width > 0.01)
        {
            // North
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(0, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(width, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(width, offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(0, offset);
            // South
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(0, height + offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(width, height + offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(width, offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(0, offset);
        }
        width = (float) (box.maxZ - box.minZ);
        if(width > 0.01)
        {
            // West
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(0, height + offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(width, height + offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(width, offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(0, offset);
            // East
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(0, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(width, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(width, offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(0, offset);
        }
        width = (float) (box.maxX - box.minX);
        height = (float) (box.maxZ - box.minZ);
        if(width > 0.01)
        {
            // Up
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.minZ).setUv(height, width + offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.maxY, (float) box.maxZ).setUv(height, offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.maxZ).setUv(0, offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.maxY, (float) box.minZ).setUv(0, width + offset);
            // Down
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.minZ).setUv(0, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.minZ).setUv(width, height + offset);
            consumer.addVertex(pose, (float) box.maxX, (float) box.minY, (float) box.maxZ).setUv(width, offset);
            consumer.addVertex(pose, (float) box.minX, (float) box.minY, (float) box.maxZ).setUv(0, offset);
        }
    }
}
