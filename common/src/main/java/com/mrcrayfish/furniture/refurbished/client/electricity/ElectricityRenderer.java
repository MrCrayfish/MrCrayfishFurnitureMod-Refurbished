package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.IndexType;
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
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
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
public final class ElectricityRenderer implements ResourceManagerReloadListener
{
    public static final String PASS_NAME = "refurbished_furniture_electricity";
    public static final Identifier ID = Utils.id("electricity_renderer");
    private static final Identifier POWERABLE_AREA = Utils.id("textures/misc/powerable_area.png");
    private static final Identifier UNPOWERABLE_AREA = Utils.id("textures/misc/unpowerable_area.png");

    private static ElectricityRenderer instance;

    public static ElectricityRenderer get()
    {
        if(instance == null)
        {
            instance = new ElectricityRenderer();
        }
        return instance;
    }

    private final SubmitStorage storage = new SubmitStorage();
    private final PoseStack poseStack = new PoseStack();
    private final ElectricityRenderState renderState = new ElectricityRenderState();
    private @Nullable Class<?> irisClass;
    private Method shaderPack;
    private Boolean shaderEnabled;
    private TextureTarget electricityTarget;
    private ResourceHandle<TextureTarget> handle;
    private boolean takenScreenshot;
    // TEMPORARY diagnostic throttle counters for the missing-overlay investigation; remove once resolved.
    private long debugLastLogMillis;
    private long debugLastLogMillis2;
    private long debugLastLogMillis3;
    private long debugLastLogMillis4;
    private long debugLastLogMillis5;
    private long debugLastLogMillis6;
    private long debugLastLogMillis7;
    private long debugLastLogMillis8;
    private long debugLastLogMillis9;
    private long debugLastLogMillis10;

    private ElectricityRenderer()
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
        this.electricityTarget = new TextureTarget("Refurbished Furniture Electricity Overlay", window.getWidth(), window.getHeight(), true, GpuFormat.RGBA8_UNORM);
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

    public TextureTarget getTextureTarget()
    {
        return this.electricityTarget;
    }

    /**
     * Gets the current electricity render state. Do not modify this.
     *
     * @return The current electricity render state.
     */
    public ElectricityRenderState getRenderState()
    {
        return this.renderState;
    }

    public void extract(Camera camera)
    {
        this.renderState.reset();

        boolean holdingWrench = WrenchHandler.isHoldingWrench();
        if(System.currentTimeMillis() - this.debugLastLogMillis > 1000)
        {
            this.debugLastLogMillis = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] extract() called, holdingWrench={}", holdingWrench);
        }

        if(holdingWrench)
        {
            WrenchHandler handler = WrenchHandler.get();
            handler.extractLinkingConnection(this.renderState);

            this.forEachVisibleElectricityNode(camera, node -> {
                // Collect node state
                boolean nodeCanBeSelected = handler.isTargetNode(node) && !handler.isCreatingLink() && !node.isNodeConnectionLimitReached();
                boolean nodeIsBeingLinked = handler.isSelectedNode(node);
                boolean nodeIsJoinable = handler.canLinkToNode(node) && handler.isTargetNode(node);
                boolean nodeHighlighted = nodeCanBeSelected || nodeIsBeingLinked || nodeIsJoinable;
                NodeRenderState nodeState = new NodeRenderState();
                nodeState.box = node.getNodeInteractBox().move(node.getNodePosition());
                nodeState.highlighted = nodeHighlighted;
                nodeState.highlightColour = handler.getLinkColour();
                this.renderState.nodes.add(nodeState);

                // Collect connection states
                for(Connection connection : node.getNodeConnections())
                {
                    BlockPos start = connection.getPosA();
                    BlockPos end = connection.getPosB();
                    boolean hovered = !handler.isCreatingLink() && connection.equals(handler.getTargetConnection());
                    int colour = connection.getColour(node.getNodeLevel());
                    this.renderState.connections.add(new ConnectionRenderState(start, end, hovered, colour));
                }
            });
        }

        if(System.currentTimeMillis() - this.debugLastLogMillis2 > 1000)
        {
            this.debugLastLogMillis2 = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] extract() result: nodes={}, connections={}, link={}, isCreatingLink={}, selectedNode={}",
                this.renderState.nodes.size(), this.renderState.connections.size(), this.renderState.link != null,
                WrenchHandler.get().isCreatingLink(), WrenchHandler.get().getSelectedNode());
        }
    }

    private void submitElectricityRenderState(ElectricityRenderState renderState, PoseStack stack)
    {
        renderState.nodes.forEach(box -> this.storage.submitNode(stack, box));
        renderState.connections.forEach(connection -> this.storage.submitConnection(stack, connection));
        if(renderState.link != null)
            this.storage.submitLinkingConnection(stack, renderState.link);
    }

    /**
     * Sets up the frame pass required to draw the electricity texture target
     *
     * @param builder the FrameGraphBuilder of the current frame
     * @param camera the current camera instance
     */
    @SuppressWarnings("DataFlowIssue")
    public void setupFramePass(FrameGraphBuilder builder, Vec3 camera)
    {
        // Reset shader enabled cache for this frame
        this.shaderEnabled = null;

        // TEMPORARY diagnostic: confirms setupFramePass is actually being invoked by the frame graph.
        if(System.currentTimeMillis() - this.debugLastLogMillis3 > 1000)
        {
            this.debugLastLogMillis3 = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] setupFramePass() called");
        }

        // Create the frame pass
        ResourceHandle<TextureTarget> handle = builder.importExternal(PASS_NAME, this.electricityTarget);
        FramePass pass = builder.addPass(PASS_NAME);
        this.handle = pass.readsAndWrites(handle);
        pass.executes(() ->
        {
            // Clear the electricity texture
            GpuTexture colorTexture = this.electricityTarget.getColorTexture();
            GpuTexture depthTexture = this.electricityTarget.getDepthTexture();
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colorTexture, new Vector4f(0, 0, 0, 0), depthTexture, 1.0);

            // Submit the states to storage
            PoseStack stack = new PoseStack();
            stack.translate(-camera.x, -camera.y, -camera.z);
            this.submitElectricityRenderState(this.renderState, stack);

            // TEMPORARY diagnostic: confirms whether anything actually made it into storage to draw.
            if(System.currentTimeMillis() - this.debugLastLogMillis4 > 1000)
            {
                this.debugLastLogMillis4 = System.currentTimeMillis();
                Constants.LOG.info("[ElectricityDebug] storage before draw: nodeSubmits={}, connectionSubmits={}, linkingConnectionSubmits={}",
                    this.storage.nodeSubmits.size(), this.storage.connectionSubmits.size(), this.storage.linkingConnectionSubmits.size());
            }

            // Draw the rest of the electricity features.
            // MC 26.2 removed MultiBufferSource entirely; build a one-off BufferBuilder and submit
            // it through the RenderType's own PreparedRenderType, which carries the pipeline/texture
            // bindings already declared in FabricRenderType.ELECTRICITY's RenderSetup.
            RenderType renderType = ClientServices.PLATFORM.getElectricityRenderType();
            try(ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(renderType.format().getVertexSize() * 256))
            {
                BufferBuilder vertexConsumer = new BufferBuilder(byteBufferBuilder, renderType.primitiveTopology(), renderType.format());
                this.storage.linkingConnectionSubmits.forEach(submit -> {
                    this.renderLinkingConnection(submit.pose, vertexConsumer, submit.linkingConnectionRenderState);
                });
                this.storage.nodeSubmits.forEach(submit -> submit.renderer.accept(submit.pose, vertexConsumer));
                this.storage.connectionSubmits.forEach(submit -> {
                    this.renderConnection(submit.pose, vertexConsumer, submit.connectionRenderState);
                });

                try(MeshData data = vertexConsumer.build())
                {
                    // TEMPORARY diagnostic: confirms whether MeshData was even produced (null = no vertices written)
                    // and what indexCount is about to be submitted.
                    if(System.currentTimeMillis() - this.debugLastLogMillis5 > 1000)
                    {
                        this.debugLastLogMillis5 = System.currentTimeMillis();
                        Constants.LOG.info("[ElectricityDebug] MeshData={}, indexCount={}", data != null, data != null ? data.drawState().indexCount() : -1);
                    }
                    if(data != null)
                    {
                        RenderSystem.AutoStorageIndexBuffer autoIndexBuffer = RenderSystem.getSequentialBuffer(renderType.primitiveTopology());
                        IndexType indexType = autoIndexBuffer.type();
                        GpuBuffer indexBuffer = autoIndexBuffer.getBuffer(data.drawState().indexCount());
                        GpuBuffer vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Electricity vertex buffer", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_COPY_DST, data.vertexBuffer().remaining());
                        RenderSystem.getDevice().createCommandEncoder().writeToBuffer(vertexBuffer.slice(), data.vertexBuffer());
                        // drawFromBuffer(vertexBuffer, indexBuffer, indexType, baseVertex, firstIndex, indexCount) -
                        // order confirmed via StagedVertexBuffer.ExecuteInfo's record field order (the prior
                        // (indexCount, 0, 0) ordering passed indexCount=0, silently drawing nothing at all).
                        // drawFromBuffer resolves its target via the RenderType's declared OutputTarget UNLESS
                        // RenderSystem.outputColorTextureOverride/outputDepthTextureOverride is non-null, in which
                        // case it redirects there instead - confirmed via PreparedRenderType#drawFromBuffer bytecode.
                        // Our pass runs via our own FrameGraphBuilder outside the normal world-render submission
                        // flow, where these overrides may still be set from elsewhere, silently redirecting our
                        // draw away from electricityTarget. Force them null for the duration of our own draw.
                        var savedColorOverride = RenderSystem.outputColorTextureOverride;
                        var savedDepthOverride = RenderSystem.outputDepthTextureOverride;
                        if(System.currentTimeMillis() - this.debugLastLogMillis10 > 1000)
                        {
                            this.debugLastLogMillis10 = System.currentTimeMillis();
                            Constants.LOG.info("[ElectricityDebug] outputColorTextureOverride was set={} before our draw", savedColorOverride != null);
                        }
                        RenderSystem.outputColorTextureOverride = null;
                        RenderSystem.outputDepthTextureOverride = null;
                        try
                        {
                            renderType.prepare().drawFromBuffer(vertexBuffer, indexBuffer, indexType, 0, 0, data.drawState().indexCount());
                        }
                        finally
                        {
                            RenderSystem.outputColorTextureOverride = savedColorOverride;
                            RenderSystem.outputDepthTextureOverride = savedDepthOverride;
                        }
                    }
                }
            }

            // Clear storage after drawing
            this.storage.clear();
        });
    }

    /**
     * Draws the electricity texture onto the screen. This is drawn after the world, but before the player hand and HUD.
     * This will result in the electricity nodes and links drawn in front of everything, effectively removing the depth.
     */
    public void blitToScreen()
    {
        // When Iris is enabled, we have to do a late call to draw
        /*if(this.isIrisShadersEnabled()) // TODO test against iris
        {
            PoseStack stack = new PoseStack();
            stack.mulPose(projMatrix);
            stack.translate(-camera.x(), -camera.y(), -camera.z());
            this.drawDeferredCalls(stack);
        }*/

        this.tryAndTakeDebugScreenshot();

        // TEMPORARY diagnostic: confirms whether blitToScreen even reaches the draw call.
        if(System.currentTimeMillis() - this.debugLastLogMillis7 > 1000)
        {
            this.debugLastLogMillis7 = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] blitToScreen() called, handle={}", this.handle != null);
        }

        // Only blit to the main texture if render pass handle was created
        if(this.handle != null)
        {
            GpuTextureView mainTexture = Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView();
            GpuTextureView electricityTexture = this.electricityTarget.getColorTextureView();
            if(System.currentTimeMillis() - this.debugLastLogMillis8 > 1000)
            {
                this.debugLastLogMillis8 = System.currentTimeMillis();
                Constants.LOG.info("[ElectricityDebug] blitToScreen() textures: mainTexture={}, electricityTexture={}", mainTexture != null, electricityTexture != null);
            }
            if(electricityTexture != null && mainTexture != null)
            {
                try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Blit", mainTexture, Optional.empty()))
                {
                    pass.setPipeline(ModRenderPipelines.ELECTRICITY_BLIT);
                    RenderSystem.bindDefaultUniforms(pass);
                    pass.bindTexture("InSampler", electricityTexture, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
                    // MC 26.2: draw(vertexCount, instanceCount, firstVertex, firstInstance) - Vulkan-style, confirmed via vanilla GuiRenderer bytecode.
                    pass.draw(3, 1, 0, 0);
                    if(System.currentTimeMillis() - this.debugLastLogMillis9 > 1000)
                    {
                        this.debugLastLogMillis9 = System.currentTimeMillis();
                        Constants.LOG.info("[ElectricityDebug] blitToScreen() draw issued successfully");
                    }
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
        PowerableAreaRenderState renderState = new PowerableAreaRenderState();
        WrenchHandler handler = WrenchHandler.get();
        handler.extractPowerableArea(renderState, camera);
        // TEMPORARY diagnostic: confirms whether renderPowerableArea is even being called, and why it
        // early-returns if it does (no shape = powerableArea.getPowerableAreaShape() returned null).
        if(System.currentTimeMillis() - this.debugLastLogMillis6 > 1000)
        {
            this.debugLastLogMillis6 = System.currentTimeMillis();
            Constants.LOG.info("[ElectricityDebug] renderPowerableArea() called, shape={}, alpha={}", renderState.shape != null, renderState.alpha);
        }
        if(renderState.shape == null || renderState.alpha <= 0)
            return;

        PoseStack stack = new PoseStack();
        stack.translate(-camera.x, -camera.y, -camera.z);

        // Draw the powerable zone border
        boolean shaders = ElectricityRenderer.get().isIrisShadersEnabled();
        RenderPipeline pipeline = shaders ? RenderPipelines.WORLD_BORDER : ModRenderPipelines.POWERABLE_AREA;
        try(ByteBufferBuilder quadBuilder = new ByteBufferBuilder(pipeline.getVertexFormatBinding(0).getVertexSize() * 4))
        {
            BufferBuilder vertexBuilder = new BufferBuilder(quadBuilder, pipeline.getPrimitiveTopology(), pipeline.getVertexFormatBinding(0));
            renderState.shape.toAabbs().forEach(box -> drawPowerableAreaBox(stack.last(), vertexBuilder, box));
            try(MeshData data = vertexBuilder.build())
            {
                if(data != null)
                {
                    RenderTarget mainTarget = Minecraft.getInstance().gameRenderer.mainRenderTarget();
                    RenderTarget weatherTarget = Minecraft.getInstance().levelRenderer.weatherTarget();
                    GpuTextureView mainColor = mainTarget.getColorTextureView();
                    GpuTextureView mainDepth = mainTarget.getDepthTextureView();
                    if(weatherTarget != null)
                    {
                        //mainColor = weatherTarget.getColorTextureView();
                        //mainDepth = weatherTarget.getDepthTextureView();
                    }

                    GpuBufferSlice slice = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrixCopy(), new Vector4f(1.0F, 1.0F, 1.0F, 0.6F * renderState.alpha), new Vector3f(), new Matrix4f());
                    RenderSystem.AutoStorageIndexBuffer autoIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getPrimitiveTopology());
                    IndexType indexType = autoIndexBuffer.type();
                    GpuBuffer indexBuffer = autoIndexBuffer.getBuffer(data.drawState().indexCount());

                    GpuBuffer vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Powerable Area vertex buffer", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_COPY_DST, data.vertexBuffer().remaining());
                    RenderSystem.getDevice().createCommandEncoder().writeToBuffer(vertexBuffer.slice(), data.vertexBuffer());

                    AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(renderState.invalid ? UNPOWERABLE_AREA : POWERABLE_AREA);

                    try(RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Powerable Area", mainColor, Optional.empty(), mainDepth, OptionalDouble.empty()))
                    {
                        pass.setPipeline(pipeline);
                        RenderSystem.bindDefaultUniforms(pass);
                        pass.setUniform("DynamicTransforms", slice);
                        pass.setIndexBuffer(indexBuffer, indexType);
                        pass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
                        pass.setVertexBuffer(0, vertexBuffer.slice());
                        // MC 26.2: drawIndexed(indexCount, instanceCount, firstIndex, baseVertex, firstInstance) - Vulkan-style, confirmed via vanilla GuiRenderer bytecode.
                        pass.drawIndexed(data.drawState().indexCount(), 1, 0, 0, 0);
                    }
                }
            }
        }
    }

    /**
     * For development purposes. Takes a screenshot of the electricity texture.
     */
    private void tryAndTakeDebugScreenshot()
    {
        // TEMPORARY: dev-env gate disabled so the user can dump electricityTarget's raw contents
        // directly to disk (Ctrl+Alt+Shift) while investigating the missing-overlay bug. Restore
        // `Services.PLATFORM.isDevelopmentEnvironment()` once resolved.
        if(true)
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

    private static class SubmitStorage
    {
        private final List<NodeSubmit> nodeSubmits = new ArrayList<>();
        private final List<ConnectionSubmit> connectionSubmits = new ArrayList<>();
        private final List<LinkingConnectionSubmit> linkingConnectionSubmits = new ArrayList<>();

        public void submitNode(PoseStack poseStack, NodeRenderState state)
        {
            this.nodeSubmits.add(new NodeSubmit(poseStack.last().copy(), (pose, consumer) -> {
                drawTexturedBox(pose, consumer, state.box, 0, 0, 0.25F, 0.25F);
                if(state.highlighted) {
                    drawInvertedColouredBox(pose, consumer, state.box.inflate(0.03125), state.highlightColour, 0.7F);
                }
            }));
        }

        public void submitConnection(PoseStack poseStack, ConnectionRenderState state)
        {
            this.connectionSubmits.add(new ConnectionSubmit(poseStack.last().copy(), state));
        }

        public void submitLinkingConnection(PoseStack poseStack, LinkingConnectionRenderState state)
        {
            this.linkingConnectionSubmits.add(new LinkingConnectionSubmit(poseStack.last().copy(), state));
        }

        private void clear()
        {
            this.nodeSubmits.clear();
            this.connectionSubmits.clear();
            this.linkingConnectionSubmits.clear();
        }

        private record NodeSubmit(PoseStack.Pose pose, BiConsumer<PoseStack.Pose, VertexConsumer> renderer) {}

        private record ConnectionSubmit(PoseStack.Pose pose, ConnectionRenderState connectionRenderState) {}

        private record LinkingConnectionSubmit(PoseStack.Pose pose, LinkingConnectionRenderState linkingConnectionRenderState) {}
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
