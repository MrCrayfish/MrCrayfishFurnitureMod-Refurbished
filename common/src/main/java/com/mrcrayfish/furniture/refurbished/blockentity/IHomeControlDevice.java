package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public interface IHomeControlDevice
{
    /**
     * @return The block position of the device in the world
     */
    BlockPos getDevicePos();

    /**
     * @return True if the device is enabled
     */
    boolean isDeviceEnabled();

    /**
     * Toggles the enabled state of the device
     */
    void toggleDeviceState();

    /**
     * Sets the enabled state of the device
     *
     * @param enabled the new state
     */
    void setDeviceState(boolean enabled);

    /**
     * @return The name of the device as a {@link Component}
     */
    Component getDeviceName();
}
