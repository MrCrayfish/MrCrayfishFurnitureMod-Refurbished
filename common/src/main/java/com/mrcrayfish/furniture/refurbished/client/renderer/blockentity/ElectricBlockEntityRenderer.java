package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.ExtraModels;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ElectricBlockEntityRenderer<T extends BlockEntity & IElectricityNode> implements BlockEntityRenderer<T>
{
    private static final Set<Connection> DRAWN_CONNECTIONS = new HashSet<>();
    private static final int DEFAULT_COLOUR = 0xFFFFFFFF;
    private static final int ERROR_COLOUR = 0xFFC33636;
    private static final int CONNECTION_COLOUR = 0xFFFFC54C;
    private static final float POWER_NODE_SCALE = 1.5F;

    public ElectricBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(T node, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        drawNodeAndConnections(node, poseStack, source, overlay);
    }

    public static void drawNodeAndConnections(IElectricityNode node, PoseStack poseStack, MultiBufferSource source, int overlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WRENCH.get()))
            return;

        // Draw node model
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        float scale = node.isSource() ? POWER_NODE_SCALE : 1.0F;
        poseStack.scale(scale, scale, scale);
        VertexConsumer consumer = source.getBuffer(ClientServices.PLATFORM.getElectrictyNodeRenderType());
        BakedModel model = getNodeModel(node);
        ClientServices.PLATFORM.drawBakedModel(model, poseStack, consumer, 15728880, overlay);
        poseStack.popPose();

        // Draw highlight colour
        LinkHandler handler = LinkHandler.get();
        boolean isLookingAt = handler.isTargetNode(node);
        if((isLookingAt && !handler.isLinking() && !node.isConnectionLimit()) || handler.isLinkingNode(node) || handler.canLinkToNode(node.getLevel(), node) && handler.isTargetNode(node))
        {
            AABB box = node.getInteractBox();
            int color = handler.getLinkColour(node.getLevel());
            LinkHandler.drawColouredBox(poseStack, source, box.inflate(0.03125), color, 0.75F);
        }

        // Draw connections
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        for(Connection connection : node.updateAndGetConnections())
        {
            if(!DRAWN_CONNECTIONS.contains(connection))
            {
                poseStack.pushPose();
                Vec3 delta = Vec3.atLowerCornerOf(connection.getPosB().subtract(connection.getPosA()));
                double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
                double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
                poseStack.mulPose(Axis.YP.rotation((float) yaw));
                poseStack.mulPose(Axis.ZP.rotation((float) pitch));
                boolean selected = !handler.isLinking() && connection.equals(handler.getTargetLink());
                int color = selected ? ERROR_COLOUR : connection.isPowered(node.getLevel()) ? CONNECTION_COLOUR : DEFAULT_COLOUR;
                float offset = (float) (Math.sin(Util.getMillis() / 500.0) + 1.0F) / 2.0F * 0.2F;
                AABB connectionBox = new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125);
                LinkHandler.drawColouredBox(poseStack, source, connectionBox, color, 0.6F + offset);
                LinkHandler.drawColouredBox(poseStack, source, connectionBox.inflate(0.03125), color, 0.4F + offset);
                poseStack.popPose();
                DRAWN_CONNECTIONS.add(connection);
            }
        }
        poseStack.popPose();
    }

    private static BakedModel getNodeModel(IElectricityNode node)
    {
        if(node.isConnectionLimit())
        {
            return ExtraModels.ELECTRIC_NODE_ERROR.getModel();
        }

        LinkHandler handler = LinkHandler.get();
        if(handler.isLinking() && !handler.isLinkingNode(node))
        {
            if(handler.canLinkToNode(node.getLevel(), node))
            {
                return ExtraModels.ELECTRIC_NODE_SUCCESS.getModel();
            }
            return ExtraModels.ELECTRIC_NODE_ERROR.getModel();
        }

        if(node.isSource())
        {
            return ExtraModels.ELECTRIC_NODE_POWER.getModel();
        }

        return ExtraModels.ELECTRIC_NODE_NEUTRAL.getModel();
    }

    @Override
    public boolean shouldRenderOffScreen(T node)
    {
        return true;
    }

    @Override
    public int getViewDistance()
    {
        return Config.CLIENT.electricityViewDistance.get();
    }

    public static void clearDrawn()
    {
        DRAWN_CONNECTIONS.clear();
    }

    public static Set<Connection> getDrawnConnections()
    {
        return DRAWN_CONNECTIONS;
    }
}
