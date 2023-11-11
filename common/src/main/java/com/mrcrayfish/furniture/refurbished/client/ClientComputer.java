package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.Display;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.computer.client.DisplayableProgram;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class ClientComputer implements IComputer
{
    private final Player player;
    private DisplayableProgram<?> displayable;

    public ClientComputer(Player player)
    {
        this.player = player;
    }

    @Override
    public void setUser(@Nullable Player player) {}

    @Override
    public Player getUser()
    {
        return this.player;
    }

    public void setProgram(@Nullable Program program)
    {
        this.displayable = program != null ? Display.get().getDisplay(program) : null;
    }

    public DisplayableProgram<?> getDisplayable()
    {
        return this.displayable;
    }

    @Override
    @Nullable
    public Program getProgram()
    {
        return this.displayable != null ? this.displayable.getProgram() : null;
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
}
