package com.mrcrayfish.furniture.refurbished.util;

import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSyncFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class BlockEntityHelper
{
    public static void sendCustomUpdate(BlockEntity entity, CompoundTag tag)
    {
        Level level = Objects.requireNonNull(entity.getLevel());
        if(level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = entity.getBlockPos();
            ClientboundBlockEntityDataPacket packet = ClientboundBlockEntityDataPacket.create(entity, entity1 -> tag);
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }

    public static void saveItems(String key, CompoundTag tag, NonNullList<ItemStack> items)
    {
        ListTag list = new ListTag();
        for(int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i);
            if(!stack.isEmpty())
            {
                CompoundTag slot = new CompoundTag();
                slot.putByte("Slot", (byte) i);
                stack.save(slot);
                list.add(slot);
            }
        }
        tag.put(key, list);
    }

    public static void loadItems(String key, CompoundTag tag, NonNullList<ItemStack> items)
    {
        items.clear();
        if(tag.contains(key, Tag.TAG_LIST))
        {
            ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
            list.forEach(nbt ->
            {
                CompoundTag slot = (CompoundTag) nbt;
                if(slot.contains("Slot", Tag.TAG_BYTE))
                {
                    int index = slot.getByte("Slot");
                    if(index >= 0 && index < items.size())
                    {
                        ItemStack stack = ItemStack.of(slot);
                        items.set(index, stack);
                    }
                }
            });
        }
    }
}
