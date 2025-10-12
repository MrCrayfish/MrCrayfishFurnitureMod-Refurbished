package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.util.CustomCodecs;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.players.NameAndId;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record ClientMailbox(UUID mailboxId, Optional<NameAndId> owner, Optional<String> customName) implements IMailbox
{
    public static final StreamCodec<RegistryFriendlyByteBuf, IMailbox> STREAM_CODEC = StreamCodec.composite(
        UUIDUtil.STREAM_CODEC,
        IMailbox::getId,
        ByteBufCodecs.optional(CustomCodecs.NAME_AND_ID),
        IMailbox::getOwner,
        ByteBufCodecs.optional(ByteBufCodecs.stringUtf8(256)),
        IMailbox::getCustomName,
        ClientMailbox::new
    );

    @Override
    public UUID getId()
    {
        return this.mailboxId;
    }

    @Override
    public Optional<NameAndId> getOwner()
    {
        return this.owner;
    }

    @Override
    public Optional<String> getCustomName()
    {
        return this.customName;
    }
}
