package com.mrcrayfish.furniture.refurbished.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class FurnitureLootTableProvider extends LootTableProvider
{
    public FurnitureLootTableProvider(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        return ImmutableList.of(Pair.of(Block::new, LootContextParamSets.BLOCK), Pair.of(Entity::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {}

    public static class Block extends BlockLoot
    {
        @Override
        protected void addTables()
        {
            CommonLootTableProvider.Block.accept(new PlatformLootBuilder.Block(this::dropSelf, (block, builder) -> {
                this.add(block, LootTable.lootTable().withPool(applyExplosionCondition(block, builder)));
            }));
        }

        @Override
        protected Iterable<net.minecraft.world.level.block.Block> getKnownBlocks()
        {
            return Registration.get(Registry.BLOCK_REGISTRY).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).map(entry -> (net.minecraft.world.level.block.Block) entry.get()).collect(Collectors.toList());
        }
    }

    public static class Entity extends EntityLoot
    {
        @Override
        protected void addTables()
        {
            CommonLootTableProvider.Entity.accept(new PlatformLootBuilder.Entity(this::add));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities()
        {
            return List.of();
        }
    }
}
