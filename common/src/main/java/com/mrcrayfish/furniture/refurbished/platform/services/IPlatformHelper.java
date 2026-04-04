package com.mrcrayfish.furniture.refurbished.platform.services;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.FuelValues;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface IPlatformHelper
{
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    Platform getPlatform();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName()
    {
        return this.isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the burn time of an ItemStack. NeoForge has custom events, so a platform specific
     * method is required to correctly get the burn time.
     *
     * @param type   the recipe type
     * @param values the vanilla fuel values
     * @param stack  the stack that is going to be burnt
     * @return the amount of time in ticks that the stack can burn for
     */
    int getBurnTime(@Nullable RecipeType<?> type, FuelValues values, ItemStack stack);

    void displayItemsAcceptor(CreativeModeTab.Builder builder, Consumer<Consumer<ItemLike>> consumer);

    enum Platform
    {
        FORGE, NEOFORGE, FABRIC;

        public boolean isForge()
        {
            return this == FORGE;
        }

        public boolean isFabric()
        {
            return this == FABRIC;
        }

        public boolean isNeoForge()
        {
            return this == NEOFORGE;
        }
    }
}