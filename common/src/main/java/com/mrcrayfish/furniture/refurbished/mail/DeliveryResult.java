package com.mrcrayfish.furniture.refurbished.mail;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public record DeliveryResult(boolean success, Optional<String> message)
{
    public static DeliveryResult createSuccess(String translationKey)
    {
        return new DeliveryResult(true, Optional.of(translationKey));
    }

    public static DeliveryResult createFail(String translationKey)
    {
        return new DeliveryResult(false, Optional.of(translationKey));
    }
}