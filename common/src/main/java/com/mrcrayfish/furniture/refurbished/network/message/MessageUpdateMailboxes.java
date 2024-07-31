package com.mrcrayfish.furniture.refurbished.network.message;

import com.mojang.authlib.GameProfile;
import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.client.ClientMailbox;
import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

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
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdateMailboxes> STREAM_CODEC = StreamCodec.of((buf, message) -> {
        buf.writeInt(message.mailboxes.size());
        message.mailboxes.forEach(mailbox -> {
            buf.writeUUID(mailbox.getId());
            Optional<GameProfile> ownerId = mailbox.getOwner();
            buf.writeBoolean(ownerId.isPresent());
            ownerId.ifPresent(profile -> {
                buf.writeUUID(profile.getId());
                String name = profile.getName();
                buf.writeBoolean(name != null);
                if(name != null) {
                    buf.writeUtf(name);
                }
            });
            Optional<String> customName = mailbox.getCustomName();
            buf.writeBoolean(customName.isPresent());
            customName.ifPresent(buf::writeUtf);
        });
    }, buf -> {
        List<IMailbox> mailboxes = new ArrayList<>();
        int size = buf.readInt();
        for(int i = 0; i < size; i++)
        {
            UUID id = buf.readUUID();
            UUID ownerId = buf.readBoolean() ? buf.readUUID() : null;
            String ownerName = ownerId != null && buf.readBoolean() ? buf.readUtf() : null;
            GameProfile profile = ownerId != null && ownerName != null ? new GameProfile(ownerId, ownerName) : null;
            String customName = buf.readBoolean() ? buf.readUtf() : null;
            mailboxes.add(new ClientMailbox(id, Optional.ofNullable(profile), Optional.ofNullable(customName)));
        }
        return new MessageUpdateMailboxes(mailboxes);
    });

    public static void handle(MessageUpdateMailboxes message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageUpdateMailboxes(message));
        context.setHandled(true);
    }
}
