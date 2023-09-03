package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlock extends FurnitureAttachedFaceBlock
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected final MetalType type;

    public LightswitchBlock(MetalType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL).setValue(POWERED, false));
        this.type = type;
    }

    public MetalType getMetalType()
    {
        return this.type;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape wallShape = Block.box(14, 4, 5, 16, 12, 11);
        VoxelShape ceilingShape = Block.box(4, 14, 5, 12, 16, 11);
        VoxelShape floorShape = Block.box(4, 0, 5, 12, 2, 11);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            Direction facing = state.getValue(FACING);
            AttachFace face = state.getValue(FACE);
            return switch(face) {
                case FLOOR -> VoxelShapeHelper.rotateHorizontally(floorShape, facing.getOpposite());
                case WALL -> VoxelShapeHelper.rotateHorizontally(wallShape, facing.getOpposite());
                case CEILING -> VoxelShapeHelper.rotateHorizontally(ceilingShape, facing.getOpposite());
            };
        })));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        boolean powered = !state.getValue(POWERED);
        level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_ALL);
        level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, powered ? 0.6F : 0.5F);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }
}
