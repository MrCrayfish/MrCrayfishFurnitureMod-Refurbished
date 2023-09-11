package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.block.ElectricityGeneratorBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.ElectricityGeneratorMenu;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class ElectricityGeneratorBlockEntity extends ElectricSourceLootBlockEntity implements IProcessingBlock
{
    public static final int DATA_ENERGY = 0;
    public static final int DATA_TOTAL_ENERGY = 1;

    protected int totalEnergy;
    protected int energy;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENERGY, () -> energy, value -> energy = value);
        builder.add(DATA_TOTAL_ENERGY, () -> totalEnergy, value -> totalEnergy = value);
    });

    public ElectricityGeneratorBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.ELECTRICITY_GENERATOR.get(), pos, state);
    }

    public ElectricityGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 1);
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "electricity_generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new ElectricityGeneratorMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof ElectricityGeneratorMenu generator && generator.getContainer() == this;
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(ElectricityGeneratorBlock.POWERED))
        {
            return state.getValue(ElectricityGeneratorBlock.POWERED);
        }
        return false;
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(ElectricityGeneratorBlock.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(ElectricityGeneratorBlock.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public void earlyLevelTick()
    {
        this.processTick();
        super.earlyLevelTick();
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ElectricityGeneratorBlockEntity generator)
    {
        AudioManager.get().playElectricityGeneratorSound(pos);
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
                    stack.shrink(1);
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
        if(this.isPowered())
        {
            if(time == 0)
            {
                this.setPowered(false);
            }
        }
        else if(time == 1)
        {
            this.setPowered(true);
        }
    }

    @Override
    public void onCompleteProcess() {}

    @Override
    public boolean canProcess()
    {
        // TODO only if connected to nodes
        return true;
    }
}
