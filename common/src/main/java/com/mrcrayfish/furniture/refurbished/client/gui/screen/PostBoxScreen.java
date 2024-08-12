package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendPackage;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.GameType;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class PostBoxScreen extends AbstractContainerScreen<PostBoxMenu>
{
    private static final Component MAILBOXES_LABEL = Utils.translation("gui", "mailboxes");
    private static final ResourceLocation POST_BOX_TEXTURE = Utils.resource("textures/gui/container/post_box.png");
    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
    private static final List<IMailbox> MAILBOX_CACHE = new ArrayList<>();
    private static final Map<UUID, PlayerInfo> PLAYER_INFO_CACHE = new HashMap<>();

    private static final int SCROLL_SPEED = 5;
    private static final int SCROLL_BAR_WIDTH = 6;
    private static final int SCROLL_BAR_HEIGHT = 27;
    private static final int MAILBOX_ENTRY_WIDTH = 85;
    private static final int MAILBOX_ENTRY_HEIGHT = 14;
    private static final int CONTAINER_LEFT = 8;
    private static final int CONTAINER_TOP = 34;
    private static final int CONTAINER_HEIGHT = 130;
    private static final int CONTAINER_WIDTH = 85;
    private static final int MAX_VISIBLE_ITEMS = Mth.ceil((double) CONTAINER_HEIGHT / MAILBOX_ENTRY_HEIGHT) + 1;

    protected List<IMailbox> mailboxes = new ArrayList<>();
    protected IMailbox selected;
    protected EditBox searchEditBox;
    protected String query = "";
    protected MultiLineEditBox messageEditBox;
    protected Button sendButton;
    protected String message = "";
    protected int scroll;
    protected int clickedY = -1;
    protected List<FormattedCharSequence> tooltip;

    public PostBoxScreen(PostBoxMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, Component.empty());
        this.imageWidth = 283;
        this.imageHeight = 172;
        this.inventoryLabelX = 113;
        this.inventoryLabelY = this.imageHeight - 93;
        this.updateSearchFilter();
    }

    @Override
    protected void init()
    {
        super.init();

        this.addRenderableWidget(this.searchEditBox = new EditBox(this.font, this.leftPos + 8, this.topPos + 18, 92, 12, Utils.translation("gui", "search_mailboxes")));
        this.searchEditBox.setValue("");
        this.searchEditBox.setResponder(s -> {
            this.query = s;
            this.updateSearchFilter();
            this.scroll(0);
        });
        if(!this.query.isBlank())
        {
            this.searchEditBox.setValue(this.query);
        }

        this.addRenderableWidget(this.messageEditBox = new MultiLineEditBox(this.font, this.leftPos + 118, this.topPos + 13, 116, 54, Utils.translation("gui", "enter_message"), Utils.translation("gui", "package_message")) {
            @Override
            protected void renderDecorations(PoseStack poseStack) {}

            @Override
            protected boolean scrollbarVisible()
            {
                return false;
            }
        });
        if(!this.message.isBlank()) {
            this.messageEditBox.setValue(this.message);
        } else {
            this.messageEditBox.setValue("");
        }
        this.messageEditBox.setValueListener(s -> this.message = s);

        this.addRenderableWidget(this.sendButton = new IconButton(this.leftPos + 284, this.topPos + 22, 20, 0, 18, 18, CommonComponents.EMPTY, btn -> {
            if(this.selected != null) {
                Network.getPlay().sendToServer(new MessageSendPackage(this.selected.getId(), this.message));
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
            }
        }, (btn, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, Utils.translation("gui", "send"), mouseX, mouseY);
        }));
    }

    @Override
    protected void containerTick()
    {
        this.searchEditBox.tick();
        this.messageEditBox.tick();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        this.searchEditBox.setSuggestion(this.searchEditBox.getValue().isBlank() ? Utils.translation("gui", "search").getString() : "");
        this.sendButton.active = this.selected != null && !this.menu.getContainer().isEmpty();
        this.searchEditBox.setTextColor(this.searchEditBox.getValue().isEmpty() && !this.searchEditBox.isFocused() ? 0x707070 : 0xE0E0E0);
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        super.renderLabels(poseStack, mouseX, mouseY);
        ScreenHelper.drawString(poseStack, MAILBOXES_LABEL, this.titleLabelX, this.titleLabelY, 0xFFE0E0E0, false);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        this.tooltip = null;

        RenderSystem.setShaderTexture(0, POST_BOX_TEXTURE);
        GuiComponent.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth + 25, this.imageHeight, 512, 256);

        // Draw mailboxes list
        GuiComponent.enableScissor(this.leftPos + CONTAINER_LEFT, this.topPos + CONTAINER_TOP, this.leftPos + CONTAINER_LEFT + CONTAINER_WIDTH, this.topPos + CONTAINER_TOP + CONTAINER_HEIGHT);
        int scroll = this.clampScroll(this.scroll + this.getDeltaScroll(mouseY));
        int startIndex = Mth.clamp(scroll / MAILBOX_ENTRY_HEIGHT, 0, Math.max(0, this.mailboxes.size() - 1 - MAX_VISIBLE_ITEMS));
        int maxItems = Math.min(MAX_VISIBLE_ITEMS, this.mailboxes.size());
        for(int i = 0; i < maxItems; i++)
        {
            int entryIndex = startIndex + i;
            int entryX = this.leftPos + CONTAINER_LEFT;
            int entryY = this.topPos + CONTAINER_TOP + entryIndex * MAILBOX_ENTRY_HEIGHT - scroll;
            IMailbox mailbox = this.mailboxes.get(entryIndex);
            boolean selected = this.selected == mailbox;

            // Draw the background of the mailbox entry
            RenderSystem.setShaderTexture(0, POST_BOX_TEXTURE);
            GuiComponent.blit(poseStack, entryX, entryY, 0, selected ? 172 : 186, MAILBOX_ENTRY_WIDTH, MAILBOX_ENTRY_HEIGHT, 512, 256);

            // Draw the face of the player's skin
            Optional<GameProfile> optional = mailbox.getOwner();
            if(optional.isPresent())
            {
                PlayerInfo info = this.getPlayerInfo(optional.get());
                RenderSystem.setShaderTexture(0, info.getSkinLocation());
                PlayerFaceRenderer.draw(poseStack, entryX + 3, entryY + 3, 8);
            }

            // Draw the name of the mailbox
            String mailboxName = mailbox.getCustomName().orElse("Mailbox");
            ScreenHelper.drawString(poseStack, mailboxName, entryX + 15, entryY + 3, selected ? 0xFFFFFF55 : 0xFFFFFFFF, true);

            // Create a tooltip of the owners username if the cursor hovers the face image
            if(this.isHovering((entryX - this.leftPos) + 3, (entryY - this.topPos) + 3, 8, 8, mouseX, mouseY))
            {
                String ownerName = mailbox.getOwner().map(GameProfile::getName).orElse("Unknown Player");
                this.tooltip = ScreenHelper.createMultilineTooltip(List.of(Component.literal(ownerName)));
            }
        }
        GuiComponent.disableScissor();

        // Draw scroll bar
        RenderSystem.setShaderTexture(0, VILLAGER_TEXTURE);
        GuiComponent.blit(poseStack, this.leftPos + CONTAINER_LEFT + CONTAINER_WIDTH + 1, this.topPos + CONTAINER_TOP + this.getScrollBarOffset(mouseY), this.canScroll() ? 0 : SCROLL_BAR_WIDTH, 199, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT, 512, 256);

        // Draw icons in item slots
        for(int j = 0; j < 3; j++)
        {
            for(int i = 0; i < 2; i++)
            {
                if(this.menu.getContainer().getItem(j * 2 + i).isEmpty())
                {
                    RenderSystem.setShaderTexture(0, POST_BOX_TEXTURE);
                    GuiComponent.blit(poseStack, this.leftPos + 235 + i * 18, this.topPos + 14 + j * 18, 85, 172, 16, 16, 512, 256);
                }
            }
        }

        if(this.isHovering(91, 5, 10, 10, mouseX, mouseY))
        {
            this.tooltip = ScreenHelper.createMultilineTooltip(List.of(Utils.translation("gui", "how_to").withStyle(ChatFormatting.GOLD), Utils.translation("gui", "post_box_info")));
        }
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        if(this.tooltip != null)
        {
            this.renderTooltip(poseStack, this.tooltip, mouseX, mouseY);
            return;
        }
        super.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1)
        {
            this.setFocused(null);
            if(this.isHovering(CONTAINER_LEFT, CONTAINER_TOP, CONTAINER_WIDTH, CONTAINER_HEIGHT, mouseX, mouseY))
            {
                int relativeMouseY = (int) (mouseY - this.topPos - CONTAINER_TOP);
                int clickedIndex = (this.scroll + relativeMouseY) / MAILBOX_ENTRY_HEIGHT;
                if(clickedIndex >= 0 && clickedIndex < this.mailboxes.size())
                {
                    IMailbox mailbox = this.mailboxes.get(clickedIndex);
                    this.selected = this.selected != mailbox ? mailbox : null;
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PUT, 1.0F, 1.0F));
                    this.sendButton.active = this.selected != null;
                    return true;
                }
            }
            // Record the mouse position when clicking on the scroll bar
            if(this.isHovering(CONTAINER_LEFT + CONTAINER_WIDTH + 1, CONTAINER_TOP + this.getScrollBarOffset((int) mouseY), SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT, mouseX, mouseY))
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
        if(button == GLFW.GLFW_MOUSE_BUTTON_1)
        {
            if(this.clickedY >= 0)
            {
                this.scroll(this.getDeltaScroll((int) mouseY));
                this.clickedY = -1;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers)
    {
        if(this.searchEditBox.isFocused())
        {
            return this.searchEditBox.keyPressed(key, scanCode, modifiers);
        }
        if(this.messageEditBox.isFocused())
        {
            return this.messageEditBox.keyPressed(key, scanCode, modifiers);
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount)
    {
        if(this.isHovering(CONTAINER_LEFT, CONTAINER_TOP, CONTAINER_WIDTH, CONTAINER_HEIGHT, mouseX, mouseY))
        {
            this.scroll((int) (-SCROLL_SPEED * amount));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    /**
     * Applies the given amount to scroll. This method will automatically
     * clamp to prevent over scrolling.
     *
     * @param amount the amount to scroll
     */
    private void scroll(int amount)
    {
        this.scroll = this.clampScroll(this.scroll + amount);
    }

    /**
     * Clamps the scroll between zero and the maximum scroll value
     * @param scroll the current scroll
     * @return the clamped scroll
     */
    private int clampScroll(int scroll)
    {
        return Mth.clamp(scroll, 0, this.getMaxScroll());
    }

    /**
     * @return The maximum value that can be scrolled
     */
    private int getMaxScroll()
    {
        return Math.max(this.mailboxes.size() * MAILBOX_ENTRY_HEIGHT - CONTAINER_HEIGHT, 0);
    }

    /**
     * @return True if the scroll bar can be used
     */
    private boolean canScroll()
    {
        return this.getMaxScroll() > 0;
    }

    /**
     * Gets the scroll bar offset with consideration to the delta scroll.
     *
     * @param mouseY the y position of the mouse
     * @return the y scroll bar offset
     */
    private int getScrollBarOffset(int mouseY)
    {
        int scroll = this.clampScroll(this.scroll + this.getDeltaScroll(mouseY));
        return (int) ((CONTAINER_HEIGHT - SCROLL_BAR_HEIGHT) * (scroll / (double) this.getMaxScroll()));
    }

    /**
     * Gets the delta scroll amount when the cursor is dragging the scroll bar. If not dragging
     * the scroll bar, zero will simply be returned.
     *
     * @param mouseY the y position of the mouse
     * @return the delta scroll amount
     */
    private int getDeltaScroll(int mouseY)
    {
        if(this.clickedY != -1)
        {
            double pixelsPerScroll = (double) (CONTAINER_HEIGHT - SCROLL_BAR_HEIGHT) / this.getMaxScroll();
            return (int) ((mouseY - this.clickedY) / pixelsPerScroll);
        }
        return 0;
    }

    /**
     * Updates the mailboxes based on the search query. There is a special case where if the query
     * starts with "@" that it will instead search the owner name instead of the mailbox name. The
     * mailboxes are then sorted by the owner name followed by the mailbox name; this makes it
     * easier for players to identify mailboxes from other players.
     */
    private void updateSearchFilter()
    {
        List<IMailbox> filteredMailboxes = MAILBOX_CACHE.stream().filter(mailbox -> {
            if(this.query.startsWith("@")) {
                String ownerName = mailbox.getOwner().map(GameProfile::getName).orElse("Unknown");
                return StringUtils.containsIgnoreCase(ownerName, this.query.substring(1));
            }
            String mailboxName = mailbox.getCustomName().orElse("Mailbox");
            return StringUtils.containsIgnoreCase(mailboxName, this.query);
        }).sorted(Comparator.comparing((IMailbox mailbox) -> {
            return mailbox.getOwner().map(GameProfile::getName).orElse("Unknown");
        }).thenComparing(mailbox -> {
            return mailbox.getCustomName().orElse("Mailbox");
        })).toList();
        this.mailboxes.clear();
        this.mailboxes.addAll(filteredMailboxes);
    }

    /**
     * Gets the player info for the given game profile. Player info is first retrieved from the
     * current connection cache, since it may already exist. If the game profile is of a player that
     * is offline, a new player info is created and moved into a special cache. The connection cache
     * is prioritised first.
     *
     * @param profile the game profile of the player
     * @return a non-null player info
     */
    private PlayerInfo getPlayerInfo(GameProfile profile)
    {
        if(this.minecraft != null)
        {
            ClientPacketListener listener = this.minecraft.getConnection();
            if(listener != null)
            {
                PlayerInfo info = listener.getPlayerInfo(profile.getId());
                if(info != null)
                {
                    return info;
                }
            }
        }
        return PLAYER_INFO_CACHE.computeIfAbsent(profile.getId(), uuid -> {
            ClientboundPlayerInfoPacket.PlayerUpdate update = new ClientboundPlayerInfoPacket.PlayerUpdate(profile, 0, GameType.SURVIVAL, null, null);
            return new PlayerInfo(update, this.minecraft.getServiceSignatureValidator(), false);
        });
    }

    /**
     * Resets the text in the message edit box
     */
    public void clearMessage()
    {
        this.messageEditBox.setValue("");
    }

    /**
     * Updates the mailbox cache from the server
     * @param mailboxes the list of new mailboxes
     */
    public static void updateMailboxes(Collection<? extends IMailbox> mailboxes)
    {
        MAILBOX_CACHE.clear();
        MAILBOX_CACHE.addAll(mailboxes);
    }
}
