package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.FreezerBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class FreezerBlockEntity extends ElectricityModuleProcessingContainerBlockEntity implements IPowerSwitch
{
    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROCESS_TIME = 2;
    public static final int DATA_MAX_PROCESS_TIME = 3;

    protected boolean enabled;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_PROCESS_TIME, () -> processingTime, value -> processingTime = value);
        builder.add(DATA_MAX_PROCESS_TIME, () -> totalProcessingTime, value -> totalProcessingTime = value);
    });

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
        return super.canProcess() && this.enabled;
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        Vec3 door = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(FreezerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, door.x, door.y, door.z, ModSounds.BLOCK_FREEZER_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        Vec3 door = Vec3.atCenterOf(this.worldPosition).relative(state.getValue(FreezerBlock.DIRECTION).getOpposite(), 0.5);
        level.playSound(null, door.x, door.y, door.z, ModSounds.BLOCK_FREEZER_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
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
    public void toggle()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Enabled", Tag.TAG_BYTE))
        {
            this.enabled = tag.getBoolean("Enabled");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Enabled", this.enabled);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("Enabled", this.enabled);
        return tag;
    }
}
