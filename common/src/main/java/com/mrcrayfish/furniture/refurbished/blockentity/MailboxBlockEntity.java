package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.block.MailboxBlock;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.mail.Mailbox;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MailboxBlockEntity extends RowedStorageBlockEntity implements INameable
{
    protected UUID uuid = UUID.randomUUID();
    protected WeakReference<Mailbox> mailboxRef;

    public MailboxBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.MAIL_BOX.get(), pos, state, Config.SERVER.mailing.mailboxInventoryRows.get());
    }

    public UUID getId()
    {
        return this.uuid;
    }

    public void regenerateId()
    {
        this.uuid = UUID.randomUUID();
        this.setChanged();
    }

    @Override
    public void setName(@Nullable ServerPlayer player, String name)
    {
        if(player == null)
            return;

        DeliveryService.get(player.server).ifPresent(service -> {
            if(!service.renameMailbox(player, player.getLevel(), this.worldPosition, name)) {
                player.sendSystemMessage(Utils.translation("gui", "rename_mailbox_failed"));
            }
        });
    }

    public boolean deliverItem(ItemStack mail)
    {
        for(int i = 0; i < this.getContainerSize(); i++)
        {
            ItemStack stack = this.getItem(i);
            if(stack.isEmpty())
            {
                this.setItem(i, mail);
                this.setUnchecked();
                return true;
            }
            if(stack.getCount() == stack.getMaxStackSize())
            {
                continue;
            }
            if(ItemStack.isSameItemSameTags(stack, mail) && stack.getCount() + mail.getCount() <= stack.getMaxStackSize())
            {
                stack.grow(mail.getCount());
                this.setChanged();
                this.setUnchecked();
                return true;
            }
        }
        return false;
    }

    public void setUnchecked()
    {
        this.level.setBlock(this.worldPosition, this.getBlockState().setValue(MailboxBlock.ENABLED, true), Block.UPDATE_ALL);
    }

    public Mailbox getMailbox()
    {
        if(this.mailboxRef != null)
        {
            Mailbox mailbox = this.mailboxRef.get();
            if(mailbox != null && !mailbox.removed().booleanValue())
            {
                return mailbox;
            }
            this.mailboxRef = null;
        }

        if(this.level instanceof ServerLevel serverLevel)
        {
            Optional<DeliveryService> optional = DeliveryService.get(serverLevel.getServer());
            if(optional.isPresent())
            {
                DeliveryService service = optional.get();
                Mailbox mailbox = service.getOrCreateMailBox(this);
                this.mailboxRef = new WeakReference<>(mailbox);
                return mailbox;
            }
        }
        return null;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "mailbox");
    }

    @Override
    public Component getDisplayName()
    {
        Mailbox mailbox = this.getMailbox();
        if(mailbox != null)
        {
            String customName = mailbox.customName().getValue();
            if(customName != null && !customName.isBlank())
            {
                return Component.literal(customName);
            }
        }
        return super.getDisplayName();
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("UUID", Tag.TAG_INT_ARRAY))
        {
            this.uuid = tag.getUUID("UUID");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putUUID("UUID", this.uuid);
    }
}
