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
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModItems
{
    public static final RegistryEntry<FridgeItem> FRIDGE_LIGHT = RegistryEntry.item(Utils.id("light_fridge"), properties -> new FridgeItem(ModBlocks.FRIDGE_LIGHT.get(), ModBlocks.FREEZER_LIGHT.get(), properties), Item.Properties::new);
    public static final RegistryEntry<FridgeItem> FRIDGE_DARK = RegistryEntry.item(Utils.id("dark_fridge"), properties -> new FridgeItem(ModBlocks.FRIDGE_DARK.get(), ModBlocks.FREEZER_DARK.get(), properties), Item.Properties::new);
    public static final RegistryEntry<Item> SPATULA = RegistryEntry.item(Utils.id("spatula"), Item::new, () -> new Item.Properties().durability(256).sword(ToolMaterial.WOOD, 3.0F, -2.4F));
    public static final RegistryEntry<Item> KNIFE = RegistryEntry.item(Utils.id("knife"), Item::new, () -> new Item.Properties().durability(256).sword(ToolMaterial.STONE, 3.0F, -2.4F));
    public static final RegistryEntry<Item> PACKAGE = RegistryEntry.item(Utils.id("package"), PackageItem::new, () -> new Item.Properties().stacksTo(1));
    public static final RegistryEntry<Item> WRENCH = RegistryEntry.item(Utils.id("wrench"), WrenchItem::new, () -> new Item.Properties().stacksTo(1));
    public static final RegistryEntry<Item> BREAD_SLICE = RegistryEntry.item(Utils.id("bread_slice"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).build()));
    public static final RegistryEntry<Item> TOAST = RegistryEntry.item(Utils.id("toast"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build()));
    public static final RegistryEntry<Item> SWEET_BERRY_JAM = RegistryEntry.item(Utils.id("sweet_berry_jam"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> SWEET_BERRY_JAM_TOAST = RegistryEntry.item(Utils.id("sweet_berry_jam_toast"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build()));
    public static final RegistryEntry<Item> GLOW_BERRY_JAM = RegistryEntry.item(Utils.id("glow_berry_jam"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> GLOW_BERRY_JAM_TOAST = RegistryEntry.item(Utils.id("glow_berry_jam_toast"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build()));
    public static final RegistryEntry<Item> SEA_SALT = RegistryEntry.item(Utils.id("sea_salt"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> WHEAT_FLOUR = RegistryEntry.item(Utils.id("wheat_flour"), Item::new, () -> new Item.Properties().craftRemainder(Items.BOWL));
    public static final RegistryEntry<Item> DOUGH = RegistryEntry.item(Utils.id("dough"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> CHEESE = RegistryEntry.item(Utils.id("cheese"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).build()));
    public static final RegistryEntry<Item> CHEESE_SANDWICH = RegistryEntry.item(Utils.id("cheese_sandwich"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5F).build()));
    public static final RegistryEntry<Item> CHEESE_TOASTIE = RegistryEntry.item(Utils.id("cheese_toastie"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.7F).build()));
    public static final RegistryEntry<Item> RAW_VEGETABLE_PIZZA = RegistryEntry.item(Utils.id("raw_vegetable_pizza"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> COOKED_VEGETABLE_PIZZA = RegistryEntry.item(Utils.id("cooked_vegetable_pizza"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> VEGETABLE_PIZZA_SLICE = RegistryEntry.item(Utils.id("vegetable_pizza_slice"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).build()));
    public static final RegistryEntry<Item> RAW_MEATLOVERS_PIZZA = RegistryEntry.item(Utils.id("raw_meatlovers_pizza"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> COOKED_MEATLOVERS_PIZZA = RegistryEntry.item(Utils.id("cooked_meatlovers_pizza"), Item::new, Item.Properties::new);
    public static final RegistryEntry<Item> MEATLOVERS_PIZZA_SLICE = RegistryEntry.item(Utils.id("meatlovers_pizza_slice"), Item::new, () -> new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build(), Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 100), 1.0F)).build()));
    public static final RegistryEntry<TelevisionRemoteItem> TELEVISION_REMOTE = RegistryEntry.item(Utils.id("television_remote"), TelevisionRemoteItem::new, () -> new Item.Properties().stacksTo(1));
}
