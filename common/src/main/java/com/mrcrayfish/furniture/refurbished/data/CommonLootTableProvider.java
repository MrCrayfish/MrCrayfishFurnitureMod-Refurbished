package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.DoorMatBlock;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * Author: MrCrayfish
 */
public class CommonLootTableProvider
{
    public static class Block
    {
        public static void accept(LootBuilder.Block builder)
        {
            // TODO system to customise instead of dropping self
            Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
                net.minecraft.world.level.block.Block block = (net.minecraft.world.level.block.Block) entry.get();
                if(block instanceof DropWithName) {
                    builder.custom(block, createDropWithName(block));
                } else if(block instanceof DoorMatBlock) {
                    builder.custom(block, createDoorMatLootPool(block));
                } else {
                    builder.self(block);
                }
            });
        }

        private static LootPool.Builder createDoorMatLootPool(net.minecraft.world.level.block.Block block)
        {
            // TODO 1.20.6 check if correct
            return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                    .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                        .copy("Finalised", "BlockEntityTag.Finalised"))
                    .apply(CopyComponentsFunction.copyComponents(
                        CopyComponentsFunction.Source.BLOCK_ENTITY)
                        .include(ModDataComponents.PALETTE_IMAGE.get())));
        }

        private static LootPool.Builder createDropWithName(net.minecraft.world.level.block.Block block)
        {
            return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)));
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
