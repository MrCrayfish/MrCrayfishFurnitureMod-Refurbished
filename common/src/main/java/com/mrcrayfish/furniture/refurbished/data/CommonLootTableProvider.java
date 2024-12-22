package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.DoorMatBlock;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class CommonLootTableProvider extends LootTableProvider
{
    public CommonLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture)
    {
        super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(FurnitureBlock::new, LootContextParamSets.BLOCK)), completableFuture);
    }

    public static class FurnitureBlock extends BlockLootSubProvider
    {
        protected FurnitureBlock(HolderLookup.Provider provider)
        {
            super(Collections.emptySet(), FeatureFlagSet.of(), provider);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            this.map.forEach(consumer);
        }

        @Override
        public void generate()
        {
            Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
                Block block = (Block) entry.get();
                if(block instanceof DropWithName) {
                    this.add(block, this::createDropWithName);
                } else if(block instanceof DoorMatBlock) {
                    this.add(block, this::createDoorMatLootPool);
                } else {
                    this.dropSelf(block);
                }
            });
        }

        private LootTable.Builder createDoorMatLootPool(Block block)
        {
            return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(block)
                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                            .include(ModDataComponents.PALETTE_IMAGE.get())))));
        }

        private LootTable.Builder createDropWithName(Block block)
        {
            return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
        }
    }
}
