package com.mrcrayfish.furniture.refurbished.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.framework.api.event.ClientConnectionEvents;
import com.mrcrayfish.framework.api.event.ScreenEvents;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CreativeFilters
{
    private static CreativeFilters instance;

    public static void init()
    {
        if(instance == null)
        {
            instance = new CreativeFilters();
        }
    }

    private final List<FilterCategory> categories;
    private AbstractWidget scrollUpButton;
    private AbstractWidget scrollDownButton;
    private CreativeModeTab lastTab;
    private int guiLeft;
    private int guiTop;
    private int scroll;

    private CreativeFilters()
    {
        ImmutableList.Builder<FilterCategory> builder = ImmutableList.builder();
        builder.add(new FilterCategory(ModTags.Items.GENERAL, new ItemStack(Items.STICK)));
        builder.add(new FilterCategory(ModTags.Items.BEDROOM, new ItemStack(Items.STICK)));
        builder.add(new FilterCategory(ModTags.Items.KITCHEN, new ItemStack(Items.STICK)));
        builder.add(new FilterCategory(ModTags.Items.OUTDOORS, new ItemStack(Items.STICK)));
        builder.add(new FilterCategory(ModTags.Items.STORAGE, new ItemStack(Items.STICK)));
        builder.add(new FilterCategory(ModTags.Items.ITEMS, new ItemStack(Items.STICK)));
        this.categories = builder.build();

        ScreenEvents.MODIFY_WIDGETS.register((screen, widgets, add, remove) -> {
            if(screen instanceof CreativeModeInventoryScreen creativeScreen) {
                this.guiLeft = ClientServices.PLATFORM.getGuiLeft(creativeScreen);
                this.guiTop = ClientServices.PLATFORM.getGuiTop(creativeScreen);
                this.categories.forEach(FilterCategory::loadItems);
                this.injectWidgets(creativeScreen, add);
            }
        });
        ScreenEvents.CLOSED.register(screen -> {
            if(screen instanceof CreativeModeInventoryScreen) {
                this.categories.forEach(category -> {
                    this.scrollUpButton = null;
                    this.scrollDownButton = null;
                    category.setFilterTab(null);
                });
            }
        });
        ScreenEvents.AFTER_DRAW.register((screen, graphics, mouseX, mouseY, partialTick) -> {
            if(screen instanceof CreativeModeInventoryScreen creativeScreen) {
                CreativeModeTab tab = ClientServices.PLATFORM.getSelectedCreativeModeTab();
                if(this.lastTab != tab) {
                    this.onSwitchCreativeTab(tab, creativeScreen);
                    this.lastTab = tab;
                }
            }
        });
        ClientConnectionEvents.LOGGING_OUT.register(player -> {
            this.categories.forEach(category -> {
                category.resetItems();
                category.setEnabled(true);
            });
        });
    }

    private void injectWidgets(CreativeModeInventoryScreen screen, Consumer<AbstractWidget> add)
    {
        this.categories.forEach(category -> {
            FilterTab tab = new FilterTab(this.guiLeft - 28, this.guiTop, category, btn -> {
                category.setEnabled(!category.isEnabled());
                ((FilterTab) btn).setEnabled(category.isEnabled());
                this.updateItems(screen);
            });
            tab.visible = false;
            add.accept(tab);
        });
        add.accept(this.scrollUpButton = new IconButton(this.guiLeft - 22, this.guiTop - 12, 0, 0, btn -> {
            if(this.scroll > 0) this.scroll--;
            this.updateFilterTabs();
        }));
        add.accept(this.scrollDownButton = new IconButton(this.guiLeft - 22, this.guiTop + 127, 10, 0, btn -> {
            if(this.scroll <= this.categories.size() - 4 - 1) this.scroll++;
            this.updateFilterTabs();
        }));
        this.updateFilterTabs();
    }

    private void updateItems(CreativeModeInventoryScreen screen)
    {
        LinkedHashSet<ItemStack> categorisedItems = new LinkedHashSet<>();
        this.categories.stream().filter(FilterCategory::isEnabled).forEach(category -> {
            category.getItems().ifPresent(items -> items.forEach(item -> {
                categorisedItems.add(new ItemStack(item)); // TODO allow items to fill (e.g. for the trampoline)
            }));
        });
        NonNullList<ItemStack> items = screen.getMenu().items;
        items.clear();
        items.addAll(categorisedItems);
        items.sort(Comparator.comparingInt(o -> Item.getId(o.getItem())));
        screen.getMenu().scrollTo(0);
    }

    private void updateFilterTabs()
    {
        this.categories.forEach(category -> category.getFilterTab().visible = false);
        for(int i = this.scroll; i < this.scroll + 4 && i < this.categories.size(); i++)
        {
            FilterTab filterTab = this.categories.get(i).getFilterTab();
            filterTab.setY(this.guiTop + 29 * (i - this.scroll) + 11);
            filterTab.visible = true;
        }
        this.scrollUpButton.active = this.scroll > 0;
        this.scrollDownButton.active = this.scroll <= this.categories.size() - 4 - 1;
    }

    private void onSwitchCreativeTab(CreativeModeTab tab, CreativeModeInventoryScreen screen)
    {
        boolean isFurnitureTab = tab == ModCreativeTabs.MAIN.get();
        this.scrollUpButton.visible = isFurnitureTab;
        this.scrollDownButton.visible = isFurnitureTab;
        if(isFurnitureTab)
        {
            this.updateFilterTabs();
            this.updateItems(screen);
            return;
        }
        this.categories.forEach(category -> category.getFilterTab().visible = false);
    }

    public static class FilterCategory
    {
        private final TagKey<Item> tag;
        private final ItemStack icon;
        private List<Item> items;
        private boolean enabled = true;
        private FilterTab filterTab;

        public FilterCategory(TagKey<Item> tag, ItemStack icon)
        {
            this.tag = tag;
            this.icon = icon;
        }

        public TagKey<Item> getTag()
        {
            return this.tag;
        }

        public ItemStack getIcon()
        {
            return this.icon;
        }

        public Optional<List<Item>> getItems()
        {
            return Optional.ofNullable(this.items);
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }

        public void setFilterTab(@Nullable FilterTab filterTab)
        {
            this.filterTab = filterTab;
        }

        public FilterTab getFilterTab()
        {
            return this.filterTab;
        }

        public void loadItems()
        {
            if(this.items != null)
                return;
            this.items = new ArrayList<>();
            BuiltInRegistries.ITEM.stream().forEach(item -> {
                if(item.builtInRegistryHolder().is(this.getTag())){
                    this.items.add(item);
                }
            });
        }

        public void resetItems()
        {
            this.items = null;
        }
    }

    private static class FilterTab extends Button
    {
        private final ItemStack icon;
        private boolean enabled;

        protected FilterTab(int x, int y, FilterCategory category, OnPress onPress)
        {
            super(x, y, 32, 26, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.icon = category.getIcon();
            this.enabled = category.isEnabled();
            category.setFilterTab(this);
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
        {
            int textureX = 26;
            int textureY = this.enabled ? 32 : 0;
            int textureWidth = this.enabled ? 32 : 28;
            int textureHeight = 26;
            RenderSystem.setShaderTexture(0, VanillaTextures.CREATIVE_TABS);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            this.drawRotatedTexture(graphics.pose().last().pose(), this.getX(), this.getY(), textureX, textureY, textureWidth, textureHeight);
            graphics.renderItem(this.icon, this.getX() + 8, this.getY() + 5);
        }

        private void drawRotatedTexture(Matrix4f matrix4f, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight)
        {
            float scaleX = (float) 1 / 256;
            float scaleY = (float) 1 / 256;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            builder.vertex(matrix4f, x, y + textureHeight, 0).uv((float) (textureX + textureHeight) * scaleX, (float) textureY * scaleY).endVertex();
            builder.vertex(matrix4f, x + textureWidth, y + textureHeight, 0).uv((float) (textureX + textureHeight) * scaleX, ((float) textureY + textureWidth) * scaleY).endVertex();
            builder.vertex(matrix4f, x + textureWidth, y, 0).uv((float) textureX * scaleX, (float) (textureY + textureWidth) * scaleY).endVertex();
            builder.vertex(matrix4f, x, y, 0).uv((float) textureX * scaleX, (float) textureY * scaleY).endVertex();
            BufferUploader.drawWithShader(builder.end());
        }
    }
}
