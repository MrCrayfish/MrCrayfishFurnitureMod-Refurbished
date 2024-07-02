package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.EmptyRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.OvenRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
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
public class StoveScreen extends ElectricityContainerScreen<StoveMenu> implements RecipeUpdateListener
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/stove.png");

    private final RecipeBookComponent recipeBookComponent =
        Services.PLATFORM.getPlatform().isFabric() ? new EmptyRecipeBookComponent() : new OvenRecipeBookComponent();
    private boolean widthTooNarrow = Services.PLATFORM.getPlatform().isFabric();
    private OnOffSlider slider;

    public StoveScreen(StoveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();

        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.widthTooNarrow = this.width < 379;
            this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        }

        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));

        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.addRenderableWidget(new ImageButton(this.leftPos + 7, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
                this.recipeBookComponent.toggleVisibility();
                this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
                button.setPosition(this.leftPos + 14, this.height / 2 - 49);
                this.slider.setPosition(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5);
            }));
            this.addWidget(this.recipeBookComponent);
            this.setInitialFocus(this.recipeBookComponent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        super.render(graphics, mouseX, mouseY, partialTick);
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
            this.recipeBookComponent.renderGhostRecipe(graphics, this.leftPos, this.topPos, true, partialTick);
            this.renderTooltip(graphics, mouseX, mouseY);
            this.recipeBookComponent.renderTooltip(graphics, this.leftPos, this.topPos, mouseX, mouseY);
        }
        else
        {
            this.renderTooltip(graphics, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int offset = this.menu.isPowered() && this.menu.isEnabled() ? (int) (Util.getMillis() / 100) % 3 : 0;
        graphics.blit(TEXTURE, this.leftPos + 32, this.topPos + 23, 176, 16 + offset * 40, 40, 40);

        for(int i = 0; i < 3; i++)
        {
            int progress = this.menu.getBakingProgress(i);
            int totalProgress = this.menu.getTotalBakingProgress(i);
            if(totalProgress == 0)
                continue;
            int height = (int) Math.ceil(16 * (progress / (float) totalProgress));
            graphics.blit(TEXTURE, this.leftPos + 84 + i * 18, this.topPos + 36, 190, 0, 17, height);
        }
    }

    @Override
    protected void containerTick()
    {
        // Disables recipe book support from Fabric
        if(!Services.PLATFORM.getPlatform().isFabric())
        {
            this.recipeBookComponent.tick();
        }
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
        boolean outside = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.imageWidth) || mouseY >= (double) (top + this.imageHeight);
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
        // Not much we can do here for Fabric
        return this.recipeBookComponent;
    }
}
