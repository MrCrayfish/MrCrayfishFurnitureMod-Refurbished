package com.mrcrayfish.furniture.refurbished.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class FurnitureLootTableProvider extends LootTableProvider
{
    public FurnitureLootTableProvider(PackOutput output)
    {
        super(output, Set.of(), List.of(new SubProviderEntry(Block::new, LootContextParamSets.BLOCK), new SubProviderEntry(Entity::new, LootContextParamSets.ENTITY)));
    }

    public static class Block extends BlockLootSubProvider
    {
        protected Block()
        {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate()
        {
            CommonLootTableProvider.Block.accept(new PlatformLootBuilder.Block(this::dropSelf, this::add));
        }
    }

    public static class Entity extends EntityLootSubProvider
    {
        protected Entity()
        {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate()
        {
            CommonLootTableProvider.Entity.accept(new PlatformLootBuilder.Entity(this::add));
        }
    }
}
