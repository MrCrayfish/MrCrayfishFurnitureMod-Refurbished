package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.ElectricBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ExtraModels;
import com.mrcrayfish.furniture.refurbished.client.LinkHandler;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ElectricBlockEntityRenderer implements BlockEntityRenderer<ElectricBlockEntity>
{
    private static final Set<Connection> DRAWN_CONNECTIONS = new HashSet<>();
    private static final int DEFAULT_COLOUR = 0xFFFFFFFF;
    private static final int HIGHLIGHT_COLOUR = 0xFFFFB64C;
    private static final int SUCCESS_COLOUR = 0xFFAAFFAA;
    private static final int ERROR_COLOUR = 0xFFC33636;
    private static final int CONNECTION_COLOUR = 0xFFFFC54C;

    private final ItemRenderer renderer;

    public ElectricBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(ElectricBlockEntity electric, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WRENCH.get()))
            return;

        // Draw node model
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        VertexConsumer consumer = source.getBuffer(RenderType.translucent());
        BakedModel model = this.getNodeModel(electric);
        ClientServices.PLATFORM.drawBakedModel(model, poseStack, consumer, 15728880, overlay);
        poseStack.popPose();

        // Draw highlight colour
        LinkHandler handler = LinkHandler.get();
        boolean isLookingAt = handler.isTargetNode(electric);
        if((isLookingAt && !handler.isLinking()) || handler.isLinkingNode(electric) || handler.canLinkToNode(electric.getLevel(), electric) && handler.isTargetNode(electric))
        {
            AABB box = electric.getInteractBox();
            int color = handler.getLinkColour(electric.getLevel());
            float red = FastColor.ARGB32.red(color) / 255F;
            float green = FastColor.ARGB32.green(color) / 255F;
            float blue = FastColor.ARGB32.blue(color) / 255F;
            DebugRenderer.renderFilledBox(poseStack, source, box.inflate(0.03125), red, green, blue, 0.35F);
        }

        // Draw connections
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        for(Connection connection : electric.getConnections())
        {
            if(!DRAWN_CONNECTIONS.contains(connection))
            {
                poseStack.pushPose();
                Vec3 delta = Vec3.atLowerCornerOf(connection.getPosB().subtract(connection.getPosA()));
                double yaw = Math.atan2(-delta.z, delta.x) + Math.PI;
                double pitch = Math.atan2(delta.horizontalDistance(), delta.y) + Mth.HALF_PI;
                poseStack.mulPose(Axis.YP.rotation((float) yaw));
                poseStack.mulPose(Axis.ZP.rotation((float) pitch));
                AABB connectionBox = new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125);
                boolean selected = !handler.isLinking() && connection.equals(handler.getTargetLink());
                int color = selected ? ERROR_COLOUR : connection.isPowered(electric.getLevel()) ? CONNECTION_COLOUR : DEFAULT_COLOUR;
                float red = FastColor.ARGB32.red(color) / 255F;
                float green = FastColor.ARGB32.green(color) / 255F;
                float blue = FastColor.ARGB32.blue(color) / 255F;
                float offset = (float) (Math.sin(Util.getMillis() / 500.0) + 1.0F) / 2.0F * 0.3F;
                DebugRenderer.renderFilledBox(poseStack, source, connectionBox, red, green, blue, 0.5F + offset);
                DebugRenderer.renderFilledBox(poseStack, source, connectionBox.inflate(0.03125), red, green, blue, 0.1F + offset);
                poseStack.popPose();
                DRAWN_CONNECTIONS.add(connection);
            }
        }
        poseStack.popPose();
    }

    private BakedModel getNodeModel(ElectricBlockEntity node)
    {
        // TODO make sure link can't connect to
        if(node.getConnections().size() >= 5) //TODO config
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
        return ExtraModels.ELECTRIC_NODE_NEUTRAL.getModel();
    }

    @Override
    public boolean shouldRenderOffScreen(ElectricBlockEntity entity)
    {
        return true;
    }

    @Override
    public int getViewDistance()
    {
        return 128;
    }

    @Override
    public boolean shouldRender(ElectricBlockEntity $$0, Vec3 $$1)
    {
        return true;
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
