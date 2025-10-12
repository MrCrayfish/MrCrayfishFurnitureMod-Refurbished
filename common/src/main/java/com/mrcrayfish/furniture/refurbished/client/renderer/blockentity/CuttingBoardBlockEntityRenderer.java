package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.block.CuttingBoardBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.CuttingBoardItemStackRenderState;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.CuttingBoardRenderState;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardBlockEntityRenderer implements BlockEntityRenderer<CuttingBoardBlockEntity, CuttingBoardRenderState>
{
    private final ItemModelResolver itemModelResolver;
    private final RandomSource random = RandomSource.create();

    public CuttingBoardBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public CuttingBoardRenderState createRenderState()
    {
        return new CuttingBoardRenderState();
    }

    @Override
    public void extractRenderState(CuttingBoardBlockEntity entity, CuttingBoardRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);
        renderState.direction = entity.getBlockState().getValueOrElse(CuttingBoardBlock.DIRECTION, Direction.NORTH);
        renderState.items = new ArrayList<>();
        for(int i = 0; i < entity.getContainerSize(); i++)
        {
            ItemStack stack = entity.getItem(i);
            if(!stack.isEmpty())
            {
                CuttingBoardItemStackRenderState state = new CuttingBoardItemStackRenderState();
                state.setDisplayAsBlock(stack.is(ModTags.Items.DISPLAY_AS_BLOCK));
                this.itemModelResolver.updateForTopItem(state, stack, ItemDisplayContext.NONE, entity.getLevel(), null, i + i);
                renderState.items.add(state);
            }
        }
    }

    @Override
    public void submit(CuttingBoardRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0625 + 0.015625, 0.5);
        poseStack.mulPose(renderState.direction.getRotation());
        poseStack.mulPose(Axis.YP.rotation(Mth.PI));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        for(int i = 0; i < renderState.items.size(); i++)
        {
            CuttingBoardItemStackRenderState state = renderState.items.get(i);
            this.random.setSeed(renderState.blockPos.hashCode() + i);
            poseStack.pushPose();
            if(state.isDisplayAsBlock())
            {
                poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0, 0.5 - 0.0625, 0);
            }
            else
            {
                poseStack.mulPose(Axis.ZP.rotation((float) this.random.nextGaussian() * Mth.PI * 0.025F));
            }
            state.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
            poseStack.translate(0, 0, state.isDisplayAsBlock() ? 0.5 : 0.0625);
        }
        poseStack.popPose();
    }
}
