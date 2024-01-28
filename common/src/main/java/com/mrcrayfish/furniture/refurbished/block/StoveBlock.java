package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.ICookingBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.RecycleBinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StoveBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StoveContainer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class StoveBlock extends FurnitureHorizontalBlock implements EntityBlock, BlockTagSupplier, WorldlyContainerHolder
{
    private final MetalType type;

    public StoveBlock(MetalType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(OPEN, false).setValue(POWERED, false).setValue(LIT, false));
        this.type = type;
    }

    public MetalType getMetalType()
    {
        return this.type;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 14, 0, 16, 16, 16);
        VoxelShape baseShape = Block.box(2, 1, 0, 16, 14, 16);
        VoxelShape bottomShape = Block.box(2, 0, 0, 16, 1, 16);
        VoxelShape closedDoorShape = Block.box(0, 1, 0, 2, 14, 16);
        VoxelShape openDoorShape = Block.box(-11, 1, 0, 2, 3, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            boolean open = state.getValue(OPEN);
            Direction direction = state.getValue(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>(List.of(topShape));
            shapes.add(VoxelShapeHelper.rotateHorizontally(baseShape, direction));
            shapes.add(VoxelShapeHelper.rotateHorizontally(bottomShape, direction));
            shapes.add(VoxelShapeHelper.rotateHorizontally(open ? openDoorShape : closedDoorShape, direction));
            return VoxelShapeHelper.combine(shapes);
        })));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide() && level.getBlockEntity(pos) instanceof StoveBlockEntity stove)
            {
                player.openMenu(stove);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!state.is(newState.getBlock()))
        {
            if(level.getBlockEntity(pos) instanceof StoveBlockEntity stove)
            {
                stove.onDestroyed(pos);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbourPos, boolean movedByPiston)
    {
        super.neighborChanged(state, level, pos, block, neighbourPos, movedByPiston);
        if(pos.above().equals(neighbourPos) && level.getBlockEntity(pos) instanceof StoveBlockEntity stove)
        {
            stove.onNeighbourChanged();
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
        builder.add(POWERED);
        builder.add(LIT);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(stack.hasCustomHoverName() && level.getBlockEntity(pos) instanceof StoveBlockEntity stove)
        {
            stove.setCustomName(stack.getHoverName());
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new StoveBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, ModBlockEntities.STOVE.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends StoveBlockEntity> cuttingBoard)
    {
        if(!level.isClientSide())
        {
            return createTickerHelper(type, cuttingBoard, StoveBlockEntity::serverTick);
        }
        return null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(level.getBlockEntity(pos) instanceof BasicLootBlockEntity blockEntity)
        {
            blockEntity.updateOpenerCount();
        }
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor accessor, BlockPos pos)
    {
        if(accessor.getBlockEntity(pos) instanceof StoveBlockEntity stove)
        {
            return stove.getContainer();
        }
        return null;
    }
}
