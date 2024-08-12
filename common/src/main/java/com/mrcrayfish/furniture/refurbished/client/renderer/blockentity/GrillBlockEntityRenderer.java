package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrcrayfish.furniture.refurbished.blockentity.FlipAnimation;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class GrillBlockEntityRenderer implements BlockEntityRenderer<GrillBlockEntity>
{
    private final ItemRenderer renderer;

    public GrillBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.renderer = context.getItemRenderer();
    }

    @Override
    public void render(GrillBlockEntity grill, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        NonNullList<ItemStack> items = grill.getCookingItems();
        for(int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i);
            if(!stack.isEmpty())
            {
                this.drawCookingSpace(grill, stack, poseStack, i, partialTick, source, light, overlay);
            }
        }

        NonNullList<ItemStack> fuel = grill.getFuelItems();
        for(int i = 0; i < fuel.size(); i++)
        {
            ItemStack stack = fuel.get(i);
            if(!stack.isEmpty())
            {
                this.drawFuel(grill, stack, poseStack, i, source, light, overlay);
            }
        }
    }

    private void drawCookingSpace(GrillBlockEntity grill, ItemStack cookingStack, PoseStack poseStack, int quadrant, float partialTick, MultiBufferSource source, int light, int overlay)
    {
        poseStack.pushPose();
        GrillBlockEntity.CookingSpace space = grill.getCookingSpace(quadrant);
        FlipAnimation animation = space.getAnimation();
        float time = animation.isPlaying() ? animation.getTime(partialTick) : 0;
        float flipProgress = this.calculateFlipProgress(time);
        float flipHeight = 0.75F;
        poseStack.translate(0, flipProgress * flipHeight, 0);
        poseStack.translate(0.3 + 0.4 * (quadrant % 2), 1.0, 0.3 + 0.4 * (quadrant / 2));
        poseStack.mulPose(Vector3f.XP.rotation(Mth.HALF_PI));
        poseStack.mulPose(Vector3f.ZP.rotation(Mth.HALF_PI * space.getRotation()));
        poseStack.mulPose(Vector3f.XP.rotation(Mth.PI * -3 * time));
        poseStack.mulPose(Vector3f.XP.rotation(!animation.isPlaying() && space.isFlipped() ? Mth.PI : 0));
        poseStack.scale(0.375F, 0.375F, 0.375F);
        this.renderer.renderStatic(cookingStack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, source, 0);
        poseStack.popPose();
    }

    private void drawFuel(GrillBlockEntity grill, ItemStack fuelStack, PoseStack poseStack, int index, MultiBufferSource source, int light, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(0.3 + 0.2 * (index % 3), 0.85, 0.3 + 0.2 * (index / 3));
        poseStack.mulPose(Vector3f.XP.rotation(Mth.HALF_PI));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(10F));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(10F));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(5F));
        poseStack.scale(0.375F, 0.375F, 0.375F);
        this.renderer.renderStatic(fuelStack, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, source, 0);
        poseStack.popPose();
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
