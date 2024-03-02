package com.mrcrayfish.furniture.refurbished.client.gui.overlay;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardCombiningRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardHelperOverlay implements IHudOverlay
{
    private static final int COLUMNS = 7;
    private static final int TITLE_HEIGHT = 13;

    @Override
    public void draw(GuiGraphics graphics, float partialTick)
    {
        if(!Config.CLIENT.showCuttingBoardHelper.get())
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null || mc.hitResult == null || mc.player == null)
            return;

        if(!(mc.hitResult instanceof BlockHitResult result))
            return;

        BlockEntity entity = mc.level.getBlockEntity(result.getBlockPos());
        if(!(entity instanceof CuttingBoardBlockEntity cuttingBoard))
            return;

        int placeIndex = cuttingBoard.getPlaceIndex();
        Container container = new SimpleContainer(placeIndex);
        IntStream.range(0, placeIndex).forEach(index -> container.setItem(index, cuttingBoard.getItem(index)));
        List<RecipeHolder<CuttingBoardCombiningRecipe>> recipes = mc.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CUTTING_BOARD_COMBINING.get());

        // Get stream of combinable recipes and filter recipes that match the currently placed items
        Stream<Item> combinable = recipes.stream()
            .filter(holder -> holder.value().matches(container, mc.level) && placeIndex < holder.value().getIngredients().size())
            .flatMap(holder -> Stream.of(holder.value().getIngredients().get(placeIndex).getItems()))
            .map(ItemStack::getItem);

        // Get stream of slicing recipes
        Stream<Item> sliceable = mc.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CUTTING_BOARD_SLICING.get()).stream()
            .flatMap(holder -> Stream.of(holder.value().getIngredients().get(0).getItems()))
            .map(ItemStack::getItem);

        Stream<Item> items = placeIndex == 0 ? Stream.concat(combinable, sliceable) : combinable;
        List<Item> placeable = items.distinct()
            .sorted(Comparator.comparing(Item::getId))
            .toList();

        boolean drawPlaceableElement = !placeable.isEmpty();
        boolean drawSliceableElement = this.canSlice(cuttingBoard);
        int areaWidth = COLUMNS * 18 + 8;
        int areaHeight = this.getAreaHeight(placeable, drawPlaceableElement, drawSliceableElement);
        int areaStart = 5;
        int areaTop = (graphics.guiHeight() - areaHeight) / 2;

        if(drawPlaceableElement)
        {
            int elementHeight = this.getPlaceableHeight(placeable);
            ScreenHelper.fillRounded(graphics, areaStart, areaTop, areaWidth, elementHeight, 0x99000000);
            ScreenHelper.fillRounded(graphics, areaStart, areaTop, areaWidth, TITLE_HEIGHT, 0x99000000);
            graphics.drawCenteredString(Minecraft.getInstance().font, Components.GUI_PLACEABLE, areaStart + areaWidth / 2, areaTop + 2, 0xFFFFFFFF);
            for(int i = 0; i < placeable.size(); i++)
            {
                int x = areaStart + (i % COLUMNS) * 18 + 1 + 4;
                int y = areaTop + (i / COLUMNS) * 18 + 1 + 4 + TITLE_HEIGHT;
                graphics.renderFakeItem(new ItemStack(placeable.get(i)), x, y);
            }
        }

        if(drawSliceableElement)
        {
            Component sliceableLabel = Components.GUI_SLICEABLE;
            int elementHeight = 18;
            int contentWidth = 16 + 2 + Minecraft.getInstance().font.width(sliceableLabel);
            int contentTop = areaTop + areaHeight - elementHeight;
            int labelStart = areaStart + (areaWidth - contentWidth) / 2;
            ScreenHelper.fillRounded(graphics, areaStart, contentTop, areaWidth, elementHeight, 0x9937AE37);
            graphics.renderFakeItem(new ItemStack(ModItems.KNIFE.get()), labelStart, contentTop + 1);
            graphics.drawString(Minecraft.getInstance().font, sliceableLabel, labelStart + 18, contentTop + 5, 0xFFFFFFFF);
        }
    }

    private int getAreaHeight(List<Item> placeable, boolean combining, boolean slicing)
    {
        int height = 0;
        if(combining) height += this.getPlaceableHeight(placeable);
        if(slicing) height += 18;
        if(combining && slicing) height += 5;
        return height;
    }

    private int getPlaceableHeight(List<Item> placeable)
    {
        return ((placeable.size() / COLUMNS) + 1) * 18 + 1 + 8 + TITLE_HEIGHT;
    }

    private boolean canSlice(CuttingBoardBlockEntity entity)
    {
        ItemStack placedItem = entity.getItem(0);
        if(entity.getHeadIndex() == 0 && !placedItem.isEmpty())
        {
            Level level = Objects.requireNonNull(entity.getLevel());
            return level.getRecipeManager().getRecipeFor(ModRecipeTypes.CUTTING_BOARD_SLICING.get(), new SimpleContainer(placedItem), level).isPresent();
        }
        return false;
    }
}
