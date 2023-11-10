package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public abstract class Program
{
    private final ResourceLocation id;
    private final Component title;

    public Program(ResourceLocation id)
    {
        this.id = id;
        this.title = Component.translatable(String.format("%s.computer_program.%s", id.getNamespace(), id.getPath()));
    }

    public final ResourceLocation getId()
    {
        return this.id;
    }

    public Component getTitle()
    {
        return this.title;
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
