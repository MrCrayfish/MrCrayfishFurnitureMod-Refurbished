package com.mrcrayfish.furniture.refurbished.mail;

import com.mojang.authlib.GameProfile;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public interface IMailbox
{
    UUID getId();

    Optional<GameProfile> getOwner();

    Optional<String> getCustomName();
}
