package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModRenderPipelines;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class FabricRenderType
{
    public static final OutputTarget ELECTRICITY_TARGET = new OutputTarget(Constants.MOD_ID + "_electricity_target", () -> {
        return ElectricityRenderer.get().getTextureTarget();
    });

    public static final RenderType ELECTRICITY = RenderType.create(Constants.MOD_ID + "_electricity",
        RenderSetup.builder(ModRenderPipelines.ELECTRICITY)
            .useLightmap()
            .sortOnUpload()
            .setOutputTarget(ELECTRICITY_TARGET)
            .withTexture("Sampler0", Utils.id("textures/misc/electricity_nodes.png"))
            .createRenderSetup());

    private static final Function<Identifier, RenderType> TELEVISION_SCREEN = Util.memoize((id) ->
        RenderType.create(Constants.MOD_ID + "_television_screen",
            RenderSetup.builder(RenderPipelines.SOLID_BLOCK)
                .useLightmap()
                .withTexture("Sampler0", id)
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup()));

    private static final Function<Identifier, RenderType> PALETTE_IMAGE = texture ->
        RenderType.create(Constants.MOD_ID + "_television_screen",
            RenderSetup.builder(RenderPipelines.CUTOUT_BLOCK)
                .useLightmap()
                .withTexture("Sampler0", texture)
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup());

    public static RenderType televisionScreen(Identifier id)
    {
        return TELEVISION_SCREEN.apply(id);
    }

    public static RenderType createPaletteImage(Identifier id)
    {
        return PALETTE_IMAGE.apply(id);
    }
}
