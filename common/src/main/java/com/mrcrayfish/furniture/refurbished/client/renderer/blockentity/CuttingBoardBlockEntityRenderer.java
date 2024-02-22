package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntityRenderer implements BlockEntityRenderer<CuttingBoardBlockEntity>
{
    private final ItemRenderer renderer;
    private final RandomSource random = RandomSource.create();

    public CuttingBoardBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(CuttingBoardBlockEntity cuttingBoard, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        Direction direction = cuttingBoard.getBlockState().getValue(CuttingBoardBlock.DIRECTION);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0625 + 0.015625, 0.5);
        poseStack.mulPose(direction.getRotation());
        poseStack.mulPose(Axis.YP.rotation(Mth.PI));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        for(int i = 0; i < cuttingBoard.getContainerSize(); i++)
        {
            ItemStack stack = cuttingBoard.getItem(i);
            if(!stack.isEmpty())
            {
                this.random.setSeed(cuttingBoard.getBlockPos().hashCode() + i);
                poseStack.pushPose();
                if(stack.is(ModTags.Items.DISPLAY_AS_BLOCK))
                {
                    poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    poseStack.translate(0, 0.5 - 0.0625, 0);
                }
                else
                {
                    poseStack.mulPose(Axis.ZP.rotation((float) this.random.nextGaussian() * Mth.PI * 0.025F));
                }
                this.renderer.renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, source, cuttingBoard.getLevel(), 0);
                poseStack.popPose();
                poseStack.translate(0, 0, stack.is(ModTags.Items.DISPLAY_AS_BLOCK) ? 0.5 : 0.0625);
            }
        }
        poseStack.popPose();
    }
}
