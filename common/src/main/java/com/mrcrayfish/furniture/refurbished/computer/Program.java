package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public abstract class Program
{
    private final ResourceLocation id;

    public Program(ResourceLocation id)
    {
        this.id = id;
    }

    public final ResourceLocation getId()
    {
        return this.id;
    }

    public void tick()
    {

    }

    public void saveState(CompoundTag tag) {}

    public void restoreState(CompoundTag tag) {}

    public void saveData(CompoundTag tag) {}

    public void loadData(CompoundTag tag) {}

    public void onClose(boolean remote)
    {

    }
}
