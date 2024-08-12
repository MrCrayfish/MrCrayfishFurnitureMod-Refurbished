package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.MicrowaveRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.inventory.MicrowaveMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

/**
 * Author: MrCrayfish
 */
public class MicrowaveScreen extends AbstractMicrowaveScreen<MicrowaveMenu> implements RecipeUpdateListener
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/microwave.png");

    private final RecipeBookComponent recipeBookComponent = new MicrowaveRecipeBookComponent();
    private boolean widthTooNarrow;

    public MicrowaveScreen(MicrowaveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void initWidgets()
    {
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 14, this.height / 2 - 49, 20, 18, 0, 0, 19, VanillaTextures.RECIPE_BUTTON, (button) -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            button.x = this.leftPos + 14;
            button.y = this.height / 2 - 49;
            this.slider.x = this.leftPos + this.imageWidth - 22 - 6;
            this.slider.y = this.topPos + 5;
        }));
        this.addWidget(this.recipeBookComponent);
        this.setInitialFocus(this.recipeBookComponent);
    }

    @Override
    protected void containerTick()
    {
        super.containerTick();

        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.tick();
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);

        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTick);
            this.recipeBookComponent.renderGhostRecipe(poseStack, this.leftPos, this.topPos, true, partialTick);
            this.renderTooltip(poseStack, mouseX, mouseY);
            this.recipeBookComponent.renderTooltip(poseStack, this.leftPos, this.topPos, mouseX, mouseY);
        }
        else
        {
            this.renderTooltip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getMaxProcessTime() > 0 && this.menu.getProcessTime() >= 0)
        {
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) this.menu.getMaxProcessTime()));
            this.blit(poseStack, this.leftPos + 71, this.topPos + 34, 176, 0, width, 17);
        }
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        if(this.menu.getProcessTime() > 0 && this.menu.getMaxProcessTime() > 0 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 71, this.topPos + 34, 25, 17))
        {
            this.renderTooltip(poseStack, Utils.translation("gui", "progress", this.menu.getProcessTime(), Components.GUI_SLASH, this.menu.getMaxProcessTime()), mouseX, mouseY);
            return;
        }
        super.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected boolean isHovering(int left, int top, int width, int height, double mouseX, double mouseY)
    {
        // Disables recipe book support from Fabric
        if(Services.PLATFORM.getPlatform().isFabric())
        {
            return super.isHovering(left, top, width, height, mouseX, mouseY);
        }
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(left, top, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        // Disables recipe book support from Fabric
        if(Services.PLATFORM.getPlatform().isFabric())
        {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        if(this.recipeBookComponent.mouseClicked(mouseX, mouseY, button))
        {
            this.setFocused(this.recipeBookComponent);
            return true;
        }
        return this.widthTooNarrow && this.recipeBookComponent.isVisible() || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button)
    {
        // Disables recipe book support from Fabric
        if(Services.PLATFORM.getPlatform().isFabric())
        {
            return super.hasClickedOutside(mouseX, mouseY, left, top, button);
        }
        boolean outside = mouseX < left || mouseY < top || mouseX >= left + this.imageWidth || mouseY >= top + this.imageHeight;
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, button) && outside;
    }

    @Override
    protected void slotClicked(Slot slot, int mouseX, int mouseY, ClickType type)
    {
        super.slotClicked(slot, mouseX, mouseY, type);

        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.slotClicked(slot);
        }
    }

    @Override
    public void recipesUpdated()
    {
        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.recipesUpdated();
        }
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent()
    {
        return this.recipeBookComponent;
    }
}
