package com.mrcrayfish.furniture.refurbished.network.message;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.PlayMessageContext;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchContructingRecipe;
import com.mrcrayfish.furniture.refurbished.network.play.ClientPlayHandler;
import com.mrcrayfish.furniture.refurbished.network.play.ServerPlayHandler;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public final class MessageWorkbench
{
    public record ItemCounts(Map<Integer, Integer> counts)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, ItemCounts> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeVarInt(message.counts.size());
            for(Map.Entry<Integer, Integer> entry : message.counts.entrySet()) {
                buf.writeInt(entry.getKey());
                buf.writeInt(entry.getValue());
            }
        }, buf -> {
            Map<Integer, Integer> counts = new Int2IntOpenHashMap();
            int size = buf.readVarInt();
            while(size-- > 0) {
                int itemId = buf.readInt();
                int count = buf.readInt();
                counts.put(itemId, count);
            }
            return new ItemCounts(counts);
        });

        public static void handle(ItemCounts message, MessageContext context)
        {
            context.execute(() -> ClientPlayHandler.handleMessageWorkbenchItemCounts(message));
            context.setHandled(true);
        }
    }

    public record SelectRecipe(int index)
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, SelectRecipe> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeVarInt(message.index);
        }, buf -> {
            return new SelectRecipe(buf.readVarInt());
        });

        public static void handle(SelectRecipe message, PlayMessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageWorkbenchSelectRecipe(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record SearchNeighbours()
    {
        public static final StreamCodec<RegistryFriendlyByteBuf, SearchNeighbours> STREAM_CODEC = StreamCodec.unit(new SearchNeighbours());

        public static void handle(SearchNeighbours message, PlayMessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageWorkbenchSearchNeighbours(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }

    public record SyncRecipes(List<RecipeHolder<WorkbenchContructingRecipe>> recipes)
    {
        @SuppressWarnings("unchecked")
        public static final StreamCodec<RegistryFriendlyByteBuf, SyncRecipes> STREAM_CODEC = StreamCodec.of((buf, message) -> {
            buf.writeCollection(message.recipes, (buf1, holder) -> RecipeHolder.STREAM_CODEC.encode(buf, holder));
        }, buf -> {
            return new SyncRecipes(buf.readList(buf1 -> {
                return (RecipeHolder<WorkbenchContructingRecipe>) RecipeHolder.STREAM_CODEC.decode(buf);
            }));
        });

        public static void handle(SyncRecipes message, PlayMessageContext context)
        {
            context.execute(() -> ServerPlayHandler.handleMessageWorkbenchSyncRecipes(message, context.getPlayer().orElse(null)));
            context.setHandled(true);
        }
    }
}
