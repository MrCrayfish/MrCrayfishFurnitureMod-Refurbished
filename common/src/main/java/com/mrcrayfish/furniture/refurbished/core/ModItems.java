package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.item.TelevisionRemoteItem;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.food.FoodProperties;
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
    public static final RegistryEntry<Item> PACKAGE = RegistryEntry.item(Utils.resource("package"), () -> new PackageItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> WRENCH = RegistryEntry.item(Utils.resource("wrench"), () -> new WrenchItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> BREAD_SLICE = RegistryEntry.item(Utils.resource("bread_slice"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).fast().build())));
    public static final RegistryEntry<Item> TOAST = RegistryEntry.item(Utils.resource("toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).fast().build())));
    public static final RegistryEntry<Item> SWEET_BERRY_JAM = RegistryEntry.item(Utils.resource("sweet_berry_jam"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SWEET_BERRY_JAM_TOAST = RegistryEntry.item(Utils.resource("sweet_berry_jam_toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build())));
    public static final RegistryEntry<Item> GLOW_BERRY_JAM = RegistryEntry.item(Utils.resource("glow_berry_jam"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> GLOW_BERRY_JAM_TOAST = RegistryEntry.item(Utils.resource("glow_berry_jam_toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).build())));
    public static final RegistryEntry<TelevisionRemoteItem> TELEVISION_REMOTE = RegistryEntry.item(Utils.resource("television_remote"), () -> new TelevisionRemoteItem(new Item.Properties().stacksTo(1)));
}
