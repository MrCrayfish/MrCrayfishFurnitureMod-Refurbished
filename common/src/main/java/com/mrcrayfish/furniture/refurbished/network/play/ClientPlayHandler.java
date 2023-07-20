package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

import java.util.Objects;

/**
 * Class containing all the handling of network messages on the client side
 * <p>
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleMessageSyncFluid(MessageSyncFluid message)
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = Objects.requireNonNull(mc.level);
        if(level.getBlockEntity(message.getPos()) instanceof KitchenSinkBlockEntity sink) // TODO abstract
        {
            sink.onSyncFluid(message.getFluid(), message.getAmount());
        }
    }
}
