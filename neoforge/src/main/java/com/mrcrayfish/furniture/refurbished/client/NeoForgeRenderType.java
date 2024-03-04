package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class NeoForgeRenderType extends RenderType
{
    public static final RenderType ELECTRICITY_NODE = RenderType.create(Constants.MOD_ID + "_block_no_depth", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, true, true, CompositeState.builder()
            .setLightmapState(LIGHTMAP)
            .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
            .setTextureState(BLOCK_SHEET_MIPPED)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(OUTLINE_TARGET)
            .createCompositeState(true)
    );

    public static final RenderType COLOURED_BOX = create(Constants.MOD_ID + "_coloured_box", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_STRIP, 0x20000, false, true, CompositeState.builder()
            .setShaderState(POSITION_COLOR_SHADER)
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(OUTLINE_TARGET)
            .createCompositeState(true));

    private static final Function<ResourceLocation, RenderType> TELEVISION_SCREEN = Util.memoize((id) -> {
        return RenderType.create(Constants.MOD_ID + "_television_screen", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, true, false, CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(RENDERTYPE_SOLID_SHADER)
                .setTextureState(new TextureStateShard(id, false, false))
                .createCompositeState(true));
    });

    public static RenderType televisionScreen(ResourceLocation id)
    {
        return TELEVISION_SCREEN.apply(id);
    }

    // Unused. Don't call
    public NeoForgeRenderType(String id, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean b1, boolean b2, Runnable run1, Runnable run2)
    {
        super(id, format, mode, bufferSize, b1, b2, run1, run2);
        throw new UnsupportedOperationException();
    }
}
