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
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class WorkbenchScreen extends ElectricityContainerScreen<WorkbenchMenu>
{
    private static final ResourceLocation WORKBENCH_TEXTURE = Utils.resource("textures/gui/container/workbench.png");

    private static final int BUTTON_SIZE = 20;
    private static final int RECIPES_PER_ROW = 4;
    private static final int WINDOW_WIDTH = BUTTON_SIZE * RECIPES_PER_ROW;
    private static final int WINDOW_HEIGHT = 70;

    protected double scroll; // 0-1
    protected int selectedIndex = -1;
    protected int hoveredIndex = -1;

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
        this.renderRecipes(graphics, partialTick, mouseX, mouseY);
        if(this.hoveredIndex != -1)
        {
            this.renderRecipeTooltip(graphics, mouseX, mouseY, this.hoveredIndex);
        }
    }

    private void renderRecipes(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        this.hoveredIndex = -1;
        graphics.enableScissor(this.leftPos + 46, this.topPos + 18, this.leftPos + 46 + WINDOW_WIDTH, this.topPos + 18 + WINDOW_HEIGHT);
        List<WorkbenchCraftingRecipe> recipes = this.menu.getRecipes();
        int maxScroll = Math.max(0, (recipes.size() / RECIPES_PER_ROW) * BUTTON_SIZE - WINDOW_HEIGHT);
        int startIndex = (int) ((maxScroll * this.scroll) / BUTTON_SIZE);
        int endIndex = startIndex + Mth.ceil(WINDOW_HEIGHT / (double) BUTTON_SIZE) + RECIPES_PER_ROW;
        for(int i = startIndex; i < endIndex && i < recipes.size(); i++)
        {
            int buttonX = this.leftPos + 46 + startIndex % RECIPES_PER_ROW;
            int buttonY = this.topPos + 18 + startIndex / RECIPES_PER_ROW;
            boolean selected = i == this.selectedIndex;
            graphics.blit(WORKBENCH_TEXTURE, buttonX, buttonY, 176, selected ? BUTTON_SIZE : 0, BUTTON_SIZE, BUTTON_SIZE);
            graphics.renderFakeItem(recipes.get(i).getResultItem(this.menu.getLevel().registryAccess()), buttonX + 2, buttonY + 2);
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
            components.add(new ClientWorkbenchRecipeTooltip(recipe));
            components.add(new ClientTextTooltip(Components.HOLD_SHIFT_DETAILS.getVisualOrderText()));
        }
        else
        {
            recipe.getMaterials().forEach(material -> components.add(new ClientWorkbenchRecipeIngredientTooltip(material)));
        }
        ClientServices.PLATFORM.renderTooltip(graphics, this.font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && this.hoveredIndex != -1)
        {
            this.selectedIndex = this.hoveredIndex;
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
