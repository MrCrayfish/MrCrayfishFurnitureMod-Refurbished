package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ElectricBlockEntityRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.LinkHitResult;
import com.mrcrayfish.furniture.refurbished.electricity.NodeHitResult;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDeleteLink;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Intersectiond;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class LinkHandler
{
    private static final int DEFAULT_LINK_COLOUR = 0xFFFFFFFF;
    private static final int SUCCESS_LINK_COLOUR = 0xFFB5FF4C;
    private static final int ERROR_LINK_COLOUR = 0xFFC33636;

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
    private double distance;

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

    /**
     * @return
     */
    public double getLinkDistance()
    {
        return this.distance;
    }

    /**
     * Updates the node hit result before rendering
     *
     * @param partialTick the current partial tick
     */
    public void beforeRender(float partialTick)
    {
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
                    this.result = performLinkRaycast(mc.player, partialTick, range);
                }
                return;
            }
        }
        this.result = null;
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
    public Connection getTargetLink()
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
        this.distance = 0;

        if(this.lastNodePos == null)
            return;

        if(!player.isAlive() || !player.getMainHandItem().is(ModItems.WRENCH.get())) {
            this.lastNodePos = null;
            return;
        }

        Vec3 start = Vec3.atCenterOf(this.lastNodePos);
        Vec3 end = this.getLinkEnd(player, partialTick);
        Vec3 delta = end.subtract(start);
        this.distance = delta.length();
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
        // TODO show text on screen if too long
        if(this.distance > Config.SERVER.electricity.maximumLinkDistance.get())
        {
            return ERROR_LINK_COLOUR;
        }

        IElectricityNode node = this.getTargetNode();
        if(node != null && !this.isLinkingNode(node) && this.canLinkToNode(level, node))
        {
            return SUCCESS_LINK_COLOUR;
        }
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
            IElectricityNode lastNode = level.getBlockEntity(this.lastNodePos) instanceof IElectricityNode node ? node : null;
            if(lastNode != null && target != null && lastNode != target)
            {
                double distance = this.lastNodePos.getCenter().distanceTo(target.getNodePosition().getCenter());
                if(distance <= Config.SERVER.electricity.maximumLinkDistance.get())
                {
                    return !target.isNodeConnectionLimitReached() && !lastNode.isConnectedToNode(target);
                }
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
