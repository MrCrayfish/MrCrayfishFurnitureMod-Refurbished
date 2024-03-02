package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluid;

/**
 * Author: MrCrayfish
 */
public record MessageSyncFluid(BlockPos pos, Fluid fluid, long amount)
{
    public static void encode(MessageSyncFluid message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeResourceLocation(message.fluid.builtInRegistryHolder().key().location());
        buffer.writeLong(message.amount);
    }

    public static MessageSyncFluid decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        Fluid fluid = BuiltInRegistries.FLUID.get(buffer.readResourceLocation());
        long amount = buffer.readLong();
        return new MessageSyncFluid(pos, fluid, amount);
    }

    public static void handle(MessageSyncFluid message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncFluid(message));
        context.setHandled(true);
    }
}
