package com.mrcrayfish.furniture.refurbished.util;

import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Container;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class BlockEntityHelper
{
    public static void sendCustomUpdate(BlockEntity entity, Consumer<ValueOutput> consumer)
    {
        Level level = entity.getLevel();
        if(level != null && level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = entity.getBlockPos();
            ClientboundBlockEntityDataPacket packet = ClientboundBlockEntityDataPacket.create(entity, (blockEntity, registryAccess) -> {
                try(ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(entity.problemPath(), Constants.LOG)) {
                    TagValueOutput output = TagValueOutput.createWithContext(collector, registryAccess);
                    consumer.accept(output);
                    return output.buildResult();
                }
            });
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }

    public static void sendCustomUpdate(BlockEntity entity, BiFunction<BlockEntity, RegistryAccess, CompoundTag> consumer)
    {
        Level level = entity.getLevel();
        if(level != null && level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = entity.getBlockPos();
            ClientboundBlockEntityDataPacket packet = ClientboundBlockEntityDataPacket.create(entity, consumer);
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }

    public static void saveItems(String key, ValueOutput output, NonNullList<ItemStack> items)
    {
        ValueOutput.TypedOutputList<ItemStackWithSlot> list = output.list(key, ItemStackWithSlot.CODEC);
        for(int slot = 0; slot < items.size(); slot++)
        {
            ItemStack stack = items.get(slot);
            if(!stack.isEmpty())
            {
                list.add(new ItemStackWithSlot(slot, stack));
            }
        }
        if(list.isEmpty())
        {
            output.discard(key);
        }
    }

    @SuppressWarnings("ConstantValue")
    public static void loadItems(String key, ValueInput input, NonNullList<ItemStack> items)
    {
        input.list(key, ItemStackWithSlot.CODEC).ifPresent(slots -> {
            items.clear();
            for(ItemStackWithSlot withSlot : slots) {
                if(withSlot.isValidInContainer(items.size())) {
                    items.set(withSlot.slot(), withSlot.stack());
                }
            }
        });
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

    public static Optional<Component> readCustomName(ValueInput input)
    {
       return input.read("CustomName", ComponentSerialization.CODEC);
    }

    public static void saveCustomName(ValueOutput output, @Nullable Component component)
    {
        if(component != null)
        {
            output.store("CustomName", ComponentSerialization.CODEC, component);
        }
    }
}
