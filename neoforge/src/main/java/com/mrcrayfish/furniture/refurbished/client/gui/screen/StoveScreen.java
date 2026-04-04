package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.OvenRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.inventory.StoveMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/**
 * Author: MrCrayfish
 */
public class StoveScreen extends AbstractStoveScreen<StoveMenu> implements RecipeUpdateListener
{
    private final OvenRecipeBookComponent recipeBookComponent;
    private boolean widthTooNarrow;

    public StoveScreen(StoveMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        this.recipeBookComponent = new OvenRecipeBookComponent(menu);
    }

    @Override
    protected void initWidgets()
    {
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 7, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            button.setPosition(this.leftPos + 7, this.height / 2 - 49);
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
    protected void afterRender(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        this.recipeBookComponent.extractRenderState(extractor, mouseX, mouseY, partialTick);
        this.extractTooltip(extractor, mouseX, mouseY);
        this.recipeBookComponent.extractTooltip(extractor, mouseX, mouseY, this.hoveredSlot);
    }

    @Override
    protected void extractSlots(GuiGraphicsExtractor extractor, int mouseX, int mouseY)
    {
        super.extractSlots(extractor, mouseX, mouseY);
        this.recipeBookComponent.extractGhostRecipe(extractor, false);
    }

    @Override
    protected boolean isHovering(int left, int top, int width, int height, double mouseX, double mouseY)
    {
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(left, top, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
    {
        if(this.recipeBookComponent.mouseClicked(event, doubleClick))
        {
            this.setFocused(this.recipeBookComponent);
            return true;
        }
        return this.widthTooNarrow && this.recipeBookComponent.isVisible() || super.mouseClicked(event, doubleClick);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top)
    {
        boolean outside = mouseX < left || mouseY < top || mouseX >= left + this.imageWidth || mouseY >= top + this.imageHeight;
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight) && outside;
    }

    @Override
    protected void slotClicked(Slot slot, int mouseX, int mouseY, ContainerInput input)
    {
        super.slotClicked(slot, mouseX, mouseY, input);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public void recipesUpdated()
    {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public void fillGhostRecipe(RecipeDisplay display)
    {
        this.recipeBookComponent.fillGhostRecipe(display);
    }
}
