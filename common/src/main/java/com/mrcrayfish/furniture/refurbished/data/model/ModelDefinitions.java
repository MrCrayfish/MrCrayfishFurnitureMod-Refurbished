package com.mrcrayfish.furniture.refurbished.data.model;

import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class ModelDefinitions
{
    private static final Set<Identifier> MODELS = new HashSet<>();
    public static final ModelTemplate TABLE = block("table", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH = block("table_north", "_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST = block("table_north_east", "_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH = block("table_north_east_south", "_north_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST = block("table_east", "_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH = block("table_east_south", "_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_SOUTH_WEST = block("table_east_south_west", "_east_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH = block("table_south", "_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST = block("table_south_west", "_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_SOUTH_WEST_NORTH = block("table_south_west_north", "_south_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST = block("table_west", "_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH = block("table_west_north", "_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_WEST_NORTH_EAST = block("table_west_north_east", "_west_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_SOUTH = block("table_north_south", "_north_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_EAST_WEST = block("table_east_west", "_east_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TABLE_NORTH_EAST_SOUTH_WEST = block("table_north_east_south_west", "_north_east_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CHAIR = block("chair", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CHAIR_TUCKED = block("chair_tucked", "_tucked", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK = block("desk", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_LEFT = block("desk_left", "_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_RIGHT = block("desk_right", "_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DESK_MIDDLE = block("desk_middle", "_middle", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_CLOSED = block("drawer_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_OPEN = block("drawer_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_LEFT_CLOSED = block("drawer_left_closed", "_left_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_LEFT_OPEN = block("drawer_left_open", "_left_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_RIGHT_CLOSED = block("drawer_right_closed", "_right_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_RIGHT_OPEN = block("drawer_right_open", "_right_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_MIDDLE_CLOSED = block("drawer_middle_closed", "_middle_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate DRAWER_MIDDLE_OPEN = block("drawer_middle_open", "_middle_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CRATE_CLOSED = block("crate_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CRATE_OPEN = block("crate_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_DEFAULT = block("kitchen_cabinetry_default", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_INSIDE_CORNER_LEFT = block("kitchen_cabinetry_inside_corner_left", "_inside_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_INSIDE_CORNER_RIGHT = block("kitchen_cabinetry_inside_corner_right", "_inside_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_OUTSIDE_CORNER_LEFT = block("kitchen_cabinetry_outside_corner_left", "_outside_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_CABINETRY_OUTSIDE_CORNER_RIGHT = block("kitchen_cabinetry_outside_corner_right", "_outside_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_DRAWER_CLOSED = block("kitchen_drawer_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_DRAWER_OPEN = block("kitchen_drawer_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_SINK = block("kitchen_sink", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate GRILL = block("grill", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate COOLER_CLOSED = block("cooler_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate COOLER_OPEN = block("cooler_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FRIDGE_CLOSED = block("fridge_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FRIDGE_OPEN = block("fridge_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FREEZER_CLOSED = block("freezer_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FREEZER_OPEN = block("freezer_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TOASTER = block("toaster", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TOASTER_COOKING = block("toaster_cooking", "_cooking", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CUTTING_BOARD = block("cutting_board", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MICROWAVE_CLOSED = block("microwave_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MICROWAVE_OPEN = block("microwave_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STOVE_CLOSED = block("stove_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STOVE_OPEN = block("stove_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate RANGE_HOOD_OFF = block("range_hood_off", "_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate RANGE_HOOD_ON = block("range_hood_on", "_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MAIL_BOX = block("mail_box", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate MAIL_BOX_UNCHECKED = block("mail_box_unchecked", "_unchecked", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate POST_BOX = block("post_box", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA = block("sofa", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_LEFT = block("sofa_left", "_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_RIGHT = block("sofa_right", "_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_MIDDLE = block("sofa_middle", "_middle", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_CORNER_LEFT = block("sofa_corner_left", "_corner_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate SOFA_CORNER_RIGHT = block("sofa_corner_right", "_corner_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LIGHTSWITCH_OFF = block("lightswitch_off", "_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LIGHTSWITCH_ON = block("lightswitch_on", "_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_LIGHT_OFF = block("ceiling_light_off", "_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_LIGHT_ON = block("ceiling_light_on", "_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate ELECTRICITY_GENERATOR_OFF = block("electricity_generator_off", "_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate ELECTRICITY_GENERATOR_ON = block("electricity_generator_on", "_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STORAGE_JAR = block("storage_jar", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LAMP_OFF = block("lamp_off", "_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LAMP_ON = block("lamp_on", "_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BASE_OFF = block("ceiling_fan_base_off", "_base_off", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BASE_ON = block("ceiling_fan_base_on", "_base_on", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN_BLADE = block("ceiling_fan_blade", TextureSlot.TEXTURE);
    public static final ModelTemplate CABINET_CLOSED_HINGE_LEFT = block("cabinet_closed_hinge_left", "_closed_hinge_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CABINET_OPEN_HINGE_LEFT = block("cabinet_open_hinge_left", "_open_hinge_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CABINET_CLOSED_HINGE_RIGHT = block("cabinet_closed_hinge_right", "_closed_hinge_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate CABINET_OPEN_HINGE_RIGHT = block("cabinet_open_hinge_right", "_open_hinge_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_STORAGE_CABINET_CLOSED_HINGE_LEFT = block("kitchen_storage_cabinet_closed_hinge_left", "_closed_hinge_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_STORAGE_CABINET_OPEN_HINGE_LEFT = block("kitchen_storage_cabinet_open_hinge_left", "_open_hinge_left", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_STORAGE_CABINET_CLOSED_HINGE_RIGHT = block("kitchen_storage_cabinet_closed_hinge_right", "_closed_hinge_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate KITCHEN_STORAGE_CABINET_OPEN_HINGE_RIGHT = block("kitchen_storage_cabinet_open_hinge_right", "_open_hinge_right", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_DEFAULT = block("trampoline_default", "_default", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH = block("trampoline_north", "_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST = block("trampoline_east", "_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH = block("trampoline_south", "_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST = block("trampoline_west", "_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_SOUTH = block("trampoline_north_south", "_north_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_WEST = block("trampoline_east_west", "_east_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST = block("trampoline_north_east", "_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH = block("trampoline_east_south", "_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST = block("trampoline_south_west", "_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH = block("trampoline_west_north", "_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST_WITH_LEG = block("trampoline_north_east_with_leg", "_north_east_with_leg", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH_WITH_LEG = block("trampoline_east_south_with_leg", "_east_south_with_leg", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST_WITH_LEG = block("trampoline_south_west_with_leg", "_south_west_with_leg", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH_WITH_LEG = block("trampoline_west_north_with_leg", "_west_north_with_leg", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST_SOUTH = block("trampoline_north_east_south", "_north_east_south", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH_WEST = block("trampoline_east_south_west", "_east_south_west", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST_NORTH = block("trampoline_south_west_north", "_south_west_north", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH_EAST = block("trampoline_west_north_east", "_west_north_east", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST = block("trampoline_north_east_south_with_leg_northeast", "_north_east_south_with_leg_northeast", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH = block("trampoline_north_east_south_with_leg_eastsouth", "_north_east_south_with_leg_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH = block("trampoline_north_east_south_with_leg_northeast_eastsouth", "_north_east_south_with_leg_northeast_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH = block("trampoline_east_south_west_with_leg_eastsouth", "_east_south_west_with_leg_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST = block("trampoline_east_south_west_with_leg_southwest", "_east_south_west_with_leg_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST = block("trampoline_east_south_west_with_leg_eastsouth_southwest", "_east_south_west_with_leg_eastsouth_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH = block("trampoline_south_west_north_with_leg_westnorth", "_south_west_north_with_leg_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST = block("trampoline_south_west_north_with_leg_southwest", "_south_west_north_with_leg_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST = block("trampoline_south_west_north_with_leg_westnorth_southwest", "_south_west_north_with_leg_westnorth_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST = block("trampoline_west_north_east_with_leg_northeast", "_west_north_east_with_leg_northeast", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_WESTNORTH = block("trampoline_west_north_east_with_leg_westnorth", "_west_north_east_with_leg_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH = block("trampoline_west_north_east_with_leg_northeast_westnorth", "_west_north_east_with_leg_northeast_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL = block("trampoline_all", "_all", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_ALL = block("trampoline_all_with_leg_all", "_all_with_leg_all", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_NORTHEAST = block("trampoline_all_with_leg_northeast", "_all_with_leg_northeast", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH = block("trampoline_all_with_leg_northeast_eastsouth", "_all_with_leg_northeast_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST = block("trampoline_all_with_leg_northeast_eastsouth_southwest", "_all_with_leg_northeast_eastsouth_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH = block("trampoline_all_with_leg_eastsouth", "_all_with_leg_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST = block("trampoline_all_with_leg_eastsouth_southwest", "_all_with_leg_eastsouth_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH = block("trampoline_all_with_leg_eastsouth_southwest_westnorth", "_all_with_leg_eastsouth_southwest_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST = block("trampoline_all_with_leg_southwest", "_all_with_leg_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH = block("trampoline_all_with_leg_southwest_westnorth", "_all_with_leg_southwest_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST = block("trampoline_all_with_leg_southwest_westnorth_northeast", "_all_with_leg_southwest_westnorth_northeast", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_WESTNORTH = block("trampoline_all_with_leg_westnorth", "_all_with_leg_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST = block("trampoline_all_with_leg_westnorth_northeast", "_all_with_leg_westnorth_northeast", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH = block("trampoline_all_with_leg_westnorth_northeast_eastsouth", "_all_with_leg_westnorth_northeast_eastsouth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_NORTHEAST_SOUTHWEST = block("trampoline_all_with_leg_northeast_southwest", "_all_with_leg_northeast_southwest", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TRAMPOLINE_ALL_WITH_LEG_EASTSOUTH_WESTNORTH = block("trampoline_all_with_leg_eastsouth_westnorth", "_all_with_leg_eastsouth_westnorth", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STOOL = block("stool", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CENTER_STYLE_1 = block("hedge_center_style_1", "_center_style_1", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CENTER_STYLE_2 = block("hedge_center_style_2", "_center_style_2", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CENTER_STYLE_3 = block("hedge_center_style_3", "_center_style_3", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CENTER_SIDE = block("hedge_center_side", "_center_side", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CONNECTION_STYLE_1 = block("hedge_connection_style_1", "_connection_style_1", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CONNECTION_STYLE_2 = block("hedge_connection_style_2", "_connection_style_2", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE_CONNECTION_STYLE_3 = block("hedge_connection_style_3", "_connection_style_3", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STEPPING_STONES_STYLE_1 = block("stepping_stones_style_1", "_style_1", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STEPPING_STONES_STYLE_2 = block("stepping_stones_style_2", "_style_2", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STEPPING_STONES_STYLE_3 = block("stepping_stones_style_3", "_style_3", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate STEPPING_STONES_STYLE_4 = block("stepping_stones_style_4", "_style_4", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate TOILET = block("toilet", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate BASIN = block("basin", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate BATH_HEAD = block("bath_head", "_head", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate BATH_BOTTOM = block("bath_bottom", "_bottom", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LATTICE_FENCE_CENTER = block("lattice_fence_center", "_center", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LATTICE_FENCE_CONNECTION = block("lattice_fence_connection", "_connection", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LATTICE_FENCE_GATE_CLOSED = block("lattice_fence_gate_closed", "_closed", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate LATTICE_FENCE_GATE_OPEN = block("lattice_fence_gate_open", "_open", TextureSlot.PARTICLE, TextureSlot.TEXTURE);

    public static final ModelTemplate FRIDGE = item("fridge", TextureSlot.TEXTURE);
    public static final ModelTemplate CEILING_FAN = item("ceiling_fan", TextureSlot.TEXTURE);
    public static final ModelTemplate HEDGE = item("hedge", TextureSlot.TEXTURE);
    public static final ModelTemplate BATH = item("bath", TextureSlot.TEXTURE);
    public static final ModelTemplate LATTICE_FENCE = item("lattice_fence", TextureSlot.TEXTURE);

    private static ModelTemplate block(String name, TextureSlot ... textures)
    {
        return new ModelTemplate(Optional.of(Utils.id("block/" + name)), Optional.empty(), textures);
    }

    private static ModelTemplate block(String name, String suffix, TextureSlot ... textures)
    {
        return new ModelTemplate(Optional.of(Utils.id("block/" + name)), Optional.of(suffix), textures);
    }

    private static ModelTemplate item(String name, TextureSlot ... textures)
    {
        return new ModelTemplate(Optional.of(Utils.id("item/" + name)), Optional.empty(), textures);
    }

    public static Set<Identifier> all()
    {
        return Set.copyOf(MODELS);
    }
}
