package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;

/**
 * Author: MrCrayfish
 */
public record MessageFlipAnimation(BlockPos pos, int index)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageFlipAnimation> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeBlockPos(message.pos);
        buf.writeInt(message.index);
    }, buf -> {
        BlockPos pos = buf.readBlockPos();
        int index = buf.readInt();
        return new MessageFlipAnimation(pos, index);
    });

    public static void handle(MessageFlipAnimation message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageFlipAnimation(message));
        context.setHandled(true);
    }
}
