package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedItem;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CommonItemModelProvider
{
    private final Consumer<PreparedItem> consumer;

    public CommonItemModelProvider(Consumer<PreparedItem> consumer)
    {
        this.consumer = consumer;
    }

    public void run()
    {
        this.fridge(ModItems.FRIDGE_LIGHT.get());
        this.fridge(ModItems.FRIDGE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_OAK_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_SPRUCE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_BIRCH_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_JUNGLE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_ACACIA_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_DARK_OAK_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_MANGROVE_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CHERRY_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_SPRUCE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_BIRCH_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_JUNGLE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_ACACIA_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_DARK_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_MANGROVE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CHERRY_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_DARK.get());
    }

    public void fridge(FridgeItem item)
    {
        MetalType type = item.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.TEXTURE, new ResourceLocation(Constants.MOD_ID, "block/" + type.getName() + "_fridge"));
        PreparedItem preparedItem = new PreparedItem(item);
        preparedItem.setModel(ModelTemplate.FRIDGE.itemModel(type).setTextures(textures));
        this.consumer.accept(preparedItem);
    }

    public void ceilingFan(CeilingFanBlock block)
    {
        WoodType woodType = block.getWoodType();
        MetalType metalType = block.getMetalType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.TEXTURE, new ResourceLocation(Constants.MOD_ID, "block/" + woodType.name() + "_" + metalType.getName() + "_ceiling_fan"));
        PreparedItem preparedItem = new PreparedItem(block.asItem());
        preparedItem.setModel(ModelTemplate.CEILING_FAN.itemModel(woodType, metalType).setTextures(textures));
        this.consumer.accept(preparedItem);
    }
}
