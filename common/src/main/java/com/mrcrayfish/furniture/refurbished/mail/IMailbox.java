package com.mrcrayfish.furniture.refurbished.mail;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.NameAndId;
import org.jetbrains.annotations.Nullable;

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
    Optional<NameAndId> getOwner(@Nullable MinecraftServer server);

    /**
     * The game profile of the player that owns the mailbox or empty optional
     * @return an optional game profile
     */
    default Optional<NameAndId> getOwner()
    {
        return this.getOwner(null);
    }

    /**
     * The name of the mailbox or empty optional
     * @return an optional string
     */
    Optional<String> getCustomName();
}
