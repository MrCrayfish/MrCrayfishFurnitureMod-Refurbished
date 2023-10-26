package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.BasinBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.BasinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.ToiletBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class ToiletBlockEntityRenderer implements BlockEntityRenderer<ToiletBlockEntity>
{
    public ToiletBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ToiletBlockEntity toilet, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        FluidContainer container = toilet.getFluidContainer();
        if(container.isEmpty())
            return;

        BlockState state = toilet.getBlockState();
        if(!state.hasProperty(BasinBlock.DIRECTION))
            return;

        Direction direction = state.getValue(BasinBlock.DIRECTION);
        Level level = Objects.requireNonNull(toilet.getLevel());
        AABB box = SimpleFluidRenderer.createRotatedBox(direction, 3, 5, 5, 11, 8, 11);
        SimpleFluidRenderer.drawContainer(level, toilet.getBlockPos(), container, box, poseStack, source, light);
    }
}
