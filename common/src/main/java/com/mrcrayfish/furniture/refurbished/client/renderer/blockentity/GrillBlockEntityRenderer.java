package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.blockentity.FlipAnimation;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.CookingItemStackRenderState;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.GrillRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class GrillBlockEntityRenderer implements BlockEntityRenderer<GrillBlockEntity, GrillRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public GrillBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public GrillRenderState createRenderState()
    {
        return new GrillRenderState();
    }

    @Override
    public void extractRenderState(GrillBlockEntity entity, GrillRenderState renderState, float partialTick, Vec3 camera, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay)
    {
        BlockEntityRenderer.super.extractRenderState(entity, renderState, partialTick, camera, overlay);

        NonNullList<ItemStack> foods = entity.getCookingItems();
        renderState.foods = new CookingItemStackRenderState[foods.size()];
        for(int i = 0; i < foods.size(); i++)
        {
            ItemStack stack = foods.get(i);
            if(!stack.isEmpty())
            {
                CookingItemStackRenderState cookingItemState = new CookingItemStackRenderState();
                renderState.foods[i] = cookingItemState;

                ItemStackRenderState itemState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.NONE, entity.getLevel(), null, 0);
                cookingItemState.item = itemState;

                GrillBlockEntity.CookingSpace space = entity.getCookingSpace(i);
                cookingItemState.itemFlipped = space.isFlipped();
                cookingItemState.itemRotation = space.getRotation();

                FlipAnimation animation = space.getAnimation();
                cookingItemState.animationTime = animation.isPlaying() ? animation.getTime(partialTick) : 0;
                cookingItemState.animationPlaying = animation.isPlaying();
            }
        }

        NonNullList<ItemStack> fuels = entity.getFuelItems();
        renderState.fuels = new ItemStackRenderState[fuels.size()];
        for(int i = 0; i < fuels.size(); i++)
        {
            ItemStack stack = fuels.get(i);
            if(!stack.isEmpty())
            {
                ItemStackRenderState itemState = new ItemStackRenderState();
                this.itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.NONE, entity.getLevel(), null, 0);
                renderState.fuels[i] = itemState;
            }
        }
    }

    @Override
    public void submit(GrillRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        if(renderState.foods != null)
        {
            for(int i = 0; i < renderState.foods.length; i++)
            {
                CookingItemStackRenderState cookingItemState = renderState.foods[i];
                if(cookingItemState != null)
                {
                    this.drawCookingSpace(cookingItemState, stack, i, renderState.lightCoords, collector);
                }
            }
        }
        if(renderState.fuels != null)
        {
            for(int i = 0; i < renderState.fuels.length; i++)
            {
                ItemStackRenderState itemState = renderState.fuels[i];
                if(itemState != null)
                {
                    this.drawFuel(itemState, stack, i, renderState.lightCoords, collector);
                }
            }
        }
    }

    private void drawCookingSpace(CookingItemStackRenderState itemState, PoseStack stack, int quadrant, int light, SubmitNodeCollector collector)
    {
        if(itemState.item != null)
        {
            float time = itemState.animationPlaying ? itemState.animationTime : 0;
            float flipProgress = this.calculateFlipProgress(time);
            float flipHeight = 0.75F;
            stack.pushPose();
            stack.translate(0, flipProgress * flipHeight, 0);
            stack.translate(0.3 + 0.4 * (quadrant % 2), 1.0, 0.3 + 0.4 * (quadrant / 2));
            stack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            stack.mulPose(Axis.ZP.rotation(Mth.HALF_PI * itemState.itemRotation));
            stack.mulPose(Axis.XP.rotation(Mth.PI * -3 * time));
            stack.mulPose(Axis.XP.rotation(!itemState.animationPlaying && itemState.itemFlipped ? Mth.PI : 0));
            stack.scale(0.375F, 0.375F, 0.375F);
            itemState.item.submit(stack, collector, light, OverlayTexture.NO_OVERLAY, 0);
            stack.popPose();
        }
    }

    private void drawFuel(ItemStackRenderState itemState, PoseStack stack, int index, int light, SubmitNodeCollector collector)
    {
        stack.pushPose();
        stack.translate(0.3 + 0.2 * (index % 3), 0.85, 0.3 + 0.2 * (index / 3));
        stack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
        stack.mulPose(Axis.YP.rotationDegrees(10F));
        stack.mulPose(Axis.ZP.rotationDegrees(10F));
        stack.mulPose(Axis.XP.rotationDegrees(5F));
        stack.scale(0.375F, 0.375F, 0.375F);
        itemState.submit(stack, collector, light, OverlayTexture.NO_OVERLAY, 0);
        stack.popPose();
    }

    /**
     * Calculates the progress for the flip animation.
     *
     * @param time the animation time
     * @return the calculated flip progress
     */
    private float calculateFlipProgress(float time)
    {
        if(time <= 0.5)
        {
            time /= 0.5F;
            return 1.0F - (float) Math.pow(1.0F - time, 4);
        }
        time -= 0.5F;
        time /= 0.5F;
        return 1.0F - (time * time * time * time);
    }
}
