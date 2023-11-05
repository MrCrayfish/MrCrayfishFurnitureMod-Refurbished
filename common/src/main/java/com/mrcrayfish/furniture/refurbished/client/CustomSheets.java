package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class CustomSheets
{
    public static final ResourceLocation TV_CHANNELS_SHEET = Utils.resource("textures/atlas/tv_channels.png");
    private static final Map<ResourceLocation, Material> TV_CHANNEL_MATERIALS = TelevisionBlockEntity.ALL_CHANNELS.stream()
            .collect(Collectors.toMap(TelevisionBlockEntity.Channel::id, channel -> createTelevisionChannelMaterial(channel.id())));

    private static Material createTelevisionChannelMaterial(ResourceLocation texture)
    {
        return new Material(TV_CHANNELS_SHEET, texture.withPrefix("tv_channels/"));
    }

    public static Material getTelevisionChannelMaterial(ResourceLocation id)
    {
        return TV_CHANNEL_MATERIALS.get(id);
    }
}
