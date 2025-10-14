package com.mrcrayfish.furniture.refurbished.client.electricity;

import com.mrcrayfish.framework.api.config.event.FrameworkConfigEvents;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.electricity.state.ConnectionRenderState;
import com.mrcrayfish.furniture.refurbished.client.electricity.state.ElectricityRenderState;
import com.mrcrayfish.furniture.refurbished.client.electricity.state.LinkingConnectionRenderState;
import com.mrcrayfish.furniture.refurbished.client.electricity.state.PowerableAreaRenderState;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.*;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageDeleteLink;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Vector3d;

/**
 * Author: MrCrayfish
 */
public class WrenchHandler
{
    private static final int DEFAULT_LINK_COLOUR = 0xFFFFFFFF;
    private static final int SUCCESS_LINK_COLOUR = 0xFFB5FF4C;
    private static final int ERROR_LINK_COLOUR = 0xFFC33636;
    private static final double NEAR_DISTANCE = 10.0;

    private static WrenchHandler instance;

    public static WrenchHandler get()
    {
        if(instance == null)
        {
            instance = new WrenchHandler();
        }
        return instance;
    }

    private final PowerableArea powerableArea = new PowerableArea();
    private @Nullable BlockPos lastNodePos;
    private HitResult result;
    private double linkLength;
    private boolean linkInsideArea;

    private WrenchHandler()
    {
        // On changes to server the config, clear zone shape cache
        FrameworkConfigEvents.RELOAD.register(object -> {
            if(object == Config.SERVER) {
                this.powerableArea.invalidate();
            }
        });
    }

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
     *
     * @return
     */
    public boolean isLinkOutsidePowerableArea()
    {
        return !this.linkInsideArea;
    }

    /**
     *
     * @param level
     * @return
     */
    @Nullable
    public IElectricityNode getLinkingNode(Level level)
    {
        if(this.lastNodePos == null)
            return null;

        LevelChunk chunk = level.getChunkAt(this.lastNodePos);
        //noinspection ConstantValue
        if(chunk == null)
            return null;

        if(chunk.getBlockEntities().get(this.lastNodePos) instanceof IElectricityNode node && node.isNodeValid())
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
        if(!isHoldingWrench())
        {
            this.lastNodePos = null;
        }
        this.updateHitResult(partialTick);
        this.updateLinkState(partialTick);
    }

    /**
     * Performs a raycast for nodes and links, and stores that result if any hit.
     *
     * @param partialTick the partial tick of the current frame
     */
    private void updateHitResult(float partialTick)
    {
        HitResult last = this.result;
        this.result = null;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.level != null && mc.gameMode != null)
        {
            // Only perform ray cast when holding wrench
            if(mc.player.getMainHandItem().is(ModItems.WRENCH.get()))
            {
                double range = mc.player.blockInteractionRange();
                HitResult newResult = WrenchItem.performNodeRaycast(mc.level, mc.player, range, partialTick);
                if(newResult.getType() == HitResult.Type.MISS)
                {
                    newResult = this.performLinkRaycast(mc.player, partialTick, range);
                }
                if(newResult.getType() != HitResult.Type.MISS)
                {
                    this.playHoverSound(last, newResult, mc.player, mc.level);
                    this.result = newResult;
                }
            }
        }
    }

    /**
     *
     * @param oldResult
     * @param newResult
     * @param player
     * @param level
     */
    private void playHoverSound(@Nullable HitResult oldResult, @Nullable HitResult newResult, Player player, Level level)
    {
        if(this.lastNodePos != null)
            return;

        if((oldResult == null || !oldResult.equals(newResult)) && newResult instanceof ConnectionHitResult)
        {
            Vec3 pos = newResult.getLocation();
            float pitch = 1.0F + 0.05F * level.random.nextFloat();
            level.playSound(player, pos.x, pos.y, pos.z, ModSounds.ITEM_WRENCH_HOVER_LINK.get(), SoundSource.BLOCKS, 1.0F, pitch);
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
        if(mc.player == null || mc.level == null)
            return;

        this.powerableArea.updatePowerSources(mc.level, this.createWrenchContext(mc.level));

        if(this.lastNodePos != null)
        {
            Vec3 start = Vec3.atCenterOf(this.lastNodePos);
            Vec3 end = this.getLinkEnd(mc.player, partialTick);
            this.linkLength = end.subtract(start).length();
        }

        if(!this.powerableArea.exists())
        {
            this.linkInsideArea = true;
        }
        else if(this.lastNodePos != null)
        {
            Vec3 start = this.lastNodePos.getCenter();
            Vec3 end = this.getLinkEnd(mc.player, partialTick);
            this.linkInsideArea = this.powerableArea.containsLine(mc.level, start, end);
        }
        else if(this.result instanceof ConnectionHitResult hitResult)
        {
            Connection connection = hitResult.getConnection();
            if(connection != null)
            {
                Vec3 start = connection.getPosA().getCenter();
                Vec3 end = connection.getPosB().getCenter();
                this.linkInsideArea = this.powerableArea.containsLine(mc.level, start, end);
            }
        }
    }

    private WrenchContext createWrenchContext(Level level)
    {
        IElectricityNode linkingNode = this.getLinkingNode(level);
        IElectricityNode targetNode = this.getTargetNode();
        Connection targetConnection = this.getTargetConnection();
        return new WrenchContext(linkingNode, targetNode, targetConnection);
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
        return this.result instanceof ConnectionHitResult linkResult ? linkResult.getConnection() : null;
    }

    /**
     *
     * @param renderState
     */
    public void extractLinkingConnection(ElectricityRenderState renderState)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.player.getMainHandItem().is(ModItems.WRENCH.get()) && this.lastNodePos != null)
        {
            LinkingConnectionRenderState state = new LinkingConnectionRenderState();
            state.start = Vec3.atCenterOf(this.lastNodePos);
            state.end = this.getLinkEnd(mc.player, mc.getDeltaTracker().getGameTimeDeltaPartialTick(false));
            state.colour = this.getLinkColour(mc.player.level());
            renderState.link = state;
        }
    }

    /**
     *
     * @param renderState
     */
    public void extractPowerableArea(PowerableAreaRenderState renderState, Vec3 camera)
    {
        VoxelShape areaShape = this.powerableArea.getPowerableAreaShape();
        if(areaShape == null)
            return;

        renderState.shape = areaShape;
        renderState.alpha = 1.0F;
        renderState.invalid = !this.linkInsideArea;

        if(this.linkInsideArea)
        {
            double nearDistanceSqr = NEAR_DISTANCE * NEAR_DISTANCE;
            renderState.alpha = renderState.shape.closestPointTo(camera)
                .map(vec -> vec.distanceToSqr(camera))
                .map(val -> 1.0F - (float) Mth.clamp(val / nearDistanceSqr, 0, 1))
                .orElse(0F);
            renderState.alpha = 1.0F - (float) Math.pow(1.0F - renderState.alpha, 5);
        }
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
        return player.getViewVector(partialTick).normalize().scale(1.5).add(player.getEyePosition(partialTick));
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
    private HitResult performLinkRaycast(Player player, float partialTick, double range)
    {
        double closestDistance = Double.POSITIVE_INFINITY;
        ConnectionRenderState closestConnection = null;
        Vec3 hit = Vec3.ZERO;

        // Hacky but we can just use the current render states
        ElectricityRenderState renderState = ElectricityRenderer.get().getRenderState();
        for(ConnectionRenderState connectionRenderState : renderState.connections)
        {
            Vec3 rayStart = player.getEyePosition(partialTick);
            Vec3 rayEnd = rayStart.add(player.getViewVector(partialTick).normalize().scale(range));
            Vec3 linkStart = connectionRenderState.a().getCenter();
            Vec3 linkEnd = connectionRenderState.b().getCenter();
            Vector3d result =  new Vector3d();
            double squareDistance = Intersectiond.findClosestPointsLineSegments(rayStart.x, rayStart.y, rayStart.z, rayEnd.x, rayEnd.y, rayEnd.z, linkStart.x, linkStart.y, linkStart.z, linkEnd.x, linkEnd.y, linkEnd.z, new Vector3d(), result);
            double distance = Math.sqrt(squareDistance);
            if(distance < 0.1 && distance < closestDistance)
            {
                closestDistance = distance;
                closestConnection = connectionRenderState;
                hit = new Vec3(result.x, result.y, result.z);
            }
        }
        if(closestConnection != null)
        {
            return new ConnectionHitResult(hit, Connection.of(closestConnection.a(), closestConnection.b()));
        }
        return new ConnectionHitResult(hit, null);
    }

    /**
     * Called when a player left clicks while holding a wrench. Since Item doesn't have a method to
     * handle this, this event is captured with modloader specific event/injections.
     *
     * @return True if an action was performed
     */
    public boolean onWrenchLeftClick(Level level)
    {
        if(!this.isLinking() && this.result instanceof ConnectionHitResult linkResult)
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
     * @return True if the player is currently holding the Wrench item. Client only
     */
    public static boolean isHoldingWrench()
    {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.isAlive() && mc.player.getMainHandItem().is(ModItems.WRENCH.get());
    }
}
