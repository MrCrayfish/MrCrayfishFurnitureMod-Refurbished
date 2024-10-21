package com.mrcrayfish.furniture.refurbished.mail;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.blockentity.MailboxBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.ClientMailbox;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO require ender pearl to send items

/**
 * Author: MrCrayfish
 */
public class DeliveryService extends SavedData
{
    private static final String STORAGE_ID = "refurbished_furniture_delivery_service";

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
    private final Map<Pair<ResourceLocation, BlockPos>, Mailbox> locator = new HashMap<>();
    private final Map<UUID, Mailbox> mailboxes = new ConcurrentHashMap<>();
    private final Queue<Mailbox> removal = new ArrayDeque<>();
    private final Map<UUID, Pair<ResourceLocation, BlockPos>> pendingNames = new HashMap<>();

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
     * @return The instance of the current minecraft server
     */
    public MinecraftServer getServer()
    {
        return this.server;
    }

    /**
     * Called every tick on the logical server
     */
    public void serverTick()
    {
        // Checks for mailboxes that need to be removed and spawn their queue into the level
        while(!this.removal.isEmpty())
        {
            Mailbox mailbox = this.removal.poll();
            mailbox.spawnQueueIntoLevel();
            this.mailboxes.remove(mailbox.id());
            this.locator.remove(Pair.of(mailbox.levelKey().location(), mailbox.pos()));
            this.setDirty();
        }

        // Try to deliver mail from queues to the block entity in the level
        this.mailboxes.forEach((uuid, mailbox) -> mailbox.tick());
    }

    /**
     * Adds an ItemStack to the queue of the mailbox matching the given id.
     *
     * @param id the identifier of the mailbox
     * @param stack the ItemStack to send
     * @return An optional string. If an error occurs, the optional will contain a translation key
     */
    public DeliveryResult sendMail(UUID id, ItemStack stack)
    {
        Mailbox mailbox = this.mailboxes.get(id);

        // Check if the mailbox exists
        if(mailbox == null)
            return DeliveryResult.createFail(Utils.translationKey("gui", "delivery_service.unknown_mailbox"));

        // Check if the queue is not full
        if(mailbox.queue().size() >= Config.SERVER.mailing.deliveryQueueSize.get())
            return DeliveryResult.createFail(Utils.translationKey("gui", "delivery_service.mailbox_queue_full"));

        // Check if mailbox is in a deliverable dimension
        if(!isDeliverableDimension(mailbox.levelKey()))
            return DeliveryResult.createFail(Utils.translationKey("gui", "delivery_service.undeliverable_dimension"));

        // Push to queue and mark as dirty to ensure it's saved
        mailbox.queue().offer(stack);
        this.setDirty();

        // Empty optional means successful
        return DeliveryResult.createSuccess(Utils.translationKey("gui", "delivery_service.package_sent"));
    }

    /**
     * Marks the given mailbox for removal
     *
     * @param mailbox the mailbox to remove
     */
    void removeMailbox(Mailbox mailbox)
    {
        this.removal.offer(mailbox);
    }

    /**
     * Determines if the given player can create/place a mailbox. Mailboxes are limited per player,
     * as specified by a maximum count in the config.
     *
     * @param player the player to test
     * @return True if the player can place a mailbox
     */
    public boolean canCreateMailbox(Player player)
    {
        long count = this.mailboxes.values().stream()
                .filter(box -> player.getUUID().equals(box.owner().getValue()))
                .count();
        return Config.SERVER.mailing.maxMailboxesPerPlayer.get() > count;
    }

    /**
     * Gets an existing or creates a new mailbox for the given mailbox block entity. This method is
     * responsible for registering mailboxes into the delivery system and is called when a player
     * placing a new mailbox block. The mailbox is initially unclaimed but is immediately claimed
     * by the placing player.
     *
     * @param blockEntity the mailbox block entity
     * @return A non-null {@link Mailbox} instance
     */
    public Mailbox getOrCreateMailBox(MailboxBlockEntity blockEntity)
    {
        this.duplicateIdCheck(blockEntity);
        return this.mailboxes.computeIfAbsent(blockEntity.getId(), uuid -> {
            ResourceKey<Level> levelKey = blockEntity.getLevel().dimension();
            BlockPos pos = blockEntity.getBlockPos();
            Mailbox mailbox = new Mailbox(uuid, levelKey, pos, new MutableObject<>(), new MutableObject<>(""), new ArrayDeque<>(), new MutableBoolean(), this);
            this.locator.put(Pair.of(levelKey.location(), pos), mailbox);
            this.setDirty();
            return mailbox;
        });
    }

    /**
     * Regenerates the id of the mailbox block entity if another mailbox with that id already exists.
     * A collision with a random UUID is very unlikely, however a player can pick a block with NBT
     * and place down a mailbox with the same UUID. Naturally, regenerating the UUID won't affect
     * the mailbox that already exists with the UUID that collided with the given blockEntity arg.
     *
     * @param blockEntity the mailbox block entity to check
     */
    private void duplicateIdCheck(MailboxBlockEntity blockEntity)
    {
        Mailbox box = this.mailboxes.get(blockEntity.getId());
        if(box != null && !box.pos().equals(blockEntity.getBlockPos()))
        {
            blockEntity.regenerateId();
        }
    }

    /**
     * Gets the mailbox at the given block position in the level. If no mailbox exists,
     * an empty optional will be returned.
     *
     * @param level the level where the mailbox exists
     * @param pos the block position of the mailbox
     * @return an optional mailbox
     */
    public Optional<Mailbox> getMailboxAtPosition(Level level, BlockPos pos)
    {
        return Optional.ofNullable(this.locator.get(Pair.of(level.dimension().location(), pos)));
    }

    /**
     * Marks a mailbox as expecting to be renamed in the future. As mailboxes are designed to only
     * be named when initially placing them down, we don't want to allow the ability to rename the
     * mailbox again. To prevent this, the player and the location of the mailbox is recorded upon
     * placing a mailbox as a valid mailbox that can be renamed.
     *
     * @param player the player who placed the mailbox
     * @param level  the level that contains the mailbox
     * @param pos    the block position of the mailbox
     */
    public void markMailboxAsPendingName(Player player, Level level, BlockPos pos)
    {
        this.pendingNames.put(player.getUUID(), Pair.of(level.dimension().location(), pos));
    }

    /**
     * Renames the mailbox at the given position with the custom name. This method will
     * not rename the mailbox if any of these conditions are true, the player is not the owner
     * of the mailbox, the level/pos combination doesn't link to a mailbox in the level, or
     * the mailbox is not expecting to be renamed. The custom name can also not be longer
     * than 32 characters.
     *
     * @param player     the player owner of the mailbox
     * @param level      the level the mailbox is located
     * @param pos        the block position of the mailbox
     * @param customName the new name for the mailbox
     * @return True if the mailbox was successfully renamed
     */
    public boolean renameMailbox(Player player, Level level, BlockPos pos, String customName)
    {
        Pair<ResourceLocation, BlockPos> pendingLocation = this.pendingNames.remove(player.getUUID());
        return this.getMailboxAtPosition(level, pos).map(mailbox -> {
            if(!Objects.equals(mailbox.owner().getValue(), player.getUUID()))
                return false;
            Pair<ResourceLocation, BlockPos> location = Pair.of(level.dimension().location(), pos);
            return Objects.equals(location, pendingLocation) && mailbox.rename(customName);
        }).orElse(false);
    }

    /**
     * Encodes the mailboxes to a FriendlyByteBuf
     */
    public void encodeMailboxes(FriendlyByteBuf buf)
    {
        buf.writeCollection(this.mailboxes.values(), (buf1, mailbox) -> {
            buf1.writeUUID(mailbox.getId());
            buf1.writeOptional(mailbox.getOwner(), (buf2, profile) -> {
                buf2.writeUUID(profile.getId());
                buf2.writeOptional(Optional.ofNullable(profile.getName()), FriendlyByteBuf::writeUtf);
            });
            buf1.writeOptional(mailbox.getCustomName(), FriendlyByteBuf::writeUtf);
        });
    }

    /**
     * Decodes the mailboxes from a FriendlyByteBuf. The list returned is immutable and the mailboxes
     * are simply a read only view of the mailboxes from the server.
     *
     * @param buf the FriendlyByteBuf to read from
     * @return an immutable list of mailboxes
     */
    public static List<IMailbox> decodeMailboxes(FriendlyByteBuf buf)
    {
        List<IMailbox> list = buf.readList(buf1 -> {
            UUID mailboxId = buf1.readUUID();
            Optional<GameProfile> profile = buf1.readOptional(buf2 -> {
                UUID playerId = buf2.readUUID();
                Optional<String> name = buf2.readOptional(FriendlyByteBuf::readUtf);
                return new GameProfile(playerId, name.orElse("Unknown"));
            });
            Optional<String> mailboxName = buf1.readOptional(FriendlyByteBuf::readUtf);
            return new ClientMailbox(mailboxId, profile, mailboxName);
        });
        return ImmutableList.copyOf(list);
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
                    CompoundTag mailboxTag = (CompoundTag) tag;
                    ResourceKey<Level> levelKey = createLevelKey(mailboxTag.getString("Level"));
                    if(levelKey == null)
                    {
                        Constants.LOG.error("Failed to load a mailbox due to invalid dimension");
                        return;
                    }
                    UUID id = mailboxTag.getUUID("UUID");
                    BlockPos pos = BlockPos.of(mailboxTag.getLong("BlockPosition"));
                    MutableObject<UUID> owner = new MutableObject<>();
                    if(mailboxTag.contains("Owner", Tag.TAG_INT_ARRAY))
                    {
                        owner.setValue(mailboxTag.getUUID("Owner"));
                    }
                    String customName = mailboxTag.getString("CustomName");
                    customName = customName.substring(0, Math.min(customName.length(), 32));
                    Queue<ItemStack> queue = Mailbox.readQueueListTag(mailboxTag);
                    Mailbox mailbox = new Mailbox(id, levelKey, pos, owner, new MutableObject<>(customName), queue, new MutableBoolean(), this);
                    this.mailboxes.putIfAbsent(id, mailbox);
                    this.locator.put(Pair.of(levelKey.location(), pos), mailbox);
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
                CompoundTag mailboxTag = new CompoundTag();
                mailboxTag.putUUID("UUID", uuid);
                mailboxTag.putString("Level", mailbox.levelKey().location().toString());
                mailboxTag.putLong("BlockPosition", mailbox.pos().asLong());
                Optional.ofNullable(mailbox.owner().getValue()).ifPresent(id -> mailboxTag.putUUID("Owner", id));
                Optional.ofNullable(mailbox.customName().getValue()).ifPresent(name -> mailboxTag.putString("CustomName", name));
                mailbox.writeQueue(mailboxTag);
                list.add(mailboxTag);
            }
        });
        compound.put("Mailboxes", list);
        return compound;
    }

    /**
     * Creates a ResourceKey for a level with the given key. This method checks for vanilla keys
     * since a reference for them already exists.
     *
     * @param levelKey a resource location (as a string) of the level key
     * @return A resource key of the level or null if the key was invalid
     */
    @Nullable
    private static ResourceKey<Level> createLevelKey(String levelKey)
    {
        return levelKey.isBlank() ? null : switch(levelKey)
        {
            case "minecraft:overworld" -> Level.OVERWORLD;
            case "minecraft:the_nether" -> Level.NETHER;
            case "minecraft:the_end" -> Level.END;
            default -> ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(levelKey));
        };
    }

    /**
     * Determines if the given ItemStack is an item that can't be sent through mail. By default,
     * items that have inventory are banned (such as Shulker Boxes) unless explicitly disabled in
     * mod's configuration. An item is also blocked if the item id is contained in the banned items
     * list, which again is defined in the mod's configuration. Banned items are mainly to prevent
     * creating large NBT on a single item, which can affect servers/world saves.
     * <p>
     * This method can only be called while in a game/server since it depends on a configuration
     * sent from the server.
     *
     * @param stack the ItemStack to check if it is banned
     * @return True if the ItemStack is banned
     */
    public static boolean isBannedItem(ItemStack stack)
    {
        // Check if the item can fit inside container items
        if(Config.SERVER.mailing.banSendingItemsWithInventories.get() && !stack.getItem().canFitInsideContainerItems())
        {
            return true;
        }

        // Check if the item is not on the banned item list
        String name = stack.getItem().getDescriptionId();
        return Config.SERVER.mailing.bannedItems.get().contains(name);
    }

    /**
     * Determines if the given level allows mailboxes to be placed
     *
     * @param level the level to test
     * @return True if mailboxes are allowed to be placed
     */
    public static boolean isDeliverableDimension(Level level)
    {
        return isDeliverableDimension(level.dimension());
    }

    /**
     * Determines if the given level allows mailboxes to be placed
     *
     * @param key the level key to test
     * @return True if mailboxes are allowed to be placed
     */
    public static boolean isDeliverableDimension(ResourceKey<Level> key)
    {
        List<String> validDimensions = Config.SERVER.mailing.allowedDimensions.get();
        if(!validDimensions.isEmpty())
        {
            return validDimensions.contains(key.location().toString());
        }
        return true;
    }
}
