package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryResult;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class MessageShowDeliveryResult extends PlayMessage<MessageShowDeliveryResult>
{
    private DeliveryResult result;

    public MessageShowDeliveryResult() {}

    public MessageShowDeliveryResult(DeliveryResult result)
    {
        this.result = result;
    }

    @Override
    public void encode(MessageShowDeliveryResult message, FriendlyByteBuf buf)
    {
        buf.writeBoolean(message.result.success());
        buf.writeOptional(message.result.message(), (buf1, s) -> buf1.writeUtf(s, 256));
    }

    @Override
    public MessageShowDeliveryResult decode(FriendlyByteBuf buf)
    {
        boolean success = buf.readBoolean();
        Optional<String> message = buf.readOptional(buf1 -> buf1.readUtf(256));
        return new MessageShowDeliveryResult(new DeliveryResult(success, message));
    }

    @Override
    public void handle(MessageShowDeliveryResult message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageShowDeliveryResult(message));
        context.setHandled(true);
    }

    public DeliveryResult getResult()
    {
        return this.result;
    }
}
