package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class PlatformLootBuilder
{
    public static class Block implements LootBuilder.Block
    {
        private final BlockLootSubProvider provider;

        public Block(BlockLootSubProvider provider)
        {
            this.provider = provider;
        }

        @Override
        public void self(net.minecraft.world.level.block.Block block)
        {
            this.provider.dropSelf(block);
        }

        @Override
        public void custom(net.minecraft.world.level.block.Block block, LootPool.Builder builder)
        {
            this.provider.add(block, LootTable.lootTable().withPool(this.provider.applyExplosionCondition(block, builder)));
        }
    }

    public static class Entity implements LootBuilder.Entity
    {
        private final BiConsumer<ResourceLocation, LootTable.Builder> consumer;

        public Entity(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            this.consumer = consumer;
        }

        @Override
        public void add(EntityType<?> type, LootTable.Builder builder)
        {
            this.consumer.accept(EntityType.getKey(type), builder);
        }
    }
}
