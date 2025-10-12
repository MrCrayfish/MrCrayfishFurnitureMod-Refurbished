package com.mrcrayfish.furniture.refurbished.mail;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.players.NameAndId;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public interface IMailbox
{
    /**
     * @return The unique identifier of the mailbox
     */
    UUID getId();

    /**
     * The game profile of the player that owns the mailbox or empty optional
     * @return an optional game profile
     */
    Optional<NameAndId> getOwner();

    /**
     * The name of the mailbox or empty optional
     * @return an optional string
     */
    Optional<String> getCustomName();
}
