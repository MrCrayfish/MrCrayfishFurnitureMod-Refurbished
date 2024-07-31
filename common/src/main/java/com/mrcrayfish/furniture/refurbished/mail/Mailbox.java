package com.mrcrayfish.furniture.refurbished.mail;

import com.mojang.authlib.GameProfile;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public record Mailbox(UUID id, ResourceKey<Level> levelKey, BlockPos pos, MutableObject<UUID> owner, MutableObject<String> customName, Queue<ItemStack> queue, MutableBoolean removed, DeliveryService service) implements IMailbox
{
    public static final int MAX_NAME_LENGTH = 32;

    /**
     * Renames the mailbox with the given custom name. If the name is blank or the length is greater
     * than the allowed limit (See {@link Mailbox#MAX_NAME_LENGTH}), then the rename will be rejected.
     *
     * @param customName the new name for the mailbox
     * @return True if the rename was successful
     */
    public boolean rename(String customName)
    {
        if(!customName.isBlank() && customName.length() <= MAX_NAME_LENGTH)
        {
            this.customName.setValue(customName);
            this.service.setDirty();
            return true;
        }
        return false;
    }

    /**
     * @return True if this mailbox has an owner
     */
    public boolean hasOwner()
    {
        return this.owner.getValue() != null;
    }

    /**
     * Sets the owner of the mailbox
     *
     * @param uuid the uuid of the new player owner
     */
    public void setOwner(UUID uuid)
    {
        this.owner.setValue(uuid);
        this.service.setDirty();
    }

    void tick()
    {
        if(this.removed.booleanValue())
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

            if(this.queue.isEmpty())
                return;

            ItemStack stack = this.queue.peek();
            if(blockEntity.deliverItem(stack))
            {
                this.queue.remove();
            }
        }
        else
        {
            this.remove();
        }
    }

    /**
     * Marks the mailbox as removed. The next tick of {@link DeliveryService} will unregister
     * this mailbox.
     */
    public void remove()
    {
        this.service.removeMailbox(this);
        this.removed.setValue(true);
    }

    /**
     * Writes the queue to the given compound tag.
     *
     * @param compound the compound tag to save the data into
     * @param provider
     */
    public void writeQueue(CompoundTag compound, HolderLookup.Provider provider)
    {
        ListTag list = new ListTag();
        this.queue.forEach(stack -> {
            if(!stack.isEmpty()) {
                list.add(stack.save(provider));
            }
        });
        compound.put("Queue", list);
    }

    /**
     * Creates a Queue from the given compound tag containing ItemStack to be delivered
     *
     * @param compound the compound tag to read the data from
     * @return a new ItemStack Queue
     */
    public static Queue<ItemStack> readQueueListTag(CompoundTag compound, HolderLookup.Provider provider)
    {
        if(compound.contains("Queue", Tag.TAG_LIST))
        {
            Queue<ItemStack> queue = new ArrayDeque<>();
            ListTag list = compound.getList("Queue", Tag.TAG_COMPOUND);
            list.forEach(tag -> {
                ItemStack stack = ItemStack.parseOptional(provider, (CompoundTag) tag);
                if(!stack.isEmpty()) {
                    queue.offer(stack);
                }
            });
            return queue;
        }
        return new ArrayDeque<>();
    }

    /**
     * Spawns all the ItemStacks in the queue into the level. This is called when the mailbox
     * is destroyed to prevent lost items.
     */
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

    @Override
    public UUID getId()
    {
        return this.id;
    }

    @Override
    public Optional<GameProfile> getOwner()
    {
        UUID ownerId = this.owner.getValue();
        if(ownerId != null)
        {
            GameProfileCache cache = this.service.getServer().getProfileCache();
            if(cache != null)
            {
                return cache.get(ownerId);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getCustomName()
    {
        return Optional.ofNullable(this.customName.getValue());
    }
}
