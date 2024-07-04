package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class ForgeRenderType extends RenderType
{
    private static final Function<ResourceLocation, RenderType> TELEVISION_SCREEN = Util.memoize((id) -> {
        return RenderType.create(Constants.MOD_ID + "_television_screen", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, true, false, RenderType.CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(RENDERTYPE_SOLID_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(id, false, false))
                .createCompositeState(true));
    });

    public static RenderType televisionScreen(ResourceLocation id)
    {
        return TELEVISION_SCREEN.apply(id);
    }

    // Unused. Don't call
    public ForgeRenderType(String id, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean b1, boolean b2, Runnable run1, Runnable run2)
    {
        super(id, format, mode, bufferSize, b1, b2, run1, run2);
        throw new UnsupportedOperationException();
    }
}
