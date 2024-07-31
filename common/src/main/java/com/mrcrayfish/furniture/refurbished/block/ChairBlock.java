package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class ChairBlock extends FurnitureHorizontalBlock implements BlockTagSupplier
{
    private static final MapCodec<ChairBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, ChairBlock::new);
    });

    public static final BooleanProperty TUCKED = BooleanProperty.create("tucked");

    private final WoodType type;

    public ChairBlock(WoodType type, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TUCKED, false));
        this.type = type;
    }

    public WoodType getWoodType()
    {
        return this.type;
    }

    @Override
    protected MapCodec<ChairBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape backrestShape = Block.box(12, 10, 2, 14, 20, 14);
        VoxelShape seatShape = Block.box(2, 8, 2, 14, 10, 14);
        VoxelShape backLeftLegShape = Shapes.box(0.75, 0, 0.75, 0.875, 0.5, 0.875);
        VoxelShape backRightLegShape = Shapes.box(0.75, 0, 0.125, 0.875, 0.5, 0.25);
        VoxelShape frontLeftLegShape = Shapes.box(0.125, 0, 0.75, 0.25, 0.5, 0.875);
        VoxelShape frontRightLegShape = Shapes.box(0.125, 0, 0.125, 0.25, 0.5, 0.25);
        VoxelShape chairShape = VoxelShapeHelper.combine(List.of(backrestShape, seatShape, backLeftLegShape, backRightLegShape, frontLeftLegShape, frontRightLegShape));
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            VoxelShape rotatedChairShape = VoxelShapeHelper.rotateHorizontally(chairShape, direction);
            if(state.getValue(TUCKED))
            {
                rotatedChairShape = rotatedChairShape.move(-0.5 * direction.getStepX(), 0, -0.5 * direction.getStepZ());
            }
            builder.put(state, rotatedChairShape);
        }
        return builder.build();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbourBlock, BlockPos neighbourPos, boolean $$5)
    {
        // If can no longer be tucked, restore to untucked state
        if(!this.canTuck(state, level, pos) && state.getValue(TUCKED))
        {
            level.setBlock(pos, state.setValue(TUCKED, false), UPDATE_ALL);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result)
    {
        if(player.isCrouching() && Seat.availableAt(level, pos) && this.canTuck(state, level, pos))
        {
            level.playSound(null, pos, ModSounds.BLOCK_CHAIR_SLIDE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
            level.setBlock(pos, state.setValue(TUCKED, !state.getValue(TUCKED)), UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }
        if(!state.getValue(TUCKED) && Seat.sit(player, pos, Utils.pixels(10), state.getValue(DIRECTION).getOpposite()))
        {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TUCKED);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    /**
     * Determines if the chair can be tucked into the block directly in front.
     * @param state the blockstate of the chair
     * @param level the level instance of the chair
     * @param pos the position of the chair in the level
     * @return true if the chair can be tucked
     */
    protected boolean canTuck(BlockState state, Level level, BlockPos pos)
    {
        BlockPos front = pos.relative(state.getValue(DIRECTION).getOpposite());
        return level.getBlockState(front).is(ModTags.Blocks.TUCKABLE);
    }
}
