package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.FreezerRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

/**
 * Author: MrCrayfish
 */
public class FreezerScreen extends AbstractFreezerScreen<FreezerMenu> implements RecipeUpdateListener
{
    private final RecipeBookComponent recipeBookComponent = new FreezerRecipeBookComponent();
    private boolean widthTooNarrow;

    public FreezerScreen(FreezerMenu menu, Inventory inventory, Component title)
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
        this.addRenderableWidget(new ImageButton(this.leftPos + 14, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            button.setPosition(this.leftPos + 14, this.height / 2 - 49);
            this.slider.setPosition(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5);
        }));
        this.addWidget(this.recipeBookComponent);
        this.setInitialFocus(this.recipeBookComponent);
    }

    @Override
    protected void containerTick()
    {
        super.containerTick();
        this.recipeBookComponent.tick();
    }

    @Override
    protected void afterRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
        this.recipeBookComponent.renderGhostRecipe(graphics, this.leftPos, this.topPos, true, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(graphics, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected boolean isHovering(int left, int top, int width, int height, double mouseX, double mouseY)
    {
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(left, top, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
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
        boolean outside = mouseX < left || mouseY < top || mouseX >= left + this.imageWidth || mouseY >= top + this.imageHeight;
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, button) && outside;
    }

    @Override
    protected void slotClicked(Slot slot, int mouseX, int mouseY, ClickType type)
    {
        super.slotClicked(slot, mouseX, mouseY, type);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public void recipesUpdated()
    {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent()
    {
        // Not much we can do here for Fabric
        return this.recipeBookComponent;
    }
}
