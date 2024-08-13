package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MrCrayfish
 */
public class ForgeFreezerBlockEntity extends FreezerBlockEntity
{
    private final LazyOptional<IItemHandlerModifiable>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN);

    public ForgeFreezerBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if(side != null && cap == ForgeCapabilities.ITEM_HANDLER && !this.remove)
        {
            if(side == Direction.DOWN)
            {
                return this.handlers[1].cast();
            }
            return this.handlers[0].cast();
        }
        return super.getCapability(cap, side);
    }
}