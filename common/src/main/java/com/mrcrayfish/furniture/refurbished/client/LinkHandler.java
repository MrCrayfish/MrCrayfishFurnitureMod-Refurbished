package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ElectricBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.LinkHitResult;
import com.mrcrayfish.furniture.refurbished.electricity.LinkManager;
import com.mrcrayfish.furniture.refurbished.electricity.NodeHitResult;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDeleteLink;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Intersectiond;
import org.joml.Matrix4f;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class LinkHandler
{
    private static final ResourceLocation POWERABLE_AREA = new ResourceLocation(Constants.MOD_ID, "textures/misc/powerable_area.png");
    private static final ResourceLocation UNPOWERABLE_AREA = new ResourceLocation(Constants.MOD_ID, "textures/misc/unpowerable_area.png");
    private static final int DEFAULT_LINK_COLOUR = 0xFFFFFFFF;
    private static final int SUCCESS_LINK_COLOUR = 0xFFB5FF4C;
    private static final int ERROR_LINK_COLOUR = 0xFFC33636;
    private static final double NEAR_DISTANCE = 10.0;

    private static LinkHandler instance;

    public static LinkHandler get()
    {
        if(instance == null)
        {
            instance = new LinkHandler();
        }
        return instance;
    }

    @Nullable
    private BlockPos lastNodePos;
    private HitResult result;
    private double linkLength;
    private boolean linkInsideArea;
    private final Set<BlockPos> sourcePositions = new HashSet<>();
    private final Set<BlockPos> lastSourcePositions = new HashSet<>();
    private VoxelShape cachedPowerableAreaShape;

    private LinkHandler() {}

    /**
     * Sets the last node position interacted by player. Used for rendering the link
     *
     * @param pos the block position of the electric node
     */
    public void setLinkingNode(BlockPos pos)
    {
        this.lastNodePos = pos;
    }

    /**
     *
     * @param node
     * @return
     */
    public boolean isLinkingNode(IElectricityNode node)
    {
        return this.lastNodePos != null && this.lastNodePos.equals(node.getNodePosition());
    }

    /**
     * @return True if currently creating a link
     */
    public boolean isLinking()
    {
        return this.lastNodePos != null;
    }

    public boolean isLinkInsidePowerableArea()
    {
        return this.linkInsideArea;
    }

    @Nullable
    public IElectricityNode getLinkingNode(Level level)
    {
        if(this.lastNodePos == null)
            return null;
        if(level.getBlockEntity(this.lastNodePos) instanceof IElectricityNode node)
            return node;
        return null;
    }

    /**
     * @return The current length of the link being created
     */
    public double getLinkLength()
    {
        return this.lastNodePos != null ? this.linkLength : 0;
    }

    /**
     * Updates the node hit result before rendering
     *
     * @param partialTick the current partial tick
     */
    public void beforeRender(float partialTick)
    {
        this.updateHitResult(partialTick);
        this.updatePowerSources();
        this.updateLinkState(partialTick);
    }

    /**
     * Performs a raycast for nodes and links, and stores that result if any hit.
     *
     * @param partialTick the partial tick of the current frame
     */
    private void updateHitResult(float partialTick)
    {
        this.result = null;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null && mc.gameMode != null)
        {
            // Only perform raycast when holding wrench
            if(mc.player.getMainHandItem().is(ModItems.WRENCH.get()))
            {
                float range = mc.gameMode.getPickRange();
                this.result = WrenchItem.performNodeRaycast(mc.level, mc.player, range, partialTick);

                // If missed, try to raycast for links
                if(this.result.getType() == HitResult.Type.MISS)
                {
                    this.result = this.performLinkRaycast(mc.player, partialTick, range);
                }
            }
        }
    }

    /**
     * Finds and updates the source nodes that are connected to either the node we are linking or
     * node we are currently looking at.
     */
    private void updatePowerSources()
    {
        this.sourcePositions.clear();

        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null)
            return;

        // Find source node block positions from the linking node
        IElectricityNode linking = this.getLinkingNode(mc.level);
        this.addSourceNodePositions(this.sourcePositions, this.getLinkingNode(mc.level));

        // Find all sources starting from the target node
        IElectricityNode target = this.getTargetNode();
        if(linking != null)
        {
            this.addSourceNodePositions(this.sourcePositions, this.getTargetNode());
        }

        // Finally try to find sources from target link if it only crosses the powerable zone
        if(linking == null && target == null)
        {
            Connection connection = this.getTargetConnection();
            if(connection != null && connection.isCrossingPowerableZone(mc.level))
            {
                IElectricityNode node = connection.getPowerableNode(mc.level);
                this.addSourceNodePositions(this.sourcePositions, node);
            }
        }
    }

    /**
     * Updates the state of the link currently being created. Performs a check to test if the link
     * is crossing the border of the powerable zone.
     *
     * @param partialTick the partial tick of the current frame
     */
    private void updateLinkState(float partialTick)
    {
        this.linkInsideArea = false;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null)
            return;

        if(this.sourcePositions.isEmpty())
        {
            this.linkInsideArea = true;
        }
        else if(this.lastNodePos != null)
        {
            this.linkInsideArea = this.sourcePositions.stream().anyMatch(pos -> {
                AABB box = new AABB(pos).inflate(Config.SERVER.electricity.powerableAreaRadius.get());
                return box.contains(this.lastNodePos.getCenter()) && box.contains(this.getLinkEnd(mc.player, partialTick));
            });
        }
        else if(this.result instanceof LinkHitResult hitResult)
        {
            Connection connection = hitResult.getConnection();
            Vec3 start = connection.getPosA().getCenter();
            Vec3 end = connection.getPosB().getCenter();
            this.linkInsideArea = this.sourcePositions.stream().anyMatch(pos -> {
                AABB box = new AABB(pos).inflate(Config.SERVER.electricity.powerableAreaRadius.get());
                return box.contains(start) && box.contains(end);
            });
        }
    }

    /**
     * Searches the electricity network starting from the provided start node and finds all the
     * source nodes that can provide power to the given start node. The block position of the source
     * node is then added to the given positions set. A null start node can be provided, it will just
     * simply not run anything.
     *
     * @param positions the set of currently found source node block positions
     * @param start     the node to start the search or null
     */
    private void addSourceNodePositions(Set<BlockPos> positions, @Nullable IElectricityNode start)
    {
        if(start == null)
            return;

        // If source node, add to positions and return. Sources don't need to search network
        if(start.isSourceNode())
        {
            positions.add(start.getNodePosition());
            return;
        }

        // Search network for all possible source nodes that can provide power to the start node
        int maxRadius = Config.SERVER.electricity.powerableAreaRadius.get();
        int searchLimit = Config.SERVER.electricity.maximumNodesInNetwork.get();
        IElectricityNode.searchNodes(start, maxRadius, searchLimit, true, node -> !node.isSourceNode(), IElectricityNode::isSourceNode).forEach(node -> {
            positions.add(node.getNodePosition());
        });
    }

    /**
     * Tests if the given electric node is currently being looked at by the player
     *
     * @param node the node to test
     * @return True if looking at the node
     */
    public boolean isTargetNode(IElectricityNode node)
    {
        return this.result instanceof NodeHitResult nodeResult && nodeResult.getNode() == node;
    }

    /**
     * @return The electricity node the local player is currently looking at or null
     */
    @Nullable
    public IElectricityNode getTargetNode()
    {
        return this.result instanceof NodeHitResult nodeResult ? nodeResult.getNode() : null;
    }

    /**
     * @return The connection link the local player is currently looking at or null
     */
    @Nullable
    public Connection getTargetConnection()
    {
        return this.result instanceof LinkHitResult linkResult ? linkResult.getConnection() : null;
    }

    /**
     * Draws the current link. A link is created when a player is using the wrench and has only
     * partially created a connection, in other words, has selected the first node only. A line will
     * be drawn from the first selected node to a target position. After the connection is made, the
     * link will not be drawn.
     *
     * @param player      the player rendering the link
     * @param poseStack   the current pose stack
     * @param source      a buffer source instance
     * @param partialTick the current partial tick
     */
    public void render(Player player, PoseStack poseStack, MultiBufferSource.BufferSource source, float partialTick)
    {
        if(!player.isAlive() || !player.getMainHandItem().is(ModItems.WRENCH.get()))
        {
            this.lastNodePos = null;
        }

        this.renderPowerableArea(poseStack, player, partialTick);

        if(this.lastNodePos != null)
        {
            this.renderPartialLink(player, this.lastNodePos, poseStack, source, partialTick);
        }
    }

    /**
     *
     * @param player
     * @param pos
     * @param poseStack
     * @param source
     * @param partialTick
     */
    private void renderPartialLink(Player player, BlockPos pos, PoseStack poseStack, MultiBufferSource.BufferSource source, float partialTick)
    {
        Vec3 start = Vec3.atCenterOf(pos);
        Vec3 end = this.getLinkEnd(player, partialTick);
        Vec3 delta = end.subtract(start);
        this.linkLength = delta.length();
        double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
        double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
        poseStack.pushPose();
        poseStack.translate(start.x, start.y, start.z);
        poseStack.mulPose(Axis.YP.rotation((float) yaw));
        poseStack.mulPose(Axis.ZP.rotation((float) pitch));
        int color = this.getLinkColour(player.level());
        drawColouredBox(poseStack, source, new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125), color, 0.75F);
        poseStack.popPose();
    }

    /**
     * Gets the end position of the link. If the player is looking at an electric node, the center
     * of that node will be returned, otherwise the end position is the player's current look vector.
     *
     * @param player      the player rendering the link
     * @param partialTick the current partial tick
     * @return a vec3 of the link end
     */
    private Vec3 getLinkEnd(Player player, float partialTick)
    {
        IElectricityNode node = this.getTargetNode();
        if(node != null && !this.isLinkingNode(node) && this.canLinkToNode(player.level(), node))
        {
            return node.getNodePosition().getCenter();
        }
        return player.getViewVector(partialTick).add(player.getEyePosition(partialTick));
    }

    /**
     * Gets the colour for the current link
     *
     * @param level the level of the player making the link
     * @return an integer colour
     */
    public int getLinkColour(Level level)
    {
        IElectricityNode linking = this.getLinkingNode(level);
        if(linking == null)
            return DEFAULT_LINK_COLOUR;

        if(this.linkLength > LinkManager.MAX_LINK_LENGTH)
            return ERROR_LINK_COLOUR;

        IElectricityNode target = this.getTargetNode();
        if(target != null && !this.isLinkingNode(target))
        {
            if(this.canLinkToNode(level, target))
            {
                return SUCCESS_LINK_COLOUR;
            }
            return ERROR_LINK_COLOUR;
        }

        if(!this.linkInsideArea)
            return ERROR_LINK_COLOUR;

        return DEFAULT_LINK_COLOUR;
    }

    /**
     * Determines if a link can be made to the given electric node. This method will always return
     * false if there isn't currently no last node position stored.
     *
     * @param level  the level of the node
     * @param target the node to test
     * @return True if a link can be made to the target node
     */
    public boolean canLinkToNode(Level level, IElectricityNode target)
    {
        if(this.lastNodePos != null)
        {
            IElectricityNode lastNode = this.getLinkingNode(level);
            if(lastNode != null && target != null && lastNode != target)
            {
                if(target.isSourceNode() && lastNode.isSourceNode())
                    return false;
                if(target.isNodeConnectionLimitReached())
                    return false;
                return !lastNode.isConnectedToNode(target);
            }
        }
        return false;
    }

    /**
     * Attempts to find the link (connection) that is nearest to the player's look ray and
     * within the player's reach distance.
     *
     * @param player      the player performing the raycast
     * @param partialTick the current partial tick
     * @param range       the reach of the player
     * @return a hit result with a link or miss if no link was found
     */
    private HitResult performLinkRaycast(Player player, float partialTick, float range)
    {
        double closestDistance = Double.POSITIVE_INFINITY;
        Connection closestConnection = null;
        Vec3 hit = Vec3.ZERO;
        Set<Connection> connections = ElectricBlockEntityRenderer.getDrawnConnections();
        for(Connection connection : connections)
        {
            Vec3 rayStart = player.getEyePosition(partialTick);
            Vec3 rayEnd = rayStart.add(player.getViewVector(partialTick).normalize().scale(range));
            Vec3 linkStart = connection.getPosA().getCenter();
            Vec3 linkEnd = connection.getPosB().getCenter();
            Vector3d result =  new Vector3d();
            double squareDistance = Intersectiond.findClosestPointsLineSegments(rayStart.x, rayStart.y, rayStart.z, rayEnd.x, rayEnd.y, rayEnd.z, linkStart.x, linkStart.y, linkStart.z, linkEnd.x, linkEnd.y, linkEnd.z, new Vector3d(), result);
            double distance = Math.sqrt(squareDistance);
            if(distance < 0.125 && distance < closestDistance)
            {
                closestDistance = distance;
                closestConnection = connection;
                hit = new Vec3(result.x, result.y, result.z);
            }
        }
        return new LinkHitResult(hit, closestConnection);
    }

    /**
     * Called when a player left clicks while holding a wrench. Since Item doesn't have a method to
     * handle this, this event is captured with modloader specific event/injections.
     *
     * @return True if an action was performed
     */
    public boolean onWrenchLeftClick(Level level)
    {
        if(!this.isLinking() && this.result instanceof LinkHitResult linkResult)
        {
            Connection connection = linkResult.getConnection();
            if(connection != null)
            {
                Vec3 hit = linkResult.getLocation();
                level.playLocalSound(hit.x, hit.y, hit.z, ModSounds.ITEM_WRENCH_REMOVE_LINK.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
                Network.getPlay().sendToServer(new MessageDeleteLink(connection.getPosA(), connection.getPosB()));
                return true;
            }
        }
        return false;
    }

    /**
     * @return The powerable area shape (or cached version), otherwise null if no powerable area.
     */
    @Nullable
    private VoxelShape getPowerableAreaShape()
    {
        if(this.sourcePositions.isEmpty())
            return null;

        // Return cached shape if same as last positions
        if(this.lastSourcePositions.equals(this.sourcePositions))
            return this.cachedPowerableAreaShape;

        // Creates the powerable area shape
        this.sourcePositions.stream().map(pos -> {
            return new AABB(pos).inflate(Config.SERVER.electricity.powerableAreaRadius.get());
        }).map(aabb -> {
            VoxelShape shape1 = Shapes.create(aabb);
            VoxelShape shape2 = Shapes.create(aabb.inflate(0.001));
            return Pair.of(shape1, shape2);
        }).reduce((p1, p2) -> {
            VoxelShape shape1 = Shapes.joinUnoptimized(p1.first(), p2.first(), BooleanOp.OR);
            VoxelShape shape2 = Shapes.joinUnoptimized(p1.second(), p2.second(), BooleanOp.OR);
            return Pair.of(shape1, shape2);
        }).map(pair -> {
            return Shapes.joinUnoptimized(pair.first(), pair.second(), BooleanOp.ONLY_SECOND);
        }).ifPresent(shape -> {
            this.cachedPowerableAreaShape = shape;
        });

        // Finally remember the positions and return the shape
        this.lastSourcePositions.clear();
        this.lastSourcePositions.addAll(this.sourcePositions);
        return this.cachedPowerableAreaShape;
    }

    /**
     * Draws the current powerable area into the level
     */
    private void renderPowerableArea(PoseStack poseStack, Player player, float partialTick)
    {
        VoxelShape areaShape = this.getPowerableAreaShape();
        if(areaShape == null)
            return;

        // Calculate the alpha colour of the powerable zone border
        Vec3 eyePos = player.getEyePosition(partialTick);
        double nearDistanceSqr = NEAR_DISTANCE * NEAR_DISTANCE;
        float areaAlpha = !this.linkInsideArea ? 1.0F : areaShape.closestPointTo(eyePos)
            .map(vec -> vec.distanceToSqr(eyePos))
            .map(val -> 1.0F - (float) Mth.clamp(val / nearDistanceSqr, 0, 1))
            .orElse(0F);

        // Don't render powerable area if alpha is zero
        if(areaAlpha <= 0)
            return;

        // Apply an easing function to make it appear quicker at the start
        areaAlpha = 1.0F - (float) Math.pow(1.0F - areaAlpha, 5);

        // Draw the powerable zone border
        Tesselator tesselator = Tesselator.getInstance();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderTexture(0, this.linkInsideArea ? POWERABLE_AREA : UNPOWERABLE_AREA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.6F * areaAlpha);
        RenderSystem.depthMask(Minecraft.useShaderTransparency());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.polygonOffset(-3.0F, -3.0F);
        RenderSystem.enablePolygonOffset();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        areaShape.toAabbs().forEach(box -> this.drawTexturedBox(poseStack, builder, box));
        BufferUploader.drawWithShader(builder.end());
        RenderSystem.polygonOffset(0.0F, 0.0F);
        RenderSystem.disablePolygonOffset();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Draws a texture box from an AABB. The position of the box is determined by the AABB.
     *
     * @param poseStack the current pose stack
     * @param consumer  the vertex consumer to accept the data. Must be VERTEX and UV only
     * @param box       the AABB box to draw
     */
    private void drawTexturedBox(PoseStack poseStack, VertexConsumer consumer, AABB box)
    {
        Matrix4f matrix = poseStack.last().pose();
        float offset = Util.getMillis() * 0.001F;
        float width = (float) (box.maxX - box.minX);
        float height = (float) (box.maxY - box.minY);
        if(width > 0.01)
        {
            // North
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(0, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(width, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(width, offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(0, offset).endVertex();
            // South
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(0, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(width, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(width, offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(0, offset).endVertex();
        }
        width = (float) (box.maxZ - box.minZ);
        if(width > 0.01)
        {
            // West
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(0, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(width, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(width, offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(0, offset).endVertex();
            // East
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(0, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(width, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(width, offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(0, offset).endVertex();
        }
        width = (float) (box.maxX - box.minX);
        height = (float) (box.maxZ - box.minZ);
        if(width > 0.01)
        {
            // Up
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).uv(0, width + offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).uv(height, width + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).uv(height, offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).uv(0, offset).endVertex();
            // Down
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).uv(0, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).uv(width, height + offset).endVertex();
            consumer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).uv(width, offset).endVertex();
            consumer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).uv(0, offset).endVertex();
        }
    }

    /**
     * Draws a coloured box with the given colour. This method is specific to drawing links, as it
     * will use the electricity connection render type. This will make the box be seen through walls.
     *
     * @param poseStack the current posestack
     * @param source    a multi buffer source
     * @param box       the aabb box to draw
     * @param colour    the colour of the box in decimal
     * @param alpha     the alpha value from 0 to 1
     */
    public static void drawColouredBox(PoseStack poseStack, MultiBufferSource source, AABB box, int colour, float alpha)
    {
        float red = FastColor.ARGB32.red(colour) / 255F;
        float green = FastColor.ARGB32.green(colour) / 255F;
        float blue = FastColor.ARGB32.blue(colour) / 255F;
        VertexConsumer consumer = source.getBuffer(ClientServices.PLATFORM.getElectricityConnectionRenderType());
        LevelRenderer.addChainedFilledBoxVertices(poseStack, consumer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }
}
