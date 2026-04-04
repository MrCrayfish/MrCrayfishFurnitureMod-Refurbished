package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.block.BathBlock;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class BathBlockEntity extends BlockEntity implements IFluidContainerBlock, IWaterTap
{
    protected final FluidContainer tank;

    // Client only
    private int animationTime;

    public BathBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.BATH.get(), pos, state);
    }

    public BathBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
        this.tank = this.createFluidContainer(state);
    }

    public boolean isHead()
    {
        return this.tank != null;
    }

    @Override
    public FluidContainer getFluidContainer()
    {
        return this.tank != null ? this.tank : this.getFluidContainerFromHead();
    }

    @Nullable
    private FluidContainer getFluidContainerFromHead()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(BathBlock.DIRECTION))
        {
            Direction direction = state.getValue(BathBlock.DIRECTION);
            Level level = Objects.requireNonNull(this.level);
            if(level.getBlockEntity(this.worldPosition.relative(direction)) instanceof BathBlockEntity bath)
            {
                return bath.getFluidContainer();
            }
        }
        return null;
    }

    @Nullable
    private FluidContainer createFluidContainer(BlockState state)
    {
        if(state.hasProperty(BathBlock.TYPE) && state.getValue(BathBlock.TYPE) == BathBlock.Type.HEAD)
        {
            return FluidContainer.create(Config.SERVER.bath.fluidCapacity.get(), container -> {
                this.setChanged();
                container.sync(this);
            });
        }
        return null;
    }

    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        FluidContainer tank = this.getFluidContainer();
        if(tank == null)
            return InteractionResult.CONSUME;

        if(this.interactWithBottle(player, hand, this.worldPosition).consumesAction())
            return InteractionResult.SUCCESS;

        if(this.performPlatformInteraction(player, hand, this.worldPosition, result.getDirection()).consumesAction())
            return InteractionResult.SUCCESS;

        if(Config.SERVER.bath.dispenseWater.get() && result.getDirection() != Direction.DOWN)
        {
            if(this.tryAndFillWithFluid(this.level, this.worldPosition, Fluids.WATER, null).consumesAction())
                return InteractionResult.SUCCESS;

            if(this.tryAndCreateObsidian(this.level, this.worldPosition, Fluids.WATER, Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0)).consumesAction())
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void playWaterAnimation()
    {
        if(this.isHead())
        {
            this.animationTime = 4;
        }
        else
        {
            BlockState state = this.getBlockState();
            if(state.hasProperty(BathBlock.DIRECTION))
            {
                BlockPos headPos = this.worldPosition.relative(state.getValue(BathBlock.DIRECTION));
                if(this.level != null && this.level.getBlockEntity(headPos) instanceof BathBlockEntity bath && bath.isHead())
                {
                    bath.playWaterAnimation();
                }
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BathBlockEntity bath)
    {
        if(bath.animationTime > 0)
        {
            Vec3 tap = Vec3.atBottomCenterOf(pos).add(0, Utils.pixels(18), 0);
            tap = tap.relative(state.getValue(BathBlock.DIRECTION), Utils.pixels(2));
            for(int i = 0; i < 5; i++)
            {
                double x = tap.x + Utils.pixels(0.5) * level.getRandom().nextGaussian();
                double z = tap.z + Utils.pixels(0.5) * level.getRandom().nextGaussian();
                level.addParticle(ModParticleTypes.TAP_WATER.get(), x, tap.y, z, 0, 0, 0);
            }
            bath.animationTime--;
        }
    }

    @Override
    public void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        if(this.tank != null)
        {
            this.tank.load(input.childOrEmpty("FluidTank"));
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        if(this.tank != null)
        {
            this.tank.save(output.child("FluidTank"));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }
}
