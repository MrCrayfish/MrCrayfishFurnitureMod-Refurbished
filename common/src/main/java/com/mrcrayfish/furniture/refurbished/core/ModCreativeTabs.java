package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

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
           output.accept(Blocks.OAK_PLANKS);
        });
    });
}
