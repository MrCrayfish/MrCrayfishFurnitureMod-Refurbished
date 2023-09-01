package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface INameable
{
    void setName(@Nullable ServerPlayer player, String name);
}
