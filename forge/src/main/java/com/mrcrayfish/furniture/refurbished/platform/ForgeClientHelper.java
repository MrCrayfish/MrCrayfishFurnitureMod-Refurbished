package com.mrcrayfish.furniture.refurbished.platform;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.client.ForgeRenderType;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class ForgeClientHelper implements IClientHelper
{
    @Override
    public int getGuiLeft(AbstractContainerScreen<?> screen)
    {
        return screen.getGuiLeft();
    }

    @Override
    public int getGuiTop(AbstractContainerScreen<?> screen)
    {
        return screen.getGuiTop();
    }

    @Override
    public CreativeModeTab getSelectedCreativeModeTab()
    {
        return CreativeModeInventoryScreen.selectedTab;
    }

    @Override
    public void setTooltipCache(Tooltip tooltip, List<FormattedCharSequence> lines)
    {
        tooltip.cachedTooltip = ImmutableList.copyOf(lines);
    }

    @Override
    public TextureAtlasSprite[] getFluidSprites(Fluid fluid, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, FluidState state)
    {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluid);
        return new TextureAtlasSprite[] {
            this.getBlockTextures().apply(extensions.getStillTexture(state, getter, pos)),
            this.getBlockTextures().apply(extensions.getFlowingTexture(state, getter, pos))
        };
    }

    @Override
    public void drawBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int light, int overlay)
    {
        Minecraft.getInstance().getItemRenderer().renderModelLists(model, ItemStack.EMPTY, light, overlay, poseStack, consumer);
    }

    @Override
    public BakedModel getBakedModel(ResourceLocation location)
    {
        return Minecraft.getInstance().getModelManager().getModel(location);
    }

    @Override
    public RenderType getElectrictyNodeRenderType()
    {
        return ForgeRenderType.ELECTRICITY_NODE;
    }

    @Override
    public RenderType getElectricityConnectionRenderType()
    {
        return ForgeRenderType.COLOURED_BOX;
    }

    @Override
    public RenderType getTelevisionScreenRenderType(ResourceLocation id)
    {
        return ForgeRenderType.televisionScreen(id);
    }

    @Override
    public SimpleParticleType createSimpleParticleType(boolean ignoreLimit)
    {
        return new SimpleParticleType(ignoreLimit);
    }

    private Function<ResourceLocation, TextureAtlasSprite> getBlockTextures()
    {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
    }
}
