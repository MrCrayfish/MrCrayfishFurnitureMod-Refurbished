package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class PlatformLootBuilder
{
    public static class Block implements LootBuilder.Block
    {
        private final Consumer<net.minecraft.world.level.block.Block> dropSelf;
        private final BiConsumer<net.minecraft.world.level.block.Block, LootTable.Builder> add;

        public Block(Consumer<net.minecraft.world.level.block.Block> dropSelf, BiConsumer<net.minecraft.world.level.block.Block, LootTable.Builder> add)
        {
            this.dropSelf = dropSelf;
            this.add = add;
        }

        @Override
        public void add(net.minecraft.world.level.block.Block block)
        {
            this.dropSelf.accept(block);
        }

        @Override
        public void add(net.minecraft.world.level.block.Block block, LootTable.Builder builder)
        {
            this.add.accept(block, builder);
        }
    }

    public static class Entity implements LootBuilder.Entity
    {
        private final BiConsumer<EntityType<?>, LootTable.Builder> add;

        public Entity(BiConsumer<EntityType<?>, LootTable.Builder> add)
        {
            this.add = add;
        }

        @Override
        public void add(EntityType<?> type, LootTable.Builder builder)
        {
            this.add.accept(type, builder);
        }
    }
}
