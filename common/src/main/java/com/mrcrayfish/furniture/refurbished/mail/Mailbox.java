package com.mrcrayfish.furniture.refurbished.mail;

import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record Mailbox(UUID id, ResourceKey<Level> levelKey, BlockPos pos, MutableObject<UUID> owner, MutableObject<String> customName, Queue<ItemStack> queue, MutableBoolean removed, DeliveryService service)
{
    public static final int MAX_NAME_LENGTH = 32;

    public boolean rename(String customName)
    {
        if(!customName.isBlank() && customName.length() <= 32)
        {
            this.customName.setValue(customName);
            return true;
        }
        return false;
    }

    void tick()
    {
        if(this.removed.booleanValue())
            return;

        if(this.queue.isEmpty())
            return;

        MinecraftServer server = this.service.getServer();
        ServerLevel level = server.getLevel(this.levelKey);
        if(level == null || !level.isLoaded(this.pos))
            return;

        if(level.getBlockEntity(this.pos) instanceof MailboxBlockEntity blockEntity)
        {
            if(blockEntity.getMailbox() != this)
            {
                this.remove();
                return;
            }

            ItemStack stack = this.queue.peek();
            if(blockEntity.deliverItem(stack))
            {
                this.queue.remove();
            }
        }
    }

    public void remove()
    {
        this.service.removeMailbox(this);
        this.removed.setValue(true);
    }

    public void writeQueue(CompoundTag compound)
    {
        ListTag list = new ListTag();
        this.queue.forEach(stack -> list.add(stack.save(new CompoundTag())));
        compound.put("Queue", list);
    }

    public static Queue<ItemStack> readQueueListTag(CompoundTag compound)
    {
        if(compound.contains("Queue", Tag.TAG_LIST))
        {
            Queue<ItemStack> queue = new ArrayDeque<>();
            ListTag list = compound.getList("Queue", Tag.TAG_COMPOUND);
            list.forEach(tag -> queue.offer(ItemStack.of((CompoundTag) tag)));
            return queue;
        }
        return new ArrayDeque<>();
    }

    void spawnQueueIntoLevel()
    {
        ServerLevel level = this.service.getServer().getLevel(this.levelKey);
        if(level != null)
        {
            Queue<ItemStack> queue = this.queue;
            while(!queue.isEmpty())
            {
                ItemStack stack = queue.poll();
                Containers.dropItemStack(level, this.pos.getX(), this.pos.getY(), this.pos.getZ(), stack);
            }
        }
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        Mailbox mailbox = (Mailbox) obj;
        return this.id.equals(mailbox.id);
    }
}
