package com.mrcrayfish.furniture.refurbished.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.block.BathBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.BathBlockEntity;
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
public class BathBlockEntityRenderer implements BlockEntityRenderer<BathBlockEntity>
{
    public BathBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BathBlockEntity bath, float partialTick, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        FluidContainer container = bath.getFluidContainer();
        if(container == null || container.isEmpty())
            return;

        BlockState state = bath.getBlockState();
        if(!state.hasProperty(BathBlock.DIRECTION))
            return;

        Direction direction = state.getValue(BathBlock.DIRECTION);
        Level level = Objects.requireNonNull(bath.getLevel());
        AABB box = this.getFluidBox(bath, direction);
        SimpleFluidRenderer.drawContainer(level, bath.getBlockPos(), container, box, poseStack, source, light);
    }

    private AABB getFluidBox(BathBlockEntity bath, Direction direction)
    {
        if(bath.isHead())
        {
            return SimpleFluidRenderer.createRotatedBox(direction, 0, 4, 2, 12, 15, 14);
        }
        return SimpleFluidRenderer.createRotatedBox(direction, 2, 4, 2, 16, 15, 14);
    }
}
