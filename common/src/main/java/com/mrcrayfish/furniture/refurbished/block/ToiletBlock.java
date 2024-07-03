package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.ToiletBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
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
public abstract class ToiletBlock extends FurnitureHorizontalEntityBlock
{
    public ToiletBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape seatShape = Block.box(0, 4, 2, 16, 10, 14);
        VoxelShape tankShape = Block.box(11, 10, 2, 16, 17, 14);
        VoxelShape lidShape = Block.box(10, 17, 2, 16, 19, 14);
        VoxelShape supportShape = Block.box(3, 0, 4, 16, 4, 12);
        VoxelShape combinedShape = VoxelShapeHelper.combine(List.of(seatShape, tankShape, lidShape, supportShape));
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> {
            return VoxelShapeHelper.rotateHorizontally(combinedShape, state.getValue(DIRECTION));
        })));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide())
        {
            if(level.getBlockEntity(pos) instanceof ToiletBlockEntity toilet)
            {
                if(toilet.interact(player, hand, result) != InteractionResult.PASS)
                {
                    return InteractionResult.CONSUME;
                }
            }
            Vec3 hit = result.getLocation().subtract(Vec3.atLowerCornerOf(pos));
            if(hit.y() <= 0.625 && Seat.sit(player, pos, 0.35, state.getValue(DIRECTION).getOpposite()))
            {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource)
    {
        if(level.getBlockEntity(pos) instanceof ToiletBlockEntity toilet)
        {
            FluidContainer container = toilet.getFluidContainer();
            if(container != null)
            {
                this.handleEvent(level, pos, container);
            }
        }
    }

    private void handleEvent(ServerLevel level, BlockPos pos, FluidContainer container)
    {
        SoundEvent event = Services.FLUID.getBucketEmptySound(Fluids.WATER);
        if(Fluids.WATER.isSame(container.getStoredFluid()) && event != null)
        {
            Vec3 center = Vec3.atCenterOf(pos);
            level.sendParticles(ParticleTypes.SPLASH, center.x, center.y, center.z, 10, 0, 0, 0, 0);
            level.playSound(null, pos, event, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        else if(Fluids.LAVA.isSame(container.getStoredFluid()))
        {
            Vec3 center = Vec3.atCenterOf(pos);
            level.sendParticles(ParticleTypes.SMOKE, center.x, center.y, center.z, 5, 0, 0, 0, 0);
            level.playSound(null, pos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ToiletBlockEntity(pos, state);
    }
}
