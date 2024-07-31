package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.item.TelevisionRemoteItem;
import com.mrcrayfish.furniture.refurbished.item.WrenchItem;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    public static final RegistryEntry<Item> SPATULA = RegistryEntry.item(Utils.resource("spatula"), () -> new SwordItem(Tiers.WOOD, new Item.Properties().durability(256)));
    public static final RegistryEntry<Item> KNIFE = RegistryEntry.item(Utils.resource("knife"), () -> new SwordItem(Tiers.STONE, new Item.Properties().durability(256)));
    public static final RegistryEntry<Item> PACKAGE = RegistryEntry.item(Utils.resource("package"), () -> new PackageItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> WRENCH = RegistryEntry.item(Utils.resource("wrench"), () -> new WrenchItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> BREAD_SLICE = RegistryEntry.item(Utils.resource("bread_slice"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).fast().build())));
    public static final RegistryEntry<Item> TOAST = RegistryEntry.item(Utils.resource("toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).fast().build())));
    public static final RegistryEntry<Item> SWEET_BERRY_JAM = RegistryEntry.item(Utils.resource("sweet_berry_jam"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SWEET_BERRY_JAM_TOAST = RegistryEntry.item(Utils.resource("sweet_berry_jam_toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build())));
    public static final RegistryEntry<Item> GLOW_BERRY_JAM = RegistryEntry.item(Utils.resource("glow_berry_jam"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> GLOW_BERRY_JAM_TOAST = RegistryEntry.item(Utils.resource("glow_berry_jam_toast"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build())));
    public static final RegistryEntry<Item> SEA_SALT = RegistryEntry.item(Utils.resource("sea_salt"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> WHEAT_FLOUR = RegistryEntry.item(Utils.resource("wheat_flour"), () -> new Item(new Item.Properties().craftRemainder(Items.BOWL)));
    public static final RegistryEntry<Item> DOUGH = RegistryEntry.item(Utils.resource("dough"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CHEESE = RegistryEntry.item(Utils.resource("cheese"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).build())));
    public static final RegistryEntry<Item> CHEESE_SANDWICH = RegistryEntry.item(Utils.resource("cheese_sandwich"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5F).build())));
    public static final RegistryEntry<Item> CHEESE_TOASTIE = RegistryEntry.item(Utils.resource("cheese_toastie"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.7F).build())));
    public static final RegistryEntry<Item> RAW_VEGETABLE_PIZZA = RegistryEntry.item(Utils.resource("raw_vegetable_pizza"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> COOKED_VEGETABLE_PIZZA = RegistryEntry.item(Utils.resource("cooked_vegetable_pizza"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> VEGETABLE_PIZZA_SLICE = RegistryEntry.item(Utils.resource("vegetable_pizza_slice"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).build())));
    public static final RegistryEntry<Item> RAW_MEATLOVERS_PIZZA = RegistryEntry.item(Utils.resource("raw_meatlovers_pizza"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> COOKED_MEATLOVERS_PIZZA = RegistryEntry.item(Utils.resource("cooked_meatlovers_pizza"), () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> MEATLOVERS_PIZZA_SLICE = RegistryEntry.item(Utils.resource("meatlovers_pizza_slice"), () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100), 1.0F).build())));
    public static final RegistryEntry<TelevisionRemoteItem> TELEVISION_REMOTE = RegistryEntry.item(Utils.resource("television_remote"), () -> new TelevisionRemoteItem(new Item.Properties().stacksTo(1)));
}
