package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.FluidContainer;
import com.mrcrayfish.furniture.refurbished.blockentity.fluid.IFluidContainerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlushItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ToiletBlockEntity extends BlockEntity implements IFluidContainerBlock
{
    protected final FluidContainer tank = FluidContainer.create(Config.SERVER.toilet.fluidCapacity.get(), container -> {
        this.setChanged();
        container.sync(this);
    });

    public ToiletBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.TOILET.get(), pos, state);
    }

    public ToiletBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public FluidContainer getFluidContainer()
    {
        return this.tank;
    }

    public InteractionResult interact(Player player, InteractionHand hand, BlockHitResult result)
    {
        Vec3 hit = result.getLocation().subtract(Vec3.atLowerCornerOf(this.worldPosition));
        if(hit.y() > 0.625) // Player hit the top half of the toilet
        {
            if(!this.tank.isEmpty() && this.flushItems(player.level()).consumesAction())
            {
                return InteractionResult.SUCCESS;
            }
        }

        if(this.interactWithBottle(player, hand, this.worldPosition).consumesAction())
            return InteractionResult.SUCCESS;

        if(this.performPlatformInteraction(player, hand, this.worldPosition, result.getDirection()).consumesAction())
            return InteractionResult.SUCCESS;

        if(Config.SERVER.toilet.dispenseWater.get() && result.getDirection() != Direction.DOWN)
        {
            if(this.tryAndFillWithFluid(this.level, this.worldPosition, Fluids.WATER, Vec3.atCenterOf(this.worldPosition)).consumesAction())
                return InteractionResult.SUCCESS;

            if(this.tryAndCreateObsidian(this.level, this.worldPosition, Fluids.WATER, Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0)).consumesAction())
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private InteractionResult flushItems(Level level)
    {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition));
        if(!entities.isEmpty())
        {
            if(!level.isClientSide())
            {
                entities.forEach(entity -> {
                    Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageFlushItem(entity.getId(), this.worldPosition));
                    entity.discard();
                });
                level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(), 40);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.tank.load(tag.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        CompoundTag tankTag = new CompoundTag();
        this.tank.save(tankTag);
        tag.put("FluidTank", tankTag);
    }
}
