package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.computer.Program;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface IComputer
{
    void setUser(@Nullable Player player);

    @Nullable
    Player getUser();

    @Nullable
    Program getProgram();
}
