package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.recipe.FreezerRecipeBookComponent;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.OnOffSlider;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.inventory.FreezerMenu;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTogglePower;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;

/**
 * Author: MrCrayfish
 */
public class FreezerScreen extends ElectricityContainerScreen<FreezerMenu> implements RecipeUpdateListener
{
    private static final ResourceLocation TEXTURE = Utils.resource("textures/gui/container/freezer.png");

    private final FreezerRecipeBookComponent recipeBookComponent = new FreezerRecipeBookComponent();
    private boolean widthTooNarrow;
    private OnOffSlider slider;

    public FreezerScreen(FreezerMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.slider = this.addRenderableWidget(new OnOffSlider(this.leftPos + this.imageWidth - 22 - 6, this.topPos + 5, Components.GUI_TOGGLE_POWER, btn -> {
            Network.getPlay().sendToServer(new MessageTogglePower());
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 14, this.height / 2 - 49, 20, 18, 0, 0, 19, VanillaTextures.RECIPE_BUTTON, (button) -> {
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.slider.setEnabled(this.menu.isEnabled());
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
        this.recipeBookComponent.renderGhostRecipe(graphics, this.leftPos, this.topPos, true, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(graphics, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if(this.menu.getMaxProcessTime() > 0 && this.menu.getProcessTime() >= 0)
        {
            int width = (int) Math.ceil(25 * (this.menu.getProcessTime() / (float) this.menu.getMaxProcessTime()));
            graphics.blit(TEXTURE, this.leftPos + 71, this.topPos + 34, 176, 0, width, 17);
        }
        if(this.menu.getProcessTime() > 0 && this.menu.getMaxProcessTime() > 0 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 71, this.topPos + 34, 25, 17))
        {
            this.setTooltipForNextRenderPass(Utils.translation("gui", "progress", this.menu.getProcessTime(), Components.GUI_SLASH, this.menu.getMaxProcessTime()));
        }
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
        boolean $$5 = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.imageWidth) || mouseY >= (double) (top + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, button) && $$5;
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
        return this.recipeBookComponent;
    }
}
