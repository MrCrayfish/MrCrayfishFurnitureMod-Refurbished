package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.BasinBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.BathBlockEntity;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public abstract class BathBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public BathBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TYPE, Type.BOTTOM));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(0, 2, 0, 32, 16, 16);
        VoxelShape insideShape = Block.box(2, 4, 2, 28, 16, 14);
        VoxelShape combinedShape = Shapes.join(baseShape, insideShape, BooleanOp.ONLY_FIRST);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            if(state.getValue(TYPE) == Type.HEAD) {
                return VoxelShapeHelper.rotateHorizontally(combinedShape.move(-1, 0, 0), state.getValue(DIRECTION));
            }
            return VoxelShapeHelper.rotateHorizontally(combinedShape, state.getValue(DIRECTION));
        })));
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
