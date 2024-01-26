package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public interface IHomeControlDevice
{
    BlockPos getDevicePos();

    boolean isDevicePowered();

    void toggleDevicePower();

    Component getDeviceName();
}
