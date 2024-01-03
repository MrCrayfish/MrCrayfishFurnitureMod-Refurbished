package com.mrcrayfish.furniture.refurbished.compat.jei.categories;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.compat.jei.Plugin;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.crafting.StackedIngredient;
import com.mrcrayfish.furniture.refurbished.crafting.WorkbenchCraftingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.Pair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class WorkbenchCraftingCategory extends FurnitureRecipeCategory<WorkbenchCraftingRecipe>
{
    public static final RecipeType<WorkbenchCraftingRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "workbench_crafting", WorkbenchCraftingRecipe.class);

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private List<Pair<Vector2i, IDrawable>> slots = new ArrayList<>();

    public WorkbenchCraftingCategory(IGuiHelper helper)
    {
        this.helper = helper;
        this.background = helper.createDrawable(Plugin.TEXTURES_2, 0, 0, 118, 64);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.WORKBENCH.get()));
    }

    @Override
    public RecipeType<WorkbenchCraftingRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "workbench_crafting");
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WorkbenchCraftingRecipe recipe, IFocusGroup focuses)
    {
        this.slots.clear();
        NonNullList<StackedIngredient> materials = recipe.getMaterials();
        int left = 6;
        int top = 6;
        int slotSize = 18;
        int boxSize = 54;
        int width = slotSize * Mth.clamp(materials.size(), 1, 3);
        int height = slotSize * Mth.clamp(Mth.ceil(materials.size() / (float) 3), 1, 3);
        for(int i = 0; i < materials.size(); i++)
        {
            int x = left + (i % 3) * slotSize + (boxSize - width) / 2;
            int y = top + (i / 3) * slotSize + (boxSize - height) / 2;
            StackedIngredient material = materials.get(i);
            List<ItemStack> stacks = Arrays.stream(material.ingredient().getItems()).map(stack -> {
                ItemStack copy = stack.copy();
                copy.setCount(material.count());
                return copy;
            }).toList();
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(stacks);
            this.slots.add(Pair.of(new Vector2i(x - 1, y - 1), this.helper.createDrawable(Plugin.TEXTURES_2, 0, 64, 18, 18)));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 24).addItemStack(Plugin.getResult(recipe));
    }

    @Override
    public void draw(WorkbenchCraftingRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY)
    {
        this.slots.forEach(pair -> {
            Vector2i pos = pair.left();
            pair.right().draw(graphics, pos.x(), pos.y());
        });
    }
}
