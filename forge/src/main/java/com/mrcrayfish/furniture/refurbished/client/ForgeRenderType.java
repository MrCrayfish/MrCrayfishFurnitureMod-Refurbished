package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.furniture.refurbished.Constants;
import net.minecraft.client.renderer.RenderType;

/**
 * Author: MrCrayfish
 */
public class ForgeRenderType extends RenderType
{
    public static final RenderType ELECTRICITY_NODE = RenderType.create(Constants.MOD_ID + "_block_no_depth", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 0x200000, true, true, RenderType.CompositeState.builder()
            .setLightmapState(RenderType.LIGHTMAP)
            .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
            .setTextureState(BLOCK_SHEET_MIPPED)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(OUTLINE_TARGET)
            .createCompositeState(true)
    );

    public static final RenderType COLOURED_BOX = create(Constants.MOD_ID + "_coloured_box", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_STRIP, 0x20000, false, true, RenderType.CompositeState.builder()
            .setShaderState(POSITION_COLOR_SHADER)
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(OUTLINE_TARGET)
            .createCompositeState(true));

    // Unused. Don't call
    public ForgeRenderType(String id, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean b1, boolean b2, Runnable run1, Runnable run2)
    {
        super(id, format, mode, bufferSize, b1, b2, run1, run2);
        throw new UnsupportedOperationException();
    }
}
