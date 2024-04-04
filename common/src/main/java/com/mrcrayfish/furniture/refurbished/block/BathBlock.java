package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.BathBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public abstract class BathBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    protected static final VoxelShape BASE_SHAPE = Block.box(0, 2, 0, 32, 16, 16);
    protected static final VoxelShape COLLISION_SHAPE = Shapes.join(BASE_SHAPE, Block.box(2, 4, 2, 28, 16, 14), BooleanOp.ONLY_FIRST);

    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public BathBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TYPE, Type.BOTTOM));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            if(state.getValue(TYPE) == Type.HEAD) {
                return VoxelShapeHelper.rotateHorizontally(BASE_SHAPE.move(-1, 0, 0), state.getValue(DIRECTION));
            }
            return VoxelShapeHelper.rotateHorizontally(BASE_SHAPE, state.getValue(DIRECTION));
        })));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        if(state.getValue(TYPE) == Type.HEAD)
        {
            return VoxelShapeHelper.rotateHorizontally(COLLISION_SHAPE.move(-1, 0, 0), state.getValue(DIRECTION));
        }
        return VoxelShapeHelper.rotateHorizontally(COLLISION_SHAPE, state.getValue(DIRECTION));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos headPos = context.getClickedPos().relative(direction);
        if(level.getBlockState(headPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(headPos))
        {
            return this.defaultBlockState().setValue(DIRECTION, direction);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, entity, stack);
        if(!level.isClientSide())
        {
            BlockPos headPos = pos.relative(state.getValue(DIRECTION));
            level.setBlock(headPos, state.setValue(TYPE, Type.HEAD), Block.UPDATE_ALL);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
        if(!state.is(newState.getBlock()))
        {
            Direction direction = state.getValue(DIRECTION);
            Type type = state.getValue(TYPE);
            BlockPos otherPos = pos.relative(type == Type.HEAD ? direction.getOpposite() : direction);
            BlockState otherState = level.getBlockState(otherPos);
            if(otherState.getBlock() instanceof BathBlock && otherState.getValue(TYPE) != type)
            {
                level.removeBlock(otherPos, false);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide())
        {
            if(level.getBlockEntity(pos) instanceof BathBlockEntity bath)
            {
                return bath.interact(player, hand, result);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BathBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        if(level.isClientSide())
        {
            return createTicker(type, ModBlockEntities.BATH.get(), BathBlockEntity::clientTick);
        }
        return null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source)
    {
        if(level.getBlockEntity(pos) instanceof BathBlockEntity bath)
        {
            FluidContainer container = bath.getFluidContainer();
            if(container == null || container.isEmpty())
                return;

            // Only start animation tick if full enough. A little bit of lava shouldn't have sounds
            double fullness = (double) container.getStoredAmount() / container.getCapacity();
            if(fullness < 0.6)
                return;

            container.getStoredFluid().defaultFluidState().animateTick(level, pos, source);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if(level.getBlockEntity(pos) instanceof BathBlockEntity bath)
        {
            FluidContainer container = bath.getFluidContainer();
            if(container == null || container.isEmpty())
                return;

            double fullness = (double) container.getStoredAmount() / container.getCapacity();
            double startY = pos.getY() + 0.25;
            double endY = pos.getY() + 0.9375 * fullness;
            if(entity.getY() < startY || entity.getY() > endY)
                return;

            if(container.getStoredFluid().isSame(Fluids.LAVA))
            {
                entity.lavaHurt();
            }
            else if(container.getStoredFluid().isSame(Fluids.WATER))
            {
                if(!entity.isSilent() && entity.wasOnFire)
                {
                    float volume = 0.7F;
                    float pitch = 1.6F + 0.4F * (level.random.nextFloat() - level.random.nextFloat());
                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.GENERIC_EXTINGUISH_FIRE, entity.getSoundSource(), volume, pitch);
                }
                entity.clearFire();
            }
        }
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public enum Type implements StringRepresentable
    {
        HEAD("head"),
        BOTTOM("bottom");

        private final String name;

        Type(String name)
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
