package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.blockentity.LightswitchBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.data.DropWithName;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class LightswitchBlock extends FurnitureAttachedFaceBlock implements EntityBlock, BlockTagSupplier, DropWithName
{
    private static final MapCodec<LightswitchBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(MetalType.CODEC.fieldOf("metal_type").forGetter(block -> {
            return block.type;
        }), propertiesCodec()).apply(builder, LightswitchBlock::new);
    });

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
    protected MapCodec<LightswitchBlock> codec()
    {
        return CODEC;
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

    /**
     * Gets the position to play sounds from the lightswitch in the level
     *
     * @param state the blockstate of the lightswitch
     * @param pos   the block position of the lightswitch
     * @return a vec3 containing the precise position to play sounds
     */
    private Vec3 getPositionForSound(BlockState state, BlockPos pos)
    {
        Vec3 center = pos.getCenter();
        AttachFace face = state.getValue(FACE);
        return switch(face) {
            case FLOOR -> center.subtract(0, 0.5, 0);
            case CEILING -> center.add(0, 0.5, 0);
            case WALL -> {
                Direction dir = state.getValue(FACING).getOpposite();
                yield center.add(dir.getStepX() * 0.5, 0, dir.getStepZ() * 0.5);
            }
        };
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return state.getValue(ENABLED) ? 1 : 0;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ENABLED);
        builder.add(POWERED);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(stack.hasCustomHoverName() && level.getBlockEntity(pos) instanceof LightswitchBlockEntity light)
        {
            light.setCustomName(stack.getHoverName());
        }
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
        if(!level.isClientSide())
        {
            return FurnitureEntityBlock.createTicker(type, ModBlockEntities.LIGHTSWITCH.get(), IModuleNode::serverTick);
        }
        return null;
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
