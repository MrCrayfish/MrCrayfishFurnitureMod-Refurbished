package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Author: MrCrayfish
 */
public class StorageJarRenderer implements BlockEntityRenderer<StorageJarBlockEntity>
{
    private final ItemRenderer renderer;
    private final Font font;
    private final EntityRenderDispatcher entityDispatcher;
    private final BlockEntityRenderDispatcher blockEntityDispatcher;

    public StorageJarRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
        this.font = context.getFont();
        this.entityDispatcher = context.getEntityRenderer();
        this.blockEntityDispatcher = context.getBlockEntityRenderDispatcher();
    }

    @Override
    public void render(StorageJarBlockEntity storageJar, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        ItemStack filter = storageJar.getItem(0);
        if(filter.isEmpty())
            return;

        Direction direction = storageJar.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.015625, 0.5);
        poseStack.scale(0.499F, 0.499F, 0.499F);

        for(int i = 0; i < storageJar.getContainerSize(); i++)
        {
            ItemStack stack = storageJar.getItem(i);
            if(stack.isEmpty())
                continue;

            BakedModel model = this.renderer.getModel(stack, storageJar.getLevel(), null, 0);
            float offset = model.isGui3d() ? 0.0375F : 0.0625F;
            this.drawItem(stack, storageJar.getLevel(), direction, poseStack, source, light, overlay, !model.isGui3d(), offset);

        }
        poseStack.popPose();

        this.drawHoveredLabel(filter, storageJar.getBlockPos(), poseStack, source);
    }

    private void drawItem(ItemStack stack, Level level, Direction facing, PoseStack poseStack, MultiBufferSource source, int light, int overlay, boolean flat, float offset)
    {
        poseStack.pushPose();
        this.setupItemRotation(poseStack, facing, flat);
        this.renderer.renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, source, level, 0);
        poseStack.popPose();
        poseStack.translate(0, offset, 0);
        this.postDrawItem(poseStack, flat);
    }

    private void setupItemRotation(PoseStack poseStack, Direction facing, boolean flat)
    {
        if(!flat) return;
        poseStack.mulPose(facing.getRotation());
        poseStack.mulPose(Axis.YP.rotation(Mth.PI));
    }

    private void postDrawItem(PoseStack poseStack, boolean flat)
    {
        if(flat)
        {
            poseStack.mulPose(Axis.YP.rotation(Mth.HALF_PI / 2.01F));
            return;
        }
        poseStack.scale(0.998F, 0.998F, 0.998F);
    }

    private void drawHoveredLabel(ItemStack stack, BlockPos pos, PoseStack poseStack, MultiBufferSource source)
    {
        HitResult result = this.blockEntityDispatcher.cameraHitResult;
        if(result.getType() != HitResult.Type.BLOCK)
            return;

        BlockPos lookPos = ((BlockHitResult) result).getBlockPos();
        if(!lookPos.equals(pos))
            return;

        if(stack.isEmpty())
            return;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.0, 0.5);
        poseStack.mulPose(this.entityDispatcher.cameraOrientation());
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        Component name = stack.getHoverName();
        int width = this.font.width(name);
        this.font.drawInBatch(name, (float) -width / 2, 0, -1, false, poseStack.last().pose(), source, Font.DisplayMode.NORMAL, 0x22000000, 0xF000F0);
        poseStack.popPose();
    }
}
