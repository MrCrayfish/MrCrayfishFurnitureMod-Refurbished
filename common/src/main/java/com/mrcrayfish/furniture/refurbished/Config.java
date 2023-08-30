package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.config.BoolProperty;
import com.mrcrayfish.framework.api.config.ConfigProperty;
import com.mrcrayfish.framework.api.config.ConfigType;
import com.mrcrayfish.framework.api.config.FrameworkConfig;
import com.mrcrayfish.framework.api.config.IntProperty;
import com.mrcrayfish.framework.api.config.ListProperty;

/**
 * Author: MrCrayfish
 */
public class Config
{
    @FrameworkConfig(id = Constants.MOD_ID, name = "client", type = ConfigType.CLIENT)
    public static final Client CLIENT = new Client();

    @FrameworkConfig(id = Constants.MOD_ID, name = "server", type = ConfigType.SERVER_SYNC)
    public static final Server SERVER = new Server();

    public static class Client
    {

    }

    public static class Server
    {
        @ConfigProperty(name = "mailing", comment = "Mailing related properties")
        public final Mailing mailing = new Mailing();

        public static class Mailing
        {
            @ConfigProperty(name = "mailboxInventoryRows", comment = """
                The amount of inventory rows in a mailbox. If you change this value from a larger to
                a smaller value, items contained in the removed rows will be deleted. Use caution
                when changing this value to avoid inconvenience and backup your saves.""")
            public final IntProperty mailboxInventoryRows = IntProperty.create(1, 1, 6);

            @ConfigProperty(name = "deliveryQueueSize", comment = """
                The maximum amount of items that can be queued for delivery to a mailbox. Items become
                queued when the receiving mailbox's inventory is full. This is to prevent infinite storage.""")
            public final IntProperty deliveryQueueSize = IntProperty.create(32, 0, 256);

            @ConfigProperty(name = "banSendingItemsWithInventories", comment = """
                If enabled, this will ban items with an inventory (like a Shulker Box) being sent through
                a Post Box. This prevents players from creating massive NBT on a single item, which can
                cause issues for your server/world save.""")
            public final BoolProperty banSendingItemsWithInventories = BoolProperty.create(true);

            @ConfigProperty(name = "bannedItems", comment = """
                Prevents items contained in this list from being sent through a Post Box.
                An example of how the list is defined:
                bannedItems = [
                    "minecraft:water_bucket",
                    "minecraft:diamond",
                    "refurbished_furniture:mailbox"
                    ...
                ]
                ^ Note: This is just an example. Write your list below.""")
            public final ListProperty<String> bannedItems = ListProperty.create(ListProperty.STRING);
        }
    }
}
