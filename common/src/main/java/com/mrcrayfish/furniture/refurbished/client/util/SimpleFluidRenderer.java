package com.mrcrayfish.furniture.refurbished.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.state.FluidEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.AABB;

/**
 * Author: MrCrayfish
 */
public class SimpleFluidRenderer
{
    /**
     * Submits a FluidEntityRenderState to be drawn. This is
     *
     * @param state
     * @param stack
     * @param collector
     */
    public static void submit(FluidEntityRenderState state, PoseStack stack, SubmitNodeCollector collector)
    {
        if(!state.valid())
            return;
        collector.submitCustomGeometry(stack, RenderTypes.translucentMovingBlock(), (pose, consumer) -> {
            drawContainer(state, pose, consumer);
        });
    }

    /**
     * Draws a fluid container. This will only draw the up face of a fluid and will use its "still"
     * state texture. The texture coordinates will be adjusted based on the given box.
     *
     * @param state    a FluidEntityRenderState instance
     * @param pose     the current pose
     * @param consumer the vertex consumer to submit quads
     */
    private static void drawContainer(FluidEntityRenderState state, PoseStack.Pose pose, VertexConsumer consumer)
    {
        AABB box = state.box;
        TextureAtlasSprite still = state.fluidSprites.still();
        int colour = state.waterTintAtPos;
        float red = ARGB.red(colour) / 255F;
        float green = ARGB.green(colour) / 255F;
        float blue = ARGB.blue(colour) / 255F;
        float fullness = (float) state.fluidAmount / state.fluidCapacity;
        float offset = (float) (box.minY + (box.maxY - box.minY) * fullness);
        float uScale = still.getU1() - still.getU0();
        float vScale = still.getV1() - still.getV0();
        float u0 = still.getU0() + uScale * (float) box.minX;
        float u1 = still.getU0() + uScale * (float) box.maxX;
        float v0 = still.getV0() + vScale * (float) box.minZ;
        float v1 = still.getV0() + vScale * (float) box.maxZ;
        consumer.addVertex(pose, (float) box.minX, offset, (float) box.minZ).setColor(red, green, blue, 1).setUv(u0, v0).setLight(state.lightCoords).setNormal(0, 1, 0);
        consumer.addVertex(pose, (float) box.minX, offset, (float) box.maxZ).setColor(red, green, blue, 1).setUv(u0, v1).setLight(state.lightCoords).setNormal(0, 1, 0);
        consumer.addVertex(pose, (float) box.maxX, offset, (float) box.maxZ).setColor(red, green, blue, 1).setUv(u1, v1).setLight(state.lightCoords).setNormal(0, 1, 0);
        consumer.addVertex(pose, (float) box.maxX, offset, (float) box.minZ).setColor(red, green, blue, 1).setUv(u1, v0).setLight(state.lightCoords).setNormal(0, 1, 0);
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
