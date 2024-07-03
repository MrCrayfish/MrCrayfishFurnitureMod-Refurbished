package com.mrcrayfish.furniture.refurbished.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

/**
 * Author: MrCrayfish
 */
public class SimpleFluidRenderer
{
    /**
     * Draws a fluid container. This will only draw the up face of a fluid and will use its "still"
     * state texture. The texture coordinates will be adjusted based on the given box.
     *
     * @param level     the level the fluid is being drawn into
     * @param pos       the block position of the fluid container
     * @param container the fluid container
     * @param box       the box which the fluid occupies
     * @param poseStack the current posestack instance
     * @param source    a buffer source
     * @param light     the light at the position of the fluid container
     */
    public static void drawContainer(Level level, BlockPos pos, FluidContainer container, AABB box, PoseStack poseStack, MultiBufferSource source, int light)
    {
        if(!container.isEmpty())
        {
            Fluid fluid = container.getStoredFluid();
            TextureAtlasSprite[] sprites = ClientServices.PLATFORM.getFluidSprites(fluid, level, pos, fluid.defaultFluidState());
            TextureAtlasSprite still = sprites[0];
            int colour = fluid.isSame(Fluids.WATER) ? BiomeColors.getAverageWaterColor(level, pos) : 0xFFFFFF;
            float red = FastColor.ARGB32.red(colour) / 255F;
            float green = FastColor.ARGB32.green(colour) / 255F;
            float blue = FastColor.ARGB32.blue(colour) / 255F;
            float fullness = (float) container.getStoredAmount() / container.getCapacity();
            float offset = (float) (box.minY + (box.maxY - box.minY) * fullness);
            float uScale = still.getU1() - still.getU0();
            float vScale = still.getV1() - still.getV0();
            float u0 = still.getU0() + uScale * (float) box.minX;
            float u1 = still.getU0() + uScale * (float) box.maxX;
            float v0 = still.getV0() + vScale * (float) box.minZ;
            float v1 = still.getV0() + vScale * (float) box.maxZ;
            RenderType type = RenderType.translucentMovingBlock(); // Hack to fix fluid not rendering with fabulous graphics
            VertexConsumer consumer = source.getBuffer(type);
            Matrix4f matrix = poseStack.last().pose();
            consumer.vertex(matrix, (float) box.minX, offset, (float) box.minZ).color(red, green, blue, 1).uv(u0, v0).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, (float) box.minX, offset, (float) box.maxZ).color(red, green, blue, 1).uv(u0, v1).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, (float) box.maxX, offset, (float) box.maxZ).color(red, green, blue, 1).uv(u1, v1).uv2(light).normal(0, 1, 0).endVertex();
            consumer.vertex(matrix, (float) box.maxX, offset, (float) box.minZ).color(red, green, blue, 1).uv(u1, v0).uv2(light).normal(0, 1, 0).endVertex();
        }
    }

    /**
     * Utility method for creating the AABB box used for rendering fluids. This method creates an
     * AABB that is rotated according to the given direction. The given min and max values are
     * assumed to be east facing position. The values must be in local pixel space.
     *
     * @param direction the direction the box should be facing
     * @param minX      the min x value of box
     * @param minY      the min y value of box
     * @param minZ      the min z value of box
     * @param maxX      the max x value of box
     * @param maxY      the max y value of box
     * @param maxZ      the max z value of box
     * @return A new rotated AABB
     */
    public static AABB createRotatedBox(Direction direction, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        // Scale the values to pixel space
        minX /= 16.0;
        minY /= 16.0;
        minZ /= 16.0;
        maxX /= 16.0;
        maxY /= 16.0;
        maxZ /= 16.0;
        return switch(direction)
        {
            case WEST -> new AABB(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
            case NORTH -> new AABB(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
            case SOUTH -> new AABB(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
            default -> new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        };
    }
}
