package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;

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

    public static class SelectRecipe extends PlayMessage<SelectRecipe>
    {
        private int index;

        public SelectRecipe() {}

        public SelectRecipe(int index)
        {
            this.index = index;
        }

        @Override
        public void encode(SelectRecipe message, FriendlyByteBuf buf)
        {
            buf.writeVarInt(message.index);
        }

        @Override
        public SelectRecipe decode(FriendlyByteBuf buf)
        {
            return new SelectRecipe(buf.readVarInt());
        }

        @Override
        public void handle(SelectRecipe message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageWorkbenchSelectRecipe(message, context.getPlayer()));
            context.setHandled(true);
        }

        public int getIndex()
        {
            return this.index;
        }
    }

    public static class SearchNeighbours extends PlayMessage<SearchNeighbours>
    {
        @Override
        public void encode(SearchNeighbours message, FriendlyByteBuf buffer) {}

        @Override
        public SearchNeighbours decode(FriendlyByteBuf buffer)
        {
            return new SearchNeighbours();
        }

        @Override
        public void handle(SearchNeighbours message, MessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageWorkbenchSearchNeighbours(message, context.getPlayer()));
            context.setHandled(true);
        }
    }
}
