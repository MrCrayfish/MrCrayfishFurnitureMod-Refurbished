package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.core.registries.Registries;

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
                builder.add((net.minecraft.world.level.block.Block) entry.get());
            });
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
