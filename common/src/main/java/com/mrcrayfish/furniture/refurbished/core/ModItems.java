package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModItems
{
    public static final RegistryEntry<Item> SPATULA = RegistryEntry.item(Utils.resource("spatula"), () -> new SwordItem(Tiers.STONE, 2, -1.4F, new Item.Properties()));
}
