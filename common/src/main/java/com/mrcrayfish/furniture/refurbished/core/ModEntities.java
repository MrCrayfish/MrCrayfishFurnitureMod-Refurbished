package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModEntities
{
    public static final RegistryEntry<EntityType<Seat>> SEAT = RegistryEntry.entityType(Utils.resource("seat"), () -> EntityType.Builder.<Seat>of((entityType, level) -> new Seat(level), MobCategory.MISC).sized(0, 0).build("seat"));
}
