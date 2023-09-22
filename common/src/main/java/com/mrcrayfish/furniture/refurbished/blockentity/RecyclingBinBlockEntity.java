package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.RecyclingBinMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Author: MrCrayfish
 */
// TODO forge version with capabilities
public class RecyclingBinBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IPowerSwitch, IAudioBlock
{
    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROCESSING_TIME = 2;

    protected RandomSource random = RandomSource.create();
    protected SimpleContainer output = new SimpleContainer(9);
    protected boolean powered;
    protected boolean processing;
    protected int processingTime;
    protected boolean enabled;
    protected boolean full;
    protected long seed = this.random.nextLong();
    protected List<ItemStack> outputCache;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_PROCESSING_TIME, () -> processingTime, value -> {});
    });

    public RecyclingBinBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.RECYCLING_BIN.get(), pos, state);
    }

    public RecyclingBinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 10);
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return direction == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof RecyclingBinMenu recyclingBin && recyclingBin.getContainer() == this;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "recycling_bin");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new RecyclingBinMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_RECYCLING_BIN_ENGINE.get();
    }

    @Override
    public BlockPos getAudioPosition()
    {
        return this.worldPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return this.isPowered() && this.processing && this.enabled && !this.isRemoved();
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
        return Config.SERVER.recyclingBin.processingTime.get();
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
        this.seed = this.level.random.nextLong();
        this.outputCache = null;
        this.full = false;
    }

    @Override
    public boolean canProcess()
    {
        return false;
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        this.full = false;
        this.outputCache = null;
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        super.setItem(index, stack);
        if(index >= 1 && index < this.getContainerSize())
        {
            this.output.setItem(index - 1, this.getItem(index));
        }
    }

    @Override
    public void toggle()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        this.sync();
    }

    /**
     *
     */
    private void updateOutputContainer()
    {
        for(int i = 1; i < this.getContainerSize(); i++)
        {
            this.output.setItem(i - 1, this.getItem(i));
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RecyclingBinBlockEntity recyclingBin)
    {
        recyclingBin.updatePoweredState();
        recyclingBin.setReceivingPower(false);

        boolean processing = recyclingBin.processTick();
        if(recyclingBin.processing != processing)
        {
            recyclingBin.processing = processing;
            recyclingBin.sync();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, RecyclingBinBlockEntity recyclingBin)
    {
        AudioManager.get().playAudioBlock(recyclingBin);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.updateOutputContainer();
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
        if(tag.contains("Seed", CompoundTag.TAG_LONG))
        {
            this.seed = tag.getLong("Seed");
        }
        if(tag.contains("Processing", CompoundTag.TAG_BYTE))
        {
            this.processing = tag.getBoolean("Processing");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Powered", this.powered);
        tag.putBoolean("Enabled", this.enabled);
        tag.putInt("ProcessTime", this.processingTime);
        tag.putLong("Seed", this.seed);
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

    /**
     *
     */
    private void sync()
    {
        if(!this.level.isClientSide())
        {
            BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
        }
    }
}
