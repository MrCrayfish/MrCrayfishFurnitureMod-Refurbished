package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.KitchenSinkBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.client.util.SimpleFluidRenderer;
import com.mrcrayfish.furniture.refurbished.util.Utils;
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
public class KitchenSinkBlockEntityRenderer implements BlockEntityRenderer<KitchenSinkBlockEntity>
{
    public KitchenSinkBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(KitchenSinkBlockEntity sink, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        FluidContainer container = sink.getFluidContainer();
        if(container == null || container.isEmpty())
            return;

        BlockState state = sink.getBlockState();
        if(!state.hasProperty(KitchenSinkBlock.DIRECTION))
            return;

        Direction direction = state.getValue(KitchenSinkBlock.DIRECTION);
        Level level = Objects.requireNonNull(sink.getLevel());
        AABB box = SimpleFluidRenderer.createRotatedBox(direction, 2, 8, 2, 12, 15, 14);
        SimpleFluidRenderer.drawContainer(level, sink.getBlockPos(), container, box, poseStack, source, light);
    }
}
