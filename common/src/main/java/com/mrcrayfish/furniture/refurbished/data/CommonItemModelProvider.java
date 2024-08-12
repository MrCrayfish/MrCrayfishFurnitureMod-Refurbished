package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.block.ColouredBathBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.block.WoodenBathBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedItem;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
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
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_LIGHT.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_SPRUCE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_BIRCH_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_JUNGLE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_ACACIA_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_DARK_OAK_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_MANGROVE_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_CRIMSON_DARK.get());
        this.ceilingFan(ModBlocks.CEILING_FAN_WARPED_DARK.get());
        this.woodenBath(ModBlocks.BATH_OAK.get());
        this.woodenBath(ModBlocks.BATH_SPRUCE.get());
        this.woodenBath(ModBlocks.BATH_BIRCH.get());
        this.woodenBath(ModBlocks.BATH_JUNGLE.get());
        this.woodenBath(ModBlocks.BATH_ACACIA.get());
        this.woodenBath(ModBlocks.BATH_DARK_OAK.get());
        this.woodenBath(ModBlocks.BATH_MANGROVE.get());
        this.woodenBath(ModBlocks.BATH_CRIMSON.get());
        this.woodenBath(ModBlocks.BATH_WARPED.get());
        this.colouredBath(ModBlocks.BATH_WHITE.get());
        this.colouredBath(ModBlocks.BATH_ORANGE.get());
        this.colouredBath(ModBlocks.BATH_MAGENTA.get());
        this.colouredBath(ModBlocks.BATH_LIGHT_BLUE.get());
        this.colouredBath(ModBlocks.BATH_YELLOW.get());
        this.colouredBath(ModBlocks.BATH_LIME.get());
        this.colouredBath(ModBlocks.BATH_PINK.get());
        this.colouredBath(ModBlocks.BATH_GRAY.get());
        this.colouredBath(ModBlocks.BATH_LIGHT_GRAY.get());
        this.colouredBath(ModBlocks.BATH_CYAN.get());
        this.colouredBath(ModBlocks.BATH_PURPLE.get());
        this.colouredBath(ModBlocks.BATH_BLUE.get());
        this.colouredBath(ModBlocks.BATH_BROWN.get());
        this.colouredBath(ModBlocks.BATH_GREEN.get());
        this.colouredBath(ModBlocks.BATH_RED.get());
        this.colouredBath(ModBlocks.BATH_BLACK.get());
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
    
    public void woodenBath(WoodenBathBlock block)
    {
        WoodType woodType = block.getWoodType();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.TEXTURE, new ResourceLocation(Constants.MOD_ID, "block/" + woodType.name() + "_bath"));
        PreparedItem preparedItem = new PreparedItem(block.asItem());
        preparedItem.setModel(ModelTemplate.BATH.itemModel(woodType).setTextures(textures));
        this.consumer.accept(preparedItem);
    }

    public void colouredBath(ColouredBathBlock block)
    {
        DyeColor color = block.getDyeColor();
        TextureMapping textures = new TextureMapping();
        textures.put(TextureSlot.TEXTURE, new ResourceLocation(Constants.MOD_ID, "block/" + color.getName() + "_bath"));
        PreparedItem preparedItem = new PreparedItem(block.asItem());
        preparedItem.setModel(ModelTemplate.BATH.itemModel(color).setTextures(textures));
        this.consumer.accept(preparedItem);
    }
}
