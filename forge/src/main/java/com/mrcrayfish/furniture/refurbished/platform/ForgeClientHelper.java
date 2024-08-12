package com.mrcrayfish.furniture.refurbished.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrcrayfish.furniture.refurbished.client.ForgeRenderType;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.FreezerScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.MicrowaveScreen;
import com.mrcrayfish.furniture.refurbished.client.gui.screen.StoveScreen;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.platform.services.IClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
    public int getSelectedCreativeModeTab()
    {
        return CreativeModeInventoryScreen.selectedTab;
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
    public RenderType getTelevisionScreenRenderType(ResourceLocation id)
    {
        return ForgeRenderType.televisionScreen(id);
    }

    @Override
    public void renderTooltip(Screen screen, PoseStack poseStack, List<ClientTooltipComponent> components, int mouseX, int mouseY)
    {
        screen.renderTooltipInternal(poseStack, components, mouseX, mouseY);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createFreezerScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new FreezerScreen((FreezerMenu) menu, playerInventory, title);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createMicrowaveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new MicrowaveScreen((MicrowaveMenu) menu, playerInventory, title);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public AbstractContainerScreen createStoveScreen(AbstractContainerMenu menu, Inventory playerInventory, Component title)
    {
        return new StoveScreen((StoveMenu) menu, playerInventory, title);
    }

    private Function<ResourceLocation, TextureAtlasSprite> getBlockTextures()
    {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
    }
}
