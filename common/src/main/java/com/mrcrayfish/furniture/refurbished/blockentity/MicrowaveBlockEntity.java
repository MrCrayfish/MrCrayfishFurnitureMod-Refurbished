package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.block.MicrowaveBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class MicrowaveBlockEntity extends ElectricityModuleProcessingContainerBlockEntity implements IPowerSwitch, IHomeControlDevice, Nameable
{
    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROCESS_TIME = 2;
    public static final int DATA_MAX_PROCESS_TIME = 3;

    protected boolean enabled;
    protected @Nullable Component name;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_PROCESS_TIME, () -> processingTime, value -> processingTime = value);
        builder.add(DATA_MAX_PROCESS_TIME, () -> totalProcessingTime, value -> totalProcessingTime = value);
    });

    public MicrowaveBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.MICROWAVE.get(), pos, state, ModRecipeTypes.MICROWAVE_HEATING.get());
    }

    public MicrowaveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends ProcessingRecipe> recipeType)
    {
        super(type, pos, state, 2, recipeType);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "microwave");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new MicrowaveMenu(ModMenuTypes.MICROWAVE.get(), windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof MicrowaveMenu microwave && microwave.getContainer() == this;
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
        level.playSound(null, this.worldPosition, ModSounds.BLOCK_MICROWAVE_OPEN.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        level.playSound(null, this.worldPosition, ModSounds.BLOCK_MICROWAVE_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 0.9F + 0.1F * level.random.nextFloat());
        this.setDoorState(state, false);
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(MicrowaveBlock.OPEN, open), Block.UPDATE_ALL);
        }
    }

    @Override
    public void togglePower()
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

    @Override
    public BlockPos getDevicePos()
    {
        return this.worldPosition;
    }

    @Override
    public boolean isDeviceEnabled()
    {
        return this.enabled;
    }

    @Override
    public void toggleDeviceState()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        this.syncDataToTrackingClients();
    }

    @Override
    public void setDeviceState(boolean enabled)
    {
        this.enabled = enabled;
        this.setChanged();
        this.syncDataToTrackingClients();
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return Components.SMART_DEVICE_MICROWAVE;
    }

    @Override
    public Component getName()
    {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    public Component getDisplayName()
    {
        return this.name != null ? this.name : this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName()
    {
        return this.name;
    }

    public void setCustomName(@Nullable Component name)
    {
        this.name = name;
    }
}
