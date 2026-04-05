package com.mrcrayfish.furniture.refurbished.client.gui.screen;

import com.mojang.authlib.GameProfile;
import com.mrcrayfish.furniture.refurbished.client.gui.widget.IconButton;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.inventory.PostBoxMenu;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryResult;
import com.mrcrayfish.furniture.refurbished.mail.IMailbox;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageSendPackage;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.players.NameAndId;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.*;

/**
 * Author: MrCrayfish
 */
public class PostBoxScreen extends AbstractContainerScreen<PostBoxMenu>
{
    private static final Component MAILBOXES_LABEL = Utils.translation("gui", "mailboxes");
    private static final MutableComponent DEFAULT_MAILBOX_NAME = Utils.translation("gui", "default_mailbox_name");
    private static final MutableComponent UNKNOWN_MAILBOX_OWNER = Utils.translation("gui", "unknown_mailbox_owner");
    private static final Identifier POST_BOX_TEXTURE = Utils.id("textures/gui/container/post_box.png");
    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("container/villager/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/villager/scroller_disabled");
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
    private static final int MAX_RESPONSE_DISPLAY_TIME = 100;

    protected List<IMailbox> mailboxes = new ArrayList<>();
    protected IMailbox selected;
    protected EditBox searchEditBox;
    protected String query = "";
    protected MultiLineEditBox messageEditBox;
    protected Button sendButton;
    protected String message = "";
    protected int scroll;
    protected int clickedY = -1;
    protected @Nullable String responseTranslationKey;
    protected boolean responseSuccess;
    protected int responseTimer;

    public PostBoxScreen(PostBoxMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, Component.empty(), 283, 172);
        this.inventoryLabelX = 113;
        this.inventoryLabelY = this.imageHeight - 93;
        this.updateSearchFilter();
    }

    @Override
    protected void init()
    {
        super.init();

        this.addRenderableWidget(this.searchEditBox = new EditBox(this.font, this.leftPos + 8, this.topPos + 18, 92, 12, Utils.translation("gui", "search_mailboxes")));
        this.searchEditBox.setHint(Utils.translation("gui", "search"));
        this.searchEditBox.setResponder(s -> {
            this.query = s;
            this.updateSearchFilter();
            this.scroll(0);
        });
        if(!this.query.isBlank())
        {
            this.searchEditBox.setValue(this.query);
        }

        this.messageEditBox = MultiLineEditBox.builder()
                .setShowBackground(false)
                .setShowDecorations(false)
                .setPlaceholder(Utils.translation("gui", "enter_message"))
                .setX(this.leftPos + 118)
                .setY(this.topPos + 13)
                .build(this.font, 116, 54, Utils.translation("gui", "package_message"));
        this.messageEditBox.setCharacterLimit(1024);
        this.messageEditBox.setLineLimit(5);
        this.addRenderableWidget(this.messageEditBox);
        this.messageEditBox.setValueListener(s -> this.message = s);
        if(!this.message.isBlank())
        {
            this.messageEditBox.setValue(this.message);
        }

        this.addRenderableWidget(this.sendButton = new IconButton(this.leftPos + 284, this.topPos + 22, 20, 0, 18, 18, CommonComponents.EMPTY, btn -> {
            if(this.selected != null) {
                Network.getPlay().sendToServer(new MessageSendPackage(this.selected.getId(), this.message));
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
            }
        }));
        this.sendButton.setTooltip(Tooltip.create(Utils.translation("gui", "send")));
    }

    @Override
    protected void containerTick()
    {
        if(this.responseTranslationKey != null)
        {
            this.responseTimer++;
            if(this.responseTimer == MAX_RESPONSE_DISPLAY_TIME)
            {
                this.responseTranslationKey = null;
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        this.sendButton.active = this.selected != null && !this.menu.getContainer().isEmpty();
        this.searchEditBox.setTextColor(this.searchEditBox.getValue().isEmpty() && !this.searchEditBox.isFocused() ? 0xFF707070 : 0xFFE0E0E0);
        super.extractRenderState(extractor, mouseX, mouseY, partialTick);
        this.extractTooltip(extractor, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor extractor, int mouseX, int mouseY)
    {
        super.extractLabels(extractor, mouseX, mouseY);
        extractor.text(this.font, MAILBOXES_LABEL, this.titleLabelX, this.titleLabelY, 0xFFE0E0E0, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick)
    {
        super.extractBackground(extractor, mouseX, mouseY, partialTick);

        extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth + 25, this.imageHeight, 512, 256);

        // Draw mailboxes list
        extractor.enableScissor(this.leftPos + CONTAINER_LEFT, this.topPos + CONTAINER_TOP, this.leftPos + CONTAINER_LEFT + CONTAINER_WIDTH, this.topPos + CONTAINER_TOP + CONTAINER_HEIGHT);
        int scroll = this.clampScroll(this.scroll + this.getDeltaScroll(mouseY));
        int startIndex = Mth.clamp(scroll / MAILBOX_ENTRY_HEIGHT, 0, Math.max(0, this.mailboxes.size() - MAX_VISIBLE_ITEMS));
        int maxItems = Math.min(MAX_VISIBLE_ITEMS, this.mailboxes.size());
        for(int i = 0; i < maxItems; i++)
        {
            int entryIndex = startIndex + i;
            int entryX = this.leftPos + CONTAINER_LEFT;
            int entryY = this.topPos + CONTAINER_TOP + entryIndex * MAILBOX_ENTRY_HEIGHT - scroll;
            IMailbox mailbox = this.mailboxes.get(entryIndex);
            boolean selected = this.selected == mailbox;

            // Draw the background of the mailbox entry
            extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, entryX, entryY, 0, selected ? 172 : 186, MAILBOX_ENTRY_WIDTH, MAILBOX_ENTRY_HEIGHT, 512, 256);

            // Draw the face of the player's skin
            Optional<NameAndId> optional = mailbox.getOwner();
            if(optional.isPresent())
            {
                PlayerInfo info = this.getPlayerInfo(optional.get());
                PlayerFaceExtractor.extractRenderState(extractor, info.getSkin(), entryX + 3, entryY + 3, 8);
            }

            // Draw the name of the mailbox
            Component mailboxName = mailbox.getCustomName()
                .filter(s -> !s.isBlank())
                .map(Component::literal)
                .orElse(DEFAULT_MAILBOX_NAME);
            extractor.text(this.font, mailboxName, entryX + 15, entryY + 3, selected ? 0xFFFFFF55 : 0xFFFFFFFF);

            // Create a tooltip of the owners username if the cursor hovers the face image
            if(this.isHovering((entryX - this.leftPos) + 3, (entryY - this.topPos) + 3, 8, 8, mouseX, mouseY))
            {
                Component ownerName = mailbox.getOwner()
                    .map(NameAndId::name)
                    .map(Component::literal)
                    .orElse(UNKNOWN_MAILBOX_OWNER);
                extractor.setTooltipForNextFrame(ownerName, mouseX, mouseY);
            }
        }
        extractor.disableScissor();

        // Draw scroll bar
        extractor.blitSprite(RenderPipelines.GUI_TEXTURED, this.canScroll() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE, this.leftPos + CONTAINER_LEFT + CONTAINER_WIDTH + 1, this.topPos + CONTAINER_TOP + this.getScrollBarOffset(mouseY), SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);

        // Draw icons in item slots
        for(int j = 0; j < 3; j++)
        {
            for(int i = 0; i < 2; i++)
            {
                if(this.menu.getContainer().getItem(j * 2 + i).isEmpty())
                {
                    extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, this.leftPos + 235 + i * 18, this.topPos + 14 + j * 18, 85, 172, 16, 16, 512, 256);
                }
            }
        }

        // Draw response message
        if(this.responseTranslationKey != null && this.responseTimer < MAX_RESPONSE_DISPLAY_TIME)
        {
            Component responseMessage = Component.translatable(this.responseTranslationKey);
            int contentWidth = this.font.width(responseMessage) + 4;
            int responseToastWidth = 4 + contentWidth + 3;
            int responseToastLeft = this.leftPos + this.imageWidth / 2 - responseToastWidth / 2;
            int responseToastTop = this.topPos - 22;
            extractor.enableScissor(responseToastLeft, this.topPos - 22, responseToastLeft + responseToastWidth, this.topPos);
            extractor.pose().pushMatrix();
            if(this.responseTimer < 5)
            {
                float frameTime = this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
                extractor.pose().translate(0, (5 - (this.responseTimer + frameTime)) * 5);
            }
            else if(MAX_RESPONSE_DISPLAY_TIME - this.responseTimer < 5)
            {
                float frameTime = this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
                float offset = 5 - (MAX_RESPONSE_DISPLAY_TIME - (this.responseTimer + frameTime));
                extractor.pose().translate(0, offset * 5);
            }
            int toastU = this.responseSuccess ? 8 : 0;
            extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, responseToastLeft, responseToastTop, toastU, 200, 4, 18, 512, 256);
            extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, responseToastLeft + 4, responseToastTop, toastU + 4, 200, contentWidth, 18, 1, 18, 512, 256);
            extractor.blit(RenderPipelines.GUI_TEXTURED, POST_BOX_TEXTURE, responseToastLeft + 4 + contentWidth, responseToastTop, toastU + 5, 200, 3, 18, 512, 256);
            extractor.text(this.font, responseMessage, responseToastLeft + 6, responseToastTop + 5, 0xFFFFFFFF);
            extractor.pose().popMatrix();
            extractor.disableScissor();
        }

        if(this.isHovering(91, 5, 10, 10, mouseX, mouseY))
        {
            extractor.setTooltipForNextFrame(ScreenHelper.createMultilineTooltip(List.of(
                Utils.translation("gui", "how_to").withStyle(ChatFormatting.GOLD),
                Utils.translation("gui", "post_box_info"))
            ).toCharSequence(this.minecraft), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
    {
        if(event.button() == GLFW.GLFW_MOUSE_BUTTON_1)
        {
            this.setFocused(null);
            if(this.isHovering(CONTAINER_LEFT, CONTAINER_TOP, CONTAINER_WIDTH, CONTAINER_HEIGHT, event.x(), event.y()))
            {
                int relativeMouseY = (int) (event.y() - this.topPos - CONTAINER_TOP);
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
            if(this.isHovering(CONTAINER_LEFT + CONTAINER_WIDTH + 1, CONTAINER_TOP + this.getScrollBarOffset((int) event.y()), SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT, event.x(), event.y()))
            {
                this.clickedY = (int) event.y();
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event)
    {
        if(event.button() == GLFW.GLFW_MOUSE_BUTTON_1)
        {
            if(this.clickedY >= 0)
            {
                this.scroll(this.getDeltaScroll((int) event.y()));
                this.clickedY = -1;
                return true;
            }
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean keyPressed(KeyEvent event)
    {
        if(this.searchEditBox.isFocused())
        {
            return this.searchEditBox.keyPressed(event);
        }
        if(this.messageEditBox.isFocused())
        {
            return this.messageEditBox.keyPressed(event);
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY)
    {
        if(this.isHovering(CONTAINER_LEFT, CONTAINER_TOP, CONTAINER_WIDTH, CONTAINER_HEIGHT, mouseX, mouseY))
        {
            this.scroll((int) (-SCROLL_SPEED * deltaY));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
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
        List<IMailbox> filteredMailboxes = this.menu.getMailboxes().stream().filter(mailbox -> {
            if(this.query.startsWith("@")) {
                String ownerName = mailbox.getOwner().map(NameAndId::name).orElse("Unknown");
                return StringUtils.containsIgnoreCase(ownerName, this.query.substring(1));
            }
            String mailboxName = mailbox.getCustomName().orElse("Mailbox");
            return StringUtils.containsIgnoreCase(mailboxName, this.query);
        }).sorted(Comparator.comparing((IMailbox mailbox) -> {
            return mailbox.getOwner().map(NameAndId::name).orElse("Unknown");
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
     * @param nameAndId the game profile of the player
     * @return a non-null player info
     */
    private PlayerInfo getPlayerInfo(NameAndId nameAndId)
    {
        if(this.minecraft != null)
        {
            ClientPacketListener listener = this.minecraft.getConnection();
            if(listener != null)
            {
                PlayerInfo info = listener.getPlayerInfo(nameAndId.id());
                if(info != null)
                {
                    return info;
                }
            }
        }
        return PLAYER_INFO_CACHE.computeIfAbsent(nameAndId.id(), uuid -> new PlayerInfo(new GameProfile(nameAndId.id(), nameAndId.name()), false));
    }

    /**
     * Shows a response message if received one from the server. This is called when a mail queue is
     * full or the selected mailbox is in an undeliverable dimension.
     *
     * @param result the result of the delivery
     */
    public void showResponse(DeliveryResult result)
    {
        result.message().ifPresent(key -> {
            this.responseSuccess = result.success();
            this.responseTranslationKey = key;
            this.responseTimer = 0;
        });
    }

    /**
     * Resets the text in the message edit box
     */
    public void clearMessage()
    {
        this.messageEditBox.setValue("");
    }
}
