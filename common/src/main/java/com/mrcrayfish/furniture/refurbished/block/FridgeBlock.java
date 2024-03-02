package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.blockentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.FridgeBlockEntity;
import com.mrcrayfish.furniture.refurbished.data.DropWithName;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FridgeBlock extends FurnitureHorizontalBlock implements EntityBlock, BlockTagSupplier, DropWithName
{
    private static final MapCodec<FridgeBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(MetalType.CODEC.fieldOf("metal_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, FridgeBlock::new);
    });

    protected final MetalType type;

    public FridgeBlock(MetalType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(OPEN, false));
        this.type = type;
    }

    public MetalType getMetalType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<FridgeBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape closedShape = Block.box(0, 0, 0, 16, 16, 16);
        VoxelShape openShape = Block.box(4, 0, 0, 16, 16, 16);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            boolean open = state.getValue(OPEN);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(VoxelShapeHelper.rotateHorizontally(open ? openShape : closedShape, direction));
            builder.put(state, VoxelShapeHelper.combine(shapes));
        }
        return builder.build();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide() && level.getBlockEntity(pos) instanceof FridgeBlockEntity fridge)
            {
                player.openMenu(fridge);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
        if(!state.is(newState.getBlock()) && level.getBlockState(pos.below()).getBlock() instanceof FreezerBlock)
        {
            level.removeBlock(pos.below(), false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack)
    {
        if(stack.hasCustomHoverName() && level.getBlockEntity(pos) instanceof FridgeBlockEntity fridge)
        {
            fridge.setCustomName(stack.getHoverName());
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FridgeBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(level.getBlockEntity(pos) instanceof BasicLootBlockEntity entity)
        {
            entity.updateOpenerCount();
        }
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }
}
