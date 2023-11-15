package com.mrcrayfish.furniture.refurbished.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.MessageDirection;
import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.network.message.*;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Network
{
    public static final FrameworkNetwork PLAY = FrameworkAPI
            .createNetworkBuilder(new ResourceLocation(Constants.MOD_ID, "play"), 1)
            .registerPlayMessage(MessageSyncFluid.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageFlipAnimation.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageSetName.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessageUpdateMailboxes.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageSendPackage.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessageClearMessage.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageDoorbellNotification.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageSyncLink.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageDeleteLink.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessageTogglePower.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessageTelevisionChannel.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessageComputerState.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessagePaddleBall.Action.class, MessageDirection.PLAY_SERVER_BOUND)
            .registerPlayMessage(MessagePaddleBall.PaddlePosition.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessagePaddleBall.BallUpdate.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessagePaddleBall.OpponentName.class, MessageDirection.PLAY_CLIENT_BOUND)
            .registerPlayMessage(MessagePaddleBall.Event.class, MessageDirection.PLAY_CLIENT_BOUND)
            .build();

    public static void init() {}

    public static FrameworkNetwork getPlay()
    {
        return PLAY;
    }
}
