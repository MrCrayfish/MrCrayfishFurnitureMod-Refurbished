package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.authlib.GameProfile;
import com.mrcrayfish.furniture.refurbished.mail.IMailbox;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record ClientMailbox(UUID id, Optional<GameProfile> owner, Optional<String> customName) implements IMailbox
{
    @Override
    public UUID getId()
    {
        return this.id;
    }

    @Override
    public Optional<GameProfile> getOwner()
    {
        return this.owner;
    }

    @Override
    public Optional<String> getCustomName()
    {
        return this.customName;
    }
}
