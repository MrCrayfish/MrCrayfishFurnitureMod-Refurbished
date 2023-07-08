package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * Author: MrCrayfish
 */
public class LootBuilder
{
    public interface Block
    {
        /**
         * Default loot table for blocks. It will just drop itself.
         *
         * @param block the target block for the loot table
         */
        void add(net.minecraft.world.level.block.Block block);

        /**
         * Adds a loot table to a block with full customisation using a builder.
         *
         * @param block the target block for the loot table
         */
        void add(net.minecraft.world.level.block.Block block, LootTable.Builder builder);
    }

    public interface Entity
    {
        /**
         * Adds a loot table to an entity with full customisation using a builder.
         *
         * @param type the entity type to target for the loot table
         */
        void add(EntityType<?> type, LootTable.Builder builder);
    }
}
