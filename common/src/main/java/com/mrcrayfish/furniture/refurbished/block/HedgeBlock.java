package com.mrcrayfish.furniture.refurbished.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class HedgeBlock extends CrossCollisionBlock implements BlockTagSupplier
{
    private static final MapCodec<HedgeBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(LeafType.CODEC.fieldOf("leaf_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, HedgeBlock::new);
    });

    private final LeafType type;

    public HedgeBlock(LeafType type, Properties properties)
    {
        super(4, 16, 4, 16, 24, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
        this.type = type;
    }

    public LeafType getLeafType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<HedgeBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader reader, ScheduledTickAccess access, BlockPos pos, Direction direction, BlockPos p_60546_, BlockState p_60543_, RandomSource p_374120_)
    {
        if(direction.getAxis().isHorizontal())
        {
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), this.canConnectToFace(reader, pos, direction));
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        LevelReader reader = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluid = reader.getFluidState(pos);
        boolean north = this.canConnectToFace(reader, pos, Direction.NORTH);
        boolean east = this.canConnectToFace(reader, pos, Direction.EAST);
        boolean south = this.canConnectToFace(reader, pos, Direction.SOUTH);
        boolean west = this.canConnectToFace(reader, pos, Direction.WEST);
        return this.defaultBlockState().setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    private boolean canConnectToFace(LevelReader reader, BlockPos pos, Direction direction)
    {
        pos = pos.relative(direction);
        BlockState state = reader.getBlockState(pos);
        return !isExceptionForConnection(state) && state.isFaceSturdy(reader, pos, direction.getOpposite()) || state.getBlock() instanceof HedgeBlock || state.getBlock() instanceof LeavesBlock;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type)
    {
        return false;
    }

    @Deprecated
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos)
    {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_HOE);
    }
}
