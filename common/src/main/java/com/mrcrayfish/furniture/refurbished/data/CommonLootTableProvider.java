package com.mrcrayfish.furniture.refurbished.data;

/**
 * Author: MrCrayfish
 */
public class CommonLootTableProvider
{
    public static class Block
    {
        public static void accept(LootBuilder.Block builder)
        {
            //builder.add(Blocks.OAK_PLANKS, LootTable.lootTable());
        }
    }

    public static class Entity
    {
        public static void accept(LootBuilder.Entity builder)
        {
            //builder.add(EntityType.ALLAY, LootTable.lootTable());
        }
    }
}
