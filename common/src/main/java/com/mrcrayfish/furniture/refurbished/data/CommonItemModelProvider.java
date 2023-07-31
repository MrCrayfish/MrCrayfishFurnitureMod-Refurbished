package com.mrcrayfish.furniture.refurbished.data;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.model.ModelTemplate;
import com.mrcrayfish.furniture.refurbished.data.model.PreparedItem;
import com.mrcrayfish.furniture.refurbished.item.FridgeItem;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

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
}
