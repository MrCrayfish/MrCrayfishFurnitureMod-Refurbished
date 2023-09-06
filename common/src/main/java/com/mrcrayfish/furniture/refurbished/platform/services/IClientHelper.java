package com.mrcrayfish.furniture.refurbished.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public interface IClientHelper
{
    int getGuiLeft(AbstractContainerScreen<?> screen);

    int getGuiTop(AbstractContainerScreen<?> screen);

    CreativeModeTab getSelectedCreativeModeTab();

    void setTooltipCache(Tooltip tooltip, List<FormattedCharSequence> lines);

    TextureAtlasSprite[] getFluidSprites(Fluid fluid, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, FluidState state);

    void drawBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int light, int overlay);

    BakedModel getBakedModel(ResourceLocation location);
}
