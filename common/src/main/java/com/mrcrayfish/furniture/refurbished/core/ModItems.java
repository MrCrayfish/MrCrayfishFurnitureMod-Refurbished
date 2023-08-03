package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
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
    public static final RegistryEntry<FridgeItem> FRIDGE_LIGHT = RegistryEntry.item(Utils.resource("light_fridge"), () -> new FridgeItem(ModBlocks.FRIDGE_LIGHT.get(), ModBlocks.FREEZER_LIGHT.get(), new Item.Properties()));
    public static final RegistryEntry<FridgeItem> FRIDGE_DARK = RegistryEntry.item(Utils.resource("dark_fridge"), () -> new FridgeItem(ModBlocks.FRIDGE_DARK.get(), ModBlocks.FREEZER_DARK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> SPATULA = RegistryEntry.item(Utils.resource("spatula"), () -> new SwordItem(Tiers.STONE, 2, -1.4F, new Item.Properties().durability(256)));
    public static final RegistryEntry<Item> KNIFE = RegistryEntry.item(Utils.resource("knife"), () -> new SwordItem(Tiers.IRON, 3, -1.4F, new Item.Properties().durability(256)));
}
