package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.client.gui.ClientWorkbenchRecipeIngredientTooltip;
import com.mrcrayfish.furniture.refurbished.client.gui.ClientWorkbenchRecipeTooltip;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.WorkbenchMenu;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
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
import net.minecraft.world.item.Item;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class WorkbenchScreen extends ElectricityContainerScreen<WorkbenchMenu>
{
    public static final ResourceLocation WORKBENCH_TEXTURE = Utils.resource("textures/gui/container/workbench.png");

    private static final int BUTTON_SIZE = 20;
    private static final int RECIPES_PER_ROW = 6;
    private static final int WINDOW_WIDTH = BUTTON_SIZE * RECIPES_PER_ROW;
    private static final int WINDOW_HEIGHT = 88;
    private static final int SCROLL_SPEED = 10;
    private static final int SCROLLBAR_HEIGHT = 15;
    private static boolean craftableOnly; // Persistent

    protected final Map<ResourceLocation, Integer> recipeToIndex;
    protected List<WorkbenchCraftingRecipe> displayRecipes = new ArrayList<>();
    protected StateSwitchingButton craftableOnlyButton;
    protected double scroll; // 0 - content height
    protected int hoveredIndex = -1;
    protected int clickedY = -1;

    public WorkbenchScreen(WorkbenchMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 216;
        this.imageHeight = 211;
        this.inventoryLabelX = 28;
        this.inventoryLabelY = this.imageHeight - 94;
        this.menu.setUpdateCallback(this::updateRecipes);
        this.recipeToIndex = Util.make(() -> {
            Map<ResourceLocation, Integer> map = new HashMap<>();
            List<WorkbenchCraftingRecipe> recipes = menu.getRecipes();
            for(int i = 0; i < recipes.size(); i++) {
                map.put(recipes.get(i).getId(), i);
            }
            return ImmutableMap.copyOf(map);
        });
        this.updateRecipes();
    }

    private void updateRecipes()
    {
        this.displayRecipes.clear();
        for(WorkbenchCraftingRecipe recipe : this.menu.getRecipes())
        {
            if(craftableOnly)
            {
                if(this.menu.canCraft(recipe))
                {
                    this.displayRecipes.add(recipe);
                }
                continue;
            }
            this.displayRecipes.add(recipe);
        }
        this.displayRecipes.sort(Comparator.comparing(WorkbenchCraftingRecipe::getResultId));
    }

    @Override
    protected int getBannerTop()
    {
        return this.topPos - 9;
    }

    @Override
    protected void init()
    {
        super.init();
        this.craftableOnlyButton = this.addRenderableWidget(new CraftableButton(this.leftPos + 184, this.topPos + 44, 26, 16, craftableOnly));
        this.craftableOnlyButton.initTextureValues(152, 41, 28, 18, VanillaTextures.RECIPE_BOOK);
    }

    @Override
    protected void containerTick()
    {

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
        if(this.hoveredIndex != -1)
        {
            this.renderRecipeTooltip(graphics, mouseX, mouseY, this.hoveredIndex);
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        graphics.blit(WORKBENCH_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.renderScrollbar(graphics, mouseY);
        this.renderRecipes(graphics, partialTick, mouseX, mouseY);
    }

    private void renderScrollbar(GuiGraphics graphics, int mouseY)
    {
        int textureU = this.getMaxScroll() > 0 ? 216 : 228;
        graphics.blit(WORKBENCH_TEXTURE, this.leftPos + 169, this.topPos + 18 + this.getScrollbarPosition(mouseY), textureU, 40, 12, SCROLLBAR_HEIGHT);
    }

    private void renderRecipes(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        this.hoveredIndex = -1;
        graphics.enableScissor(this.leftPos + 46, this.topPos + 18, this.leftPos + 46 + WINDOW_WIDTH, this.topPos + 18 + WINDOW_HEIGHT);
        List<WorkbenchCraftingRecipe> recipes = this.displayRecipes;
        double scroll = this.getScrollAmount(mouseY);
        int startIndex = (int) (scroll / BUTTON_SIZE) * RECIPES_PER_ROW;
        int endIndex = startIndex + Mth.ceil(WINDOW_HEIGHT / (double) BUTTON_SIZE + 1) * RECIPES_PER_ROW;
        boolean mouseInWindow = ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 46, this.topPos + 18, WINDOW_WIDTH, WINDOW_HEIGHT);
        for(int i = startIndex; i < endIndex && i < recipes.size(); i++)
        {
            WorkbenchCraftingRecipe recipe = recipes.get(i);
            int recipeIndex = this.recipeToIndex.get(recipe.getId());
            boolean canCraft = this.menu.canCraft(recipe);
            boolean selected = recipeIndex == this.menu.getSelectedRecipeIndex();
            int buttonX = this.leftPos + 46 + (i % RECIPES_PER_ROW) * BUTTON_SIZE;
            int buttonY = this.topPos + 18 + (i / RECIPES_PER_ROW) * BUTTON_SIZE - (int) scroll;
            int textureU = 216 + (!canCraft ? BUTTON_SIZE : 0);
            int textureV = selected ? BUTTON_SIZE : 0;
            graphics.blit(WORKBENCH_TEXTURE, buttonX, buttonY, textureU, textureV, BUTTON_SIZE, BUTTON_SIZE);
            graphics.renderFakeItem(recipe.getResultItem(this.menu.getLevel().registryAccess()), buttonX + 2, buttonY + 2);
            if(mouseInWindow && ScreenHelper.isMouseWithinBounds(mouseX, mouseY, buttonX, buttonY, BUTTON_SIZE, BUTTON_SIZE))
            {
                this.hoveredIndex = recipeIndex;
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
            Map<Integer, Integer> counted = new HashMap<>();
            recipe.getMaterials().forEach(material -> components.add(new ClientWorkbenchRecipeIngredientTooltip(this.menu, material, counted)));
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
                if(this.minecraft != null && this.minecraft.gameMode != null)
                {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, this.hoveredIndex);
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
            }

            if(ScreenHelper.isMouseWithinBounds(mouseX, mouseY, this.leftPos + 169, this.topPos + 18 + this.getScrollbarPosition((int) mouseY), 12, SCROLLBAR_HEIGHT))
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
        return Math.max(0, (int) (Math.ceil(this.displayRecipes.size() / (double) RECIPES_PER_ROW) * BUTTON_SIZE) - WINDOW_HEIGHT);
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

    @Override
    public void setFocused(@Nullable GuiEventListener listener)
    {
        if(listener == this.craftableOnlyButton)
            return;
        super.setFocused(listener);
    }

    private class CraftableButton extends StateSwitchingButton
    {
        public CraftableButton(int x, int y, int width, int height, boolean state)
        {
            super(x, y, width, height, state);
        }

        @Override
        public void onClick(double mouseX, double mouseY)
        {
            this.isStateTriggered = !this.isStateTriggered;
            WorkbenchScreen.craftableOnly = this.isStateTriggered;
            WorkbenchScreen.this.updateRecipes();
        }
    }
}
