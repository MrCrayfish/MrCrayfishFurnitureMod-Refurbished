package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.ClientWorkbenchRecipeIngredientTooltip;
import com.mrcrayfish.furniture.refurbished.client.gui.ClientWorkbenchRecipeTooltip;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class WorkbenchScreen extends ElectricityContainerScreen<WorkbenchMenu>
{
    public static final ResourceLocation WORKBENCH_TEXTURE = Utils.resource("textures/gui/container/workbench.png");

    private static final int BUTTON_SIZE = 20;
    private static final int RECIPES_PER_ROW = 4;
    private static final int WINDOW_WIDTH = BUTTON_SIZE * RECIPES_PER_ROW;
    private static final int WINDOW_HEIGHT = 70;
    private static final int SCROLL_SPEED = 10;
    private static final int SCROLLBAR_HEIGHT = 15;

    protected double scroll; // 0 - content height
    protected int hoveredIndex = -1;
    protected int clickedY = -1;

    public WorkbenchScreen(WorkbenchMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageHeight = 193;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        graphics.blit(WORKBENCH_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderScrollbar(graphics, mouseY);
        this.renderRecipes(graphics, partialTick, mouseX, mouseY);
        if(this.hoveredIndex != -1)
        {
            this.renderRecipeTooltip(graphics, mouseX, mouseY, this.hoveredIndex);
        }
    }

    private void renderScrollbar(GuiGraphics graphics, int mouseY)
    {
        int textureU = this.getMaxScroll() > 0 ? 176 : 188;
        //System.out.println(this.getScrollbarPosition(mouseY));
        graphics.blit(WORKBENCH_TEXTURE, this.leftPos + 129, this.topPos + 18 + this.getScrollbarPosition(mouseY), textureU, 60, 12, SCROLLBAR_HEIGHT);
    }

    private void renderRecipes(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        this.hoveredIndex = -1;
        graphics.enableScissor(this.leftPos + 46, this.topPos + 18, this.leftPos + 46 + WINDOW_WIDTH, this.topPos + 18 + WINDOW_HEIGHT);
        List<WorkbenchCraftingRecipe> recipes = this.menu.getRecipes();
        double scroll = this.getScrollAmount(mouseY);
        int startIndex = (int) (scroll / BUTTON_SIZE) * RECIPES_PER_ROW;
        int endIndex = startIndex + Mth.ceil(WINDOW_HEIGHT / (double) BUTTON_SIZE + 1) * RECIPES_PER_ROW;
        for(int i = startIndex; i < endIndex && i < recipes.size(); i++)
        {
            WorkbenchCraftingRecipe recipe = recipes.get(i);
            boolean canCraft = this.menu.canCraft(recipe);
            boolean selected = i == this.menu.getSelectedRecipeIndex();
            int buttonX = this.leftPos + 46 + (i % RECIPES_PER_ROW) * BUTTON_SIZE;
            int buttonY = this.topPos + 18 + (i / RECIPES_PER_ROW) * BUTTON_SIZE - (int) scroll;
            int textureV = !canCraft ? BUTTON_SIZE * 2 : selected ? BUTTON_SIZE : 0;
            graphics.blit(WORKBENCH_TEXTURE, buttonX, buttonY, 176, textureV, BUTTON_SIZE, BUTTON_SIZE);
            graphics.renderFakeItem(recipe.getResultItem(this.menu.getLevel().registryAccess()), buttonX + 2, buttonY + 2);
            if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, buttonX, buttonY, BUTTON_SIZE, BUTTON_SIZE))
            {
                this.hoveredIndex = i;
            }
        }
        graphics.disableScissor();
    }

    private void renderRecipeTooltip(GuiGraphics graphics, int mouseX, int mouseY, int recipeIndex)
    {
        WorkbenchCraftingRecipe recipe = this.menu.getRecipes().get(recipeIndex);
        List<ClientTooltipComponent> components = new ArrayList<>();
        components.add(new ClientTextTooltip(recipe.getResultItem(this.menu.getLevel().registryAccess()).getHoverName().getVisualOrderText()));
        if(!Screen.hasShiftDown())
        {
            components.add(new ClientWorkbenchRecipeTooltip(this.menu, recipe));
            components.add(new ClientTextTooltip(Components.HOLD_SHIFT_DETAILS.getVisualOrderText()));
        }
        else
        {
            recipe.getMaterials().forEach(material -> components.add(new ClientWorkbenchRecipeIngredientTooltip(this.menu, material)));
        }
        ClientServices.PLATFORM.renderTooltip(graphics, this.font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            if(this.hoveredIndex != -1)
            {
                WorkbenchCraftingRecipe recipe = this.menu.getRecipes().get(this.hoveredIndex);
                if(this.menu.canCraft(recipe) && this.minecraft != null && this.minecraft.gameMode != null)
                {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, this.hoveredIndex);
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
            }

            if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 129, this.topPos + 18 + this.getScrollbarPosition((int) mouseY), 12, SCROLLBAR_HEIGHT))
            {
                this.clickedY = (int) mouseY;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && this.clickedY != -1)
        {
            this.scroll = this.getScrollAmount((int) mouseY);
            this.clickedY = -1;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        if(this.clickedY == -1 && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 46, this.topPos + 18, WINDOW_WIDTH + 15, WINDOW_HEIGHT))
        {
            this.scroll = Mth.clamp(this.scroll - delta * SCROLL_SPEED, 0, this.getMaxScroll());
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private double getScrollAmount(int mouseY)
    {
        return Mth.clamp(this.scroll + this.getScrollbarDelta(mouseY), 0, this.getMaxScroll());
    }

    private int getMaxScroll()
    {
        return Math.max(0, (int) (Math.ceil(this.menu.getRecipes().size() / (double) RECIPES_PER_ROW) * BUTTON_SIZE) - WINDOW_HEIGHT);
    }

    private int getScrollbarPosition(int mouseY)
    {
        return (int) ((this.getScrollAmount(mouseY) / this.getMaxScroll()) * (WINDOW_HEIGHT - SCROLLBAR_HEIGHT));
    }

    private int getScrollbarDelta(int mouseY)
    {
        double scrollPerUnit = this.getMaxScroll() / (double) (WINDOW_HEIGHT - SCROLLBAR_HEIGHT);
        return this.clickedY != -1 ? (int) ((mouseY - this.clickedY) * scrollPerUnit) : 0;
    }
}
