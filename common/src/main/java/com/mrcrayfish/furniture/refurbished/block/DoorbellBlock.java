package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mrcrayfish.furniture.refurbished.blockentity.DoorbellBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.FurnitureScreens;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class DoorbellBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    private static final MapCodec<DoorbellBlock> CODEC = simpleCodec(DoorbellBlock::new);

    public DoorbellBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(ENABLED, false));
    }

    @Override
    protected MapCodec<DoorbellBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(14, 4, 5, 16, 12, 11);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(baseShape, state.getValue(DIRECTION));
        })));
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return state.getValue(ENABLED) ? 16 : 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos)
    {
        Direction facing = state.getValue(DIRECTION);
        BlockPos relativePos = pos.relative(facing);
        return reader.getBlockState(relativePos).isFaceSturdy(reader, relativePos, facing.getOpposite());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState relativeState, LevelAccessor level, BlockPos pos, BlockPos relativePos)
    {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, relativeState, level, pos, relativePos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(entity instanceof Player)
        {
            if(!level.isClientSide())
            {
                if(level.getBlockEntity(pos) instanceof DoorbellBlockEntity doorbell)
                {
                    doorbell.setOwner(entity.getUUID());
                }
            }
            else
            {
                FurnitureScreens.openNameableScreen(pos, Utils.translation("gui", "set_doorbell_name"), DoorbellBlockEntity.MAX_NAME_LENGTH);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction face = context.getClickedFace();
        if(face.getAxis().isHorizontal())
        {
            return this.defaultBlockState().setValue(DIRECTION, face.getOpposite());
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(ENABLED))
        {
            return InteractionResult.CONSUME;
        }
        if(level.getBlockEntity(pos) instanceof DoorbellBlockEntity doorbell && doorbell.isNodePowered())
        {
            level.setBlock(pos, state.setValue(ENABLED, true), Block.UPDATE_ALL);
            level.scheduleTick(pos, this, 20);
            level.playSound(null, pos, ModSounds.BLOCK_DOORBELL_CHIME.get(), SoundSource.BLOCKS);
            level.updateNeighbourForOutputSignal(pos, this);
            doorbell.sendNotificationToOwner(player);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if(state.getValue(ENABLED))
        {
            level.setBlock(pos, state.setValue(ENABLED, false), Block.UPDATE_ALL);
            level.updateNeighbourForOutputSignal(pos, this);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ENABLED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new DoorbellBlockEntity(pos, state);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
