package com.mrcrayfish.furniture.refurbished.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Author: MrCrayfish
 */
public class BlockEntityHelper
{
    public static void sendCustomUpdate(BlockEntity entity, BiFunction<BlockEntity, RegistryAccess, CompoundTag> update)
    {
        Level level = entity.getLevel();
        if(level != null && level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = entity.getBlockPos();
            ClientboundBlockEntityDataPacket packet = ClientboundBlockEntityDataPacket.create(entity, update);
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }

    public static void saveItems(String key, CompoundTag tag, NonNullList<ItemStack> items, HolderLookup.Provider provider)
    {
        ListTag list = new ListTag();
        for(int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i);
            if(!stack.isEmpty())
            {
                CompoundTag slot = new CompoundTag();
                slot.putByte("Slot", (byte) i);
                list.add(stack.save(provider, slot));
            }
        }
        tag.put(key, list);
    }

    public static void loadItems(String key, HolderLookup.Provider provider, CompoundTag tag, NonNullList<ItemStack> items)
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
                        items.set(index, ItemStack.parseOptional(provider, slot));
                    }
                }
            });
        }
    }

    public static NonNullList<ItemStack> nonNullListFromContainer(Container container)
    {
        int size = container.getContainerSize();
        NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++)
        {
            items.set(i, container.getItem(i));
        }
        return items;
    }

    public static void saveCustomName(CompoundTag tag, @Nullable Component component, HolderLookup.Provider provider)
    {
        if(component != null)
        {
            tag.putString("CustomName", Component.Serializer.toJson(component, provider));
        }
    }
}
