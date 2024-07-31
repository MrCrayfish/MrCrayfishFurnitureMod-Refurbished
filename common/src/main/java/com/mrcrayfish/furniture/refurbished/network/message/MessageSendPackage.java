package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import org.jetbrains.annotations.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record MessageSendPackage(UUID mailboxId, String message)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageSendPackage> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeUUID(message.mailboxId);
        buf.writeUtf(message.message);
    }, buf -> {
        return new MessageSendPackage(buf.readUUID(), buf.readUtf());
    });

    public static void handle(MessageSendPackage message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSendPackage(message, context.getPlayer().orElse(null), context));
        context.setHandled(true);
    }

    @Nullable
    public String message()
    {
        return !this.message.isBlank() ? this.message : null;
    }
}
