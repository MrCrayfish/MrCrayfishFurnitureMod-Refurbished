package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public record MessageUpdatePainting(PaletteImage image)
{
    public static void encode(MessageUpdatePainting message, FriendlyByteBuf buffer)
    {
        message.image.write(buffer);
    }

    public static MessageUpdatePainting decode(FriendlyByteBuf buffer)
    {
        return new MessageUpdatePainting(PaletteImage.read(buffer));
    }

    public static void handle(MessageUpdatePainting message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageUpdatePainting(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
