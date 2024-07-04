package com.mrcrayfish.furniture.refurbished.platform;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.client.FabricRenderType;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FabricClientHelper implements IClientHelper
{
    @Override
    public int getGuiLeft(AbstractContainerScreen<?> screen)
    {
        return screen.leftPos;
    }

    @Override
    public int getGuiTop(AbstractContainerScreen<?> screen)
    {
        return screen.topPos;
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
        return FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(getter, pos, state);
    }

    @Override
    public void drawBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int light, int overlay)
    {
        Minecraft.getInstance().getItemRenderer().renderModelLists(model, ItemStack.EMPTY, light, overlay, poseStack, consumer);
    }

    @Override
    public BakedModel getBakedModel(ResourceLocation location)
    {
        ModelManager manager = Minecraft.getInstance().getModelManager();
        return manager.bakedRegistry.getOrDefault(location, manager.getMissingModel());
    }

    @Override
    public RenderType getTelevisionScreenRenderType(ResourceLocation id)
    {
        return FabricRenderType.televisionScreen(id);
    }

    @Override
    public SimpleParticleType createSimpleParticleType(boolean ignoreLimit)
    {
        return FabricParticleTypes.simple(ignoreLimit);
    }

    @Override
    public void renderTooltip(GuiGraphics graphics, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner position)
    {
        graphics.renderTooltipInternal(font, components, mouseX, mouseY, position);
    }
}
