package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.ComputerScreen;
import com.mrcrayfish.furniture.refurbished.computer.Computer;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ClientComputer implements IComputer
{
    private final BlockPos pos;
    private final Player player;
    private DisplayableProgram<?> displayable;
    private ComputerScreen screen;

    public ClientComputer(BlockPos pos, Player player)
    {
        this.pos = pos;
        this.player = player;
    }

    @Override
    public BlockPos getComputerPos()
    {
        return this.pos;
    }

    @Override
    public void setUser(@Nullable Player player) {}

    @Override
    public Player getUser()
    {
        return this.player;
    }

    @Nullable
    public DisplayableProgram<?> getDisplayable()
    {
        return this.displayable;
    }

    public void setScreen(ComputerScreen screen)
    {
        this.screen = screen;
    }

    public void setProgram(@Nullable Program program)
    {
        this.displayable = program != null ? Display.get().getDisplay(program) : null;
        if(this.displayable != null && this.screen != null)
        {
            this.displayable.setListener(new DisplayableProgram.Listener(this.screen));
        }
    }

    @Override
    @Nullable
    public Program getProgram()
    {
        return this.displayable != null ? this.displayable.getProgram() : null;
    }

    @Override
    public void launchProgram(@Nullable ResourceLocation id)
    {
        if(id == null)
        {
            this.setProgram(null);
            return;
        }
        Program program = Computer.get().createProgramInstance(id, this).orElse(null);
        this.setProgram(program);
    }

    @Nullable
    @Override
    public ComputerMenu getMenu()
    {
        Player player = this.getUser();
        if(player != null && player.containerMenu instanceof ComputerMenu menu && menu.getComputer() == this)
        {
            return menu;
        }
        return null;
    }

    @Override
    public boolean isServer()
    {
        return false;
    }

    @Override
    public boolean isValid(Player player)
    {
        return true;
    }
}
