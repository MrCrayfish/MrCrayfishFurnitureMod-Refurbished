package com.mrcrayfish.furniture.refurbished.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.players.NameAndId;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class CustomCodecs
{
    public static final StreamCodec<FriendlyByteBuf, NameAndId> NAME_AND_ID = StreamCodec.of((buf, nameAndId) -> {
        buf.writeUUID(nameAndId.id());
        buf.writeUtf(nameAndId.name());
    }, buf -> {
        UUID id = buf.readUUID();
        String name = buf.readUtf();
        return new NameAndId(id, name);
    });
}
