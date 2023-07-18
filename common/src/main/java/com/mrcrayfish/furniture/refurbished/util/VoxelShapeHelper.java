package com.mrcrayfish.furniture.refurbished.util;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class VoxelShapeHelper
{
    /**
     * Rotates the VoxelShape to the given direction. The provided shape must be east facing. An
     * exception will be thrown if the direction is not on the horizontal plane.
     *
     * @param shape     the shape to rotate
     * @param direction the direction the new shape will be facing
     * @return a new voxel shape in the given direction
     */
    public static VoxelShape rotateHorizontally(VoxelShape shape, Direction direction)
    {
        Preconditions.checkArgument(direction.getAxis().isHorizontal());
        return shape.toAabbs().stream().map(box -> createRotatedShape(box, direction)).reduce(Shapes.empty(), VoxelShapeHelper::join);
    }

    /**
     * Combines a list of voxel shapes into a singular voxel shape using OR operation.
     *
     * @param shapes a list of shapes to combine
     * @return a singular voxel shape of the combined shapes
     */
    public static VoxelShape combine(List<VoxelShape> shapes)
    {
        return shapes.stream().reduce(Shapes.empty(), VoxelShapeHelper::join).optimize();
    }

    /**
     * Joins two voxel shapes together using OR operation.
     * @param a the first shape
     * @param b the second shape
     * @return a voxel shape of the joined shapes
     */
    private static VoxelShape join(VoxelShape a, VoxelShape b)
    {
        return Shapes.joinUnoptimized(a, b, BooleanOp.OR);
    }

    /**
     * Creates a rotated voxel shapes from a start and end point. The input points are assumed to be
     * in an east facing position, otherwise the output voxel shape will be the incorrect rotation.
     *
     * @param box the starting x position of the cuboid
     * @param direction the direction the cuboid should be rotated
     * @return a voxel shape of the
     */
    private static VoxelShape createRotatedShape(AABB box, Direction direction)
    {
        return switch(direction)
        {
            case WEST -> Shapes.box(1 - box.maxX, box.minY, 1 - box.maxZ, 1 - box.minX, box.maxY, 1 - box.minZ);
            case NORTH -> Shapes.box(box.minZ, box.minY, 1 - box.maxX, box.maxZ, box.maxY, 1 - box.minX);
            case SOUTH -> Shapes.box(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX);
            default -> Shapes.box(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
        };
    }
}
