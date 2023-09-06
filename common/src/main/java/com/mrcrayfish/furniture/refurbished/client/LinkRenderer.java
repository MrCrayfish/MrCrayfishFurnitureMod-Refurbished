package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.electric.NodeHitResult;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class LinkRenderer
{
    private static final int DEFAULT_LINK_COLOUR = 0xFFFFFFFF;
    private static final int SUCCESS_LINK_COLOUR = 0xFFB5FF4C;

    private static LinkRenderer instance;

    public static LinkRenderer get()
    {
        if(instance == null)
        {
            instance = new LinkRenderer();
        }
        return instance;
    }

    @Nullable
    private BlockPos lastNodePos;
    private NodeHitResult result;

    private LinkRenderer() {}

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
    public boolean isLinkingNode(IElectricNode node)
    {
        return this.lastNodePos != null && this.lastNodePos.equals(node.getPosition());
    }

    /**
     * @return True if currently creating a link
     */
    public boolean isLinking()
    {
        return this.lastNodePos != null;
    }

    /**
     * Updates the node hit result before rendering
     *
     * @param partialTick the current partial tick
     */
    public void renderTick(float partialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null && mc.gameMode != null)
        {
            // Only perform raycast when holding wrench
            if(mc.player.getMainHandItem().is(ModItems.WRENCH.get()))
            {
                float range = mc.gameMode.getPickRange();
                this.result = WrenchItem.performRaycast(mc.level, mc.player, range, partialTick);
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
    public boolean isTargetNode(IElectricNode node)
    {
        return this.result != null && this.result.getNode() == node;
    }

    @Nullable
    public IElectricNode getTargetNode()
    {
        return this.result != null ? this.result.getNode() : null;
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
    public void drawLink(Player player, PoseStack poseStack, MultiBufferSource.BufferSource source, float partialTick)
    {
        if(this.lastNodePos == null)
            return;

        if(!player.isAlive() || !player.getMainHandItem().is(ModItems.WRENCH.get())) {
            this.lastNodePos = null;
            return;
        }

        Vec3 start = Vec3.atCenterOf(this.lastNodePos);
        Vec3 end = this.getLinkEnd(player, partialTick);
        Vec3 delta = end.subtract(start);
        double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
        double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
        poseStack.translate(start.x, start.y, start.z);
        poseStack.mulPose(Axis.YP.rotation((float) yaw));
        poseStack.mulPose(Axis.ZP.rotation((float) pitch));
        int color = this.getLinkColour(player.level());
        float red = FastColor.ARGB32.red(color) / 255F;
        float green = FastColor.ARGB32.green(color) / 255F;
        float blue = FastColor.ARGB32.blue(color) / 255F;
        DebugRenderer.renderFilledBox(poseStack, source, new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125), red, green, blue, 0.5F);
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
        IElectricNode node = this.getTargetNode();
        if(node != null && !this.isLinkingNode(node) && this.canLinkToNode(player.level(), node))
        {
            return this.result.getPos().getCenter();
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
        IElectricNode node = this.getTargetNode();
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
    public boolean canLinkToNode(Level level, IElectricNode target)
    {
        if(this.lastNodePos != null)
        {
            IElectricNode lastNode = level.getBlockEntity(this.lastNodePos) instanceof IElectricNode node ? node : null;
            if(lastNode != null && target != null && lastNode != target)
            {
                return !lastNode.isConnectedTo(target);
            }
        }
        return false;
    }
}
