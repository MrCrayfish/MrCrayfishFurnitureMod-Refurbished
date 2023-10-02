package com.mrcrayfish.furniture.refurbished.data.model;

import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ModelTemplate
{
    private static final Set<ResourceLocation> MODELS = new HashSet<>();
    public static final ModelTemplate TABLE = block("table", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH = block("table_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST = block("table_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH = block("table_north_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST = block("table_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH = block("table_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH_WEST = block("table_east_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH = block("table_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST = block("table_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST_NORTH = block("table_south_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST = block("table_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH = block("table_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH_EAST = block("table_west_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_SOUTH = block("table_north_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_WEST = block("table_east_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH_WEST = block("table_north_east_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CHAIR = block("chair", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CHAIR_TUCKED = block("chair_tucked", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK = block("desk", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_LEFT = block("desk_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_RIGHT = block("desk_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_MIDDLE = block("desk_middle", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_CLOSED = block("drawer_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_OPEN = block("drawer_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_LEFT_CLOSED = block("drawer_left_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_LEFT_OPEN = block("drawer_left_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_RIGHT_CLOSED = block("drawer_right_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_RIGHT_OPEN = block("drawer_right_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_MIDDLE_CLOSED = block("drawer_right_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_MIDDLE_OPEN = block("drawer_right_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CRATE_CLOSED = block("crate_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CRATE_OPEN = block("crate_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_DEFAULT = block("kitchen_cabinetry_default", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_INSIDE_CORNER_LEFT = block("kitchen_cabinetry_inside_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT = block("kitchen_cabinetry_inside_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT = block("kitchen_cabinetry_outside_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT = block("kitchen_cabinetry_outside_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_DRAWER_CLOSED = block("kitchen_drawer_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_DRAWER_OPEN = block("kitchen_drawer_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_SINK = block("kitchen_sink", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate GRILL = block("grill", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate COOLER_CLOSED = block("cooler_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate COOLER_OPEN = block("cooler_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FRIDGE_CLOSED = block("fridge_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FRIDGE_OPEN = block("fridge_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FREEZER_CLOSED = block("freezer_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FREEZER_OPEN = block("freezer_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TOASTER = block("toaster", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TOASTER_COOKING = block("toaster_cooking", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CUTTING_BOARD = block("cutting_board", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MICROWAVE_CLOSED = block("microwave_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MICROWAVE_OPEN = block("microwave_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STOVE_CLOSED = block("stove_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STOVE_OPEN = block("stove_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FRYING_PAN = block("frying_pan", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MAIL_BOX = block("mail_box", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MAIL_BOX_UNCHECKED = block("mail_box_unchecked", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate POST_BOX = block("post_box", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA = block("sofa", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_LEFT = block("sofa_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_RIGHT = block("sofa_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_MIDDLE = block("sofa_middle", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_CORNER_LEFT = block("sofa_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_CORNER_RIGHT = block("sofa_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DOORBELL = block("doorbell", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DOORBELL_PRESSED = block("doorbell_pressed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LIGHTSWITCH_OFF = block("lightswitch_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LIGHTSWITCH_ON = block("lightswitch_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_LIGHT_OFF = block("ceiling_light_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_LIGHT_ON = block("ceiling_light_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate ELECTRICITY_GENERATOR_OFF = block("electricity_generator_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate ELECTRICITY_GENERATOR_ON = block("electricity_generator_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STORAGE_JAR = block("storage_jar", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate RECYCLE_BIN_CLOSED = block("recycle_bin_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate RECYCLE_BIN_OPEN = block("recycle_bin_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LAMP_OFF = block("lamp_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LAMP_ON = block("lamp_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BASE_OFF = block("ceiling_fan_base_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BASE_ON = block("ceiling_fan_base_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BLADE = block("ceiling_fan_blade", TextureSlot.TEXTURE);

    public static final ModelTemplate FRIDGE = item("fridge", TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN = item("ceiling_fan", TextureSlot.TEXTURE);

    private static ModelTemplate block(String name, TextureSlot ... textures)
    {
        ModelTemplate model = new ModelTemplate("block", name, textures);
        MODELS.add(model.location);
        return model;
    }

    private static ModelTemplate item(String name, TextureSlot ... textures)
    {
        ModelTemplate model = new ModelTemplate("item", name, textures);
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

    public PreparedBlockState.Model stateModel()
    {
        return PreparedBlockState.Model.create(this.path, this.location, this.textures);
    }

    public PreparedBlockState.Model stateModel(WoodType type)
    {
        return PreparedBlockState.Model.create(type.name() + "_" + this.path, this.location, this.textures);
    }

    public PreparedBlockState.Model stateModel(DyeColor color)
    {
        return PreparedBlockState.Model.create(color.getName() + "_" + this.path, this.location, this.textures);
    }

    public PreparedBlockState.Model stateModel(MetalType type)
    {
        return PreparedBlockState.Model.create(type.getName() + "_" + this.path, this.location, this.textures);
    }

    public PreparedBlockState.Model stateModel(WoodType woodType, MetalType metalType)
    {
        return PreparedBlockState.Model.create(woodType.name() + "_" + metalType.getName() + "_" + this.path, this.location, this.textures);
    }

    public PreparedItem.Model itemModel(WoodType type)
    {
        return PreparedItem.Model.create(type.name() + "_" + this.path, this.location, this.textures);
    }

    public PreparedItem.Model itemModel(MetalType type)
    {
        return PreparedItem.Model.create(type.getName() + "_" + this.path, this.location, this.textures);
    }

    public PreparedItem.Model itemModel(WoodType woodType, MetalType metalType)
    {
        return PreparedItem.Model.create(woodType.name() + "_" + metalType.getName() + "_" + this.path, this.location, this.textures);
    }

    public ExtraModel extraModel(WoodType type)
    {
        return new ExtraModel(type.name() + "_" + this.path, this.location, this.textures);
    }

    public ExtraModel extraModel(WoodType woodType, MetalType metalType)
    {
        return new ExtraModel(woodType.name() + "_" + metalType.getName() + "_" + this.path, this.location, this.textures);
    }
}
