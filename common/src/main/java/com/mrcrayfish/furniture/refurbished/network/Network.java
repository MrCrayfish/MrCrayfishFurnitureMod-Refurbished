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
            .registerPlayMessage("sync_fluid", MessageSyncFluid.class, MessageSyncFluid::encode, MessageSyncFluid::decode, MessageSyncFluid::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("flip_animation", MessageFlipAnimation.class, MessageFlipAnimation::encode, MessageFlipAnimation::decode, MessageFlipAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("set_name", MessageSetName.class, MessageSetName::encode, MessageSetName::decode, MessageSetName::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("update_mailboxes", MessageUpdateMailboxes.class, MessageUpdateMailboxes::encode, MessageUpdateMailboxes::decode, MessageUpdateMailboxes::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("send_package", MessageSendPackage.class, MessageSendPackage::encode, MessageSendPackage::decode, MessageSendPackage::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("clear_message", MessageClearMessage.class, MessageClearMessage::encode, MessageClearMessage::decode, MessageClearMessage::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("doorbell_notification", MessageDoorbellNotification.class, MessageDoorbellNotification::encode, MessageDoorbellNotification::decode, MessageDoorbellNotification::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("sync_link", MessageSyncLink.class, MessageSyncLink::encode, MessageSyncLink::decode, MessageSyncLink::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("delete_link", MessageDeleteLink.class, MessageDeleteLink::encode, MessageDeleteLink::decode, MessageDeleteLink::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("toggle_power", MessageTogglePower.class, MessageTogglePower::encode, MessageTogglePower::decode, MessageTogglePower::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("television_channel", MessageTelevisionChannel.class, MessageTelevisionChannel::encode, MessageTelevisionChannel::decode, MessageTelevisionChannel::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("computer_state", MessageComputerState.class, MessageComputerState::encode, MessageComputerState::decode, MessageComputerState::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("computer_open_program", MessageComputerOpenProgram.class, MessageComputerOpenProgram::encode, MessageComputerOpenProgram::decode, MessageComputerOpenProgram::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("paddle_ball_action", MessagePaddleBall.Action.class, MessagePaddleBall.Action::encode, MessagePaddleBall.Action::decode, MessagePaddleBall.Action::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("paddle_ball_paddle_position", MessagePaddleBall.PaddlePosition.class, MessagePaddleBall.PaddlePosition::encode, MessagePaddleBall.PaddlePosition::decode, MessagePaddleBall.PaddlePosition::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_ball_update", MessagePaddleBall.BallUpdate.class, MessagePaddleBall.BallUpdate::encode, MessagePaddleBall.BallUpdate::decode, MessagePaddleBall.BallUpdate::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_opponent_name", MessagePaddleBall.OpponentName.class, MessagePaddleBall.OpponentName::encode, MessagePaddleBall.OpponentName::decode, MessagePaddleBall.OpponentName::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("paddle_ball_event", MessagePaddleBall.Event.class, MessagePaddleBall.Event::encode, MessagePaddleBall.Event::decode, MessagePaddleBall.Event::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("home_control_toggle", MessageHomeControl.Toggle.class, MessageHomeControl.Toggle::encode, MessageHomeControl.Toggle::decode, MessageHomeControl.Toggle::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("home_control_update_all", MessageHomeControl.UpdateAll.class, MessageHomeControl.UpdateAll::encode, MessageHomeControl.UpdateAll::decode, MessageHomeControl.UpdateAll::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("update_painting", MessageUpdatePainting.class, MessageUpdatePainting::encode, MessageUpdatePainting::decode, MessageUpdatePainting::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("tool_animation", MessageToolAnimation.class, MessageToolAnimation::encode, MessageToolAnimation::decode, MessageToolAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("flash_item", MessageFlushItem.class, MessageFlushItem::encode, MessageFlushItem::decode, MessageFlushItem::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("water_tap_animation", MessageWaterTapAnimation.class, MessageWaterTapAnimation::encode, MessageWaterTapAnimation::decode, MessageWaterTapAnimation::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("workbench_item_counts", MessageWorkbench.ItemCounts.class, MessageWorkbench.ItemCounts::encode, MessageWorkbench.ItemCounts::decode, MessageWorkbench.ItemCounts::handle, PacketFlow.CLIENTBOUND)
            .registerPlayMessage("workbench_select_recipe", MessageWorkbench.SelectRecipe.class, MessageWorkbench.SelectRecipe::encode, MessageWorkbench.SelectRecipe::decode, MessageWorkbench.SelectRecipe::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("workbench_search_neighbours", MessageWorkbench.SearchNeighbours.class, MessageWorkbench.SearchNeighbours::encode, MessageWorkbench.SearchNeighbours::decode, MessageWorkbench.SearchNeighbours::handle, PacketFlow.SERVERBOUND)
            .registerPlayMessage("name_mailbox", MessageNameMailbox.class, MessageNameMailbox::encode, MessageNameMailbox::decode, MessageNameMailbox::handle, PacketFlow.CLIENTBOUND)
            .build();
    }

    public static FrameworkNetwork getPlay()
    {
        return play;
    }
}
