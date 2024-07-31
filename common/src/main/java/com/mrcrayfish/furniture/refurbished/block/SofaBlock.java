package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class SofaBlock extends FurnitureHorizontalBlock implements BlockTagSupplier
{
    private static final MapCodec<SofaBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(DyeColor.CODEC.fieldOf("color").forGetter(block -> {
            return block.color;
        }), propertiesCodec()).apply(builder, SofaBlock::new);
    });

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    private final DyeColor color;

    public SofaBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected MapCodec<SofaBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(0, 3, 0, 16, 10, 16);
        VoxelShape frontLeftLegShape = Block.box(0, 0, 0, 3, 3, 3);
        VoxelShape frontRightLegShape = Block.box(0, 0, 13, 3, 3, 16);
        VoxelShape backLeftLegShape = Block.box(13, 0, 0, 16, 3, 3);
        VoxelShape backRightLegShape = Block.box(13, 0, 13, 16, 3, 16);
        VoxelShape leftArmShape = Block.box(0, 6, -2, 16, 14, 2);
        VoxelShape rightArmShape = Block.box(0, 6, 14, 16, 14, 18);
        VoxelShape backRestShape = Block.box(12, 10, 0, 16, 20, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state ->
        {
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(baseShape);
            shapes.add(backRestShape);
            switch(state.getValue(SHAPE))
            {
                case DEFAULT ->
                {
                    shapes.add(frontLeftLegShape);
                    shapes.add(frontRightLegShape);
                    shapes.add(backLeftLegShape);
                    shapes.add(backRightLegShape);
                    shapes.add(leftArmShape);
                    shapes.add(rightArmShape);
                }
                case LEFT ->
                {
                    shapes.add(frontLeftLegShape);
                    shapes.add(backLeftLegShape);
                    shapes.add(leftArmShape);
                }
                case RIGHT ->
                {
                    shapes.add(frontRightLegShape);
                    shapes.add(backRightLegShape);
                    shapes.add(rightArmShape);
                }
                case CORNER_LEFT ->
                {
                    shapes.add(backLeftLegShape);
                    shapes.add(VoxelShapeHelper.rotateHorizontally(backRestShape, Direction.NORTH));
                }
                case CORNER_RIGHT ->
                {
                    shapes.add(backRightLegShape);
                    shapes.add(VoxelShapeHelper.rotateHorizontally(backRestShape, Direction.SOUTH));
                }
            }
            return VoxelShapeHelper.rotateHorizontally(VoxelShapeHelper.combine(shapes), state.getValue(DIRECTION));
        })));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        if(state != null)
        {
            return state.setValue(SHAPE, this.getShape(state, context.getLevel(), context.getClickedPos()));
        }
        return null;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result)
    {
        if(Seat.sit(player, pos, Utils.pixels(10), state.getValue(DIRECTION).getOpposite()))
        {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return state.setValue(SHAPE, this.getShape(state, level, pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE);
    }

    public Shape getShape(BlockState state, LevelAccessor level, BlockPos pos)
    {
        Direction facing = state.getValue(DIRECTION);
        Direction front = this.getSofaDirection(level, pos, facing.getOpposite());
        if(front != null)
        {
            if(front == facing.getClockWise())
            {
                return Shape.CORNER_RIGHT;
            }
            else if(front == facing.getCounterClockWise())
            {
                return Shape.CORNER_LEFT;
            }
        }
        boolean left = this.isConnectable(level, pos, facing, facing.getCounterClockWise());
        boolean right = this.isConnectable(level, pos, facing, facing.getClockWise());
        if(left && right)
        {
            return Shape.MIDDLE;
        }
        else if(left)
        {
            return Shape.RIGHT;
        }
        else if(right)
        {
            return Shape.LEFT;
        }
        return Shape.DEFAULT;
    }

    private Direction getSofaDirection(LevelAccessor level, BlockPos pos, Direction side)
    {
        BlockState relativeState = level.getBlockState(pos.relative(side));
        return relativeState.getBlock() instanceof SofaBlock ? relativeState.getValue(DIRECTION) : null;
    }

    private boolean isConnectable(LevelAccessor level, BlockPos pos, Direction facing, Direction offset)
    {
        BlockPos relativePos = pos.relative(offset);
        BlockState relativeState = level.getBlockState(pos.relative(offset));
        if(relativeState.getBlock() instanceof SofaBlock)
        {
            Direction other = relativeState.getValue(DIRECTION);
            return other == facing || other == offset;
        }
        return relativeState.isFaceSturdy(level, relativePos, offset.getOpposite());
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    public enum Shape implements StringRepresentable
    {
        DEFAULT("default"),
        LEFT("left"),
        RIGHT("right"),
        MIDDLE("middle"),
        CORNER_LEFT("corner_left"),
        CORNER_RIGHT("corner_right");

        private final String name;

        Shape(String name)
        {
            this.name = name;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
    }
}
