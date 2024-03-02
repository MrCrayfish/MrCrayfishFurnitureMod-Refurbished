package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record MessageSendPackage(UUID mailboxId, String message)
{
    public static void encode(MessageSendPackage message, FriendlyByteBuf buffer)
    {
        buffer.writeUUID(message.mailboxId);
        buffer.writeUtf(message.message);
    }

    public static MessageSendPackage decode(FriendlyByteBuf buffer)
    {
        return new MessageSendPackage(buffer.readUUID(), buffer.readUtf());
    }

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
