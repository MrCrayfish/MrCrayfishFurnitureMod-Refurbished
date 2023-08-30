package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.config.ConfigProperty;
import com.mrcrayfish.framework.api.config.ConfigType;
import com.mrcrayfish.framework.api.config.FrameworkConfig;
import com.mrcrayfish.framework.api.config.IntProperty;

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
        @ConfigProperty(name = "mailbox", comment = "Mailbox related properties")
        public final Mailbox mailbox = new Mailbox();

        public static class Mailbox
        {
            @ConfigProperty(name = "inventoryRows", comment = """
                The amount of inventory rows in a mailbox. If you change this value from a larger to
                a smaller value, items contained in the removed rows will be deleted. Use caution
                when changing this value to avoid inconvenience and backup your saves.""")
            public final IntProperty inventoryRows = IntProperty.create(1, 1, 6);

            @ConfigProperty(name = "queueSize", comment = """
                The maximum amount of items that can be queued for delivery to a mailbox. Items become
                queued when the receiving mailbox's inventory is full. This is to prevent infinite storage.""")
            public final IntProperty queueSize = IntProperty.create(32, 0, 256);
        }
    }
}
