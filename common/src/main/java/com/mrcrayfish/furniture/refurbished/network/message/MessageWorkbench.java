package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public final class MessageWorkbench
{
    public static class ItemCounts extends PlayMessage<ItemCounts>
    {
        private Map<Integer, Integer> counts;

        public ItemCounts() {}

        public ItemCounts(Map<Integer, Integer> counts)
        {
            this.counts = counts;
        }

        @Override
        public void encode(ItemCounts message, FriendlyByteBuf buffer)
        {
            buffer.writeVarInt(message.counts.size());
            for(Map.Entry<Integer, Integer> entry : message.counts.entrySet())
            {
                buffer.writeInt(entry.getKey());
                buffer.writeInt(entry.getValue());
            }
        }

        @Override
        public ItemCounts decode(FriendlyByteBuf buffer)
        {
            Map<Integer, Integer> counts = new Int2IntOpenHashMap();
            int size = buffer.readVarInt();
            while(size-- > 0)
            {
                int itemId = buffer.readInt();
                int count = buffer.readInt();
                counts.put(itemId, count);
            }
            return new ItemCounts(counts);
        }

        @Override
        public void handle(ItemCounts message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageWorkbenchItemCounts(message));
            context.setHandled(true);
        }

        public Map<Integer, Integer> getCounts()
        {
            return this.counts;
        }
    }
}
