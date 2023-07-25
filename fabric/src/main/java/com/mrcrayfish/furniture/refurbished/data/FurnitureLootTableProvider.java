package com.mrcrayfish.furniture.refurbished.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class FurnitureLootTableProvider
{
    public static void addProviders(FabricDataGenerator.Pack pack)
    {
        pack.addProvider(Block::new);
        pack.addProvider(Entity::new);
    }

    public static class Block extends FabricBlockLootTableProvider
    {
        public Block(FabricDataOutput output)
        {
            super(output);
        }

        @Override
        public void generate()
        {
            CommonLootTableProvider.Block.accept(new PlatformLootBuilder.Block(this));
        }
    }

    public static class Entity extends SimpleFabricLootTableProvider
    {
        public Entity(FabricDataOutput output)
        {
            super(output, LootContextParamSets.ENTITY);
        }

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            CommonLootTableProvider.Entity.accept(new PlatformLootBuilder.Entity(consumer));
        }
    }
}
