package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.ToasterBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.ToasterBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class ToasterBlockEntityRenderer implements BlockEntityRenderer<ToasterBlockEntity>
{
    private final ItemRenderer renderer;

    public ToasterBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(ToasterBlockEntity toaster, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, toaster.isHeating() ? 0.4 : 0.5, 0.5);
        Direction direction = toaster.getBlockState().getValue(ToasterBlock.DIRECTION);
        Level level = toaster.getLevel();
        this.drawItem(toaster.getItem(0), 1, direction, level, poseStack, source, light, overlay);
        this.drawItem(toaster.getItem(1), -1, direction, level, poseStack, source, light, overlay);
        poseStack.popPose();
        ElectricBlockEntityRenderer.drawNodeAndConnections(toaster, poseStack, source, overlay);
    }

    private void drawItem(ItemStack stack, int offset, Direction direction, Level level, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            Vec3i normal = direction.getNormal();
            poseStack.translate(0.095 * normal.getX() * offset, 0, 0.095 * normal.getZ() * offset);
            poseStack.mulPose(direction.getRotation());
            poseStack.mulPose(Axis.XN.rotation(Mth.HALF_PI));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.renderer.renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, source, level, 0);
            poseStack.popPose();
        }
    }
}
