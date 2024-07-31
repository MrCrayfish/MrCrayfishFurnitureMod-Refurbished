package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * Author: MrCrayfish
 */
public record MessageSyncFluid(BlockPos pos, Fluid fluid, long amount)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSyncFluid> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeResourceLocation(BuiltInRegistries.FLUID.getKey(message.fluid));
        buf.writeLong(message.amount);
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        Fluid fluid = BuiltInRegistries.FLUID.get(buf.readResourceLocation());
        long amount = buf.readLong();
        return new MessageSyncFluid(pos, fluid, amount);
    });

    public static void handle(MessageSyncFluid message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncFluid(message));
        context.setHandled(true);
    }
}
