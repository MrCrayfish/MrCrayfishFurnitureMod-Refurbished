package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * Standalone JSON models without the need of an item or block.
 * <p>
 * Author: MrCrayfish
 */
public enum ExtraModels
{
    ELECTRIC_NODE_NEUTRAL(Utils.resource("extra/electric_node_neutral")),
    ELECTRIC_NODE_ERROR(Utils.resource("extra/electric_node_error")),
    ELECTRIC_NODE_SUCCESS(Utils.resource("extra/electric_node_success")),
    ELECTRIC_NODE_POWER(Utils.resource("extra/electric_node_power")),
    OAK_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/oak_light_ceiling_fan_blade")),
    SPRUCE_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/spruce_light_ceiling_fan_blade")),
    BIRCH_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/birch_light_ceiling_fan_blade")),
    JUNGLE_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/jungle_light_ceiling_fan_blade")),
    ACACIA_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/acacia_light_ceiling_fan_blade")),
    DARK_OAK_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/dark_oak_light_ceiling_fan_blade")),
    MANGROVE_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/mangrove_light_ceiling_fan_blade")),
    CRIMSON_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/crimson_light_ceiling_fan_blade")),
    WARPED_LIGHT_CEILING_FAN_BLADE(Utils.resource("extra/warped_light_ceiling_fan_blade")),
    OAK_DARK_CEILING_FAN_BLADE(Utils.resource("extra/oak_dark_ceiling_fan_blade")),
    SPRUCE_DARK_CEILING_FAN_BLADE(Utils.resource("extra/spruce_dark_ceiling_fan_blade")),
    BIRCH_DARK_CEILING_FAN_BLADE(Utils.resource("extra/birch_dark_ceiling_fan_blade")),
    JUNGLE_DARK_CEILING_FAN_BLADE(Utils.resource("extra/jungle_dark_ceiling_fan_blade")),
    ACACIA_DARK_CEILING_FAN_BLADE(Utils.resource("extra/acacia_dark_ceiling_fan_blade")),
    DARK_OAK_DARK_CEILING_FAN_BLADE(Utils.resource("extra/dark_oak_dark_ceiling_fan_blade")),
    MANGROVE_DARK_CEILING_FAN_BLADE(Utils.resource("extra/mangrove_dark_ceiling_fan_blade")),
    CRIMSON_DARK_CEILING_FAN_BLADE(Utils.resource("extra/crimson_dark_ceiling_fan_blade")),
    WARPED_DARK_CEILING_FAN_BLADE(Utils.resource("extra/warped_dark_ceiling_fan_blade"));

    private final ResourceLocation location;

    ExtraModels(ResourceLocation location)
    {
        this.location = location;
    }

    /**
     * @return The instance of the baked model
     */
    public BakedModel getModel()
    {
        return ClientServices.PLATFORM.getBakedModel(this.location);
    }

    /**
     * Registers all the extra models, so they can be loaded and baked. Called on a modloader
     * specific level.
     * @param register a consumer accepting a resource location path to the model
     */
    public static void register(Consumer<ResourceLocation> register)
    {
        for(ExtraModels model : values())
        {
            register.accept(model.location);
        }
    }
}
