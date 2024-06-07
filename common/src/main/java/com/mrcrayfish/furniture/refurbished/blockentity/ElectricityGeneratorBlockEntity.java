package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.block.ElectricityGeneratorBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.NodeSearchResult;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.ElectricityGeneratorMenu;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class ElectricityGeneratorBlockEntity extends ElectricitySourceLootBlockEntity implements IProcessingBlock, IPowerSwitch, ILevelAudio
{
    public static final int DATA_ENERGY = 0;
    public static final int DATA_TOTAL_ENERGY = 1;
    public static final int DATA_ENABLED = 2;
    public static final int DATA_OVERLOADED = 3;
    public static final int DATA_POWERED = 4;
    public static final int DATA_NODE_COUNT = 5;

    protected final Vec3 audioPosition;
    protected int totalEnergy;
    protected int energy;
    protected boolean enabled;
    protected int nodeCount;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENERGY, () -> energy, value -> {});
        builder.add(DATA_TOTAL_ENERGY, () -> totalEnergy, value -> {});
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_OVERLOADED, () -> overloaded ? 1 : 0, value -> {});
        builder.add(DATA_POWERED, () -> this.isNodePowered() ? 1 : 0, value -> {});
        builder.add(DATA_NODE_COUNT, () -> nodeCount, value -> {});
    });

    public ElectricityGeneratorBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.ELECTRICITY_GENERATOR.get(), pos, state);
    }

    public ElectricityGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 1);
        this.audioPosition = pos.getCenter().add(0, -0.375, 0);
    }

    @Override
    public int getNodeMaximumConnections()
    {
        return Config.SERVER.electricity.maximumLinksPerElectricityGenerator.get();
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "electricity_generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        if(!this.enabled)
        {
            this.searchNodeNetwork();
        }
        return new ElectricityGeneratorMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof ElectricityGeneratorMenu generator && generator.getContainer() == this;
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_ELECTRICITY_GENERATOR_ENGINE.get();
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
        return this.isNodePowered() && !this.isRemoved();
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
        BlockState state = this.getBlockState();
        if(state.hasProperty(ElectricityGeneratorBlock.POWERED))
        {
            return state.getValue(ElectricityGeneratorBlock.POWERED);
        }
        return false;
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(ElectricityGeneratorBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(ElectricityGeneratorBlock.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public void togglePower()
    {
        this.enabled = !this.enabled;
        if(this.enabled)
        {
            NodeSearchResult result = this.searchNodeNetwork();
            if(!result.overloaded())
            {
                if(this.overloaded)
                {
                    this.overloaded = false;
                }
            }
            else
            {
                this.enabled = false;
            }
        }
        this.setChanged();
    }

    @Override
    public void onNodeOverloaded()
    {
        this.enabled = false;
        this.setChanged();
    }

    @Override
    public NodeSearchResult searchNodeNetwork()
    {
        NodeSearchResult result = super.searchNodeNetwork();
        this.nodeCount = result.nodes().size();
        return result;
    }

    @Override
    public EnergyMode getEnergyMode()
    {
        return EnergyMode.ONLY_WHEN_PROCESSING;
    }

    @Override
    public int getEnergy()
    {
        return this.energy;
    }

    @Override
    public void addEnergy(int energy)
    {
        this.energy += energy;
    }

    @Override
    public boolean requiresEnergy()
    {
        return true;
    }

    @Override
    public int retrieveEnergy(boolean simulate)
    {
        ItemStack stack = this.getItem(0);
        if(!stack.isEmpty())
        {
            int energy = Services.ITEM.getBurnTime(stack, null) * Config.SERVER.electricity.fuelToPowerRatio.get();
            if(energy > 0)
            {
                if(!simulate)
                {
                    Item remainingItem = stack.getItem().getCraftingRemainingItem();
                    if(stack.getMaxStackSize() == 1 && remainingItem != null)
                    {
                        this.setItem(0, new ItemStack(remainingItem));
                    }
                    else
                    {
                        stack.shrink(1);
                    }
                    this.totalEnergy = energy;
                }
                return energy;
            }
        }
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
        return 1;
    }

    @Override
    public int getProcessingTime()
    {
        return 0;
    }

    @Override
    public void setProcessingTime(int time)
    {
        if(this.isNodePowered())
        {
            if(time == 0)
            {
                this.setNodePowered(false);
            }
        }
        else if(time == 1)
        {
            this.setNodePowered(true);
        }
    }

    @Override
    public void onCompleteProcess() {}

    @Override
    public boolean canProcess()
    {
        return this.enabled && !this.isNodeOverloaded();
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ElectricityGeneratorBlockEntity generator)
    {
        AudioManager.get().playLevelAudio(generator);
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
    public void earlyNodeLevelTick()
    {
        this.processTick();
        super.earlyNodeLevelTick();
    }

    @Override
    public double getMaxOutboundLinkLength()
    {
        return Config.SERVER.electricity.maximumElectricityGeneratorLinkLength.get();
    }
}
