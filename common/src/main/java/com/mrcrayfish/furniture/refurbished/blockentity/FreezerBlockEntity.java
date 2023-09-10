package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
import com.mrcrayfish.furniture.refurbished.block.StoveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.electric.Connection;
import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class FreezerBlockEntity extends ProcessingContainerBlockEntity implements IElectricNode
{
    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};
    public static final int DATA_PROCESS_TIME = 0;
    public static final int DATA_MAX_PROCESS_TIME = 1;

    protected final Set<Connection> connections = new HashSet<>();
    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_PROCESS_TIME, () -> processingTime, value -> processingTime = value);
        builder.add(DATA_MAX_PROCESS_TIME, () -> totalProcessingTime, value -> totalProcessingTime = value);
    });
    protected boolean powered;

    public FreezerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FREEZER.get(), pos, state, 2, ModRecipeTypes.FREEZER_SOLIDIFYING.get());
    }

    public FreezerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 2, ModRecipeTypes.FREEZER_SOLIDIFYING.get());
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "freezer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new FreezerMenu(ModMenuTypes.FREEZER.get(), windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof FreezerMenu freezer && freezer.getContainer() == this;
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return direction == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public int[] getInputSlots()
    {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots()
    {
        return OUTPUT_SLOTS;
    }

    @Override
    public int[] getEnergySlots()
    {
        return NO_SLOTS;
    }

    @Override
    public boolean canProcess()
    {
        return this.powered && super.canProcess();
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        // TODO sounds
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.setDoorState(state, false);
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(FreezerBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }

    @Override
    public BlockPos getPosition()
    {
        return this.worldPosition;
    }

    @Override
    public BlockEntity getBlockEntity()
    {
        return this;
    }

    @Override
    public boolean isSource()
    {
        return false;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public void setPowered(boolean powered)
    {
        this.powered = powered;
        this.setChanged();

        // Sync the state to the client
        if(!this.level.isClientSide())
        {
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("Powered", powered);
            BlockEntityHelper.sendCustomUpdate(this, compound);
        }
    }

    @Override
    public Set<Connection> getConnections()
    {
        return this.connections;
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.readConnections(tag);
        if(tag.contains("Powered", Tag.TAG_BYTE))
        {
            this.powered = tag.getBoolean("Powered");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeConnections(tag);
        tag.putBoolean("Powered", this.powered);
    }
}
