package com.mrcrayfish.furniture.refurbished.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mrcrayfish.framework.api.event.ClientConnectionEvents;
import com.mrcrayfish.framework.api.event.ScreenEvents;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.client.util.VanillaTextures;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModCreativeTabs;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModTags;
import com.mrcrayfish.furniture.refurbished.platform.ClientServices;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class CreativeFilters
{
    private static CreativeFilters instance;

    public static CreativeFilters get()
    {
        if(instance == null)
        {
            instance = new CreativeFilters();
        }
        return instance;
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
        builder.add(new FilterCategory(ModTags.Items.GENERAL, new ItemStack(ModBlocks.CHAIR_OAK.get())));
        builder.add(new FilterCategory(ModTags.Items.BEDROOM, new ItemStack(ModBlocks.DRAWER_MANGROVE.get())));
        builder.add(new FilterCategory(ModTags.Items.KITCHEN, new ItemStack(ModBlocks.KITCHEN_SINK_YELLOW.get())));
        builder.add(new FilterCategory(ModTags.Items.OUTDOORS, new ItemStack(ModBlocks.GRILL_RED.get())));
        builder.add(new FilterCategory(ModTags.Items.BATHROOM, new ItemStack(ModBlocks.TOILET_OAK.get())));
        builder.add(new FilterCategory(ModTags.Items.ELECTRONICS, new ItemStack(ModBlocks.ELECTRICITY_GENERATOR_LIGHT.get())));
        builder.add(new FilterCategory(ModTags.Items.STORAGE, new ItemStack(ModBlocks.CRATE_BIRCH.get())));
        builder.add(new FilterCategory(ModTags.Items.FOOD, new ItemStack(ModItems.SWEET_BERRY_JAM_TOAST.get())));
        builder.add(new FilterCategory(ModTags.Items.ITEMS, new ItemStack(ModItems.SPATULA.get())));
        this.categories = builder.build();

        /* Initializes and injects widgets into the creative mode screen for the filter system */
        ScreenEvents.MODIFY_WIDGETS.register((screen, widgets, add, remove) -> {
            if(screen instanceof CreativeModeInventoryScreen creativeScreen) {
                this.guiLeft = ClientServices.PLATFORM.getGuiLeft(creativeScreen);
                this.guiTop = ClientServices.PLATFORM.getGuiTop(creativeScreen);
                this.categories.forEach(FilterCategory::loadItems);
                this.injectWidgets(creativeScreen, add);
            }
        });

        /* Handles removing widget from memory when screen is closed */
        ScreenEvents.CLOSED.register(screen -> {
            if(screen instanceof CreativeModeInventoryScreen) {
                this.categories.forEach(category -> {
                    this.scrollUpButton = null;
                    this.scrollDownButton = null;
                    category.setFilterTab(null);
                });
            }
        });

        /* Handles sending an event when the current creative mode tab is changed */
        ScreenEvents.AFTER_DRAW.register((screen, graphics, mouseX, mouseY, partialTick) -> {
            if(screen instanceof CreativeModeInventoryScreen creativeScreen) {
                CreativeModeTab tab = ClientServices.PLATFORM.getSelectedCreativeModeTab();
                if(this.lastTab != tab) {
                    this.onSwitchCreativeTab(tab, creativeScreen);
                    this.lastTab = tab;
                }
            }
        });

        /* Handles resetting categories when the local player exits the world */
        ClientConnectionEvents.LOGGING_OUT.register(player -> {
            this.categories.forEach(category -> {
                category.resetItems();
                category.setEnabled(true);
            });
        });
    }

    /**
     * Injects custom widgets into the creative mode screen to provide the interface for the
     * filtering system.
     * @param screen the instance of the creative mode screen
     * @param add a consumer which handles adding widgets to the screen
     */
    private void injectWidgets(CreativeModeInventoryScreen screen, Consumer<AbstractWidget> add)
    {
        this.categories.forEach(category -> {
            FilterTab tab = new FilterTab(this.guiLeft - 28, this.guiTop, category, btn -> {
                // Holding ctrl down will allow multiple categories to be enabled
                if(Screen.hasControlDown() || Screen.hasShiftDown()) {
                    category.setEnabled(!category.isEnabled());
                } else {
                    this.categories.forEach(c -> c.setEnabled(false));
                    category.setEnabled(true);
                }
                this.updateItems(screen);
            });
            tab.visible = false;
            add.accept(tab);
        });
        add.accept(this.scrollUpButton = new IconButton(this.guiLeft - 22, this.guiTop - 12, 0, 0, btn -> {
            if(this.scroll > 0) this.scroll--;
            this.updateWidgets();
        }));
        add.accept(this.scrollDownButton = new IconButton(this.guiLeft - 22, this.guiTop + 127, 10, 0, btn -> {
            if(this.scroll <= this.categories.size() - 4 - 1) this.scroll++;
            this.updateWidgets();
        }));
        this.updateWidgets();
        this.onSwitchCreativeTab(ClientServices.PLATFORM.getSelectedCreativeModeTab(), screen);
    }

    /**
     * Updates the items shown in the creative mode item list to only show items from
     * categories that are enabled.
     * @param screen the instance of the creative mode screen
     */
    private void updateItems(CreativeModeInventoryScreen screen)
    {
        Set<Item> seenItems = new HashSet<>();
        LinkedHashSet<ItemStack> categorisedItems = new LinkedHashSet<>();
        Services.PLATFORM.getCreativeModeTab().getDisplayItems().forEach(stack -> {
            this.categories.stream().filter(FilterCategory::isEnabled).forEach(category -> {
                Item item = stack.getItem();
                if(!seenItems.contains(item) && stack.is(category.tag)) {
                    categorisedItems.add(stack.copy());
                    seenItems.add(item);
                }
            });
        });
        NonNullList<ItemStack> items = screen.getMenu().items;
        items.clear();
        items.addAll(categorisedItems);
        screen.getMenu().scrollTo(0);
    }

    /**
     * Updates the visibility, state, and position of the custom widgets. This includes
     * the filter tabs and scroll buttons. This method is called when either scroll button
     * is pressed.
     */
    private void updateWidgets()
    {
        this.categories.forEach(category -> category.setVisible(false));
        for(int i = this.scroll; i < this.scroll + 4 && i < this.categories.size(); i++)
        {
            FilterCategory category = this.categories.get(i);
            category.setY(this.guiTop + 29 * (i - this.scroll) + 11);
            category.setVisible(true);
        }
        this.scrollUpButton.active = this.scroll > 0;
        this.scrollDownButton.active = this.scroll <= this.categories.size() - 4 - 1;
    }

    /**
     * Called when the creative mode tab is switched to a different tab.
     *
     * @param tab    the new creative mode tab
     * @param screen the instance of the creative mode screen
     */
    private void onSwitchCreativeTab(CreativeModeTab tab, CreativeModeInventoryScreen screen)
    {
        boolean isFurnitureTab = tab == Services.PLATFORM.getCreativeModeTab();
        this.scrollUpButton.visible = isFurnitureTab;
        this.scrollDownButton.visible = isFurnitureTab;
        if(isFurnitureTab)
        {
            this.updateWidgets();
            this.updateItems(screen);
            return;
        }
        this.categories.forEach(category -> category.setVisible(false));
    }

    /**
     * Called when the mouse scrolls
     *
     * @param mouseX the x position of the mouse at the time of the scroll event
     * @param mouseY the y position of the mouse at the time of the scroll event
     * @param scroll the amount that was scrolled
     * @return True if the event was handled
     */
    public boolean onMouseScroll(double mouseX, double mouseY, double scroll)
    {
        CreativeModeTab selectedTab = ClientServices.PLATFORM.getSelectedCreativeModeTab();
        if(selectedTab != Services.PLATFORM.getCreativeModeTab())
            return false;

        double startX = this.guiLeft - 28;
        double startY = this.guiTop + 29;
        if(mouseX >= startX && mouseX < startX + 28 && mouseY >= startY && mouseY < startY + 113)
        {
            int oldScroll = this.scroll;
            this.scroll += scroll > 0 ? -1 : 1;
            this.scroll = Mth.clamp(this.scroll, 0, this.categories.size() - 4);
            if(this.scroll != oldScroll)
            {
                this.updateWidgets();
            }
            return true;
        }
        return false;
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

        /**
         * @return The item tag associated with this filter category
         */
        public TagKey<Item> getTag()
        {
            return this.tag;
        }

        /**
         * @return The item to show on the filter tab
         */
        public ItemStack getIcon()
        {
            return this.icon;
        }

        /**
         * Gets the items that were found to contain the tag that match the
         * tag provided in this filter category.
         * @return an optional list of items, only present if items have been loaded
         */
        public Optional<List<Item>> getItems()
        {
            return Optional.ofNullable(this.items);
        }

        /**
         * Sets the state of this filter category. This will determine if the items
         * matching this filter categories' tag will be shown in the creative mode item list.
         * @param enabled true if items should be shown
         */
        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        /**
         * Returns the state of this filter category. Used when updating the items
         * shown in the creative mode screen item list.
         * @return True if this filter category is enabled
         */
        public boolean isEnabled()
        {
            return this.enabled;
        }

        /**
         * Sets the filter tab associated with this filter category
         * @param filterTab the instance of the filter tab widget
         */
        public void setFilterTab(@Nullable FilterTab filterTab)
        {
            this.filterTab = filterTab;
        }

        /**
         * Sets the visibility of the filter tab widget for this category
         * @param visible true to make the filter tab visible
         */
        public void setVisible(boolean visible)
        {
            if(this.filterTab != null)
            {
                this.filterTab.visible = visible;
            }
        }

        /**
         * Sets the y position of the filter tab widget for this category
         * @param y the new y position
         */
        public void setY(int y)
        {
            if(this.filterTab != null)
            {
                this.filterTab.setY(y);
            }
        }

        /**
         * If items aren't initialized, finds all items in the game registry that have
         * the tag that matches the one of tag provided in this filter category. Found items
         * will be added to a list and cached.
         */
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

        /**
         * Resets the items cache
         */
        public void resetItems()
        {
            this.items = null;
        }
    }

    private static class FilterTab extends Button
    {
        private final FilterCategory category;

        protected FilterTab(int x, int y, FilterCategory category, OnPress onPress)
        {
            super(x, y, 32, 26, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.category = category;
            category.setFilterTab(this);
            ResourceLocation tagId = category.getTag().location();
            String tooltipTitle = String.format("filterCategory.%s.%s", tagId.getNamespace(), tagId.getPath().replace("/", "."));
            String tooltipDesc = tooltipTitle + ".desc";
            this.setTooltip(ScreenHelper.createMultilineTooltip(List.of(Component.translatable(tooltipTitle), Component.translatable(tooltipDesc).withStyle(ChatFormatting.GRAY))));
        }

        @Override
        public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
        {
            int textureX = 26;
            int textureY = this.category.isEnabled() ? 32 : 0;
            int textureWidth = this.category.isEnabled() ? 32 : 28;
            int textureHeight = 26;
            RenderSystem.setShaderTexture(0, VanillaTextures.CREATIVE_TABS);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            this.drawRotatedTexture(poseStack.last().pose(), this.getX(), this.getY(), textureX, textureY, textureWidth, textureHeight);
            ScreenHelper.drawItem(poseStack, this.category.getIcon(), this.getX() + 8, this.getY() + 5);
        }

        /**
         * Draws a texture that is rotated 90 degrees counter-clockwise.
         */
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

        @Override
        protected ClientTooltipPositioner createTooltipPositioner()
        {
            return DefaultTooltipPositioner.INSTANCE;
        }
    }
}
