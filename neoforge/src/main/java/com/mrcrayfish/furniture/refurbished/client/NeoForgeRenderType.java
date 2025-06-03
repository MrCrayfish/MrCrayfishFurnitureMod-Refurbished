package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class NeoForgeRenderType
{
    private static final Function<ResourceLocation, RenderType> TELEVISION_SCREEN = Util.memoize((id) -> {
        return RenderType.create(Constants.MOD_ID + "_television_screen", 0x200000, false, false, RenderPipelines.SOLID, RenderType.CompositeState.builder()
                .setLightmapState(RenderType.LIGHTMAP)
                .setTextureState(new RenderStateShard.TextureStateShard(id, TriState.FALSE, false))
                .createCompositeState(true));
    });

    public static RenderType televisionScreen(ResourceLocation id)
    {
        return TELEVISION_SCREEN.apply(id);
    }
}
