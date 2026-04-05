package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.util.CustomCodecs;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.NameAndId;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record ClientMailbox(UUID mailboxId, Optional<NameAndId> owner, Optional<String> customName) implements IMailbox
{
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMailbox> STREAM_CODEC = StreamCodec.composite(
        UUIDUtil.STREAM_CODEC, ClientMailbox::mailboxId,
        ByteBufCodecs.optional(CustomCodecs.NAME_AND_ID), ClientMailbox::owner,
        ByteBufCodecs.optional(ByteBufCodecs.stringUtf8(256)), ClientMailbox::customName,
        ClientMailbox::new
    );

    @Override
    public UUID getId()
    {
        return this.mailboxId;
    }

    @Override
    public Optional<NameAndId> getOwner(@Nullable MinecraftServer server)
    {
        return this.owner;
    }

    @Override
    public Optional<String> getCustomName()
    {
        return this.customName;
    }
}
