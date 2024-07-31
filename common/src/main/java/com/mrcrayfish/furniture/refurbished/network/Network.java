package com.mrcrayfish.furniture.refurbished.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.network.message.*;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Network
{
    public static FrameworkNetwork play;

    public static void init() {
        Network.play = FrameworkAPI.createNetworkBuilder(new ResourceLocation(Constants.MOD_ID, "play"), 1)
            .registerPlayMessage("sync_fluid", MessageSyncFluid.class, MessageSyncFluid.STREAM_CODEC, MessageSyncFluid::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("flip_animation", MessageFlipAnimation.class, MessageFlipAnimation.STREAM_CODEC, MessageFlipAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("set_name", MessageSetName.class, MessageSetName.STREAM_CODEC, MessageSetName::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("update_mailboxes", MessageUpdateMailboxes.class, MessageUpdateMailboxes.STREAM_CODEC, MessageUpdateMailboxes::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("send_package", MessageSendPackage.class, MessageSendPackage.STREAM_CODEC, MessageSendPackage::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("clear_message", MessageClearMessage.class, MessageClearMessage.STREAM_CODEC, MessageClearMessage::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("doorbell_notification", MessageDoorbellNotification.class, MessageDoorbellNotification.STREAM_CODEC, MessageDoorbellNotification::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("sync_link", MessageSyncLink.class, MessageSyncLink.STREAM_CODEC, MessageSyncLink::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("delete_link", MessageDeleteLink.class, MessageDeleteLink.STREAM_CODEC, MessageDeleteLink::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("toggle_power", MessageTogglePower.class, MessageTogglePower.STREAM_CODEC, MessageTogglePower::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("television_channel", MessageTelevisionChannel.class, MessageTelevisionChannel.STREAM_CODEC, MessageTelevisionChannel::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("computer_state", MessageComputerState.class, MessageComputerState.STREAM_CODEC, MessageComputerState::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("computer_open_program", MessageComputerOpenProgram.class, MessageComputerOpenProgram.STREAM_CODEC, MessageComputerOpenProgram::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("paddle_ball_action", MessagePaddleBall.Action.class, MessagePaddleBall.Action.STREAM_CODEC, MessagePaddleBall.Action::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("paddle_ball_paddle_position", MessagePaddleBall.PaddlePosition.class, MessagePaddleBall.PaddlePosition.STREAM_CODEC, MessagePaddleBall.PaddlePosition::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_ball_update", MessagePaddleBall.BallUpdate.class, MessagePaddleBall.BallUpdate.STREAM_CODEC, MessagePaddleBall.BallUpdate::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_opponent_name", MessagePaddleBall.OpponentName.class, MessagePaddleBall.OpponentName.STREAM_CODEC, MessagePaddleBall.OpponentName::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_event", MessagePaddleBall.Event.class, MessagePaddleBall.Event.STREAM_CODEC, MessagePaddleBall.Event::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("home_control_toggle", MessageHomeControl.Toggle.class, MessageHomeControl.Toggle.STREAM_CODEC, MessageHomeControl.Toggle::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("home_control_update_all", MessageHomeControl.UpdateAll.class, MessageHomeControl.UpdateAll.STREAM_CODEC, MessageHomeControl.UpdateAll::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("update_painting", MessageUpdatePainting.class, MessageUpdatePainting.STREAM_CODEC, MessageUpdatePainting::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("tool_animation", MessageToolAnimation.class, MessageToolAnimation.STREAM_CODEC, MessageToolAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("flash_item", MessageFlushItem.class, MessageFlushItem.STREAM_CODEC, MessageFlushItem::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("water_tap_animation", MessageWaterTapAnimation.class, MessageWaterTapAnimation.STREAM_CODEC, MessageWaterTapAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("workbench_item_counts", MessageWorkbench.ItemCounts.class, MessageWorkbench.ItemCounts.STREAM_CODEC, MessageWorkbench.ItemCounts::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("workbench_select_recipe", MessageWorkbench.SelectRecipe.class, MessageWorkbench.SelectRecipe.STREAM_CODEC, MessageWorkbench.SelectRecipe::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("workbench_search_neighbours", MessageWorkbench.SearchNeighbours.class, MessageWorkbench.SearchNeighbours.STREAM_CODEC, MessageWorkbench.SearchNeighbours::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("name_mailbox", MessageNameMailbox.class, MessageNameMailbox.STREAM_CODEC, MessageNameMailbox::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("withdraw_experience", MessageWithdrawExperience.class, MessageWithdrawExperience.STREAM_CODEC, MessageWithdrawExperience::handle, PacketFlow.SERVERBOUND)
            .build();
    }

    public static FrameworkNetwork getPlay()
    {
        return play;
    }
}
