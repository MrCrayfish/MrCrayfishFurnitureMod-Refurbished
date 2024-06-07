package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.config.BoolProperty;
import com.mrcrayfish.framework.api.config.ConfigProperty;
import com.mrcrayfish.framework.api.config.ConfigType;
import com.mrcrayfish.framework.api.config.DoubleProperty;
import com.mrcrayfish.framework.api.config.FrameworkConfig;
import com.mrcrayfish.framework.api.config.IntProperty;
import com.mrcrayfish.framework.api.config.ListProperty;
import com.mrcrayfish.framework.api.config.LongProperty;
import com.mrcrayfish.furniture.refurbished.platform.Services;

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
        @ConfigProperty(name = "doorbellNotification", comment = """
            If enabled, displays a toast notification when another player rings one of your doorbells.""")
        public final BoolProperty doorbellNotification = BoolProperty.create(true);

        @ConfigProperty(name = "electricityViewDistance", comment = """
            The maximum distance that electricity nodes and connections can be seen by the camera.""")
        public final IntProperty electricityViewDistance = IntProperty.create(48, 1, 128);

        @ConfigProperty(name = "showCuttingBoardHelper", comment = """
            If enabled, shows an overlay on the HUD to help with cutting board slicing and combining recipes.
            Note: This is an experimental feature.""")
        public final BoolProperty showCuttingBoardHelper = BoolProperty.create(false);
    }

    public static class Server
    {
        @ConfigProperty(name = "mailing", comment = "Mailing related properties")
        public final Mailing mailing = new Mailing();

        @ConfigProperty(name = "electricity", comment = "Electricity related properties")
        public final Electricity electricity = new Electricity();

        @ConfigProperty(name = "recycleBin", comment = "Recycle Bin related properties")
        public final RecycleBin recycleBin = new RecycleBin();

        @ConfigProperty(name = "trampoline", comment = "Trampoline related properties")
        public final Trampoline trampoline = new Trampoline();

        @ConfigProperty(name = "kitchenSink", comment = "Kitchen Sink related properties")
        public final FluidStorage kitchenSink = new FluidStorage(Services.FLUID.getBucketCapacity() * 3);

        @ConfigProperty(name = "basin", comment = "Basin related properties")
        public final FluidStorage basin = new FluidStorage(Services.FLUID.getBucketCapacity());

        @ConfigProperty(name = "bath", comment = "Bath related properties")
        public final FluidStorage bath = new FluidStorage(Services.FLUID.getBucketCapacity() * 10);

        @ConfigProperty(name = "toilet", comment = "Toilet related properties")
        public final FluidStorage toilet = new FluidStorage(Services.FLUID.getBucketCapacity());

        public static class Mailing
        {
            @ConfigProperty(name = "maxMailboxesPerPlayer", comment = """
                The maximum amount of mailboxes a player is allowed to register/own.""")
            public final IntProperty maxMailboxesPerPlayer = IntProperty.create(16, 1, Integer.MAX_VALUE);

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

        public static class Electricity
        {
            @ConfigProperty(name = "maximumDaisyChain", comment = """
                The maximum daisy chain that electricity can travel from a power source. A daisy chain
                is electricity nodes connected one after the other to one or more other electricity
                nodes. The larger this value is, the more processing time is required for updating
                the power in an electricity network.""")
            public final IntProperty maximumDaisyChain = IntProperty.create(8, 1, 32);

            @ConfigProperty(name = "maximumLinkLength", comment = """
                The maximum distance from one electricity node to another that a link can be made.
                If this value is increased to a large value, it may result in an electricity node
                being in an unloaded chunk. Electricity nodes in unloaded chunks won't be updated
                even if the power does reach according to the maximumDaisyChain property.""")
            public final DoubleProperty maximumLinkLength = DoubleProperty.create(16, 1, 64);

            @ConfigProperty(name = "maximumLinksPerElectricityNode", comment = """
                The maximum amount of links that can be connected to a single electricity node.""")
            public final IntProperty maximumLinksPerElectricityNode = IntProperty.create(6, 1, 64);

            @ConfigProperty(name = "maximumElectricityGeneratorLinkLength", comment = """
                The maximum length of a link from an electricity generator to an electricity node.
                If this value is increased to a large value, it may result in an electricity node
                being in an unloaded chunk. Electricity nodes in unloaded chunks won't be updated
                even if the power does reach according to the maximumDaisyChain property.""")
            public final DoubleProperty maximumElectricityGeneratorLinkLength = DoubleProperty.create(64, 1, 256);

            @ConfigProperty(name = "maximumLinksPerElectricityGenerator", comment = """
                The maximum amount of links that can be connected to an Electricity Generator.""")
            public final IntProperty maximumLinksPerElectricityGenerator = IntProperty.create(12, 1, 64);

            @ConfigProperty(name = "fuelToPowerRatio", comment = """
                The amount of power that is generated by the electricity generator per fuel tick.
                For example, coal has 1600 ticks of fuel, and at a ratio of 4, it would produce
                6400 units of power. The default value of 8 means that the electricity generator
                would consume almost 2 coal per in-game day.""")
            public final IntProperty fuelToPowerRatio = IntProperty.create(16, 1, 128);

            @ConfigProperty(name = "maximumNodesInNetwork", comment = """
                The maximum amount of nodes in a network that can be powered by an electricity
                source, like the electricity generator.""")
            public final IntProperty maximumNodesInNetwork = IntProperty.create(64, 1, 512);
        }

        public static class RecycleBin
        {
            @ConfigProperty(name = "processingTime", comment = """
                The amount of time in ticks that it takes to perform one cycle of recycling""")
            public final IntProperty processingTime = IntProperty.create(10, 1, Integer.MAX_VALUE);

            @ConfigProperty(name = "experiencePerItem", comment = """
                The amount of experience to add when recycling an item.""")
            public final DoubleProperty experiencePerItem = DoubleProperty.create(0.05, 0, Double.MAX_VALUE);

            @ConfigProperty(name = "maximumExperienceLevels", comment = """
                The maximum amount of experience levels that the recycling bin can hold. Recycling stops once it reaches that level.""")
            public final IntProperty maximumExperienceLevels = IntProperty.create(5, 1, 100);
        }

        public static class Trampoline
        {
            @ConfigProperty(name = "maxBounceHeight", comment = """
                The maximum height that can be achieved from bouncing on the trampoline. The maximum
                height is affected the shape of the trampoline, the center trampoline in a 3x3 will
                be able to reach the maximum bounce height, while a single trampoline will only reach
                half.""")
            public final DoubleProperty maxBounceHeight = DoubleProperty.create(8.0, 0.0, 64.0);
        }

        public static class FluidStorage
        {
            @ConfigProperty(name = "fluidCapacity", comment = """
                The storage capacity of the fluid tank contained in this block. If you're using Forge,
                1000 units represents the capacity of a bucket, while it's 81000 on Fabric. So if you
                want 3 buckets worth of storage, it will be 3000 (Forge) or 243000 (Fabric)""")
            public final LongProperty fluidCapacity;

            @ConfigProperty(name = "dispenseWater", comment = """
                If enabled, when interacting with this block it will dispense free water into
                it's fluid tank, assuming the tank is empty or contains water but has not reached it's
                capacity.""")
            public final BoolProperty dispenseWater = BoolProperty.create(true);

            public FluidStorage(long capacity)
            {
                this.fluidCapacity = LongProperty.create(capacity, 1, Long.MAX_VALUE);
            }
        }
    }
}
