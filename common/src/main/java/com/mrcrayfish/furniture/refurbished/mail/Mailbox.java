package com.mrcrayfish.furniture.refurbished.mail;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Author: MrCrayfish
 */
public final class Mailbox implements IMailbox
{
    public static final int MAX_NAME_LENGTH = 32;

    private static final Codec<Queue<ItemStack>> ITEMSTACK_QUEUE_CODEC = ItemStack.CODEC.listOf()
            .flatXmap(list -> DataResult.success(new ArrayDeque<>(list)), queue -> DataResult.success(new ArrayList<>(queue)));

    public static final Codec<Mailbox> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("UUID").forGetter(Mailbox::id),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("Level").forGetter(Mailbox::levelKey),
            BlockPos.CODEC.fieldOf("BlockPosition").forGetter(Mailbox::pos),
            ITEMSTACK_QUEUE_CODEC.fieldOf("Queue").forGetter(Mailbox::queue),
            UUIDUtil.CODEC.optionalFieldOf("Owner").forGetter(Mailbox::owner),
            Codec.string(0, MAX_NAME_LENGTH).optionalFieldOf("CustomName").orElse(Optional.of("Mailbox")).forGetter(Mailbox::customName)
    ).apply(instance, Mailbox::new));

    private final UUID id;
    private final ResourceKey<Level> levelKey;
    private final BlockPos pos;
    private final Queue<ItemStack> queue;
    private Optional<UUID> owner;
    private Optional<String> customName;
    private DeliveryService service;
    private boolean removed;

    public Mailbox(UUID id, ResourceKey<Level> levelKey, BlockPos pos, Queue<ItemStack> queue, Optional<UUID> owner, Optional<String> customName)
    {
        this.id = id;
        this.levelKey = levelKey;
        this.pos = pos;
        this.queue = queue;
        this.owner = owner;
        this.customName = customName;
    }

    public Mailbox(UUID id, ResourceKey<Level> levelKey, BlockPos pos)
    {
        this(id, levelKey, pos, new ArrayDeque<>(), Optional.empty(), Optional.empty());
    }

    public void setService(DeliveryService service)
    {
        if(this.service == null)
        {
            this.service = service;
        }
    }

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
            this.customName = Optional.of(customName);
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
        return this.owner.isPresent();
    }

    /**
     * Sets the owner of the mailbox
     *
     * @param uuid the uuid of the new player owner
     */
    public void setOwner(UUID uuid)
    {
        this.owner = Optional.of(uuid);
        this.service.setDirty();
    }

    public boolean removed()
    {
        return this.removed;
    }

    void tick(DeliveryService service, MinecraftServer server)
    {
        if(this.removed)
            return;

        ServerLevel level = server.getLevel(this.levelKey);
        if(level == null || !level.isLoaded(this.pos))
            return;

        if(level.getBlockEntity(this.pos) instanceof MailboxBlockEntity blockEntity)
        {
            if(blockEntity.getMailbox().stream().noneMatch(mailbox -> mailbox == this))
            {
                service.removeMailbox(this);
                this.removed = true;
                return;
            }

            if(this.queue.isEmpty())
                return;

            ItemStack stack = this.queue.peek();
            if(blockEntity.deliverItem(stack))
            {
                this.queue.remove();
                service.setDirty();
            }
        }
        else
        {
            service.removeMailbox(this);
            this.removed = true;
        }
    }

    /**
     * Spawns all the ItemStacks in the queue into the level. This is called when the mailbox
     * is destroyed to prevent lost items.
     */
    void spawnQueueIntoLevel(MinecraftServer server)
    {
        ServerLevel level = server.getLevel(this.levelKey);
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
    public Optional<NameAndId> getOwner(@Nullable MinecraftServer server)
    {
        UUID ownerId = this.owner.orElse(null);
        if(ownerId != null && server != null)
        {
            return server.services().nameToIdCache().get(ownerId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getCustomName()
    {
        return this.customName;
    }

    public UUID id()
    {
        return id;
    }

    public ResourceKey<Level> levelKey()
    {
        return levelKey;
    }

    public BlockPos pos()
    {
        return pos;
    }

    public Optional<UUID> owner()
    {
        return owner;
    }

    public Optional<String> customName()
    {
        return customName;
    }

    public Queue<ItemStack> queue()
    {
        return queue;
    }
}
