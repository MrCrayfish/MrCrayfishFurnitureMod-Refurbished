package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.Registration;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModCreativeTabs
{
    public static final RegistryEntry<CreativeModeTab> MAIN = RegistryEntry.creativeModeTab(Utils.resource("creative_tab"), builder -> {
        builder.icon(() -> new ItemStack(ModBlocks.TABLE_OAK.get()));
        builder.title(Component.translatable("itemGroup." + Constants.MOD_ID).withStyle(ChatFormatting.GOLD));
        builder.displayItems((params, output) -> {
            Set<Item> acceptedItems = new HashSet<>();
            Consumer<Item> add = item -> {
                if(item != Items.AIR && !acceptedItems.contains(item)) {
                    output.accept(item);
                    acceptedItems.add(item);
                }
            };
            Registration.get(Registries.BLOCK).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
                Block block = (Block) entry.get();
                add.accept(block.asItem());
            });
            Registration.get(Registries.ITEM).stream().filter(entry -> entry.getId().getNamespace().equals(Constants.MOD_ID)).forEach(entry -> {
                add.accept((Item) entry.get());
            });
        });
    });
}
