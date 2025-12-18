package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.blockentity.TelevisionBlockEntity;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class CustomSheets
{
    public static final Identifier TV_CHANNELS_SHEET = Utils.resource("textures/atlas/tv_channels.png");
    public static final MaterialMapper TV_CHANNEL_MAPPER = new MaterialMapper(TV_CHANNELS_SHEET, "tv_channels");
    private static final Map<Identifier, Material> TV_CHANNEL_MATERIALS = TelevisionBlockEntity.ALL_CHANNELS.stream()
            .collect(Collectors.toMap(TelevisionBlockEntity.Channel::id, channel -> TV_CHANNEL_MAPPER.apply(channel.id())));

    public static Material getTelevisionChannelMaterial(Identifier id)
    {
        return TV_CHANNEL_MATERIALS.get(id);
    }
}
