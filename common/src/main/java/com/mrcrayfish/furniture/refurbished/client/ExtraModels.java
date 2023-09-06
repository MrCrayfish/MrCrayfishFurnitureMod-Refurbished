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
    ELECTRIC_NODE_SUCCESS(Utils.resource("extra/electric_node_success"));

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
