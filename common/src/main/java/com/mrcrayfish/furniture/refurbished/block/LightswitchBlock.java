package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.LightswitchBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlock extends FurnitureAttachedFaceBlock implements EntityBlock
{
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected final MetalType type;

    public LightswitchBlock(MetalType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL).setValue(ENABLED, false).setValue(POWERED, false));
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
        boolean enabled = !state.getValue(ENABLED);
        level.setBlock(pos, state.setValue(ENABLED, enabled), Block.UPDATE_ALL);
        Vec3 sound = this.getPositionForSound(state, pos);
        level.playSound(null, sound.x, sound.y, sound.z, ModSounds.BLOCK_LIGHTSWITCH_FLICK.get(), SoundSource.BLOCKS, 0.7F, enabled ? 1.0F : 0.8F);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private Vec3 getPositionForSound(BlockState state, BlockPos pos)
    {
        Vec3 center = pos.getCenter();
        AttachFace face = state.getValue(FACE);
        if(face == AttachFace.FLOOR)
        {
            return center.subtract(0, 0.5, 0);
        }
        if(face == AttachFace.CEILING)
        {
            return center.add(0, 0.5, 0);
        }
        Direction dir = state.getValue(FACING).getOpposite();
        return center.add(dir.getStepX() * 0.5, 0, dir.getStepZ() * 0.5);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!state.is(newState.getBlock()))
        {
            if(level.getBlockEntity(pos) instanceof IElectricityNode node)
            {
                node.onDestroyed();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ENABLED);
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new LightswitchBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, ModBlockEntities.LIGHTSWITCH.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends LightswitchBlockEntity> lightSwitch)
    {
        if(!level.isClientSide())
        {
            return createTickerHelper(type, lightSwitch, LightswitchBlockEntity::serverTick);
        }
        return null;
    }
}
