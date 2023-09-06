package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.ElectricBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.LinkRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.NodeHitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.debug.DebugRenderer;
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
    private static final int HIGHLIGHT_COLOUR = 0xFFAAFFAA;
    private static final int ERROR_COLOUR = 0xFFFFAAAA;

    public ElectricBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ElectricBlockEntity electric, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WRENCH.get()))
            return;

        // Draw interaction box
        AABB box = electric.getInteractBox();
        boolean isLookingAt = LinkRenderer.get().isLookingAtNode(electric);
        int color = this.getBoxColor(electric, isLookingAt);
        float red = FastColor.ARGB32.red(color) / 255F;
        float green = FastColor.ARGB32.green(color) / 255F;
        float blue = FastColor.ARGB32.blue(color) / 255F;
        DebugRenderer.renderFilledBox(poseStack, source, isLookingAt ? box.inflate(0.03125) : box, red, green, blue, 1.0F);

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
                DebugRenderer.renderFilledBox(poseStack, source, new AABB(0, -0.03125, -0.03125, delta.length(), 0.03125, 0.03125), 1.0F, 1.0F, 1.0F, 0.5F);
                DRAWN_CONNECTIONS.add(connection);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }

    private int getBoxColor(ElectricBlockEntity electric, boolean hover)
    {
        boolean maxed = electric.getConnections().size() >= 5;
        return hover ? (maxed ? ERROR_COLOUR : HIGHLIGHT_COLOUR) : DEFAULT_COLOUR;
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
}
