package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.RecycleBinMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class RecycleBinBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IPowerSwitch, ILevelAudio, IHomeControlDevice, Nameable
{
    public static final int[] INPUT_SLOTS = new int[]{0, 1, 2};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROCESSING_TIME = 2;
    public static final int DATA_RECYCLED = 3;

    protected final Vec3 audioPosition;
    protected boolean powered;
    protected boolean processing;
    protected int processingTime;
    protected boolean enabled;
    protected int recycled;
    protected @Nullable Component name;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_PROCESSING_TIME, () -> processingTime, value -> {});
        builder.add(DATA_RECYCLED, () -> recycled, value -> {});
    });

    public RecycleBinBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.RECYCLE_BIN.get(), pos, state);
    }

    public RecycleBinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 3);
        this.audioPosition = pos.getCenter();
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return INPUT_SLOTS;
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof RecycleBinMenu recycleBin && recycleBin.getContainer() == this;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "recycle_bin");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new RecycleBinMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_RECYCLE_BIN_ENGINE.get();
    }

    @Override
    public SoundSource getSource()
    {
        return SoundSource.BLOCKS;
    }

    @Override
    public Vec3 getAudioPosition()
    {
        return this.audioPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return this.isNodePowered() && this.processing && this.enabled && !this.isRemoved();
    }

    @Override
    public int getAudioHash()
    {
        return this.worldPosition.hashCode();
    }

    @Override
    public boolean isAudioEqual(ILevelAudio other)
    {
        return other == this;
    }

    @Override
    public boolean isNodePowered()
    {
        return this.powered;
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        this.powered = powered;
        this.setChanged();
        this.sync();
    }

    @Override
    public int getEnergy()
    {
        return 0;
    }

    @Override
    public void addEnergy(int energy) {}

    @Override
    public boolean requiresEnergy()
    {
        return false;
    }

    @Override
    public int retrieveEnergy(boolean simulate)
    {
        return 0;
    }

    @Override
    public int updateAndGetTotalProcessingTime()
    {
        return this.getTotalProcessingTime();
    }

    @Override
    public int getTotalProcessingTime()
    {
        return Config.SERVER.recycleBin.processingTime.get();
    }

    @Override
    public int getProcessingTime()
    {
        return this.processingTime;
    }

    @Override
    public void setProcessingTime(int time)
    {
        this.processingTime = time;
    }

    @Override
    public void onCompleteProcess()
    {
        this.recycleItem();
    }

    @Override
    public boolean canProcess()
    {
        if(this.enabled && this.isNodePowered())
        {
            for(int index : INPUT_SLOTS)
            {
                if(!this.getItem(index).isEmpty())
                {
                    return !this.isMaxExperience();
                }
            }
        }
        return false;
    }

    private int getCurrentExperiencePoints()
    {
        return (int) Mth.clamp(this.recycled * Config.SERVER.recycleBin.experiencePerItem.get(), 0, this.getMaxExperience());
    }

    private boolean isMaxExperience()
    {
        return this.getCurrentExperiencePoints() >= this.getMaxExperience();
    }

    public void withdrawExperience(ServerPlayer player)
    {
        int points = this.getCurrentExperiencePoints();
        if(points > 0)
        {
            player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY(), player.getZ(), points));
            this.recycled = 0;
            this.setChanged();
        }
    }

    /*
     * Credit to https://minecraft.wiki/w/Experience for the equations.
     */
    private int getMaxExperience()
    {
        int maxLevel = Config.SERVER.recycleBin.maximumExperienceLevels.get();
        if(maxLevel <= 16)
        {
            return Mth.square(maxLevel) + 6 * maxLevel;
        }
        else if(maxLevel <= 31)
        {
            return (int) (2.5 * Mth.square(maxLevel) - 40.5 * maxLevel + 360);
        }
        return (int) (4.5 * Mth.square(maxLevel) - 162.5 * maxLevel + 2220);
    }

    @Override
    public void togglePower()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        this.sync();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RecycleBinBlockEntity recycleBin)
    {
        recycleBin.updateNodePoweredState();
        recycleBin.setNodeReceivingPower(false);

        boolean processing = recycleBin.processTick();
        if(recycleBin.processing != processing)
        {
            recycleBin.processing = processing;
            recycleBin.sync();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, RecycleBinBlockEntity recycleBin)
    {
        AudioManager.get().playLevelAudio(recycleBin);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("Powered", Tag.TAG_BYTE))
        {
            this.powered = tag.getBoolean("Powered");
        }
        if(tag.contains("Enabled", Tag.TAG_BYTE))
        {
            this.enabled = tag.getBoolean("Enabled");
        }
        if(tag.contains("ProcessTime", Tag.TAG_INT))
        {
            this.processingTime = tag.getInt("ProcessTime");
        }
        if(tag.contains("Processing", CompoundTag.TAG_BYTE))
        {
            this.processing = tag.getBoolean("Processing");
        }
        if(tag.contains("Recycled", Tag.TAG_INT))
        {
            this.recycled = Math.max(tag.getInt("Recycled"), 0);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Powered", this.powered);
        tag.putBoolean("Enabled", this.enabled);
        tag.putInt("ProcessTime", this.processingTime);
        tag.putInt("Recycled", this.recycled);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", this.powered);
        tag.putBoolean("Enabled", this.enabled);
        tag.putBoolean("Processing", this.processing);
        return tag;
    }

    private void sync()
    {
        if(!this.level.isClientSide())
        {
            BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
        }
    }

    /**
     * Performs the recycle action
     */
    private void recycleItem()
    {
        for(int index : INPUT_SLOTS)
        {
            ItemStack stack = this.getItem(index);
            if(!stack.isEmpty())
            {
                stack.shrink(1);
                this.recycled++;
                this.setChanged();
                break;
            }
        }
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
        BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
    }

    @Override
    public void setDeviceState(boolean enabled)
    {
        this.enabled = enabled;
        this.setChanged();
        BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
    }

    @Override
    public Component getDeviceName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomName();
        }
        return Components.SMART_DEVICE_RECYCLE_BIN;
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
