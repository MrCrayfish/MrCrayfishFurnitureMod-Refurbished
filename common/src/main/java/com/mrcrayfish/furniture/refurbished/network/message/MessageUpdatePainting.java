package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Author: MrCrayfish
 */
public record MessageUpdatePainting(PaletteImage image)
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdatePainting> STREAM_CODEC = StreamCodec.composite(
        PaletteImage.STREAM_CODEC, MessageUpdatePainting::image,
        MessageUpdatePainting::new
    );

    public static void handle(MessageUpdatePainting message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageUpdatePainting(message, context.getPlayer().orElse(null)));
        context.setHandled(true);
    }
}
