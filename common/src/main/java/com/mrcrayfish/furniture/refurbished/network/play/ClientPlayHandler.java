package com.mrcrayfish.furniture.refurbished.network.play;

import com.mrcrayfish.furniture.refurbished.blockentity.FryingPanBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.KitchenSinkBlockEntity;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlipAnimation;
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

    public static void handleMessageFlipAnimation(MessageFlipAnimation message)
    {
        Minecraft mc = Minecraft.getInstance();
        Level level = Objects.requireNonNull(mc.level);
        if(level.getBlockEntity(message.getPos()) instanceof GrillBlockEntity grill)
        {
            grill.playFlipAnimation(message.getIndex());
        }
        else if(level.getBlockEntity(message.getPos()) instanceof FryingPanBlockEntity fryingPan)
        {
            fryingPan.playFlipAnimation(message.getIndex());
        }
    }
}
