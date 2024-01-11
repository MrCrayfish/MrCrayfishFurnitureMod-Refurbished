package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.Components;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class MailboxItem extends BlockItem
{
    public MailboxItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state)
    {
        if(context.getPlayer() instanceof ServerPlayer player)
        {
            DeliveryService service = DeliveryService.get(player.server).orElse(null);
            if(service != null)
            {
                if(!service.canCreateMailbox(player))
                {
                    int maxCount = Config.SERVER.mailing.maxMailboxesPerPlayer.get();
                    player.sendSystemMessage(Components.GUI_MAIL_BOX_LIMIT.apply(maxCount), true);
                    return false;
                }
                return super.canPlace(context, state);
            }
        }
        return super.canPlace(context, state);
    }
}
