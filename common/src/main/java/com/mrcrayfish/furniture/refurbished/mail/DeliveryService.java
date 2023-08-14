package com.mrcrayfish.furniture.refurbished.mail;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class DeliveryService extends SavedData
{
    private static final String STORAGE_ID = Utils.resource("delivery_service").toString();

    public static Optional<DeliveryService> get(MinecraftServer server)
    {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        if(level != null)
        {
            return Optional.of(level.getDataStorage().computeIfAbsent(tag -> new DeliveryService(server, tag), () -> new DeliveryService(server), STORAGE_ID));
        }
        return Optional.empty();
    }

    private final MinecraftServer server;
    private final Map<UUID, Mailbox> mailboxes = new HashMap<>();
    private final Queue<Mailbox> removal = new ArrayDeque<>();

    public DeliveryService(MinecraftServer server)
    {
        this(server, new CompoundTag());
    }

    public DeliveryService(MinecraftServer server, CompoundTag compound)
    {
        this.server = server;
        this.load(compound);
    }

    /**
     *
     * @return
     */
    public MinecraftServer getServer()
    {
        return this.server;
    }

    public void serverTick()
    {
        // Checks for mailboxes that need to be removed and spawn their queue into the level
        while(!this.removal.isEmpty())
        {
            Mailbox mailbox = this.removal.poll();
            mailbox.spawnQueueIntoLevel();
            this.mailboxes.remove(mailbox.id());
        }

        // Try to deliver mail from queues to the block entity in the level
        this.mailboxes.forEach((uuid, mailbox) -> mailbox.tick());
    }

    /**
     *
     * @param mailbox
     */
    void removeMailbox(Mailbox mailbox)
    {
        this.removal.offer(mailbox);
    }

    /**
     *
     * @param blockEntity
     * @return
     */
    public Mailbox getOrCreateMailBox(MailboxBlockEntity blockEntity)
    {
        return this.mailboxes.computeIfAbsent(blockEntity.getId(), uuid -> {
            ResourceKey<Level> levelKey = blockEntity.getLevel().dimension();
            BlockPos pos = blockEntity.getBlockPos();
            return new Mailbox(uuid, levelKey, pos, new MutableObject<>(), new ArrayDeque<>(), new MutableBoolean(), this);
        });
    }

    private void load(CompoundTag compound)
    {
        if(compound.contains("Mailboxes", Tag.TAG_LIST))
        {
            ListTag list = compound.getList("Mailboxes", Tag.TAG_COMPOUND);
            list.forEach(tag ->
            {
                try
                {
                    CompoundTag mailbox = (CompoundTag) tag;
                    UUID id = mailbox.getUUID("UUID");
                    ResourceKey<Level> levelKey = createLevelKey(mailbox.getString("Level"));
                    BlockPos pos = BlockPos.of(mailbox.getLong("BlockPosition"));
                    MutableObject<UUID> owner = new MutableObject<>();
                    if(mailbox.contains("Owner", Tag.TAG_INT_ARRAY))
                    {
                        owner.setValue(mailbox.getUUID("Owner"));
                    }
                    Queue<ItemStack> queue = Mailbox.readQueueListTag(mailbox);
                    this.mailboxes.putIfAbsent(id, new Mailbox(id, levelKey, pos, owner, queue, new MutableBoolean(), this));
                }
                catch(Exception e)
                {
                    Constants.LOG.error("Failed to load a mailbox due to invalid data");
                }
            });
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        ListTag list = new ListTag();
        this.mailboxes.forEach((uuid, mailbox) ->
        {
            if(!mailbox.removed().booleanValue())
            {
                CompoundTag tag = new CompoundTag();
                tag.putUUID("UUID", uuid);
                tag.putString("Level", mailbox.levelKey().location().toString());
                tag.putLong("BlockPosition", mailbox.pos().asLong());
                Optional.ofNullable(mailbox.owner().getValue()).ifPresent(id -> tag.putUUID("Owner", id));
                mailbox.writeQueue(tag);
                list.add(tag);
            }
        });
        compound.put("Mailboxes", list);
        return compound;
    }

    /**
     *
     * @param levelKey
     * @return
     */
    @Nullable
    private static ResourceKey<Level> createLevelKey(String levelKey)
    {
        return levelKey.isBlank() ? null : switch(levelKey)
        {
            case "minecraft:overworld" -> Level.OVERWORLD;
            case "minecraft:the_nether" -> Level.NETHER;
            case "minecraft:the_end" -> Level.END;
            default -> ResourceKey.create(Registries.DIMENSION, new ResourceLocation(levelKey));
        };
    }
}
