package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.image.PaletteImage;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class MessageUpdatePainting extends PlayMessage<MessageUpdatePainting>
{
    private PaletteImage image;

    public MessageUpdatePainting() {}

    public MessageUpdatePainting(PaletteImage image)
    {
        this.image = image;
    }

    @Override
    public void encode(MessageUpdatePainting message, FriendlyByteBuf buffer)
    {
        message.image.write(buffer);
    }

    @Override
    public MessageUpdatePainting decode(FriendlyByteBuf buffer)
    {
        return new MessageUpdatePainting(PaletteImage.read(buffer));
    }

    @Override
    public void handle(MessageUpdatePainting message, MessageContext context)
    {
        context.execute(() -> ServerPlayHandler.handleMessageUpdatePainting(message, context.getPlayer()));
        context.setHandled(true);
    }

    public PaletteImage getImage()
    {
        return this.image;
    }
}
