package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.PlateBlockEntity;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class PlateBlock extends FallingBlock implements EntityBlock, BlockTagSupplier
{
    protected final Map<BlockState, VoxelShape> shapes;

    public PlateBlock(Properties properties)
    {
        super(properties);
        this.shapes = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(3, 0, 3, 13, 1.5, 13);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, state -> baseShape)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(level.getBlockEntity(pos) instanceof PlateBlockEntity plate)
        {
            ItemStack heldItem = player.getItemInHand(hand);
            if(!level.isClientSide())
            {
                if(player.getVehicle() instanceof Seat && plate.eat(player))
                {
                    return InteractionResult.CONSUME;
                }
                if(plate.placeItem(player, heldItem))
                {
                    if(!player.isCreative())
                    {
                        heldItem.shrink(1);
                    }
                    return InteractionResult.CONSUME;
                }
                else if(plate.popItem())
                {
                    return InteractionResult.CONSUME;
                }
                return InteractionResult.PASS;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PlateBlockEntity(pos, state);
    }

    @Override
    protected void falling(FallingBlockEntity entity)
    {
        entity.disableDrop();
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            Vec3 center = Vec3.atBottomCenterOf(pos);
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.defaultBlockState()), center.x, center.y + 0.0625, center.z, 10, 0.25, 0.25, 0.25, 0);
            serverLevel.playSound(null, center.x, center.y, center.z, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.5F + 0.2F * level.random.nextFloat());
        }
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
