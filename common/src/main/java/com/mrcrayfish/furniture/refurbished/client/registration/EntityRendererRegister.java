package com.mrcrayfish.furniture.refurbished.client.registration;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

/**
 * Author: MrCrayfish
 */
@FunctionalInterface
public interface EntityRendererRegister
{
    <E extends Entity> void apply(EntityType<? extends E> entityType, EntityRendererProvider<E> entityRendererFactory);
}
