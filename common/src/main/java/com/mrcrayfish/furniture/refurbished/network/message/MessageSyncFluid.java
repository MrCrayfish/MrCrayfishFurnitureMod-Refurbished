package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluid;

/**
 * Author: MrCrayfish
 */
public class MessageSyncFluid extends PlayMessage<MessageSyncFluid>
{
    private BlockPos pos;
    private Fluid fluid;
    private long amount;

    public MessageSyncFluid() {}

    public MessageSyncFluid(BlockPos pos, Fluid fluid, long amount)
    {
        this.pos = pos;
        this.fluid = fluid;
        this.amount = amount;
    }

    @Override
    public void encode(MessageSyncFluid message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeResourceLocation(message.fluid.builtInRegistryHolder().key().location());
        buffer.writeLong(message.amount);
    }

    @Override
    public MessageSyncFluid decode(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        Fluid fluid = Registry.FLUID.get(buffer.readResourceLocation());
        long amount = buffer.readLong();
        return new MessageSyncFluid(pos, fluid, amount);
    }

    @Override
    public void handle(MessageSyncFluid message, MessageContext context)
    {
        context.execute(() -> ClientPlayHandler.handleMessageSyncFluid(message));
        context.setHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public Fluid getFluid()
    {
        return this.fluid;
    }

    public long getAmount()
    {
        return this.amount;
    }
}
