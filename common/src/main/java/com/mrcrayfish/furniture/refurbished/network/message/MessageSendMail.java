package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MessageSendMail extends PlayMessage<MessageSendMail>
{
    private UUID mailboxId;
    private String message;

    public MessageSendMail() {}

    public MessageSendMail(UUID mailboxId, String message)
    {
        this.mailboxId = mailboxId;
        this.message = message;
    }

    @Override
    public void encode(MessageSendMail message, FriendlyByteBuf buffer)
    {
        buffer.writeUUID(message.mailboxId);
        buffer.writeUtf(message.message);
    }

    @Override
    public MessageSendMail decode(FriendlyByteBuf buffer)
    {
        return new MessageSendMail(buffer.readUUID(), buffer.readUtf());
    }

    @Override
    public void handle(MessageSendMail message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageSendMail(message, context.getPlayer()));
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
