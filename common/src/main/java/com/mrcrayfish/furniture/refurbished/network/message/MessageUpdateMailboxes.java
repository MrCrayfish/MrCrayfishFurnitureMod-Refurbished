package com.mrcrayfish.furniture.refurbished.network.message;

import com.mojang.authlib.GameProfile;
import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.client.ClientMailbox;
import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record MessageUpdateMailboxes(Collection<? extends IMailbox> mailboxes)
{
    public static void encode(MessageUpdateMailboxes message, FriendlyByteBuf buffer)
    {
        buffer.writeInt(message.mailboxes.size());
        message.mailboxes.forEach(mailbox -> {
            buffer.writeUUID(mailbox.getId());
            Optional<GameProfile> ownerId = mailbox.getOwner();
            buffer.writeBoolean(ownerId.isPresent());
            ownerId.ifPresent(profile -> {
                buffer.writeUUID(profile.getId());
                String name = profile.getName();
                buffer.writeBoolean(name != null);
                if(name != null) {
                    buffer.writeUtf(name);
                }
            });
            Optional<String> customName = mailbox.getCustomName();
            buffer.writeBoolean(customName.isPresent());
            customName.ifPresent(buffer::writeUtf);
        });
    }

    public static MessageUpdateMailboxes decode(FriendlyByteBuf buffer)
    {
        List<IMailbox> mailboxes = new ArrayList<>();
        int size = buffer.readInt();
        for(int i = 0; i < size; i++)
        {
            UUID id = buffer.readUUID();
            UUID ownerId = buffer.readBoolean() ? buffer.readUUID() : null;
            String ownerName = ownerId != null && buffer.readBoolean() ? buffer.readUtf() : null;
            GameProfile profile = ownerId != null && ownerName != null ? new GameProfile(ownerId, ownerName) : null;
            String customName = buffer.readBoolean() ? buffer.readUtf() : null;
            mailboxes.add(new ClientMailbox(id, Optional.ofNullable(profile), Optional.ofNullable(customName)));
        }
        return new MessageUpdateMailboxes(mailboxes);
    }

    public static void handle(MessageUpdateMailboxes message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageUpdateMailboxes(message));
        context.setHandled(true);
    }
}
