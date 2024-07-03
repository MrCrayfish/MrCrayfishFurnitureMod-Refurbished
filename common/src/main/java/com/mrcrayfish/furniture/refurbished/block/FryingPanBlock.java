package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.IHeatingSource;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class FryingPanBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier
{
    public FryingPanBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(2.5, 1, 2.5, 13.5, 4, 13.5);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> baseShape)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos pos = context.getClickedPos();
        Vec3 clickVec = context.getClickLocation().subtract(Vec3.atLowerCornerOf(pos));
        Direction forward = context.getHorizontalDirection();
        Direction right = forward.getClockWise();
        double side = right.getAxis().choose(clickVec.x, 0, clickVec.z);
        side = Math.abs(Math.min(right.getAxisDirection().getStep(), 0) + side);
        forward = side < 0.5 ? forward.getOpposite() : forward;
        return this.defaultBlockState().setValue(DIRECTION, forward).setValue(LIT, this.isHeated(context.getLevel(), pos));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && result.getDirection() != Direction.DOWN)
        {
            if(level.getBlockEntity(pos) instanceof FryingPanBlockEntity fryingPan)
            {
                ItemStack stack = player.getItemInHand(hand);
                if(stack.is(ModItems.SPATULA.get()))
                {
                    fryingPan.flipItem();
                    FryingPanBlock.playSpatulaScoopSound(level, pos, 0.1875);
                }
                else if(!stack.isEmpty())
                {
                    if(fryingPan.placeContents(level, stack, player.getDirection().get2DDataValue()))
                    {
                        if(!player.getAbilities().instabuild)
                        {
                            stack.shrink(1);
                        }
                    }
                }
                else
                {
                    fryingPan.removeContents();
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos)
    {
        return reader.getBlockEntity(pos.below()) instanceof IHeatingSource;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FryingPanBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source)
    {
        if(level.getBlockEntity(pos) instanceof FryingPanBlockEntity fryingPan)
        {
            if(!fryingPan.getItem(0).isEmpty() && fryingPan.isFlippingNeeded())
            {
                double posX = pos.getX() + 0.35 + 0.3 * level.random.nextDouble();
                double posY = pos.getY() + 0.15;
                double posZ = pos.getZ() + 0.35 + 0.3 * level.random.nextDouble();
                level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, 0, 0.05, 0);
                fryingPan.spawnSteam(level, posX, posY, posZ);
            }

            if(state.getValue(LIT) && fryingPan.getItem(0).isEmpty() && source.nextInt(2) == 0)
            {
                double posX = pos.getX() + 0.3 + 0.4 * level.random.nextDouble();
                double posY = pos.getY() + 0.15;
                double posZ = pos.getZ() + 0.3 + 0.4 * level.random.nextDouble();
                level.addParticle(ModParticleTypes.STEAM.get(), posX, posY, posZ, 0, 0.05, 0);
            }
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        if(level.isClientSide())
        {
            return createTicker(type, ModBlockEntities.FRYING_PAN.get(), FryingPanBlockEntity::clientTick);
        }
        return null;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos $$4, boolean $$5)
    {
        if(!state.getValue(FryingPanBlock.LIT))
        {
            if(this.isHeated(level, pos))
            {
                level.setBlock(pos, state.setValue(FryingPanBlock.LIT, true), Block.UPDATE_ALL);
            }
        }
        else if(!this.isHeated(level, pos))
        {
            level.setBlock(pos, state.setValue(FryingPanBlock.LIT, false), Block.UPDATE_ALL);
        }
    }

    private boolean isHeated(Level level, BlockPos pos)
    {
        BlockPos belowPos = pos.below();
        return level != null && level.getBlockEntity(belowPos) instanceof IHeatingSource source && source.isHeating();
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public static void playSpatulaScoopSound(Level level, BlockPos pos, double offset)
    {
        Vec3 vec = Vec3.atBottomCenterOf(pos);
        level.playSound(null, vec.x, vec.y + offset, vec.z, ModSounds.ITEM_SPATULA_SCOOP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
