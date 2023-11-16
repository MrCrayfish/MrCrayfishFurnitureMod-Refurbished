package com.mrcrayfish.furniture.refurbished.computer;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public abstract class Program
{
    protected final ResourceLocation id;
    protected final Component title;
    protected final IComputer computer;

    public Program(ResourceLocation id, IComputer computer)
    {
        this.id = id;
        this.title = Component.translatable(String.format("computer_program.%s.%s", id.getNamespace(), id.getPath()));
        this.computer = computer;
    }

    public final ResourceLocation getId()
    {
        return this.id;
    }

    public Component getTitle()
    {
        return this.title;
    }

    public final IComputer getComputer()
    {
        return this.computer;
    }

    public void tick() {}

    public void onClose(boolean remote) {}
}
