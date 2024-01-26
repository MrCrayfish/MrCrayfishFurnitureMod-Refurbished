package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface IComputer
{
    /**
     * @return The block position of the computer
     */
    BlockPos getComputerPos();

    /**
     * Sets the player that is using this computer or null if player stopped using
     *
     * @param player a player or null if stopped using
     */
    void setUser(@Nullable Player player);

    /**
     * @return The player currently using this computer or null if no user
     */
    @Nullable
    Player getUser();

    /**
     * @return The program currently running on this computer or null
     */
    @Nullable
    Program getProgram();

    /**
     * Finds and launches the program for the given id. If the id does not match any program or is
     * null, and a program is running, it will simply close that program.
     *
     * @param id the id of the program
     */
    void launchProgram(@Nullable ResourceLocation id);

    /**
     * @return The computer menu instance of the player using the computer
     */
    @Nullable
    ComputerMenu getMenu();

    /**
     * @return True if this computer is running on the server
     */
    boolean isServer();

    /**
     * Determines if the computer is still valid for access for the given player
     *
     * @param player a player instance to test
     * @return True if the can still access this computer
     */
    boolean isValid(Player player);
}
