package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MessageSendPackage extends PlayMessage<MessageSendPackage>
{
    private UUID mailboxId;
    private String message;

    public MessageSendPackage() {}

    public MessageSendPackage(UUID mailboxId, String message)
    {
        this.mailboxId = mailboxId;
        this.message = message;
    }

    @Override
    public void encode(MessageSendPackage message, FriendlyByteBuf buffer)
    {
        buffer.writeUUID(message.mailboxId);
        buffer.writeUtf(message.message);
    }

    @Override
    public MessageSendPackage decode(FriendlyByteBuf buffer)
    {
        return new MessageSendPackage(buffer.readUUID(), buffer.readUtf());
    }

    @Override
    public void handle(MessageSendPackage message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSendPackage(message, context.getPlayer(), context));
        context.setHandled(true);
    }

    public UUID getMailboxId()
    {
        return this.mailboxId;
    }

    public String getMessage()
    {
        return this.message;
    }
}
