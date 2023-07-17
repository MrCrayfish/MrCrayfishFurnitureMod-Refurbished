package com.mrcrayfish.furniture.refurbished.data.model;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ModelTemplate
{
    private static final Set<ResourceLocation> MODELS = new HashSet<>();
    public static final ModelTemplate TABLE = block("table", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH = block("table_north", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST = block("table_north_east", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH = block("table_north_east_south", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST = block("table_east", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH = block("table_east_south", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH_WEST = block("table_east_south_west", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH = block("table_south", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST = block("table_south_west", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST_NORTH = block("table_south_west_north", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST = block("table_west", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH = block("table_west_north", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH_EAST = block("table_west_north_east", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_SOUTH = block("table_north_south", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_WEST = block("table_east_west", TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH_WEST = block("table_north_east_south_west", TextureSlot.TEXTURE);

    private static ModelTemplate block(String name, TextureSlot ... textures)
    {
        ModelTemplate model = new ModelTemplate("block", name, textures);
        MODELS.add(model.location);
        return model;
    }

    public static Set<ResourceLocation> all()
    {
        return Set.copyOf(MODELS);
    }

    private final ResourceLocation location;
    private final String path;
    private final TextureSlot[] textures;

    public ModelTemplate(String folder, String path, TextureSlot ... textures)
    {
        this.location = Utils.resource(folder + "/" + path);
        this.path = path;
        this.textures = textures;
    }

    public PreparedStateModel prepared()
    {
        return PreparedStateModel.create(this.path, this.location, this.textures);
    }

    public PreparedStateModel prepared(WoodType type)
    {
        return PreparedStateModel.create(type.name() + "_" + this.path, this.location, this.textures);
    }
}
