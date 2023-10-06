package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class TrampolineBlock extends FurnitureBlock implements BlockTagSupplier
{
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    private final DyeColor color;

    public TrampolineBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 10, 0, 16, 13, 16);
        VoxelShape bottomLeftSupportShortShape = Block.box(1, 0, 1, 3, 2, 15);
        VoxelShape bottomLeftSupportLongShape = Block.box(1, 0, 0, 3, 2, 16);
        VoxelShape bottomLeftSupportNorthShape = Block.box(1, 0, 0, 3, 2, 15);
        VoxelShape bottomLeftSupportSouthShape = Block.box(1, 0, 1, 3, 2, 16);
        VoxelShape backLeftLegShape = Block.box(1, 2, 1, 3, 10, 3);
        VoxelShape frontLeftLegShape = Block.box(1, 2, 13, 3, 10, 15);
        VoxelShape bottomRightSupportShortShape = Block.box(13, 0, 1, 15, 2, 15);
        VoxelShape bottomRightSupportLongShape = Block.box(13, 0, 0, 15, 2, 16);
        VoxelShape bottomRightSupportNorthShape = Block.box(13, 0, 0, 15, 2, 15);
        VoxelShape bottomRightSupportSouthShape = Block.box(13, 0, 1, 15, 2, 16);
        VoxelShape frontRightLegShape = Block.box(13, 2, 13, 15, 10, 15);
        VoxelShape backRightLegShape = Block.box(13, 2, 1, 15, 10, 3);
        VoxelShape northWestCornerSupportShape = Block.box(1, 0, 0, 3, 2, 3);
        VoxelShape northEastCornerSupportShape = Block.box(13, 0, 0, 15, 2, 3);
        VoxelShape southEastCornerSupportShape = Block.box(13, 0, 13, 15, 2, 16);
        VoxelShape southWestCornerSupportShape = Block.box(1, 0, 13, 3, 2, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> topShape)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getTrampolineState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getTrampolineState(state, level, pos);
    }

    private BlockState getTrampolineState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        boolean connectedNorth = this.isTrampoline(level, pos.north());
        boolean connectedEast = this.isTrampoline(level, pos.east());
        boolean connectedSouth = this.isTrampoline(level, pos.south());
        boolean connectedWest = this.isTrampoline(level, pos.west());
        boolean legNorthWest = connectedNorth && connectedWest && !this.isTrampoline(level, pos.north().west());
        boolean legNorthEast = connectedNorth && connectedEast && !this.isTrampoline(level, pos.north().east());
        boolean legSouthEast = connectedSouth && connectedEast && !this.isTrampoline(level, pos.south().east());
        boolean legSouthWest = connectedSouth && connectedWest && !this.isTrampoline(level, pos.south().west());
        int packedValue = Shape.createPackedValue(connectedNorth, connectedEast, connectedSouth, connectedWest, legNorthWest, legNorthEast, legSouthEast, legSouthWest);
        Shape shape = Shape.fromPackedValue(packedValue);
        return state.setValue(SHAPE, shape);
    }

    private boolean isTrampoline(LevelAccessor level, BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() instanceof TrampolineBlock;
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }

    // To reduce the number of block states, an enum is being used to define only valid shapes.
    // This took down the original 256 states to 47. This doesn't work in all cases.
    public enum Shape implements StringRepresentable
    {
        DEFAULT("default"),
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west"),
        NORTH_SOUTH("north_south"),
        EAST_WEST("east_west"),
        NORTH_EAST("north_east"),
        EAST_SOUTH("east_south"),
        SOUTH_WEST("south_west"),
        WEST_NORTH("west_north"),
        NORTH_EAST_WITH_LEG("north_east_with_leg_northeast"),
        EAST_SOUTH_WITH_LEG("east_south_with_leg_eastsouth"),
        SOUTH_WEST_WITH_LEG("south_west_with_leg_southwest"),
        WEST_NORTH_WITH_LEG("west_north_with_leg_westnorth"),
        NORTH_EAST_SOUTH("north_east_south"),
        EAST_SOUTH_WEST("east_south_west"),
        SOUTH_WEST_NORTH("south_west_north"),
        WEST_NORTH_EAST("west_north_east"),
        NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST("north_east_south_with_leg_northeast"),
        NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH("north_east_south_with_leg_eastsouth"),
        NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH("north_east_south_with_leg_northeast_eastsouth"),
        EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH("east_south_west_with_leg_eastsouth"),
        EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST("east_south_west_with_leg_southwest"),
        EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST("east_south_west_with_leg_eastsouth_southwest"),
        SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH("south_west_north_with_leg_westnorth"),
        SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST("south_west_north_with_leg_southwest"),
        SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST("south_west_north_with_leg_westnorth_southwest"),
        WEST_NORTH_EAST_WITH_LEG_NORTHEAST("west_north_east_with_leg_northeast"),
        WEST_NORTH_EAST_WITH_LEG_WESTNORTH("west_north_east_with_leg_westnorth"),
        WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH("west_north_east_with_leg_northeast_westnorth"),
        ALL("north_east_south_west"),
        ALL_WITH_LEG_ALL("north_east_south_west_with_leg_northeast_eastsouth_southwest_westnorth"),
        ALL_WITH_LEG_NORTHEAST("north_east_south_west_with_leg_northeast"),
        ALL_WITH_LEG_NORTHEAST_EASTSOUTH("north_east_south_west_with_leg_northeast_eastsouth"),
        ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST("north_east_south_west_with_leg_northeast_eastsouth_southwest"),
        ALL_WITH_LEG_EASTSOUTH("north_east_south_west_with_leg_eastsouth"),
        ALL_WITH_LEG_EASTSOUTH_SOUTHWEST("north_east_south_west_with_leg_eastsouth_southwest"),
        ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH("north_east_south_west_with_leg_eastsouth_southwest_westnorth"),
        ALL_WITH_LEG_SOUTHWEST("north_east_south_west_with_leg_southwest"),
        ALL_WITH_LEG_SOUTHWEST_WESTNORTH("north_east_south_west_with_leg_southwest_westnorth"),
        ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST("north_east_south_west_with_leg_southwest_westnorth_northeast"),
        ALL_WITH_LEG_WESTNORTH("north_east_south_west_with_leg_westnorth"),
        ALL_WITH_LEG_WESTNORTH_NORTHEAST("north_east_south_west_with_leg_westnorth_northeast"),
        ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH("north_east_south_west_with_leg_westnorth_northeast_eastsouth"),
        ALL_WITH_LEG_NORTHEAST_SOUTHWEST("north_east_south_west_with_leg_northeast_southwest"),
        ALL_WITH_LEG_EASTSOUTH_WESTNORTH("north_east_south_west_with_leg_eastsouth_westnorth");

        public static final Map<Integer, Shape> PACKED_VALUE_TO_SHAPE = Arrays.stream(values())
                .collect(Collectors.toMap(shape -> shape.packedValue, shape -> shape));

        private final String name;
        private final int packedValue;

        Shape(String name)
        {
            this.name = name;
            this.packedValue = createPackedValue(name);
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
        
        public static Shape fromPackedValue(int packedValue)
        {
            return PACKED_VALUE_TO_SHAPE.getOrDefault(packedValue, DEFAULT);
        }

        private static int createPackedValue(String name)
        {
            List<String> values = Arrays.asList(name.split("_"));
            boolean north = values.contains("north");   // connected north
            boolean east = values.contains("east");     // connected east
            boolean south = values.contains("south");   // connected south
            boolean west = values.contains("west");     // connected west
            boolean lnw = values.contains("westnorth"); // leg north-west
            boolean lne = values.contains("northeast"); // leg north-east
            boolean lse = values.contains("eastsouth"); // leg south-east
            boolean lsw = values.contains("southwest"); // leg south-west
            return createPackedValue(north, east, south, west, lnw, lne, lse, lsw);
        }
  
        public static int createPackedValue(boolean n, boolean e, boolean s, boolean w, boolean cnw, boolean cne, boolean cse, boolean csw)
        {
            boolean[] states = {n, e, s, w, cnw, cne, cse, csw};
            int value = 0;
            for(int i = 0; i < states.length; i++)
            {
                if(states[i])
                {
                    value |= 1 << i;
                }
            }
            return value;
        }
    }
}
